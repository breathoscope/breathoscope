package org.inspire.breath.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.inspire.breath.R;

import java.io.IOException;

public class HrRecordingActivity extends AppCompatActivity {

    private Snackbar snackbar;
    private MediaRecorder recorder;
    private ImageButton mRecordBtn, mPauseBtn, mStopBtn;
    String fileName;

    // Requesting permission to RECORD_AUDIO
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_recording);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        displaySnackbar();

        findViews();
        mPauseBtn.setVisibility(View.GONE);
        mStopBtn.setVisibility(View.GONE);

        mRecordBtn.setOnClickListener((v) -> {

            toggleVisibleButtons();

            try {
                initRecorder();
            } catch (IOException e) {
                Log.e("tag","initRecorder() failed");
            }

            Toast.makeText(HrRecordingActivity.this, "Recording to " + fileName,
                    Toast.LENGTH_LONG).show();

            recorder.start();

        });

        mPauseBtn.setOnClickListener((v) -> {
            toggleVisibleButtons();
        });

        mStopBtn.setOnClickListener((v) -> {
            toggleVisibleButtons();
            Toast.makeText(HrRecordingActivity.this, "Recording stopped",
                    Toast.LENGTH_SHORT).show();
            recorder.stop();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recorder != null) {
            recorder.release();
        }
    }

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

    private void initRecorder() throws IOException {
        fileName = getExternalCacheDir().getAbsolutePath();
//        fileName += "/test.3gp";
        fileName += "/test.wav"; // changing file extension because my phone reads 3gp as video
        Toast.makeText(HrRecordingActivity.this, fileName, Toast.LENGTH_LONG).show();
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(fileName);
        recorder.prepare();
    }

    void displaySnackbar() {
        snackbar = Snackbar.make(findViewById(R.id.activity_hr_coordinator),
                getString(R.string.snackbar_mic_warning),
                Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

}
