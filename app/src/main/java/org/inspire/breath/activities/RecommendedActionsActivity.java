package org.inspire.breath.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.inspire.breath.R;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.SessionDao;
import org.inspire.breath.data.blobs.BreathTestResult;
import org.inspire.breath.data.blobs.DangerTestResult;
import org.inspire.breath.data.blobs.DiarrhoeaTestResult;
import org.inspire.breath.data.blobs.FeverTestResult;
import org.inspire.breath.data.blobs.HrCountTest;
import org.inspire.breath.data.blobs.MalariaTestResult;
import org.inspire.breath.data.blobs.RecommendActionsResult;

public class RecommendedActionsActivity extends TestActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_actions);
        Intent i = getIntent();
        int id = i.getIntExtra(SESSION_ID_KEY, 0);

        Button done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SessionDao dao = AppRoomDatabase.getDatabase().sessionDao();
        Session session = dao.getRecordingById(id);
        session.getRecommendedActions().addAction(RecommendActionsResult.Test.BREATH, new RecommendActionsResult.Action("Breath a little slower", 1));

        //Get cards so we can hide ones that don't exist
        CardView feverCard = findViewById(R.id.diagnose_fever_result);
        CardView breathCard = findViewById(R.id.diagnose_breath_test);
        CardView malariaCard = findViewById(R.id.diagnose_malaria_test);
        CardView diarrhoeaCard = findViewById(R.id.diagnose_diarrhoea_test);
        CardView heartCard = findViewById(R.id.diagnose_heartrate_test);
        CardView dangerCard = findViewById(R.id.diagnose_danger_test);

        //Get tests
        FeverTestResult feverTestResult = session.getFeverTestResult();
        BreathTestResult breathTestResult = session.getBreathTestResult();
        MalariaTestResult malariaTestResult = session.getMalariaTestResult();
        DiarrhoeaTestResult diarrhoeaTestResult = session.getDiarrhoeaTestResult();
        HrCountTest heartRateTestResult = session.getHrCount();
        DangerTestResult dangerTestResult = session.getDangerTestResult();

        if(breathTestResult == null)
            breathCard.setVisibility(View.GONE);
        if(feverTestResult == null)
            feverCard.setVisibility(View.GONE);
        if(malariaTestResult == null)
            malariaCard.setVisibility(View.GONE);
        if(diarrhoeaTestResult == null)
            diarrhoeaCard.setVisibility(View.GONE);
        if(heartRateTestResult == null)
            heartCard.setVisibility(View.GONE);
        if(dangerTestResult == null)
            dangerCard.setVisibility(View.GONE);

        if(feverTestResult != null) {
            TextView feverActions = findViewById(R.id.feverActions);
            TextView feverResult = findViewById(R.id.feverResult);

            feverResult.setText(getResources().getString(R.string.degrees, session.getFeverTestResult().getTemperature()));
            RecommendActionsResult.Action action = session.getRecommendedActions().getAction(RecommendActionsResult.Test.FEVER);
            if(action != null) {
                feverActions.setTextColor(getColorForSeverity(action.getSeverity()));
                feverActions.setText(action.getAction());
            }
        }

        if(breathTestResult != null) {
            TextView breathActions = findViewById(R.id.breathActions);
            TextView breathResult = findViewById(R.id.breathResult);
            breathResult.setText(breathTestResult.getBreathrate() + " breaths per minute");
            RecommendActionsResult.Action action = session.getRecommendedActions().getAction(RecommendActionsResult.Test.BREATH);
            breathActions.setTextColor(getColorForSeverity(action.getSeverity()));
            breathActions.setText(action.getAction());
        }

        if(diarrhoeaTestResult != null) {
            TextView diarrhoeaActions = findViewById(R.id.diarrhoeaActions);
            RecommendActionsResult.Action action = session.getRecommendedActions().getAction(RecommendActionsResult.Test.DIARRHOEA);
            diarrhoeaActions.setTextColor(getColorForSeverity(action.getSeverity()));
            diarrhoeaActions.setText(action.getAction());
        }

        if(dangerTestResult != null) {
            TextView dangerActions = findViewById(R.id.dangerActions);
            RecommendActionsResult.Action action = session.getRecommendedActions().getAction(RecommendActionsResult.Test.DANGER);
            dangerActions.setTextColor(getColorForSeverity(action.getSeverity()));
            dangerActions.setText(action.getAction());
        }

        if(malariaTestResult != null) {
            TextView malariaActions = findViewById(R.id.malariaActions);
            TextView malariaResult = findViewById(R.id.malariaResult);
            malariaResult.setText(session.getMalariaTestResult().getTestResult());
            RecommendActionsResult.Action action = session.getRecommendedActions().getAction(RecommendActionsResult.Test.MALARIA);
            malariaActions.setTextColor(getColorForSeverity(action.getSeverity()));
            malariaActions.setText(action.getAction());
        }

        if(heartRateTestResult != null) {
            TextView heartrateActions = findViewById(R.id.heartrateActions);
            RecommendActionsResult.Action action = session.getRecommendedActions().getAction(RecommendActionsResult.Test.HEART);
            heartrateActions.setTextColor(getColorForSeverity(action.getSeverity()));
            heartrateActions.setText(action.getAction());
        }


    }

    public int getColorForSeverity(int severity) {
        switch(severity) {
            case RecommendActionsResult.Action.OK:
                return Color.GREEN;
            case RecommendActionsResult.Action.MED:
                return Color.rgb(255, 165, 0); //orange
            case RecommendActionsResult.Action.SEVERE:
                return Color.RED;
        }
        return Color.GRAY;
    }

}
