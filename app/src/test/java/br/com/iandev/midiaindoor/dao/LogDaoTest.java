package br.com.iandev.midiaindoor.dao;

import android.content.ContentValues;
import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import br.com.iandev.midiaindoor.BuildConfig;
import br.com.iandev.midiaindoor.model.Log;
import br.com.iandev.midiaindoor.util.SQLiteUtil;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 22/03/2017.
 * Changes:
 * Version     Date        Responsible     Change
 * 22/03/2017  Lucas
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LogDaoTest extends ConnectionFactoryTest {
    private Context context;

    /*
        @Override
        public void onCreate(Bundle arguments) {
            super.onCreate(arguments);
            context = getContext();
        }

        @Before
        public void setup() {
            ConnectionFactory.getInstance(RuntimeEnvironment.application);
        }

        @After
        public void tearDown() {
            ConnectionFactory.getInstance(RuntimeEnvironment.application).close();
        }

        @Test
        public void getEntityName() throws Exception {
            assertEquals(new LogDao(null).getEntityName(), this.getClass().getSimpleName().replace("DaoTest", ""));
        }
    */
    @Test
    public void getContentValues() throws Exception {
        Long id = 1L;
        Log log = new Log(id);

        Date date = new Date();
        log.setDate(date);

        ContentValues cv = new LogDao(context).getContentValues(log);

        assertEquals(cv.get("id"), id);
        assertEquals(cv.get("date"), SQLiteUtil.getDate(date));
    }

    @Test
    public void getEntity() throws Exception {

    }

}