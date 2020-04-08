package com.carryxyh.lifecycle;

import com.carryxyh.config.Config;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Endpoint
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public abstract class Endpoint implements Lifecycle {

    public enum State {
        INITIALIZED,
        STOPPED,
        STARTED,
        CLOSED
    }

    private volatile int state = 0;

    private static final AtomicIntegerFieldUpdater<Endpoint> STATE_UPDATER =
            AtomicIntegerFieldUpdater.newUpdater(Endpoint.class, "state");

    private static final int INIT = 0;

    private static final int STARTED = 1;

    private static final int STOPPED = 2;

    private static final int CLOSED = 3;

    public State state() {
        if (state == INIT) {
            return State.INITIALIZED;
        } else if (state == STARTED) {
            return State.STARTED;
        } else if (state == STOPPED) {
            return State.STOPPED;
        } else if (state == CLOSED) {
            return State.CLOSED;
        }
        return null;
    }

    /**
     * Returns <tt>true</tt> if the state is started.
     */
    public boolean started() {
        return state == STARTED;
    }

    /**
     * Returns <tt>true</tt> if the state is stopped.
     */
    public boolean stopped() {
        return state == STOPPED;
    }

    /**
     * Returns <tt>true</tt> if the state is closed.
     */
    public boolean closed() {
        return state == CLOSED;
    }

    public boolean stoppedOrClosed() {
        return state == STOPPED || state == CLOSED;
    }

    @Override
    public void init(Config config) throws Exception {
        if (state != INIT) {
            return;
        }

        doInit(config);
    }

    @Override
    public void start() throws Exception {
        if (moveToStarted()) {
            doStart();
        }
    }

    @Override
    public void stop() throws Exception {
        if (moveToStopped()) {
            doStop();
        }
    }

    @Override
    public void close() throws Exception {
        if (started()) {
            stop();
        }
        if (moveToClosed()) {
            doClose();
        }
    }

    public boolean moveToStarted() {
        return STATE_UPDATER.compareAndSet(this, INIT, STARTED) ||
                STATE_UPDATER.compareAndSet(this, STOPPED, STARTED);
    }

    public boolean moveToStopped() {
        return STATE_UPDATER.compareAndSet(this, STARTED, STOPPED);
    }

    public boolean moveToClosed() {
        return STATE_UPDATER.compareAndSet(this, STOPPED, CLOSED);
    }

    protected void doInit(Config config) throws Exception {
        // empty for default.
    }

    protected void doStart() throws Exception {
        // empty for default.
    }

    protected void doStop() throws Exception {
        // empty for default.
    }

    protected void doClose() throws Exception {
        // empty for default.
    }
}
