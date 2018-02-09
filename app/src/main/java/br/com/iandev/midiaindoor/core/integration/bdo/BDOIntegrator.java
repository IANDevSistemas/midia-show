package br.com.iandev.midiaindoor.core.integration.bdo;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import br.com.iandev.midiaindoor.core.TimeCounter;
import br.com.iandev.midiaindoor.util.CryptUtil;
import br.com.iandev.midiaindoor.util.Downloader;
import br.com.iandev.midiaindoor.util.JSONUtil;

/**
 * Created by Lucas on 07/01/2017.
 * Changes:
 * Date        Responsible     Change
 * 07/01/2017  Lucas
 */

public class BDOIntegrator {
    private static final String TAG = Manager.class.getSimpleName();
    private static final String ALTERNATIVE_FQDN = "server1iandev.no-ip.org:8030";
    private static final String USR = "dispositivomidia";
    private static final String PWD = "!c0e634d76b46faf1f1d40589788864e3f8823def";

    // Class environment
    private static List<String> mainServersList = new ArrayList<>();
    private static String siteFQDN = "";
    private static String token = "";
    private static TimeCounter timeCounter = null;

    private static String idDispositivo = "";
    private static String dispositivo = "";
    private static String codigo = "";
    private static String idPessoa = "";
    private static String senha = "";
    private static String versao = "";
    protected String idConteudo = "";
    protected String conteudo = "";

    public static void setSiteFQDN(String siteFQDN) {
        BDOIntegrator.siteFQDN = siteFQDN;
        BDOIntegrator.mainServersList = new ArrayList<>();
    }

    public static void error() {
        try {
            mainServersList.remove(0);
        } catch (Exception ex) {
            // Silence is golden
        }
    }

    public static void setTimeCounter(TimeCounter timeCounter) {
        BDOIntegrator.timeCounter = timeCounter;
    }

    public static void setIdDispositivo(String idDispositivo) {
        BDOIntegrator.idDispositivo = idDispositivo;
    }

    public static void setDispositivo(String dispositivo) {
        BDOIntegrator.dispositivo = dispositivo;
    }

    public static void setCodigo(String codigo) {
        BDOIntegrator.codigo = codigo;
    }

    public static void setIdPessoa(String idPessoa) {
        BDOIntegrator.idPessoa = idPessoa;
    }

//    private static Date dataUltimaAutenticacao = null;
//    private static Long tempoExpiracaoToken= null;
//    private static String token = null;

    public static void setSenha(String senha) {
        BDOIntegrator.senha = senha;
    }

    public static void setVersao(String versao) {
        BDOIntegrator.versao = versao;
    }

    public static String getMainServerFQDN() throws ExecutionException, InterruptedException, MalformedURLException {
        if (mainServersList == null || mainServersList.size() <= 0) {
            OutputStream os = new ByteArrayOutputStream();
            doRequest(os, new URL(siteFQDN));
            mainServersList = JSONUtil.parseStringArray(os.toString());
            mainServersList.add(ALTERNATIVE_FQDN);
        }
        return mainServersList.get(0);
    }

    protected static void doRequest(final OutputStream os, final URL url) throws ExecutionException, InterruptedException {
        Downloader downloader = new Downloader();
        downloader.setURL(url);
        downloader.setOutputStream(os);
        downloader.execute();
        downloader.get();
    }

    protected static void doSiteRequest(final OutputStream os) throws InterruptedException, ExecutionException, MalformedURLException {
        try {
            doRequest(os, new URL(getMainServerFQDN()));
        } catch (Exception ex) {
            mainServersList.remove(0);
        }
    }

    protected URL getURL(String urlString, TreeMap<String, String> query) throws UnsupportedEncodingException, MalformedURLException {
        StringBuilder sbQuery = new StringBuilder(urlString);
        StringBuilder sbHash = new StringBuilder("#");

        for (Map.Entry<String, String> argument : query.entrySet()) {
            sbHash.append(argument.getValue()).append("#");
        }

        if (BDOIntegrator.timeCounter != null) {
            sbHash.append(BDOIntegrator.timeCounter.getDate().getTime());
        }
        sbHash.append("#");

        query.put("hash", CryptUtil.getHexHash(sbHash.toString(), CryptUtil.ALGORITH_SHA));

        boolean first = true;
        for (Map.Entry<String, String> argument : query.entrySet()) {
            if (first) {
                sbQuery.append("?");
                first = false;
            } else {
                sbQuery.append("&");
            }
            sbQuery.append(URLEncoder.encode(argument.getKey(), "UTF-8"));
            sbQuery.append("=");
            sbQuery.append(URLEncoder.encode(argument.getValue(), "UTF-8"));
        }
        return new URL(sbQuery.toString());
    }

    protected TreeMap<String, String> getBaseQuery(final String apelido) {
        return getBaseQuery(apelido, "execAndGetObject");
    }

    //TODO: rever usos para ver se estão respeitando a ordem dos parametros
    protected TreeMap<String, String> getBaseQuery(final String apelido, final String action) {
        TreeMap<String, String> query = new TreeMap<String, String>() {{
            put("usr", BDOIntegrator.USR);
            put("pwd", BDOIntegrator.PWD);
            put("action", action);
            put("apelido", apelido);
            put("iddispositivo", BDOIntegrator.idDispositivo);
            put("dispositivo", BDOIntegrator.dispositivo);
            put("codigo", BDOIntegrator.codigo);
            put("idpessoa", BDOIntegrator.idPessoa);
            put("idconteudo", BDOIntegrator.this.idConteudo);
            put("conteudo", BDOIntegrator.this.conteudo);
            put("senha", CryptUtil.getHexHash(BDOIntegrator.senha, CryptUtil.ALGORITH_SHA));
            put("dataultimaautenticacao", "");
            put("tempoexpiracaotoken", "");
            put("token", BDOIntegrator.token);
            put("versao", versao);
        }};

        return query;
    }
}
