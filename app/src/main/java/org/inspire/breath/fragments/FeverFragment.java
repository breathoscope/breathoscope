package org.inspire.breath.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.inspire.breath.R;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;
import org.inspire.breath.data.PatientDao;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.SessionDao;
import org.inspire.breath.data.blobs.FeverTestResult;
import org.inspire.breath.data.blobs.RecommendActionsResult;

public class FeverFragment extends Fragment {

    public static FeverFragment newInstance() {
        return new FeverFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fever, container, false);
        Button yes = v.findViewById(R.id.fever_prompt_yes);
        Button no = v.findViewById(R.id.fever_prompt_no);
        ThermometerViewModel viewModel = ViewModelProviders.of(getActivity()).get(ThermometerViewModel.class);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateOutcome(true, viewModel.temperature, viewModel);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateOutcome(false, viewModel.temperature, viewModel);
            }
        });

        return v;
    }

    void ValidateOutcome(boolean feverRecently, float temperature, ThermometerViewModel viewModel) {
        SessionDao dao = AppRoomDatabase.getDatabase().sessionDao();
        PatientDao patientDao = AppRoomDatabase.getDatabase().patientDao();
        Session session = AppRoomDatabase.getDatabase().sessionDao().getRecordingById(viewModel.sessionID);
        Patient currentPatient = patientDao.getPatientById(session.getPatientId());

        FeverTestResult result = new FeverTestResult();
        result.setTemperature(temperature);
        result.setHasFever(feverRecently);

        RecommendActionsResult actionsResult = session.getRecommendedActions();

        if(result.hasFever()) {
            actionsResult.isUrgent = true;
            int age = Integer.parseInt(currentPatient.getAge());
            if (age < 1 || age > 5) {
                result.setShouldPerformMalaria(false);
                actionsResult.addAction(RecommendActionsResult.Test.FEVER, "Refer to HC immediately");
            }
            else {
                result.setShouldPerformMalaria(true);
                actionsResult.addAction(RecommendActionsResult.Test.FEVER, "Perform Malaria RDT");
            }
        }

        session.setFeverTestResult(result);
        session.setRecommendedActionsResultBlob(actionsResult.toBlob());
        dao.upsertRecording(session);
        getActivity().finish();


    }
}
