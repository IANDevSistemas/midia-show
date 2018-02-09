package br.com.iandev.midiaindoor.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Lucas on 13/11/2016.
 */

public class FileUncompressorUtil {
    private static final int BUFFER_SIZE = 4096;

    public static void unzip(File zipFile, File directory) throws IOException {

        ZipInputStream zipIS = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE));

        try {
            ZipEntry ze = null;

            // for each file in the zip
            while ((ze = zipIS.getNextEntry()) != null) {

                File file = new File(directory, ze.getName());

                new File(file.getParent()).mkdirs();
                // if it is a directory just make it
                if (ze.isDirectory()) {
                    file.mkdirs();
                    break;
                }

                // unzip the file
                int size;
                byte[] buffer = new byte[BUFFER_SIZE];

                BufferedOutputStream fileBOS = new BufferedOutputStream(new FileOutputStream(file, false), BUFFER_SIZE);

                try {
                    while ((size = zipIS.read(buffer, 0, BUFFER_SIZE)) != -1) {
                        fileBOS.write(buffer, 0, size);
                    }
                    zipIS.closeEntry();
                } finally {
                    fileBOS.flush();
                    fileBOS.close();
                }
            }
        } finally {
            zipIS.close();
        }
    }
}
