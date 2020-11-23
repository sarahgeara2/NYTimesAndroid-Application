package com.example.nytimesapplication.core.util;


public class NotConnectedException extends Exception {
    private static final long serialVersionUID = 1900926677490660714L;

    /**
     * Constructs a {@code NotConnectedException} with no specified detail
     * message.
     */
    public NotConnectedException() {
    }

    /**
     * Constructs a {@code TimeoutException} with the specified detail
     * message.
     *
     * @param message the detail message
     */
    public NotConnectedException(String message) {
        super(message);
    }
}
