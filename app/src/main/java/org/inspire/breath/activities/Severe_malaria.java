package org.inspire.breath.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import org.inspire.breath.R;

public class Severe_malaria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_severe_malaria);
    }


    public void YesSev_Click(View view) {

        Intent intent = new Intent(this, Severe_And_Pos.class);
        startActivity(intent);
    }
    public void NoSev_Click(View view) {

        Intent intent = new Intent(this, NonSevereMalariaTreatment.class);
        startActivity(intent);
    }
}
