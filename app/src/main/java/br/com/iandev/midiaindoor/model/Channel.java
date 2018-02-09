package br.com.iandev.midiaindoor.model;

import org.json.JSONObject;

import br.com.iandev.midiaindoor.util.JSONUtil;

/**
 * Created by Lucas on 12/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 12/12/2016  Lucas
 */

public class Channel extends Model<Channel> {
    private String description;
    private Character status;

    public Channel(Long id) {
        super(id);
    }

    @Override
    public Channel parse(JSONObject jsonObject) {
        Channel model = new Channel(null);
        try {
            model.setId(JSONUtil.getLong(jsonObject, "id"));
            model.setDescription(JSONUtil.getString(jsonObject, "description"));
            model.setStatus(JSONUtil.getCharacter(jsonObject, "status"));
        } catch (Exception ex) {
        }
        return model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%d - %s", this.getId(), this.getDescription());
    }
}
