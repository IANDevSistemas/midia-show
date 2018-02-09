package br.com.iandev.midiaindoor.wrap.facede;

/**
 * Created by Lucas on 30/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 30/03/2017  Lucas
 */

public class NotFoundException extends ControllerException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
