package br.com.iandev.midiaindoor.wrap.log;

import android.util.Log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;


public class LogCatAppender extends AppenderSkeleton {
    protected Layout tagLayout;

    public LogCatAppender(Layout messageLayout, Layout tagLayout) {
        this.tagLayout = tagLayout;
        setLayout(messageLayout);
    }

    public LogCatAppender(Layout messageLayout) {
        this(messageLayout, new PatternLayout("%c"));
    }

    public LogCatAppender() {
        this(new PatternLayout("%m%n"));
    }

    @Override
    protected void append(LoggingEvent le) {
        int level = le.getLevel().toInt();

        if (level == Level.ALL_INT) {
            if (le.getThrowableInformation() != null) {
                Log.v(getTagLayout().format(le), getLayout().format(le), le.getThrowableInformation().getThrowable());
            } else {
                Log.v(getTagLayout().format(le), getLayout().format(le));
            }

        } else if (level == Level.DEBUG_INT || level == Level.TRACE_INT) {
            if (le.getThrowableInformation() != null) {
                Log.d(getTagLayout().format(le), getLayout().format(le), le.getThrowableInformation().getThrowable());
            } else {
                Log.d(getTagLayout().format(le), getLayout().format(le));
            }

        } else if (level == Level.INFO_INT) {
            if (le.getThrowableInformation() != null) {
                Log.i(getTagLayout().format(le), getLayout().format(le), le.getThrowableInformation().getThrowable());
            } else {
                Log.i(getTagLayout().format(le), getLayout().format(le));
            }

        } else if (level == Level.WARN_INT) {
            if (le.getThrowableInformation() != null) {
                Log.w(getTagLayout().format(le), getLayout().format(le), le.getThrowableInformation().getThrowable());
            } else {
                Log.w(getTagLayout().format(le), getLayout().format(le));
            }

        } else if (level == Level.ERROR_INT) {
            if (le.getThrowableInformation() != null) {
                Log.e(getTagLayout().format(le), getLayout().format(le), le.getThrowableInformation().getThrowable());
            } else {
                Log.e(getTagLayout().format(le), getLayout().format(le));
            }

        } else if (level == Level.FATAL_INT) {
            if (le.getThrowableInformation() != null) {
                Log.wtf(getTagLayout().format(le), getLayout().format(le), le.getThrowableInformation().getThrowable());
            } else {
                Log.wtf(getTagLayout().format(le), getLayout().format(le));
            }

        } else if (level == Level.OFF_INT) {
            // Silence is golden
        }

    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    public Layout getTagLayout() {
        return tagLayout;
    }

    public void setTagLayout(Layout tagLayout) {
        this.tagLayout = tagLayout;
    }
}
