package com.earnix.webk.dom;

import java.io.IOException;

public class UncheckedIOException extends RuntimeException {
    public UncheckedIOException(IOException cause) {
        super(cause);
    }

    public IOException ioException() {
        return (IOException) getCause();
    }
}
