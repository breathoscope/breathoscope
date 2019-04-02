package org.inspire.breath.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.inspire.breath.R;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.SessionDao;

import java.util.Arrays;

public class RecommendedActionsActivity extends TestActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_actions);
        Intent i = getIntent();
        int id = i.getIntExtra(SESSION_ID_KEY, 0);
        SessionDao dao = AppRoomDatabase.getDatabase().sessionDao();
        Session session = dao.getSessionById(id).get(0);
        TextView view = findViewById(R.id.actions);
        if(session.getRecommendedActions().isUrgent)
            view.setTextColor(Color.RED);
        view.setText(Arrays.toString(session.getRecommendedActions().getActions().entrySet().toArray()));

    }

}
