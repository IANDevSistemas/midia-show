package br.com.iandev.midiaindoor.core.interfaces;

import br.com.iandev.midiaindoor.core.AppException;

/**
 * Created by Lucas on 29/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 29/03/2017  Lucas
 */

public class PlayableException extends AppException {
    public PlayableException() {
        super();
    }

    public PlayableException(String message) {
        super(message);
    }
}
