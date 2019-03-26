package org.inspire.breath.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.AudioRecord;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Recording;
import org.inspire.breath.data.RecordingDao;
import org.inspire.breath.utils.RawToWavConverter;
import org.inspire.breath.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


// TODO restore main activity
public class HrRecordingActivity extends AppCompatActivity {

    private final int RECORDER_AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    private final int RECORDER_SAMPLE_RATE = 44100;
    private final int RECORDER_CHANNEL_CFG = AudioFormat.CHANNEL_IN_MONO;
    private final int RECORDER_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private final int RECORDER_BUF_SIZE = AudioRecord.getMinBufferSize(
            RECORDER_SAMPLE_RATE,
            RECORDER_CHANNEL_CFG,
            RECORDER_AUDIO_FORMAT
    );

    MediaPlayer mp;

    private ByteArrayOutputStream baos;
    String sessionId;
    Recording r;
    RecordingDao dao;

    private String rawOutputPath;
    private String wavOutputPath;

    private Thread writeThread;

    private AudioRecord recorder;
    private boolean isRecording;

    private ImageButton mRecordBtn, mRestartBtn, mStopBtn;
    private Button mPlayBtn;

    boolean isPlaying;

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

        initDb();

        rawOutputPath = getApplicationContext().getExternalCacheDir().getAbsolutePath() + "/out.pcm";
        wavOutputPath = getApplicationContext().getExternalCacheDir().getAbsolutePath() + "/out.wav";

        isRecording = false;
        isPlaying = false;

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        displaySnackbar();

        initViews();

        mRecordBtn.setOnClickListener((v) -> {

            initRecorder();

            toggleVisibleButtons();

            mPlayBtn.setVisibility(View.INVISIBLE);

            recorder.startRecording();
            isRecording = true;
            writeThread = new Thread(new AudioFileWriter());
            writeThread.start();

        });

        mRestartBtn.setOnClickListener((v) -> {

            mPlayBtn.setVisibility(View.INVISIBLE);

            stopRecording();
            Toast.makeText(HrRecordingActivity.this, "Session cancelled", Toast.LENGTH_SHORT).show();
        });

        mStopBtn.setOnClickListener((v) -> {
            stopRecording();
            rawToWav();
            mPlayBtn.setVisibility(View.VISIBLE);

        });

        mPlayBtn.setOnClickListener((v) -> {
            if (!isPlaying) {
                playAudio();
            } else {
                stopPlayingAudio();
            }
        });

    }

    private void initViews() {
        this.mRecordBtn = findViewById(R.id.hr_record_btn);
        this.mRestartBtn = findViewById(R.id.hr_restart_btn);
        this.mStopBtn = findViewById(R.id.hr_stop_btn);
        this.mPlayBtn = findViewById(R.id.recording_play_button);

        mRestartBtn.setVisibility(View.INVISIBLE);
        mStopBtn.setVisibility(View.INVISIBLE);
        mPlayBtn.setVisibility(View.INVISIBLE);
    }

    private void toggleVisibleButtons() {

        if (mRecordBtn.getVisibility() == View.VISIBLE) {
            mRecordBtn.setVisibility(View.INVISIBLE);
            mRestartBtn.setVisibility(View.VISIBLE);
            mStopBtn.setVisibility(View.VISIBLE);
        } else {
            mRecordBtn.setVisibility(View.VISIBLE);
            mRestartBtn.setVisibility(View.INVISIBLE);
            mStopBtn.setVisibility(View.INVISIBLE);
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

    private void stopRecording() {
        toggleVisibleButtons();
        isRecording = false;
        recorder.stop();
        recorder.release();
        writeThread = null;

        // TODO untested, take off main thread maybe
        r.setHrRecordingBlob(baos.toByteArray());


    }

    void displaySnackbar() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_hr_coordinator),
                getString(R.string.snackbar_mic_warning),
                Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    private void rawToWav() {

        try {
            new RawToWavConverter(RECORDER_SAMPLE_RATE).rawToWave(
                    new File(rawOutputPath),
                    new File(wavOutputPath)
            );

        } catch (Exception e) {
            Toast.makeText(HrRecordingActivity.this, "WAV CONVERSION FAILED", Toast.LENGTH_SHORT).show();
        }

    }

    private void playAudio() {

        mPlayBtn.setText(R.string.recording_stop);

        isPlaying = true;
        mp = new MediaPlayer();
        mp.setOnCompletionListener((c) -> {
           isPlaying = false;
           mPlayBtn.setText(getString(R.string.recording_play));
        });

        try {
            mp.setDataSource(wavOutputPath);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            Toast.makeText(HrRecordingActivity.this, "IOException", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlayingAudio() {

        mPlayBtn.setText(getString(R.string.recording_play));

        isPlaying = false;
        mp.stop();
        mp.release();
    }

    // untested
    private void initDb() {
        Intent i = getIntent();
        // TODO confirm following string name
        sessionId = i.getStringExtra("sessionId");

        dao = AppRoomDatabase.getDatabase().recordingDao();
        r = dao.getRecordingById(Integer.parseInt(sessionId)).get(0);
    }

    // thread to save raw data to app's cache directory
    // overwrites previous recording
    class AudioFileWriter implements Runnable {
        public void run() {

            byte[] audioData = new byte[RECORDER_BUF_SIZE];

            FileOutputStream os = null;
            try {
                os = new FileOutputStream(rawOutputPath);
                baos = new ByteArrayOutputStream();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while (isRecording) {
                recorder.read(audioData, 0, RECORDER_BUF_SIZE);
                try {
                    baos.write(audioData);
                    os.write(audioData, 0, RECORDER_BUF_SIZE );

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
