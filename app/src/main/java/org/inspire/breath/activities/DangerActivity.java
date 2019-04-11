package org.inspire.breath.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.inspire.breath.R;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.blobs.DangerTestResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class DangerActivity extends TestActivity {

    public static final String[] QUESTIONS = new String[]{"Does the patient have convulsions?",
            "Is the patient lethargic or unconscious?",
            "Is the patient vomiting everything?",
            "Is the patient unable to breastfeed or drink?"
    };

    private Stack<Boolean> answers;

    private TextView mQuestion;
    private Button mYes;
    private Button mNo;

    private int counter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger);
        answers = new Stack<>();
        findViews();
        setupListeners();
        mQuestion.setText(QUESTIONS[counter]); // init
    }

    private void setupListeners() {
        mYes.setOnClickListener(v -> {
            answers.push(true);
            postAnswer();
        });
        mNo.setOnClickListener(v -> {
            answers.push(false);
            postAnswer();
        });
    }

    private void postAnswer() {
        if (counter == QUESTIONS.length - 1) {
            getSession().setDangerTestResult(new DangerTestResult(answers));
            AppRoomDatabase.getDatabase()
                    .sessionDao()
                    .upsertRecording(getSession());
            finish();
        }
        else {
            counter++;
            mQuestion.setText(QUESTIONS[counter]);
        }
    }

    private void findViews() {
        this.mQuestion = findViewById(R.id.danger_question);
        this.mYes = findViewById(R.id.danger_yes);
        this.mNo = findViewById(R.id.danger_no);
    }

    @Override
    public void onBackPressed() {
        if (counter == 0)
            super.onBackPressed();
        else {
            answers.pop();
            counter--;
            mQuestion.setText(QUESTIONS[counter]);
        }
    }
}
