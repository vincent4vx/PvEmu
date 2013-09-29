package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import jelly.Constants;
import jelly.Loggin;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {

    protected NioSocketAcceptor _acceptor = new NioSocketAcceptor();

    public MinaServer(int port, MinaIoHandler handler) throws IOException {
        _acceptor.getSessionConfig().setReadBufferSize(256);
        _acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, Constants.MAX_IDLE_TIME);
        _acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(
                Charset.forName("UTF-8"),
                "\000",
                "\n\000")));
        _acceptor.setHandler(handler);
        _acceptor.bind(new InetSocketAddress(port));
    }

    public NioSocketAcceptor getAcceptor() {
        return _acceptor;
    }

    public void stop() {
        try {
            for (IoSession session : _acceptor.getManagedSessions().values()) {
                if (session == null || session.isClosing()) {
                    continue;
                }
                session.close(true);
            }
           /* _acceptor.unbind();
            _acceptor.dispose(false);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
