package org.inspire.breath.activities;

import android.support.v7.app.AppCompatActivity;

import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Recording;

public class TestActivity extends AppCompatActivity {

    public static String SESSION_ID_KEY = "SESSION_ID_KEY";
    protected Recording mSession = null;

    public Recording getSession() {
        if (mSession == null) {
            mSession = AppRoomDatabase.getDatabase()
                        .recordingDao()
                        .getRecordingById(getIntent().getLongExtra(SESSION_ID_KEY, -1));
        }
        return mSession;
    }
}
