package br.com.iandev.midiaindoor.dao;

import android.os.Bundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;
import java.util.Set;

import br.com.iandev.midiaindoor.AppTest;
import br.com.iandev.midiaindoor.BuildConfig;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by Lucas on 25/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 25/03/2017  Lucas
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ConnectionFactoryTest extends AppTest {

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
    }

    @Before
    public void setUp() throws Exception {
        ConnectionFactory.getInstance(RuntimeEnvironment.application);
    }

    @After
    public void tearDown() throws Exception {
        ConnectionFactory.getInstance(RuntimeEnvironment.application).close();
    }

    @Test
    public void getInstance() throws Exception {
        assertEquals(ConnectionFactory.getInstance(getContext()), ConnectionFactory.getInstance(getContext()));
    }

    @Test
    public void onCreate() throws Exception {
        try {
            ConnectionFactory connectionFactory = ConnectionFactory.getInstance(getContext());
            List<Dao> daos = connectionFactory.getDaos();
            Set<String> tableNames = connectionFactory.getTableNames();

            assertFalse(null == daos);
            assertFalse(null == tableNames);

            assertEquals(daos.size(), tableNames.size());

            for (Dao dao : daos) {
                assertTrue(tableNames.contains(dao.getEntityName()));
            }
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
}