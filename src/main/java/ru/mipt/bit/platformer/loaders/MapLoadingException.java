package ru.mipt.bit.platformer.loaders;

public class MapLoadingException extends Exception {
    public MapLoadingException(String message) {
        super(message);
    }

    public MapLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
