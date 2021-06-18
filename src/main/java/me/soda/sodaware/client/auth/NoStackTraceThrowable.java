package me.soda.sodaware.client.auth;


public class NoStackTraceThrowable extends RuntimeException {

    public NoStackTraceThrowable(final String msg) {
        super(msg);
        this.setStackTrace(new StackTraceElement[0]);
    }

    @Override
    public String toString() {
        return "Fuck off";
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
