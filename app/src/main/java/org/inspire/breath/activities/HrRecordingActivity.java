package org.inspire.breath.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.media.AudioRecord;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.inspire.breath.utils.RawToWavConverter;

import org.inspire.breath.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HrRecordingActivity extends AppCompatActivity {

    // TODO switch main activity back to LoginActivity before merge

    private static final int RECORDER_AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    private static final int RECORDER_SAMPLE_RATE = 44100;
    private static final int RECORDER_CHANNEL_CFG = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final int RECORDER_BUF_SIZE = AudioRecord.getMinBufferSize(
            RECORDER_SAMPLE_RATE,
            RECORDER_CHANNEL_CFG,
            RECORDER_AUDIO_FORMAT
    );

    private Thread writeThread;

    private AudioRecord recorder;
    private boolean isRecording;

    private ImageButton mRecordBtn, mPauseBtn, mStopBtn;
    String fileName;

    // Requesting permission to RECORD_AUDIO
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_recording);

        initRecorder();
        isRecording = false;

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        displaySnackbar();

        initViews();

        mRecordBtn.setOnClickListener((v) -> {

            toggleVisibleButtons();

            recorder.startRecording();
            isRecording = true;
            writeThread = new Thread(new AudioFileWriter());
            writeThread.start();

        });


        // TODO make pause btn a restart btn instead
        mPauseBtn.setOnClickListener((v) -> {
            toggleVisibleButtons();
        });

        mStopBtn.setOnClickListener((v) -> {
            toggleVisibleButtons();
            isRecording = false;
            recorder.stop();
            recorder.release();
            writeThread = null;

            rawToWav();

        });

    }

    private void initViews() {
        this.mRecordBtn = findViewById(R.id.hr_record_btn);
        this.mPauseBtn = findViewById(R.id.hr_pause_btn);
        this.mStopBtn = findViewById(R.id.hr_stop_btn);

        mPauseBtn.setVisibility(View.GONE);
        mStopBtn.setVisibility(View.GONE);
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

    private void initRecorder() {

        recorder = new AudioRecord.Builder()
                .setAudioSource(RECORDER_AUDIO_SOURCE)
                .setAudioFormat(new AudioFormat.Builder()
                        .setEncoding(RECORDER_AUDIO_FORMAT)
                        .setSampleRate(RECORDER_SAMPLE_RATE)
                        .setChannelMask(RECORDER_CHANNEL_CFG)
                        .build())
                .setBufferSizeInBytes(RECORDER_BUF_SIZE)
                .build();

    }


    void displaySnackbar() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_hr_coordinator),
                getString(R.string.snackbar_mic_warning),
                Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    private void rawToWav() {

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {}

        try {
            new RawToWavConverter(RECORDER_SAMPLE_RATE).rawToWave(
                    new File(getExternalCacheDir().getAbsolutePath() + "/test.pcm"),
                    new File(getExternalCacheDir().getAbsolutePath() + "/test.wav")
            );


        } catch (Exception e) {
            Toast.makeText(HrRecordingActivity.this, "WAV CONVERSION FAILED", Toast.LENGTH_SHORT).show();
        }

    }

    class AudioFileWriter implements Runnable {
        public void run() {

            byte[] audioData = new byte[RECORDER_BUF_SIZE];

            fileName = getExternalCacheDir().getAbsolutePath();
            fileName += "/test.pcm";
            FileOutputStream os = null;
            try {
                os = new FileOutputStream(fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while (isRecording) {
                recorder.read(audioData, 0, RECORDER_BUF_SIZE);

                try {
                    os.write(audioData, 0, RECORDER_BUF_SIZE );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
