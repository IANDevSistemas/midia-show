package br.com.iandev.midiaindoor.core.integration;

import br.com.iandev.midiaindoor.core.AppException;

/**
 * Created by Lucas on 07/01/2017.
 */

public class AuthenticatorException extends AppException {
    public AuthenticatorException() {
        super();
    }

    public AuthenticatorException(String message) {
        super(message);
    }
}
