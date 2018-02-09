package br.com.iandev.midiaindoor.core.interfaces;

/**
 * Created by Lucas on 27/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 27/03/2017  Lucas
 */

public class UpdatebleException extends Exception {
    public UpdatebleException() {
        super();
    }

    public UpdatebleException(String message) {
        super(message);
    }
}