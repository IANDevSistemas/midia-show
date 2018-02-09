package br.com.iandev.midiaindoor.wrap.facede;

import java.util.List;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.dao.ProgrammingDao;
import br.com.iandev.midiaindoor.model.Programming;

/**
 * Created by Lucas on 31/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 31/03/2017  Lucas
 */

public class ProgrammingFacede extends Facede<Programming> {

    private static List<Programming> cache;

    public ProgrammingFacede(App app) {
        super(app, new ProgrammingDao(app.getContext()));
    }

    @Override
    protected List<Programming> getCache() {
        return cache;
    }

    @Override
    protected void setCache(List<Programming> cache) {
        ProgrammingFacede.cache = cache;
    }
}
