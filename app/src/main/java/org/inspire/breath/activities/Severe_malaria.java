package org.inspire.breath.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import org.inspire.breath.R;

public class Severe_malaria extends AppCompatActivity  {
    public static boolean severeMalariaCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_severe_malaria);
    }

    public void YesSev_Click(View view) {
        severeMalariaCheck =true;
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    public void NoSev_Click(View view) {
        severeMalariaCheck = false;
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
