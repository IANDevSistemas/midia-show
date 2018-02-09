package br.com.iandev.midiaindoor;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Lucas on 25/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 25/03/2017  Lucas
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AppTest extends AndroidJUnitRunner {
    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
    }
}