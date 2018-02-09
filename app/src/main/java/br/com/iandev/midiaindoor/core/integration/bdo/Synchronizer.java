package br.com.iandev.midiaindoor.core.integration.bdo;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import br.com.iandev.midiaindoor.core.integration.SynchronizerException;

/**
 * Created by Lucas on 21/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 21/12/2016  Lucas
 */

public class Synchronizer extends Authenticator {
    private URL getURLForSync() throws MalformedURLException, UnsupportedEncodingException, ExecutionException, InterruptedException {
        return super.getURL(String.format("http://%s/bdoserver2.7/CntServlet", getMainServerFQDN()), super.getBaseQuery("MIDIAINDOOR-servidor-programacao"));
    }

    public JSONObject doSync() throws SynchronizerException {
        try {
            OutputStream os = new ByteArrayOutputStream();
            doRequest(os, getURLForSync());
            return new JSONObject(os.toString());
        } catch (Exception ex) {
            throw new SynchronizerException(ex.getMessage());
        }
    }
}
