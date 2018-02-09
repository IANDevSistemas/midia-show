package br.com.iandev.midiaindoor.wrap.facede;

import br.com.iandev.midiaindoor.core.AppException;

/**
 * Created by Lucas on 30/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 30/03/2017  Lucas
 */

public class ControllerException extends AppException {
    public ControllerException() {
        super();
    }

    public ControllerException(String message) {
        super(message);
    }
}
