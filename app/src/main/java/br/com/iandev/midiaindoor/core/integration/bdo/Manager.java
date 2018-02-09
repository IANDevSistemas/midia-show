package br.com.iandev.midiaindoor.core.integration.bdo;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.iandev.midiaindoor.core.integration.ManagerException;

/**
 * Created by Lucas on 08/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 08/12/2016  Lucas
 */

public class Manager extends Authenticator {
    private static final String TAG = Manager.class.getSimpleName();

    private String accessURL;
    private File packageFile;

    public void setConteudo(String conteudo) {
        super.conteudo = conteudo;
    }

    public void setIdConteudo(String idConteudo) {
        super.idConteudo = idConteudo;
    }


    public void setAccessURL(String accessURL) {
        this.accessURL = accessURL;
    }

    public void setPackageFile(File packageFile) {
        this.packageFile = packageFile;
    }

    private URL getURLForDownload() throws MalformedURLException, UnsupportedEncodingException {
        return super.getURL(String.format("%s/CntServlet", accessURL), super.getBaseQuery("MIDIAINDOOR-servidor-conteudo", "execAndFileDownload"));
    }

    private URL getInformationURL() throws MalformedURLException, UnsupportedEncodingException {
        return super.getURL(String.format("%s/CntServlet", accessURL), super.getBaseQuery("MIDIAINDOOR-servidor-conteudo"));
    }

    public void doDownload() throws ManagerException {
        try {
            OutputStream fos = new FileOutputStream(packageFile);
            doRequest(fos, this.getURLForDownload());
        } catch (Exception ex) {
            throw new ManagerException(ex.getMessage());
        }
    }

    public JSONObject getInformation() throws ManagerException {
        try {
            final OutputStream bous = new ByteArrayOutputStream();
            doRequest(bous, getInformationURL());
            return new JSONObject(bous.toString());
        } catch (Exception ex) {
            throw new ManagerException(ex.getMessage());
        }
    }
}