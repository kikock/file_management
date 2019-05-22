package com.kiko.file.manager.error;

public class FMInitializationException extends FileManagerException {

    public FMInitializationException(String message) {
        super(message);
    }

    public FMInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}