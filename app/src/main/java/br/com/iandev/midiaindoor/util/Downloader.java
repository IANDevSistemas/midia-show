package br.com.iandev.midiaindoor.util;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Lucas on 13/11/2016.
 */

public class Downloader extends AsyncTask<Object, Integer, Map.Entry<Downloader.BackGroundReturn, String>> {
    private static int DEFAULT_BUFFER_LENGTH = 4096;
    private URL url;
    private OutputStream outputStream;
    private int bufferLength;

    public Downloader() {
        this.setBufferLength(DEFAULT_BUFFER_LENGTH);
    }

    public Downloader setURL(URL url) {
        this.url = url;
        return this;
    }

    public Downloader setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        return this;
    }

    public void setBufferLength(int bufferLength) {
        if (bufferLength <= 0) {
            throw new IllegalArgumentException("bufferLength must be greater than 0");
        }
        this.bufferLength = bufferLength;
    }

    @Override
    protected Map.Entry<BackGroundReturn, String> doInBackground(Object... params) {
        InputStream is = null;
        OutputStream os = outputStream;
        HttpURLConnection conn = null;

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(Long.valueOf(IntervalUtil.parse("00:00:30")).intValue());
            conn.setReadTimeout(Long.valueOf(IntervalUtil.parse("00:00:30")).intValue());
            conn.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return new Entry<>(BackGroundReturn.HTTP_ERROR, String.format("Server returned HTTP %s %s", conn.getResponseCode(), conn.getResponseMessage()));
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = conn.getContentLength();

            // download the file
            is = conn.getInputStream();

            byte data[] = new byte[bufferLength];
            long total = 0;
            int count;
            while ((count = is.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    is.close();
                    return null;
                }

                total += count;
                // publishing the progress....
                if (fileLength > 0) { // only if total length is known
                    this.publishProgress((int) (total * 100 / fileLength));
                }

                os.write(data, 0, count);
            }
        } catch (Exception ex) {
            return new Entry<>(BackGroundReturn.UNKNOW_EXCEPTION, ex.toString());
        } catch (Throwable ex) {
            return new Entry<>(BackGroundReturn.UNKNOW_THROWABLE, ex.toString());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException ignored) {
            }

            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ignored) {
            }

            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public enum BackGroundReturn {
        HTTP_ERROR(1),
        UNKNOW_EXCEPTION(2),
        UNKNOW_THROWABLE(3);
        int value;

        BackGroundReturn(int value) {
            this.value = value;
        }
    }
}

