package br.com.iandev.midiaindoor.util;

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by Lucas on 30/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 30/03/2017  Lucas
 */
public class ThrowableUtilTest {
    @Test
    public void getStackTrace() throws Exception {
        try {
            Integer.parseInt("X");
        } catch (Exception ex) {
            try {
                ThrowableUtil.getStackTrace(ex);
            } catch (Exception ex2) {
                fail(ex.getMessage());
            }
        }
    }

    @Test
    public void getResumeStackTrace() throws Exception {

    }

    @Test
    public void getResumeStackTrace1() throws Exception {

    }
}