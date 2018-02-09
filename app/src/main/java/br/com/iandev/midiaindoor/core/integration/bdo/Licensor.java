package br.com.iandev.midiaindoor.core.integration.bdo;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import br.com.iandev.midiaindoor.core.integration.LicencerException;

/**
 * Created by Lucas on 21/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 21/12/2016  Lucas
 */

public class Licensor extends Authenticator {
    private URL getURLForRegistry() throws UnsupportedEncodingException, MalformedURLException, ExecutionException, InterruptedException {
        return this.getURL(String.format("http://%s/bdoserver2.7/CntServlet", getMainServerFQDN()), super.getBaseQuery("MIDIAINDOOR-servidor-registro"));
    }

    public JSONObject doLicensing() throws LicencerException {
        try {
            OutputStream bous = new ByteArrayOutputStream();
            doRequest(bous, getURLForRegistry());
            return new JSONObject(bous.toString());
        } catch (Exception ex) {
            throw new LicencerException(ex.getMessage());
        }
    }
}