package org.inspire.breath.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import org.inspire.breath.R;
import android.support.v4.app.SupportActivity;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.blobs.MalariaTestResult;

import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.blobs.MalariaTestResult;
import org.inspire.breath.data.blobs.RecommendActionsResult;

public class Understanding_results extends AppCompatActivity  {

    private Session session ;//session object
    private int age;
    private String parseAge;

    //asks about malaria severity signs
    private boolean hasSevereMalaria= Severe_malaria.severeMalariaCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_understanding_results);

        session = AppRoomDatabase.getDatabase().sessionDao().getRecordingById(HomeActivity.currentSession);//getting session instance --> getting info
        age = Integer.parseInt(AppRoomDatabase.getDatabase().patientDao().getPatientById(session.getPatientId()).getAge());
    }

    public void Positive_Click(View view) {
        setAnswer("yes");
        Intent intent = new Intent(this, Severe_malaria.class);
        startActivity(intent);
    }

    public void Negative_Click(View view) {
        setAnswer("no");
        Intent intent = new Intent(this, HomeActivity.class);
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


        if(string=="no")
        {
            RecommendActionsResult recommendActionsResult = session.getRecommendedActions();
            recommendActionsResult.addAction(RecommendActionsResult.Test.MALARIA, "Refer To Health Center");
            session.setRecommendedActionsResultBlob(recommendActionsResult.toBlob());
        }
        else if(string=="yes"&& hasSevereMalaria==false)
        {
            RecommendActionsResult recommendActionsResult = session.getRecommendedActions();
            recommendActionsResult.addAction(RecommendActionsResult.Test.MALARIA, "Treat With Oral AntiMalaria ");
            session.setRecommendedActionsResultBlob(recommendActionsResult.toBlob());
        }
        else if(string=="yes" && hasSevereMalaria==true)
        {
            RecommendActionsResult recommendActionsResult = session.getRecommendedActions();
            recommendActionsResult.addAction(RecommendActionsResult.Test.MALARIA, "Give pre- referral Artesunate && Refer to Hospital URGENTLY ");
            session.setRecommendedActionsResultBlob(recommendActionsResult.toBlob());

        }

        //sets the blob, i.e using the function
        session.setMalariaTestResult(mtr);

        //store in database
        //upsert --> inserts and updates
        AppRoomDatabase.getDatabase().sessionDao().upsertRecording(session);
    }
}

