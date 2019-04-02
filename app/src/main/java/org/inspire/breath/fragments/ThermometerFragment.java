package org.inspire.breath.fragments;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


import org.inspire.breath.R;

public class ThermometerFragment extends Fragment implements View.OnClickListener {

    private ThermometerViewModel mViewModel;
    TextView myTextFrequency;
    TextView myTextTemperature;
    TextView myTextUnits;

    public float currentRecording;
    public float fTemperatureCelsius;
    public float frequency;
    public int number_of_periods;
    public int number_of_samples;
    public int number_of_samples_temp;
    public int start_samples;
    public int stop_samples;

    RecorderThread recorderThread;
    public boolean recording;

    public MediaPlayer mp;

    public static ThermometerFragment newInstance() {
        return new ThermometerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            if (!ReadMsgAllowed()) {
                requestReadMsgPermission();
            }
        }
        View v = inflater.inflate(R.layout.thermometer_fragment, container, false);
        Button cv = v.findViewById(R.id.thermometerRecord);
        cv.setOnClickListener(this);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myTextFrequency = (TextView)getView().findViewById(R.id.textFrequency);
        myTextTemperature = (TextView)getView().findViewById(R.id.textTemperature);
        myTextUnits = (TextView)getView().findViewById(R.id.textUnits);

        mViewModel = ViewModelProviders.of(this).get(ThermometerViewModel.class);
    }

    @Override
    public void onClick(View v) {
        // TODO: Store data into session passed via Bundle
    }

    @Override
    public void onStart() {
        super.onStart();
        mp = new MediaPlayer();
        mp = MediaPlayer.create(this.getActivity(), R.raw.invert120s15khz);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setLooping(true);
        mp.start();

        AudioManager am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);


        recorderThread = new RecorderThread();
        recorderThread.start();
        recording = true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void requestReadMsgPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.RECORD_AUDIO));
        ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.RECORD_AUDIO},200);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==200)
        {
            if(grantResults.length>0&& grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getActivity().getApplicationContext(),"Permission Granted",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(),"Permission Rejected",Toast.LENGTH_LONG).show();
            }
        }


    }

    private boolean ReadMsgAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.RECORD_AUDIO);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    class RecorderThread extends Thread {


        public RecorderThread () {
        }

        @Override
        public void run() {
            AudioRecord recorder;
            short audioData[];
            int bufferSize,p;

            bufferSize=50000;

            recorder = new AudioRecord (MediaRecorder.AudioSource.MIC,44100, AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,bufferSize);
            audioData = new short [bufferSize];


            while (recording) {  //loop while recording is needed
                if (recorder.getState()==android.media.AudioRecord.STATE_INITIALIZED)
                    if (recorder.getRecordingState()==android.media.AudioRecord.RECORDSTATE_STOPPED)
                        recorder.startRecording();

                    else {

                        recorder.read(audioData,0,bufferSize); //read the PCM audio data into the audioData array

                        //Now we need to decode the PCM data using the Zero Crossings Method
                        p=0;
                        number_of_periods=0;
                        start_samples=0;
                        stop_samples=0;


                        while ((audioData[p]<0 && audioData[p+1]>=0)==false) {
                            p++;
                        }

                        for (number_of_samples=0;number_of_samples<=10000;number_of_samples++)
                        {

                            if(audioData[p]<0 && audioData[p+1]>=0) {
                                number_of_periods++;
                                number_of_samples_temp=0;
                            }
                            number_of_samples_temp++;
                            p++;
                        }

                        number_of_periods--;
                    }


                frequency =(float) ((float) 1/(((10000-number_of_samples_temp)/(float)number_of_periods)*0.0000226757));
                fTemperatureCelsius=(frequency/4)-273;

                handler1.sendMessage(handler1.obtainMessage());
            } //while recording

            if (recorder.getState()==android.media.AudioRecord.RECORDSTATE_RECORDING) recorder.stop();
            recorder.release();

        }

    }

    Handler handler1 = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            myTextUnits.setText("°C");
            myTextTemperature.setText(String.format( "%.1f", fTemperatureCelsius) );

            myTextFrequency.setText(String.format( "%.2f"+"Hz", frequency ) );
        }

    };
}
