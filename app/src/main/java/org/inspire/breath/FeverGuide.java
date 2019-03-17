package org.inspire.breath;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    int questionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fever_guide);
        questionView = (TextView)findViewById(R.id.question_txt);
        answerView = (TextView)findViewById(R.id.answer_txt);
        leftButton = (Button)findViewById(R.id.yes_btn);
        rightButton = (Button)findViewById(R.id.no_btn);
        nextButton = (Button)findViewById(R.id.next_btn);

        questionView.setText(questions.retQ(questionNumber));
        leftButton.setText(questions.retAQ(questionNumber, 0));
        rightButton.setText(questions.retAQ(questionNumber, 1));
        answerView.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);

        //Button listener 1
        leftButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                if (questionNumber < 4){
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

        //Button listener 2
        rightButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                if (questionNumber < 4){
                    answerView.setText(questions.retA(6));
                    answerView.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                }
                else if(questionNumber == 4)
                {
                    answerView.setText(questions.retA(questionNumber+1));
                    answerView.setVisibility(View.VISIBLE);
                }
            }
        });

        //Button listener 3
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                if (questionNumber<4){
                    questionNumber++;
                    questionView.setText(questions.retQ(questionNumber));
                    answerView.setVisibility(View.GONE);

                    leftButton.setText(questions.retAQ(questionNumber, 0));
                    rightButton.setText(questions.retAQ(questionNumber, 1));
                    nextButton.setVisibility(View.GONE);
                }
                else if(questionNumber==4){
                    questionNumber++;
                    leftButton.setVisibility(View.GONE);
                    rightButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
                }
            }
        });
    }
}
