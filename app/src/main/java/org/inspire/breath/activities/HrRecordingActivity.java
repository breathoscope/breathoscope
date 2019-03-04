package org.inspire.breath.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import org.inspire.breath.R;

public class HrRecordingActivity extends AppCompatActivity {

    private ImageButton mRecordBtn, mPauseBtn, mStopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_screen);

        findViews();

        mRecordBtn.setOnClickListener((v) -> {
            Toast.makeText(HrRecordingActivity.this, "Recording started",
                    Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_hr_recording);

        });

    }

    private void findViews() {
        this.mRecordBtn = findViewById(R.id.hr_record_btn);
        this.mPauseBtn = findViewById(R.id.hr_pause_btn);
        this.mStopBtn = findViewById(R.id.hr_stop_btn);
    }

}
