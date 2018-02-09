package br.com.iandev.midiaindoor.wrap.facede;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.dao.Dao;
import br.com.iandev.midiaindoor.model.Model;
import br.com.iandev.midiaindoor.wrap.Wrapper;

/**
 * Created by Lucas on 30/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 30/03/2017  Lucas
 */

public abstract class Facede<T extends Model> extends Wrapper {
    private final Dao<T> dao;

    public Facede(App app, Dao<T> dao) {
        super(app);
        this.dao = dao;
    }

    protected abstract List<T> getCache();

    protected abstract void setCache(List<T> cache);

    protected synchronized final Dao<T> getDao() {
        return this.dao;
    }

    public synchronized T get() throws NotFoundException {
        throw new NotFoundException("Not implemented");
    }

    public synchronized T get(T entity) throws NotFoundException {
        if (!isCacheValid()) {
            setCache(getDao().list());
        }
        for (T c : getCache()) {
            try {
                if (c.getId().equals(entity.getId())) {
                    return c;
                }
            } catch (Exception ex) {
                // Silence is golden
            }
        }

        throw new NotFoundException("It wasn't found");
    }

    public synchronized List<T> list() throws NotFoundException {
        if (!isCacheValid()) {
            setCache(getDao().list());
        }
        return new ArrayList<>(getCache());
    }

    public synchronized long set(T entity) throws IllegalStateException {
        if (isCacheValid() && entity.getId() != null) {
            Iterator<T> it = getCache().iterator();
            while (it.hasNext()) {
                try {
                    if (it.next().getId().equals(entity.getId())) {
                        it.remove();
                        break;
                    }
                } catch (Exception ex) {
                    // Silence is golden
                }
            }
            getCache().add(entity);
        } else {
            setCache(null);
        }
        return getDao().insertOrUpdate(entity);
    }

    public synchronized void set(List<T> entityList) throws IllegalStateException {
        setCache(new ArrayList<>(entityList));
        getDao().insertOrUpdate(getCache());
    }

    public synchronized long delete(T entity) {
        if (isCacheValid()) {
            Iterator<T> it = getCache().iterator();
            boolean removed = false;
            while (it.hasNext()) {
                try {
                    if (it.next().getId().equals(entity.getId())) {
                        it.remove();
                        removed = true;
                        break;
                    }
                } catch (Exception ex) {
                    // Silence is golden
                }
            }
            if (!removed) {
                setCache(null);
            }
        }
        return getDao().delete(entity);
    }

    public synchronized long delete(List<T> entityList) {
        setCache(null);
        return getDao().delete(entityList);
    }

    public synchronized long delete() {
        setCache(null);
        return getDao().delete();
    }

    public synchronized void reset() {
        setCache(null);
        getDao().resetSequence();
    }

    private boolean isCacheValid() {
        return getCache() != null;
    }
}
