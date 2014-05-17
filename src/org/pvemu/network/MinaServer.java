package org.pvemu.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.pvemu.common.Constants;
import org.pvemu.common.Loggin;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {

    protected NioSocketAcceptor acceptor = new NioSocketAcceptor();

    public MinaServer(int port, MinaIoHandler handler) throws IOException {
        acceptor.getSessionConfig().setReadBufferSize(256);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, Constants.MAX_IDLE_TIME);
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(
                Charset.forName("UTF-8"),
                "\000",
                "\n\000")));
        acceptor.setHandler(handler);
        acceptor.bind(new InetSocketAddress(port));
        acceptor.setCloseOnDeactivation(true);
    }

    public NioSocketAcceptor getAcceptor() {
        return acceptor;
    }

    public void stop() {
        try {
            for (IoSession session : acceptor.getManagedSessions().values()) {
                if (session == null || session.isClosing()) {
                    continue;
                }
                session.close(true).awaitUninterruptibly();
            }
            acceptor.unbind();
            acceptor.dispose(true);
        } catch (Exception e) {
            Loggin.error("Unable to stop the MinaServer", e);
        }
    }
}
