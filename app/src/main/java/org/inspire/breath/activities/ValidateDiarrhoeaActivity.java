package org.inspire.breath.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import org.inspire.breath.R;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Recording;
import org.inspire.breath.data.RecordingDao;
import org.inspire.breath.data.blobs.DiarrhoeaTestResult;


public class ValidateDiarrhoeaActivity extends TestActivity {

    Button answerYes, answerNo;
    TextView question;
    private int questionNum = 0;
    private Recording session;


    public String mQuestions [] = {
            "Do you have more then 3 loose stools /24h?",
            "3 weeks without blood or rice water aspect?",
            "Signs of severe dehydration: Sunken eyes, Skin pinch >3 seconds or other danger signs?"
    };

    public String mAnswers [] = {
            "No need for treatment",
            "Refer to HC",
            "Uncomplicated diarrhoea: give ORS and Zinc sulfate according to age, plus Albendazole (if not received within last 6 months)"
    };

    public String getQuestion(int a){
        String question = mQuestions[a];
        return question;
    }

    public String getAnswer(int a){
        String answer = mAnswers[a];
        return answer;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_diarrhoea);

        answerYes = (Button) findViewById(R.id.buttonYes);
        answerNo = (Button) findViewById(R.id.buttonNo);
        question = (TextView) findViewById(R.id.question);


        question.setText(getQuestion(questionNum));

        answerYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNum<(mQuestions.length-1)) {
                    questionNum++;
                    question.setText(getQuestion(questionNum));
                }
                else if (questionNum>=(mQuestions.length-1)){
                    question.setText(getAnswer(1));

                    DiarrhoeaTestResult writer = new DiarrhoeaTestResult();
                    writer.setResult(1);
                    Recording a = getSession();
                    a.setDiarrhoeaTestResultBlob(writer.toBlob());
                    AppRoomDatabase.getDatabase()
                                    .recordingDao()
                                    .insertRecording(a);

                    answerYes.setVisibility(View.GONE);
                    answerNo.setVisibility(View.GONE);
                }


            }
        });

        answerNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question.setText(getAnswer(questionNum));

                DiarrhoeaTestResult writer = new DiarrhoeaTestResult();
                writer.setResult(questionNum);
                Recording a = getSession();
                a.setDiarrhoeaTestResultBlob(writer.toBlob());
                AppRoomDatabase.getDatabase()
                        .recordingDao()
                        .insertRecording(a);

                answerYes.setVisibility(View.GONE);
                answerNo.setVisibility(View.GONE);

            }
        });

    }



}
