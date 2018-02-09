package br.com.iandev.midiaindoor.core.integration;

import br.com.iandev.midiaindoor.core.AppException;

/**
 * Created by Lucas on 21/12/2016.
 */

public class SynchronizerException extends AppException {
    public SynchronizerException() {
        super();
    }

    public SynchronizerException(String message) {
        super(message);
    }
}
