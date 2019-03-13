package org.inspire.breath.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import org.inspire.breath.R;


public class ValidateDiarrhoeaActivity extends AppCompatActivity {

    Button answerYes, answerNo;
    TextView question;
    private Questions mQuestions = new Questions();
    private String mAnswer;
    private int mQuestionsLength = mQuestions.mQuestions.length;
    private int questionNum = 0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_diarrhoea);

        answerYes = (Button) findViewById(R.id.buttonYes);
        answerNo = (Button) findViewById(R.id.buttonNo);

        question = (TextView) findViewById(R.id.question);
        question.setText(mQuestions.getQuestion(questionNum));
        answerYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNum<2) {
                    questionNum++;
                    question.setText(mQuestions.getQuestion(questionNum));
                }
                else if (questionNum>=2){
                    question.setText(mQuestions.getAnswer(1));
                    answerYes.setVisibility(View.GONE);
                    answerNo.setVisibility(View.GONE);
                }


            }
        });

        answerNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setText(mQuestions.getAnswer(questionNum));
                answerYes.setVisibility(View.GONE);
                answerNo.setVisibility(View.GONE);

            }
        });

    }



}
