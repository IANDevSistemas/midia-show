package br.com.iandev.midiaindoor.wrap.runners;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.core.TimeCounter;
import br.com.iandev.midiaindoor.core.integration.ManagerException;
import br.com.iandev.midiaindoor.model.Content;
import br.com.iandev.midiaindoor.wrap.facede.ContentFacede;
import br.com.iandev.midiaindoor.wrap.facede.IllegalStateException;
import br.com.iandev.midiaindoor.wrap.facede.NotFoundException;

/**
 * Created by Lucas on 29/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 29/03/2017  Lucas
 */

public class Manager extends IntegrationRunnable<Content> {
    private final ContentFacede contentFacede;
    private List<Content> contentList;
    private Iterator<Content> contentIterator;
    private boolean downloadForced = false;
    private boolean autoUpdateList = false;

    public Manager(App app, final Content content) {
        super(app);
        if (content == null) {
            throw new IllegalArgumentException("content must not be null");
        }
        this.contentFacede = new ContentFacede(super.getApp());
        this.contentList = new ArrayList<Content>() {
            {
                add(content);
            }
        };
    }

    public Manager(App app, final List<Content> contentList) {
        super(app);
        if (contentList == null) {
            throw new IllegalArgumentException("contentList must not be null");
        }
        this.contentFacede = new ContentFacede(super.getApp());
        this.contentList = new ArrayList<>(contentList);
    }

    public Manager(App app) {
        super(app);
        this.autoUpdateList = true;
        this.contentFacede = new ContentFacede(super.getApp());
    }

    @Override
    public final void start() {
        try {
            super.setUp();

            TimeCounter timeCounter = super.getApp().getTimeCounter();
            Content content = contentFacede.get(getContentIterator().next());

            if (mustUpdate(content, timeCounter.getDate())) {
                content.setLastUpdateAttemptDate(timeCounter.getDate());
                contentFacede.set(content);

                doUpdate(content);

                content.setLastUpdateDate(timeCounter.getDate());
                contentFacede.set(content);
                super.success(contentFacede.get(content));
            } else {
                super.success(content);
            }
        } catch (Exception ex) {
            super.error(ex);
        }
    }

    private Content doUpdate(Content content) throws ManagerException, JSONException, IOException, IllegalStateException, NotFoundException {
        br.com.iandev.midiaindoor.core.integration.bdo.Manager manager = new br.com.iandev.midiaindoor.core.integration.bdo.Manager();

        manager.setIdConteudo(content.getId().toString());
        manager.setConteudo(content.getAlias());
        manager.setAccessURL(content.getAccessUrl());
        manager.setPackageFile(content.getPackageFile());

        JSONObject json = manager.getInformation();

        String hash = json.getString("hash");
        String indexFilePath = json.getString("indexFilePath");
        Long durationInterval = content.getDurationInterval();

        if (json.has("durationInterval")) {
            durationInterval = json.getLong("durationInterval");
        }

        if (mustDownload(hash, indexFilePath, content)) {
            manager.doDownload();
            content.uncompressPackage();
            content.setHash(hash);
            content.setIndexFilePath(indexFilePath);
            content.setDurationInterval(durationInterval);
        }
        return content;
    }

    public boolean mustDownload(String hash, String indexFilePath, Content content) {
        return this.isDownloadForced() || !hash.equals(content.getHash()) || !indexFilePath.equals(content.getIndexFilePath());
    }

    public boolean mustUpdate(Content content, Date date) {
        return this.isDownloadForced() || content.mustUpdate(date);
    }

    public boolean isDownloadForced() {
        return downloadForced;
    }

    public void setDownloadForced(boolean downloadForced) {
        this.downloadForced = downloadForced;
    }

    private Iterator<Content> getContentIterator() throws NotFoundException {
        if (contentIterator == null || !contentIterator.hasNext()) {
            if (autoUpdateList) {
                contentList = newContentList();
            }
            contentIterator = contentList.iterator();
        }
        return contentIterator;
    }

    public List<Content> newContentList() throws NotFoundException {
        List<Content> contentList = contentFacede.list();

        Collections.sort(contentList, new Comparator<Content>() {
            @Override
            public int compare(Content content1, Content content2) {
                if (content1.getLastUpdateAttemptDate() == null) {
                    return -1;
                }

                if (content2.getLastUpdateAttemptDate() == null) {
                    return 1;
                }

                return content1.getLastUpdateAttemptDate().compareTo(content2.getLastUpdateAttemptDate());
            }
        });

        return contentList;
    }

    public String getTag() {
        return "Manager";
    }
}
