package org.inspire.breath.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.inspire.breath.R;

public class PneumoniaSeveritySigns extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pneumonia_severity_signs);
    }


    public void YesPnSev_Click(View view) {

        Intent intent = new Intent(this, SeverePneumoniaTreatment.class);
        startActivity(intent);
    }

    public void NoPnSev_Click(View view) {

        Intent intent = new Intent(this,NonSeverePneumoniaTreatment.class);
        startActivity(intent);
    }
}
