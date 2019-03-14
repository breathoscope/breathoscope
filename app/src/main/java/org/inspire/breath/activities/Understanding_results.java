package org.inspire.breath.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import org.inspire.breath.R;

public class Understanding_results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_understanding_results);
    }

    public void Positive_Click(View view) {

        Intent intent = new Intent(this, Severe_malaria.class);
        startActivity(intent);
    }

    public void Negative_Click(View view) {

        Intent intent = new Intent(this, Refer_HC.class);
        startActivity(intent);
    }

    public void Invalid_Click(View view) {

        Intent intent = new Intent(this, Understanding_results.class);
        startActivity(intent);
    }
}
