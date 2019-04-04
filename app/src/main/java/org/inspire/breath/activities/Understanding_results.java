package org.inspire.breath.activities;

import android.content.Intent;
import android.se.omapi.Session;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import org.inspire.breath.R;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Recording;
import org.inspire.breath.data.blobs.MalariaTestResult;

public class Understanding_results extends AppCompatActivity {

    private Recording session ;//session object
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_understanding_results);
        session = AppRoomDatabase.getDatabase().recordingDao().getRecordingById(HomeActivity.currentSession).get(0);//getting session instance --> getting info
        age = AppRoomDatabase.getDatabase().patientDao().getPatientById(session.getPatientId()).get(0).getAge(); //getting the child's age
    }



    public void Positive_Click(View view) {

        setAnswer("yes");
        Intent intent = new Intent(this, Severe_malaria.class);
        startActivity(intent);
    }

    public void Negative_Click(View view) {
        setAnswer("no");

        Intent intent = new Intent(this, Refer_HC.class);
        startActivity(intent);
    }

    public void Invalid_Click(View view) {
        setAnswer("invalid");

        Intent intent = new Intent(this, MalariaActivity.class);
        startActivity(intent);
    }

    public void setAnswer(String string) //---> setting information
    {
        //setting the value but not in the database
        MalariaTestResult mtr = session.getMalariaTestResult();
        mtr.answer = string;
        session.setMalariaTestResult(mtr);//sets the blob, i.e using the function

        //store in database
        AppRoomDatabase.getDatabase().recordingDao().upsertRecording(session);
    }
}
