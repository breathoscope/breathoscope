package org.inspire.breath.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.inspire.breath.R;

public class FeverMainQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fever_main_q);
    }

    public void Yes_Click(View view)
    {
        Intent intent = new Intent(this,MalariaActivity.class);
        startActivity(intent);
    }

    public void No_Click(View view)
    {
        Intent intent = new Intent(this,PneumoniaSeveritySigns.class);
        startActivity(intent);

    }
}
