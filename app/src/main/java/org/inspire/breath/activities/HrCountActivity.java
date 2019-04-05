package org.inspire.breath.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.*;
import android.widget.TextView;

import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.blobs.HrCountTest;


import org.inspire.breath.R;
import org.inspire.breath.utils.WawReader;

public class HrCountActivity extends TestActivity {
    TextView heartBeats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_count);
        Intent i = getIntent();
        File f = (File)i.getExtras().get("HR_FILE");
        WawReader reader = new WawReader();
        int beats = reader.getResult(f);
        heartBeats = (TextView)findViewById(R.id.textView3);
        heartBeats.setText(""+beats);

        HrCountTest writer = new HrCountTest();
        writer.setResult(beats);
        Session a = getSession();
        a.setHrCountBlob(writer.toBlob());
        AppRoomDatabase.getDatabase()
                .sessionDao()
                .insertRecording(a);


    }
}
