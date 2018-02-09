package br.com.iandev.midiaindoor.core;

/**
 * Created by Lucas on 21/12/2016.
 */

public abstract class AppException extends Exception {
    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }
}
