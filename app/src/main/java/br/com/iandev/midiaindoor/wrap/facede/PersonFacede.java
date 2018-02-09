package br.com.iandev.midiaindoor.wrap.facede;

import java.util.List;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.dao.PersonDao;
import br.com.iandev.midiaindoor.model.Person;
import br.com.iandev.midiaindoor.wrap.settings.LicenseSettings;

/**
 * Created by Lucas on 31/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 31/03/2017  Lucas
 */

public final class PersonFacede extends Facede<Person> {
    private static List<Person> cache;

    public PersonFacede(App app) {
        super(app, new PersonDao(app.getContext()));
    }

    @Override
    protected List<Person> getCache() {
        return cache;
    }

    @Override
    protected void setCache(List<Person> cache) {
        PersonFacede.cache = cache;
    }

    @Override
    public Person get() throws NotFoundException {
        Person person;
        try {
            LicenseSettings licenseSettings = new LicenseSettings(super.getApp());
            Long personId = Long.valueOf(licenseSettings.getOwnerId());

//            person = getDao().read(new Person(personId));
            person = super.get(new Person(personId));
            if (!personId.equals(person.getId())) {
                throw new NotFoundException("It wasn't found");
            }
        } catch (Exception ex) {
            throw new NotFoundException(ex.getMessage());
        }
        return person;
    }

    @Override
    public Person get(Person entity) throws NotFoundException {
        throw new NotFoundException("It wasn't found");
    }

    @Override
    public List<Person> list() throws NotFoundException {
        throw new NotFoundException("It doesn't exists");
    }

    @Override
    public long set(Person entity) throws IllegalStateException {
        try {
            LicenseSettings licenseSettings = new LicenseSettings(super.getApp());
            Long personId = Long.valueOf(licenseSettings.getOwnerId());

            if (!personId.equals(entity.getId())) {
                throw new IllegalStateException("Its id isn't valid");
            }

            super.delete();
            super.set(entity);
            entity = this.get();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        return entity.getId();
    }

    @Override
    public void set(List<Person> entity) throws IllegalStateException {
        throw new IllegalStateException("Operation isn't valid");
    }
}
