package br.com.iandev.midiaindoor.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by Lucas on 30/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 30/03/2017  Lucas
 */

public class ThrowableUtil {
    public static String getStackTrace(Throwable ex) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ex.printStackTrace(ps);
        ps.close();
        return new String(baos.toByteArray());
    }

    public static String getResumeStackTrace(Throwable ex) {
        String st = getStackTrace(ex);
        String resume = st.substring(0, 256);
        int idx = st.indexOf("Caused by");
        if (idx != -1) {
            resume += st.substring(idx, idx + 256);
        }
        return resume;
    }

    public static String getResumeStackTrace(Throwable ex, int lenght) {
        String st = getStackTrace(ex);
        String resume = st.substring(0, (lenght < st.length() ? lenght : st.length()) - 1);
        int idx = st.indexOf("Caused by");
        if (idx != -1) {
            resume += st.substring(idx, (idx + lenght < idx + st.length() ? idx + lenght : idx + st.length()) - 1);
        }
        return resume;
    }
}
