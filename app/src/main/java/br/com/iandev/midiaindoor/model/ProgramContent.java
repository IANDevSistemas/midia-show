package br.com.iandev.midiaindoor.model;

import org.json.JSONObject;

import br.com.iandev.midiaindoor.util.JSONUtil;

/**
 * Created by Lucas on 12/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 12/12/2016  Lucas
 */

public class ProgramContent extends Model<ProgramContent> {
    private Content content;
    private Program program;
    private Integer sequence;

    public ProgramContent(Long id) {
        super(id);
    }

    @Override
    public ProgramContent parse(JSONObject jsonObject) {
        ProgramContent model = new ProgramContent(null);
        try {
            model.setId(JSONUtil.getLong(jsonObject, "id"));
            model.setContent(new Content(JSONUtil.getLong(jsonObject, "content")));
            model.setProgram(new Program(JSONUtil.getLong(jsonObject, "program")));
            model.setSequence(JSONUtil.getInteger(jsonObject, "sequence"));

            return model;
        } catch (Exception ex) {
        }
        return model;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }


    @Override
    public String toString() {
        return String.format("%s - %s", this.getContent() != null ? this.getContent().toString() : "", this.getProgram() != null ? this.getContent().toString() : "");
    }

}
