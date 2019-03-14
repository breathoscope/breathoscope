package org.inspire.breath.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.inspire.breath.R;

public class ChildAge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_age);
    }


    public void Age_Click(View view){
        String value = ( (EditText)findViewById(R.id.age)).getText().toString();
        int age = Integer.parseInt(value);
        if ( age<=5 && age>2){
            Intent intent = new Intent(this,Understanding_results.class);
            startActivity(intent);

        }
        else if (age<=2 || age>5) {
            Intent intent = new Intent(this,Refer_HC.class);
            startActivity(intent);
        }
    }

    public static class Severe_AND_Pos extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_severe__and__pos);
        }
    }
}
