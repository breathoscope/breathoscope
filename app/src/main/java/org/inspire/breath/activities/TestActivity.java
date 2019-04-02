package org.inspire.breath.activities;

import android.support.v7.app.AppCompatActivity;

import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Session;

public class TestActivity extends AppCompatActivity {

    public static String SESSION_ID_KEY = "SESSION_ID_KEY";
    protected Session mSession = null;

    public Session getSession() {
        if (mSession == null) {
            mSession = AppRoomDatabase.getDatabase()
                        .sessionDao()
                        .getRecordingById(getIntent().getLongExtra(SESSION_ID_KEY, -1));
        }
        return mSession;
    }
}
