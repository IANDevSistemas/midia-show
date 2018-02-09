package br.com.iandev.midiaindoor.util;

import android.os.Environment;

import org.jetbrains.annotations.Contract;

import java.io.File;

/**
 * Created by Lucas on 21/12/2016.
 */
public class Files {
    private static final File appDirectory = new File(Environment.getExternalStorageDirectory(), "midiaindoor");
    private static final File assetsDirectory = getDirectory(getAppDirectory(), "assets");
    private static final File contentsDirectory = getDirectory(getAssetsDirectory(), "contents");
    private static final File logsDirectory = getDirectory(getAppDirectory(), "logs");

    @Contract(pure = true)
    private static File getAssetsDirectory() {
        return assetsDirectory;
    }

    @Contract(pure = true)
    public static File getManageableDirectory() {
        return contentsDirectory;
    }

    @Contract(pure = true)
    public static File getLogsDirectory() {
        return logsDirectory;
    }

    @Contract(pure = true)
    public static File getAppDirectory() {
        return appDirectory;
    }

    public static File getDirectory(File parent, String child) {
        File dir = new File(parent, child);
        boolean mkdirs = dir.mkdirs();
        if (!mkdirs) {
        }
        return dir;
    }

    public static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File fileChild : files) {
                deleteDirectory(fileChild);
            }
        }
        directory.delete();
    }

    public static long size(File file) {
        long length = 0;
        if (file.isFile()) {
            length += file.length();
        } else {
            for (File child : file.listFiles()) {
                length += size(child);
            }
        }
        return length;
    }

    public static long size() {
        return size(getAppDirectory());
    }

}
