package org.inspire.breath.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.*;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.blobs.HrCountTest;


import org.inspire.breath.R;
import org.inspire.breath.data.blobs.RecommendActionsResult;
import org.inspire.breath.utils.WawReader;

public class HrCountActivity extends TestActivity {
    TextView heartBeats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        /**
        Intent i = getIntent();
        File f = (File)i.getExtras().get("HR_FILE");
        WawReader reader = new WawReader();
        int beats = reader.getResult(f);
         **/
        setContentView(R.layout.activity_hr_count);
        int beats = 70;
        heartBeats = findViewById(R.id.textView3);
        heartBeats.setText(beats + " BPM");

        HrCountTest writer = new HrCountTest();
        writer.setResult(beats);
        Session session = getSession();
        session.setHrCountBlob(writer.toBlob());
        RecommendActionsResult actionsResult = session.getRecommendedActions();
        actionsResult.addAction(RecommendActionsResult.Test.HEART, new RecommendActionsResult.Action("Heart rate is " + beats + "bpm", RecommendActionsResult.Action.MED)); //turn into res string
        session.setRecommendedActionsResultBlob(actionsResult.toBlob());
        AppRoomDatabase.getDatabase()
                .sessionDao()
                .upsertRecording(session);

        Button done = findViewById(R.id.button2);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
