package br.com.iandev.midiaindoor.core.interfaces;

import java.io.File;
import java.io.IOException;

/**
 * Created by Lucas on 18/12/2016.
 */

public interface Content extends Updateble, Playable {
    String getDescription();

    String getHash();

    void setHash(String hash);

    String getAlias();

    String getIndexFilePath();

    void setIndexFilePath(String indexFilePath);

    Long getDurationInterval();

    void setDurationInterval(Long durationTime);

    File getPackageFile();

    File getBaseDirectory();

    boolean uncompressPackage() throws IOException;

    String getAccessUrl();

    void clearFiles();

}
