package org.inspire.breath.fragments;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.inspire.breath.R;
import org.inspire.breath.activities.BreathRateActivity;
import org.inspire.breath.activities.HomeActivity;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Recording;
import org.inspire.breath.data.RecordingDao;
import org.inspire.breath.data.blobs.BreathTestResult;

public class BreathRateFragment extends Fragment {

    private BreathRateViewModel mViewModel;

    public static BreathRateFragment newInstance() {
        return new BreathRateFragment();
    }
    public static int count = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.breath_rate_fragment, container, false);
        TextView countdown = v.findViewById(R.id.countdowntimer);
        TextView countAmount = v.findViewById(R.id.tapCount); //bonus points for rhyming
        Button startRecording = v.findViewById(R.id.breathRecording);
        Button tapTest = v.findViewById(R.id.tapTest);

        startRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecording.setEnabled(false);
                tapTest.setEnabled(true);
                count = 0;
                countAmount.setText(count + "");
                new CountDownTimer(60000, 1000) {
                    public void onTick(long millis) {
                        countdown.setText((millis/1000) + "");
                    }

                    public void onFinish() {
                        startRecording.setEnabled(true);
                        tapTest.setEnabled(false);
                        countdown.setText(60+"");
                        if(count != 0) {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) { // DOUBLE BONUS POINTS FOR RHYMING
                                        case DialogInterface.BUTTON_POSITIVE:
                                            RecordingDao dao = AppRoomDatabase.getDatabase().recordingDao();
                                            BreathRateViewModel viewModel = ViewModelProviders.of(getActivity()).get(BreathRateViewModel.class);
                                            Recording r = dao.getRecordingById(viewModel.sessionID).get(0);
                                            BreathTestResult result = new BreathTestResult();
                                            result.setBreathrate(count);
                                            r.setBreathTestResultBlob(result.toBlob());
                                            dao.upsertRecording(r);
                                            getActivity().finish();
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            //No button clicked
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage(getResources().getString(R.string.confirmdialog, count)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                                    .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                        }
                    }
                }.start();
            }
        });

        tapTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                countAmount.setText(count + "");
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BreathRateViewModel.class);
        // TODO: Use the ViewModel
    }

}