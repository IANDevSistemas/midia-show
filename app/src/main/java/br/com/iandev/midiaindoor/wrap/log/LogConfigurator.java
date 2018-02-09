package br.com.iandev.midiaindoor.wrap.log;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.helpers.LogLog;

import java.io.IOException;

public class LogConfigurator {
    private Level rootLevel = Level.DEBUG;
    private String filePattern = "%p %t %d %c - %m%n";
    private String logCatPattern = "%m%n";
    private String fileName = "default.log";
    private int maxBackupSize = 5;
    private long maxFileSize = 32768;
    private boolean immediateFlush = true;
    private boolean useLogCatAppender = true;
    private boolean useFileAppender = true;
    private boolean resetConfiguration = true;
    private boolean internalDebugging = false;


    public LogConfigurator() {
    }


    public LogConfigurator(String fileName) {
        setFileName(fileName);
    }


    public LogConfigurator(String fileName, Level rootLevel) {
        this(fileName);
        setRootLevel(rootLevel);
    }


    public LogConfigurator(String fileName, Level rootLevel, String filePattern) {
        this(fileName);
        setRootLevel(rootLevel);
        setFilePattern(filePattern);
    }


    public LogConfigurator(String fileName, int maxBackupSize, long maxFileSize, String filePattern, Level rootLevel) {
        this(fileName, rootLevel, filePattern);
        setMaxBackupSize(maxBackupSize);
        setMaxFileSize(maxFileSize);
    }

    public void configure() {
        Logger root = Logger.getRootLogger();

        if (isResetConfiguration()) {
            LogManager.getLoggerRepository().resetConfiguration();
        }

        LogLog.setInternalDebugging(isInternalDebugging());

        if (isUseFileAppender()) {
            configureFileAppender();
        }

        if (isUseLogCatAppender()) {
            configureLogCatAppender();
        }

        root.setLevel(getRootLevel());
    }


    public void setLevel(Class loggerName, Level level) {
        Logger.getLogger(loggerName).setLevel(level);
    }

    private void configureFileAppender() {
        Logger root = Logger.getRootLogger();

        Layout fileLayout = new PatternLayout(getFilePattern());
        RollingFileAppender rollingFileAppender;
        try {
            rollingFileAppender = new RollingFileAppender(fileLayout, getFileName());
        } catch (IOException e) {
            throw new RuntimeException("Exception configuring log system", e);
        }

        rollingFileAppender.setMaxBackupIndex(getMaxBackupSize());
        rollingFileAppender.setMaximumFileSize(getMaxFileSize());
        rollingFileAppender.setImmediateFlush(isImmediateFlush());

        root.addAppender(rollingFileAppender);
    }

    private void configureLogCatAppender() {
        Logger root = Logger.getRootLogger();
        Layout logCatLayout = new PatternLayout(getLogCatPattern());
        LogCatAppender logCatAppender = new LogCatAppender(logCatLayout);

        root.addAppender(logCatAppender);
    }


    public Level getRootLevel() {
        return rootLevel;
    }


    public void setRootLevel(Level level) {
        rootLevel = level;
    }

    public String getFilePattern() {
        return filePattern;
    }

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    public String getLogCatPattern() {
        return logCatPattern;
    }

    public void setLogCatPattern(String logCatPattern) {
        this.logCatPattern = logCatPattern;
    }

    public String getFileName() {
        return fileName;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public int getMaxBackupSize() {
        return maxBackupSize;
    }


    public void setMaxBackupSize(int maxBackupSize) {
        this.maxBackupSize = maxBackupSize;
    }


    public long getMaxFileSize() {
        return maxFileSize;
    }


    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public boolean isImmediateFlush() {
        return immediateFlush;
    }

    public void setImmediateFlush(boolean immediateFlush) {
        this.immediateFlush = immediateFlush;
    }

    public boolean isUseFileAppender() {
        return useFileAppender;
    }


    public void setUseFileAppender(boolean useFileAppender) {
        this.useFileAppender = useFileAppender;
    }


    public boolean isUseLogCatAppender() {
        return useLogCatAppender;
    }


    public void setUseLogCatAppender(boolean useLogCatAppender) {
        this.useLogCatAppender = useLogCatAppender;
    }

    public boolean isResetConfiguration() {
        return resetConfiguration;
    }

    public void setResetConfiguration(boolean resetConfiguration) {
        this.resetConfiguration = resetConfiguration;
    }

    public boolean isInternalDebugging() {
        return internalDebugging;
    }

    public void setInternalDebugging(boolean internalDebugging) {
        this.internalDebugging = internalDebugging;
    }
}
