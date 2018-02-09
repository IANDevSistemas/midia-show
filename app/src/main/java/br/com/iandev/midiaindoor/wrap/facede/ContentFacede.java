package br.com.iandev.midiaindoor.wrap.facede;

import java.util.List;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.dao.ContentDao;
import br.com.iandev.midiaindoor.model.Content;

/**
 * Created by Lucas on 31/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 31/03/2017  Lucas
 */

public class ContentFacede extends Facede<Content> {
    private static List<Content> cache;

    public ContentFacede(App app) {
        super(app, new ContentDao(app.getContext()));
    }

    @Override
    protected List<Content> getCache() {
        return cache;
    }

    @Override
    protected void setCache(List<Content> cache) {
        ContentFacede.cache = cache;
    }

    @Override
    public final long set(Content entity) throws IllegalStateException {
        try {
            Content content = this.get(entity);

            if (entity.getLastUpdateDate() == null) {
                entity.setLastUpdateDate(content.getLastUpdateDate());
            }

            if (entity.getLastUpdateAttemptDate() == null) {
                entity.setLastUpdateAttemptDate(content.getLastUpdateAttemptDate());
            }

            if (entity.getHash() == null) {
                entity.setHash(content.getHash());
            }

            if (entity.getIndexFilePath() == null || "".equals(entity.getIndexFilePath())) {
                entity.setIndexFilePath(content.getIndexFilePath());
            }

            if (entity.getDurationInterval() == null || entity.getDurationInterval() == -1) {
                entity.setDurationInterval(content.getDurationInterval());
            }

        } catch (NotFoundException ex) {
            // Silence is golden
        }
        return super.set(entity);
    }

    @Override
    public final void set(List<Content> entityList) throws IllegalStateException {
        for (Content entity : entityList) {
            this.set(entity);
        }
    }

    @Override
    public final long delete(Content entity) {
        long count = super.delete(entity);
        entity.clearFiles();
        return count;
    }
}
