package org.inspire.breath;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FeverGuide extends AppCompatActivity {

    FeverQuestions questions = new FeverQuestions();

    TextView questionView;
    TextView answerView;

    Button leftButton;
    Button rightButton;
    Button nextButton;

    String answer;
    int questionNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fever_guide);
        hideAnswer();
        questionView = (TextView)findViewById(R.id.question_txt);
        answerView = (TextView)findViewById(R.id.answer_txt);
        leftButton = (Button)findViewById(R.id.yes_btn);
        rightButton = (Button)findViewById(R.id.no_btn);
        nextButton = (Button)findViewById(R.id.next_btn);

        questionView.setText(questions.retQ(questionNumber));
        leftButton.setText(questions.retAQ1(0));
        rightButton.setText(questions.retAQ1(1));
        answerView.setVisibility(View.GONE);

        //Button listener 1
        leftButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                if (questionNumber == 0){
                    answerView.setText(questions.retA(questionNumber));
                    answerView.setVisibility(View.VISIBLE);
                }
                else if(questionNumber == 1)
                {
                    answerView.setText(questions.retA(questionNumber));
                    answerView.setVisibility(View.VISIBLE);
                }
                else if(questionNumber == 2)
                {
                    answerView.setText(questions.retA(questionNumber));
                    answerView.setVisibility(View.VISIBLE);
                }
                else if(questionNumber == 3)
                {
                    answerView.setText(questions.retA(questionNumber));
                    answerView.setVisibility(View.VISIBLE);
                }
                else if(questionNumber == 4)
                {
                    answerView.setText(questions.retA(questionNumber+1));
                    answerView.setVisibility(View.VISIBLE);
                }
            }
        });

        //Button listener 1
        rightButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                if (questionNumber == 0){
                    answerView.setText(questions.retA(questionNumber));
                    answerView.setVisibility(View.VISIBLE);
                }
                else if(questionNumber == 1)
                {
                    answerView.setText(questions.retA(questionNumber));
                    answerView.setVisibility(View.VISIBLE);
                }
                else if(questionNumber == 2)
                {
                    answerView.setText(questions.retA(questionNumber));
                    answerView.setVisibility(View.VISIBLE);
                }
                else if(questionNumber == 3)
                {
                    answerView.setText(questions.retA(questionNumber));
                    answerView.setVisibility(View.VISIBLE);
                }
                else if(questionNumber == 4)
                {
                    answerView.setText(questions.retA(questionNumber+1));
                    answerView.setVisibility(View.VISIBLE);
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
        if(answer.equals("Yes")){
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
