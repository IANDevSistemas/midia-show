package br.com.iandev.midiaindoor.wrap.log;

import java.io.File;

import br.com.iandev.midiaindoor.util.Files;

/**
 * Created by Lucas on 19/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 19/04/2017  Lucas
 */

public class Logger {

    public static final String FILENAME = new File(Files.getLogsDirectory(), "log.txt").getAbsolutePath();

    public static org.apache.log4j.Logger getLogger(Class clazz) {
        final LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(FILENAME);
        logConfigurator.configure();
        return org.apache.log4j.Logger.getLogger(clazz);
    }
}
