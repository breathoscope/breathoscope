package org.inspire.breath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;

import org.inspire.breath.activities.TestActivity;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.blobs.DangerTestResult;

public class DangerSignsGuide extends TestActivity {
    DangerSignsQuestions questions = new DangerSignsQuestions();
    boolean anyDangerSigns = false;
    boolean answered = false;
    byte results[] = new byte[4];
    TextView questionView;
    TextView questionTitleView;
    TextView answerView;
    TextView answerTitleView;
    Button leftButton;
    Button rightButton;
    Button nextButton;
    int questionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_signs_guide);
        questionView = (TextView)findViewById(R.id.question_txt);
        questionTitleView = (TextView)findViewById(R.id.question_title_txt);
        answerView = (TextView)findViewById(R.id.answer_txt);
        answerTitleView =(TextView)findViewById(R.id.answer_title_txt);
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
                    results[questionNumber] = 1;
                }
                else if(questionNumber == 3 && !answered){
                    String tmpStr = questions.retA(questionNumber) + "\n" + questions.anyDangerSigns();
                    answerView.setText(tmpStr);
                    answerView.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    answered = true;
                    results[questionNumber] = 1;
                }
                else if(questionNumber > 3){
                    //Store data.
                    DangerTestResult storage = new DangerTestResult();
                    storage.setResults(results);
                    Session tmp = getSession();
                    tmp.setDangerTestResultBlob(storage.toBlob());
                    AppRoomDatabase.getDatabase().sessionDao().insertRecording(tmp);
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
                    results[questionNumber] = 0;
                }
                else if(questionNumber == 3 && !anyDangerSigns && !answered) {
                    answerView.setText(questions.retA(questionNumber+1));
                    answerView.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    answered = true;
                    results[questionNumber] = 0;
                }
                else if(questionNumber == 3 && anyDangerSigns && !answered) {
                    answerView.setText(questions.anyDangerSigns());
                    answerView.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    answered = true;
                    results[questionNumber] = 0;
                }
                else if(questionNumber > 3)
                {
                    questionNumber = 0;
                    questionView.setText(questions.retQ(questionNumber));
                    leftButton.setText(questions.retO(0));
                    rightButton.setText(questions.retO(1));
                    answerView.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
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
                    questionTitleView.setText("Review");
                    answerTitleView.setVisibility(View.GONE);
                    answerView.setVisibility(View.GONE);
                    String reviewText = "The patient's status is as follows:\n\u2022" + questions.retRA(0, results[0]) + "\n\u2022" + questions.retRA(1, results[1]) +
                            "\n\u2022" + questions.retRA(2, results[2]) + "\n\u2022" + questions.retRA(3, results[3]);
                    questionView.setText(reviewText);
                    leftButton.setText("Correct");
                    rightButton.setText("Wrong");
                    nextButton.setVisibility(View.GONE);
                    answered = false;
                }
            }
        });
    }
}