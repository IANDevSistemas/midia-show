/*
 * Created on 11/11/2004 16:06:35
 */
package br.com.iandev.midiaindoor.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Douglas Granzotto
 * @version 1.0
 */
public class CryptUtil {

    public static final int ALGORITH_SHA = 0;
    public static final int ALGORITH_MD5 = 1;
    public static final String[] ALGORITHM_LIST = {"SHA", "MD5"};

    public static byte[] getHash(String source, int algorithm) {
        return getHash(source.getBytes(), algorithm);
    }

    public static byte[] getHash(byte[] source, int algorithm) {
        byte[] hash;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM_LIST[algorithm]);
            md.update(source);
            hash = md.digest();
        } catch (NoSuchAlgorithmException e) {
            hash = null;
        }
        return hash;
    }

    public static String getHexHash(String source, int algorithm) {
        return getHexHash(source.getBytes(), algorithm);
    }

    public static String getHexHash(byte[] source, int algorithm) {
        return byteArrayToHexString(getHash(source, algorithm));
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

}
