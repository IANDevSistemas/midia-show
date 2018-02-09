package br.com.iandev.midiaindoor.wrap.facede;

import java.util.List;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.dao.ProgramDao;
import br.com.iandev.midiaindoor.model.Program;

/**
 * Created by Lucas on 31/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 31/03/2017  Lucas
 */

public class ProgramFacede extends Facede<Program> {

    private static List<Program> cache;

    public ProgramFacede(App app) {
        super(app, new ProgramDao(app.getContext()));
    }

    @Override
    protected List<Program> getCache() {
        return cache;
    }

    @Override
    protected void setCache(List<Program> cache) {
        ProgramFacede.cache = cache;
    }
}
