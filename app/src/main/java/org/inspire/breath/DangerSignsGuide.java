package org.inspire.breath;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;

public class DangerSignsGuide extends AppCompatActivity {
    DangerSignsQuestions questions = new DangerSignsQuestions();

    boolean anyDangerSigns = false;
    TextView questionView;
    TextView answerView;

    Button yesButton;
    Button noButton;
    Button nextButton;

    String answer;
    int questionNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_signs_guide);
        hideAnswer();
        questionView = (TextView)findViewById(R.id.question_txt);
        answerView = (TextView)findViewById(R.id.answer_txt);
        yesButton = (Button)findViewById(R.id.yes_btn);
        noButton = (Button)findViewById(R.id.no_btn);
        nextButton = (Button)findViewById(R.id.next_btn);

        updateQuestion();

        //Button listener 1
        yesButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                //Button logic

                if (yesButton.getText() == "Yes"){
                    answer = "Yes";
                    displayAnswer();
                }
                else{
                    answer = "";
                }
            }
        });

        //Button listener 1
        noButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                //Button logic

                if (noButton.getText() == "No"){
                    answer = "No";
                    questionNumber++;
                    updateQuestion();
                }
                else{
                    answer = "";
                }
            }
        });

        //Button listener 1
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                //Button logic

                if (nextButton.getText() == "Next"){
                    questionNumber++;
                    hideAnswer();
                    updateQuestion();
                }
                else{
                    answer = "";
                }
            }
        });
    }

    private void displayAnswer(){
        if(answer == "Yes"){
            answerView.setText(questions.retA(questionNumber));
            answerView.setVisibility(View.VISIBLE);
        }
    }
    private void hideAnswer(){
        answerView.setVisibility(View.GONE);
    }
    private void updateQuestion(){
        questionView.setText(questions.retQ(questionNumber));

    }
}
