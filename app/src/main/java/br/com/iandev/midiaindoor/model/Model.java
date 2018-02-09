package br.com.iandev.midiaindoor.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 12/12/2016.
 */

public abstract class Model<T> implements Serializable {
    private Long id;

    public Model(Long id) {
        this.id = id;
    }

    public final Long getId() {
        return id;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    public final List<T> parseList(JSONArray jsonArray) {
        List<T> list = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(parse(jsonArray.getJSONObject(i)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public final List<T> parseList(String json) {
        List<T> list = new ArrayList<>();
        try {
            list = parseList(new JSONArray(json));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public final T parse(String jsonObject) {
        T entity = null;
        try {
            entity = parse(new JSONObject(jsonObject));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entity;
    }

    public abstract T parse(JSONObject jsonObject);
}
