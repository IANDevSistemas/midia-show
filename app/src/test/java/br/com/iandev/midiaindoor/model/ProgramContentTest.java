package br.com.iandev.midiaindoor.model;

import android.support.test.runner.AndroidJUnitRunner;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import br.com.iandev.midiaindoor.BuildConfig;
import br.com.iandev.midiaindoor.util.JSONUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Lucas on 25/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 25/03/2017  Lucas
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ProgramContentTest extends AndroidJUnitRunner {
    @Test
    public void parse() throws Exception {
        JSONObject jsonObject = new JSONObject();

        Long id = 1L;
        jsonObject.put("id", id);
        Content content = new Content(2L);
        jsonObject.put("content", JSONUtil.getModel(content));
        Program program = new Program(3L);
        jsonObject.put("program", JSONUtil.getModel(program));
        Integer sequence = 4;
        jsonObject.put("sequence", sequence);

        ProgramContent model = new ProgramContent(null).parse(jsonObject);

        assertEquals(id, model.getId());
        assertFalse(content.getId() == null);
        assertEquals(content.getId(), model.getContent().getId());
        assertFalse(program.getId() == null);
        assertEquals(program.getId(), model.getProgram().getId());
        assertEquals(sequence, model.getSequence());
    }

}