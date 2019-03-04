package org.inspire.breath.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.inspire.breath.R;

//import static android.media.MediaRecorder.AudioSource.MIC;

public class HrRecordingActivity extends AppCompatActivity {

    //private MediaRecorder recorder;
    private ImageButton mRecordBtn, mPauseBtn, mStopBtn;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_recording);

        findViews();
        mPauseBtn.setVisibility(View.GONE);
        mStopBtn.setVisibility(View.GONE);

        mRecordBtn.setOnClickListener((v) -> {
            Toast.makeText(HrRecordingActivity.this, "Recording started",
                    Toast.LENGTH_SHORT).show();
            toggleVisibleButtons();
        });

        mPauseBtn.setOnClickListener((v) -> {
            toggleVisibleButtons();
        });

        // mRecordBtn.setVisibility(View.INVISIBLE);

    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (recorder != null) {
//            recorder.release();
//        }
//    }

    private void findViews() {
        this.mRecordBtn = findViewById(R.id.hr_record_btn);
        this.mPauseBtn = findViewById(R.id.hr_pause_btn);
        this.mStopBtn = findViewById(R.id.hr_stop_btn);
    }

    private void toggleVisibleButtons() {
        if (mRecordBtn.getVisibility() == View.VISIBLE) {
            mRecordBtn.setVisibility(View.GONE);
            mPauseBtn.setVisibility(View.VISIBLE);
            mStopBtn.setVisibility(View.VISIBLE);
        } else {
            mRecordBtn.setVisibility(View.VISIBLE);
            mPauseBtn.setVisibility(View.GONE);
            mStopBtn.setVisibility(View.GONE);
        }
    }

//    private void initRecorder() throws IOException {
//        fileName = getExternalCacheDir().getAbsolutePath();
//        fileName += "/test.3gp";
//        Toast.makeText(HrRecordingActivity.this, fileName, Toast.LENGTH_LONG).show();
//        recorder = new MediaRecorder();
//        recorder.setAudioSource(MIC);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        recorder.setOutputFile(fileName);
//        recorder.prepare();
//
//    }

}
