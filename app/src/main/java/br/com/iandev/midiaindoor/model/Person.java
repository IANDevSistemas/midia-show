package br.com.iandev.midiaindoor.model;

import org.json.JSONObject;

import br.com.iandev.midiaindoor.util.JSONUtil;

/**
 * Created by Lucas on 20/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 20/03/2017  Lucas
 */

public class Person extends Model {
    private String name;
    private String alias;
    private Character status;

    public Person(Long id) {
        super(id);
    }

    @Override
    public Person parse(JSONObject jsonObject) {
        Person model = new Person(null);
        try {
            model.setId(JSONUtil.getLong(jsonObject, "id"));
            model.setName(JSONUtil.getString(jsonObject, "name"));
            model.setAlias(JSONUtil.getString(jsonObject, "alias"));
            model.setStatus(JSONUtil.getCharacter(jsonObject, "status"));

        } catch (Exception ex) {
        }
        return model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%d - %s", getId(), getName());
    }

}
