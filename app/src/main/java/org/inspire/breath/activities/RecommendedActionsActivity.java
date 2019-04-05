package org.inspire.breath.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.inspire.breath.R;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.SessionDao;
import org.inspire.breath.data.blobs.BreathTestResult;
import org.inspire.breath.data.blobs.DiarrhoeaTestResult;
import org.inspire.breath.data.blobs.FeverTestResult;
import org.inspire.breath.data.blobs.MalariaTestResult;
import org.inspire.breath.data.blobs.RecommendActionsResult;

import java.util.Arrays;

public class RecommendedActionsActivity extends TestActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_actions);
        Intent i = getIntent();
        int id = i.getIntExtra(SESSION_ID_KEY, 0);

        ImageButton done = findViewById(R.id.imageButton);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecommendedActionsActivity.this, PatientsActivity.class);
                startActivity(intent);
            }
        });
        SessionDao dao = AppRoomDatabase.getDatabase().sessionDao();
        Session session = dao.getRecordingById(id);
        session.getRecommendedActions().addAction(RecommendActionsResult.Test.BREATH, "Breath a little slower");

        //Get cards so we can hide ones that don't exist
        CardView feverCard = findViewById(R.id.diagnose_fever_result);
        CardView breathCard = findViewById(R.id.diagnose_breath_test);
        CardView malariaCard = findViewById(R.id.diagnose_malaria_test);
        CardView diarrhoeaCard = findViewById(R.id.diagnose_diarrhoea_test);

        //Get tests
        FeverTestResult feverTestResult = session.getFeverTestResult();
        BreathTestResult breathTestResult = session.getBreathTestResult();
        MalariaTestResult malariaTestResult = session.getMalariaTestResult();
        DiarrhoeaTestResult diarrhoeaTestResult = session.getDiarrhoeaTestResult();

        if(breathTestResult == null)
            breathCard.setVisibility(View.GONE);
        if(feverTestResult == null)
            feverCard.setVisibility(View.GONE);
        if(malariaTestResult == null)
            malariaCard.setVisibility(View.GONE);
        if(diarrhoeaTestResult == null)
            diarrhoeaCard.setVisibility(View.GONE);

        TextView feverActions = findViewById(R.id.feverActions);
        TextView feverResult = findViewById(R.id.feverResult);

        feverResult.setText(getResources().getString(R.string.degrees, session.getFeverTestResult().getTemperature()));

        if(session.getRecommendedActions().isUrgent)
            feverActions.setTextColor(Color.RED);
        feverActions.setText(session.getRecommendedActions().getActions(RecommendActionsResult.Test.FEVER));

        TextView breathActions = findViewById(R.id.breathActions);
        TextView breathResult = findViewById(R.id.breathResult);

        if(breathTestResult != null) {
            breathResult.setText(breathTestResult.getBreathrate() + " breaths per minute");
            if (session.getRecommendedActions().isUrgent)
                breathActions.setTextColor(Color.RED);
            breathActions.setText(session.getRecommendedActions().getActions(RecommendActionsResult.Test.BREATH));
        }

        TextView diarrhoeaActions = findViewById(R.id.diarrhoeaActions);
        TextView diarrhoeaResult = findViewById(R.id.diarrhoeaResult);

        if(diarrhoeaTestResult != null) {
            diarrhoeaResult.setText(diarrhoeaTestResult.getAnswer());
            if (session.getRecommendedActions().isUrgent)
                diarrhoeaActions.setTextColor(Color.RED);
            diarrhoeaActions.setText(session.getRecommendedActions().getActions(RecommendActionsResult.Test.DIARRHOEA));
        }


    }

}
