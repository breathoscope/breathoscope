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

    String answer;
    int questionNumber = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_signs_guide);



        //Button listener 1
        buttonChoice1.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                //Button logic

                if (buttonChoice1.getText() == )
            }
        }
    }


}
