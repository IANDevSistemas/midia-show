package br.com.iandev.midiaindoor.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 26/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 26/03/2017  Lucas
 */
public class IntervalUtilTest {
    @Test
    public void parse() throws Exception {
        assertEquals(1000L * (1L), IntervalUtil.parse("00:00:01"));
        assertEquals(1000L * (59L), IntervalUtil.parse("00:00:59"));

        assertEquals(1000L * (60L), IntervalUtil.parse("00:01:00"));
        assertEquals(1000L * (59L * 60L), IntervalUtil.parse("00:59:00"));

        assertEquals(1000L * (60L * 60L), IntervalUtil.parse("01:00:00"));
        assertEquals(1000L * (23L * 60L * 60L), IntervalUtil.parse("23:00:00"));

        assertEquals(1000L * (0L), IntervalUtil.parse("00:00:00"));
        assertEquals(1000L * (23L * 60L * 60L + 59L * 60L + 59L), IntervalUtil.parse("23:59:59"));
    }

    @Test
    public void staticToString() throws Exception {

    }

    @Test
    public void trunc() throws Exception {

    }
}