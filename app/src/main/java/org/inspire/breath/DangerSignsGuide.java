package org.inspire.breath;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;

public class DangerSignsGuide extends AppCompatActivity {
    DangerSignsQuestions questions = new DangerSignsQuestions();

    boolean anyDangerSigns = false;
    boolean answered = false;
    boolean results[] = new boolean[4];
    TextView questionView;
    TextView answerView;
    Button leftButton;
    Button rightButton;
    Button nextButton;
    int questionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_signs_guide);
        questionView = (TextView)findViewById(R.id.question_txt);
        answerView = (TextView)findViewById(R.id.answer_txt);
        leftButton = (Button)findViewById(R.id.yes_btn);
        rightButton = (Button)findViewById(R.id.no_btn);
        nextButton = (Button)findViewById(R.id.next_btn);

        questionView.setText(questions.retQ(questionNumber));
        leftButton.setText(questions.retO(0));
        rightButton.setText(questions.retO(1));
        answerView.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);

        //Button listener 1
        leftButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                if (questionNumber < 3 && !answered){
                    answerView.setText(questions.retA(questionNumber));
                    answerView.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    anyDangerSigns = true;
                    answered = true;
                    results[questionNumber] = true;
                }
                else if(questionNumber == 3 && !answered){
                    answerView.setText(questions.retA(questionNumber) + " " + questions.anyDangerSigns());
                    answerView.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.GONE);
                    answered = true;
                    results[questionNumber] = true;
                }
            }
        });

        //Button listener 2
        rightButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                if (questionNumber < 3 && !answered){
                    answerView.setText(questions.retA(5));
                    answerView.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    answered = true;
                    results[questionNumber] = false;
                }
                else if(questionNumber == 3 && anyDangerSigns && !answered) {
                    answerView.setText(questions.retA(questionNumber+1));
                    answerView.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.GONE);
                    answered = true;
                    results[questionNumber] = false;
                }
            }
        });

        //Button listener 3
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                if (questionNumber < 3){
                    questionNumber++;
                    questionView.setText(questions.retQ(questionNumber));
                    answerView.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
                    answered = false;
                }
                else if(questionNumber == 3){
                    questionNumber++;
                    leftButton.setVisibility(View.GONE);
                    rightButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
                    answered = false;
                }
            }
        });
    }
}