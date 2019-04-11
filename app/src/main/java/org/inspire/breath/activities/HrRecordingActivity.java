package org.inspire.breath.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.AudioRecord;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.blobs.HrCountTest;
import org.inspire.breath.utils.RawToWavConverter;
import org.inspire.breath.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HrRecordingActivity extends TestActivity {

    private final int RECORDER_AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    private final int RECORDER_SAMPLE_RATE = 44100;
    private final int RECORDER_CHANNEL_CFG = AudioFormat.CHANNEL_IN_MONO;
    private final int RECORDER_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private final int RECORDER_BUF_SIZE = AudioRecord.getMinBufferSize(
            RECORDER_SAMPLE_RATE,
            RECORDER_CHANNEL_CFG,
            RECORDER_AUDIO_FORMAT
    );

    private final int SECONDS = 10;

    private CountDownTimer cdt;

    private boolean isRecording;
    private boolean isPlaying;

    private Session session;
    private ByteArrayOutputStream baos;

    private String rawOutputPath;
    private String wavOutputPath;

    private Thread writeThread;

    private ImageButton mRecordBtn, mRestartBtn;
    private Button mPlayBtn, mConfirmBtn;
    private TextView mCountdown;
    private ImageView heartView;
    private AnimationDrawable heartAnimation;

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

        rawOutputPath = getApplicationContext().getExternalCacheDir().getAbsolutePath() + "/out.pcm";
        wavOutputPath = getApplicationContext().getExternalCacheDir().getAbsolutePath() + "/out.wav";

        isRecording = false;
        isPlaying = false;

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        displaySnackbar();

        initViews();
        initCountdown();
        setupListeners();
        
    }

    private void initViews() {
        this.mRecordBtn = findViewById(R.id.hr_record_btn);
        this.mRestartBtn = findViewById(R.id.hr_restart_btn);
        this.mPlayBtn = findViewById(R.id.recording_play_button);
        this.mConfirmBtn = findViewById(R.id.hr_confirm_btn);
        this.mCountdown = findViewById(R.id.hr_countdown);

        this.heartView = findViewById(R.id.imageView4);
        heartView.setBackgroundResource(R.drawable.animation_heart);
        heartAnimation = (AnimationDrawable) heartView.getBackground();

        mRestartBtn.setVisibility(View.INVISIBLE);
        mPlayBtn.setVisibility(View.INVISIBLE);
        mConfirmBtn.setVisibility(View.INVISIBLE);
        mCountdown.setVisibility(View.INVISIBLE);
    }

    private void toggleVisibleButtons() {

        if (mRecordBtn.getVisibility() == View.VISIBLE) {
            mRecordBtn.setVisibility(View.INVISIBLE);
            mRestartBtn.setVisibility(View.VISIBLE);
        } else {
            mRecordBtn.setVisibility(View.VISIBLE);
            mRestartBtn.setVisibility(View.INVISIBLE);
        }

    }
/**
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
 **/

    private void stopRecording() {
        toggleVisibleButtons();
        heartAnimation.stop();
        isRecording = false;
        /**
        if(recorder != null) {
            recorder.stop();
            recorder.release();
        }

        writeThread = null;

        storeRecording();
         **/
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
        /**
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
             **/
    }

    private void stopPlayingAudio() {

        mPlayBtn.setText(getString(R.string.recording_play));

        isPlaying = false;
        /**
        mp.stop();
        mp.release();
         **/
    }

    private void storeRecording() {

        session = getSession();
        session.setHrRecording(baos);

        Runnable r = () -> {
            AppRoomDatabase.getDatabase().sessionDao().updateRecording(session);
        };
        new Thread(r).start();

    }

    private void setupListeners() {
        mRecordBtn.setOnClickListener((v) -> {

//            initRecorder();

            heartAnimation.start();

            toggleVisibleButtons();

            mPlayBtn.setVisibility(View.INVISIBLE);
            mConfirmBtn.setVisibility(View.INVISIBLE);

//          recorder.startRecording();
            isRecording = true;
//            writeThread = new Thread(new AudioFileWriter());
//            writeThread.start();


            mCountdown.setVisibility(View.VISIBLE);
            mCountdown.setText(Integer.toString(SECONDS));
            cdt.start();

        });

        mRestartBtn.setOnClickListener((v) -> {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:

                            mPlayBtn.setVisibility(View.INVISIBLE);
                            mConfirmBtn.setVisibility(View.INVISIBLE);

                            cdt.cancel();
                            mCountdown.setVisibility(View.INVISIBLE);
                            initCountdown();
                            stopRecording();
                            Toast.makeText(HrRecordingActivity.this, "Recording cancelled", Toast.LENGTH_SHORT).show();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;

                        default:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(HrRecordingActivity.this);
            builder.setMessage(getString(R.string.recording_cancel_warning))
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener)
                    .show();

        });

        mPlayBtn.setOnClickListener((v) -> {
            if (!isPlaying) {
                playAudio();
            } else {
                stopPlayingAudio();
            }
        });

        mConfirmBtn.setOnClickListener((v) -> {

            if (isPlaying) {
                stopPlayingAudio();
            }

            // replace HrRecordingActivity with heart beat parsing activity
            Intent intent = new Intent(getApplicationContext(), HrCountActivity.class);
            intent.putExtra("HR_FILE", new File(wavOutputPath));
            intent.putExtra(SESSION_ID_KEY, getSession().getId());
            startActivityForResult(intent, 1);

        });

    }

    void initCountdown() {
        cdt = new CountDownTimer(SECONDS * 1000, 1000) {

            int seconds = SECONDS;

            public void onTick(long millis) {
                mCountdown.setText(Integer.toString(seconds--));
            }
            public void onFinish(){
                stopRecording();
                //rawToWav();
                mPlayBtn.setVisibility(View.VISIBLE);
                mConfirmBtn.setVisibility(View.VISIBLE);
                initCountdown();
                mCountdown.setVisibility(View.GONE);

            }
        };
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
                //recorder.read(audioData, 0, RECORDER_BUF_SIZE);
                try {
                    baos.write(audioData);
                    os.write(audioData, 0, RECORDER_BUF_SIZE );

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

}
