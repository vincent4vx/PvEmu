package org.pvemu.jelly.utils;

import java.util.ArrayList;

public class PendingList<T> extends ArrayList<T> {

    public synchronized void push(T object) {
        add(object);
        notify();
    }

    public synchronized T pop() {
        T object = null;

        if (!isEmpty()) {
            object = get(0);
            remove(0);
        }

        return object;
    }

    public synchronized void waitForElements() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
    }
}
