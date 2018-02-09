package br.com.iandev.midiaindoor.wrap.facede;

import java.util.List;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.dao.ProgramContentDao;
import br.com.iandev.midiaindoor.model.ProgramContent;

/**
 * Created by Lucas on 31/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 31/03/2017  Lucas
 */

public class ProgramContentFacede extends Facede<ProgramContent> {
    private static List<ProgramContent> cache;

    public ProgramContentFacede(App app) {
        super(app, new ProgramContentDao(app.getContext()));
    }

    @Override
    protected List<ProgramContent> getCache() {
        return cache;
    }

    @Override
    protected void setCache(List<ProgramContent> cache) {
        ProgramContentFacede.cache = cache;
    }
}
