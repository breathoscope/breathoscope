package org.inspire.breath.fragments.home;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.inspire.breath.R;
import org.inspire.breath.activities.BreathRateActivity;
import org.inspire.breath.activities.HomeActivity;
import org.inspire.breath.activities.MalariaActivity;
import org.inspire.breath.activities.ThermometerActivity;
import org.inspire.breath.activities.ValidateDiarrhoeaActivity;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.blobs.BreathTestResult;
import org.inspire.breath.data.blobs.DangerTestResult;
import org.inspire.breath.data.blobs.DiarrhoeaTestResult;
import org.inspire.breath.data.blobs.FeverTestResult;
import org.inspire.breath.data.blobs.MalariaTestResult;
import org.inspire.breath.data.blobs.RecommendActionsResult;
import org.inspire.breath.utils.FragmentedFragment;

public class Testing extends FragmentedFragment {

    // Tests
    private AppCompatCheckBox mFeverTick;
    private AppCompatCheckBox mDangerTick;
    private AppCompatCheckBox mDiarrhoeaTick;
    private AppCompatCheckBox mMalariaTick;
    private AppCompatCheckBox mBreathTick;

    // Cards
    private CardView mMalariaCard;
    private CardView mFeverCard;
    private CardView mDangerCard;
    private CardView mDiarrhoeaCard;
    private CardView mBreathCard;
    private Patient mPatient;
    private Session mSession;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_testing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setupListeners();
    }

    private void findViews(View root) {
        this.mBreathTick = root.findViewById(R.id.home_test_breath_tick);
        this.mDangerTick = root.findViewById(R.id.home_test_danger_tick);
        this.mDiarrhoeaTick = root.findViewById(R.id.home_test_diarrhoea_tick);
        this.mFeverTick = root.findViewById(R.id.home_test_fever_tick);
        this.mMalariaTick = root.findViewById(R.id.home_test_malaria_tick);

        this.mMalariaCard = root.findViewById(R.id.home_test_malaria_card);
        mMalariaCard.setVisibility(View.GONE);
        this.mBreathCard = root.findViewById(R.id.home_test_breath_card);
        this.mDiarrhoeaCard = root.findViewById(R.id.home_test_diarrhoea_card);
        this.mFeverCard = root.findViewById(R.id.home_test_fever_card);
        this.mDangerCard = root.findViewById(R.id.home_test_danger_card);
    }

    public void getData() {
        Intent intent = getActivity().getIntent();
        int patient_id = intent.getIntExtra(HomeActivity.PATIENT_ID_KEY, -1);
        int session_id = intent.getIntExtra(HomeActivity.SESSION_ID_KEY, -1);

        this.mPatient = AppRoomDatabase.getDatabase().patientDao().getPatientById(patient_id);
        this.mSession = AppRoomDatabase.getDatabase().sessionDao().getRecordingById(session_id);

        if(mSession.getRecommendedActions() == null) {
            mSession.setRecommendedActionsResultBlob(new RecommendActionsResult().toBlob());
            AppRoomDatabase.getDatabase().sessionDao().upsertRecording(mSession);
        }

        // Test data
        FeverTestResult feverTestResult = this.mSession.getFeverTestResult();
        MalariaTestResult malariaTestResult = this.mSession.getMalariaTestResult();
        BreathTestResult breathTestResult = this.mSession.getBreathTestResult();
        DiarrhoeaTestResult diarrhoeaTestResult = this.mSession.getDiarrhoeaTestResult();
        DangerTestResult dangerTestResult = this.mSession.getDangerTestResult();
        RecommendActionsResult recommendActionsResult = this.mSession.getRecommendedActions();

        if(feverTestResult != null && feverTestResult.shouldPerformMalariaTest())
            mMalariaCard.setVisibility(View.VISIBLE);

        if (recommendActionsResult != null) {
            //if (recommendActionsResult.isUrgent) {
            //    Intent i = new Intent(this, RecommendedActionsActivity.class);
            //    i.putExtra(SESSION_ID_KEY, mSession.getId());
            //    startActivity(i);
            //}
        }
        if (feverTestResult != null)
            mFeverTick.setChecked(true);
        if (malariaTestResult != null)
            mMalariaTick.setChecked(true);
        if (diarrhoeaTestResult != null)
            mDiarrhoeaTick.setChecked(true);
        if (dangerTestResult != null)
            mDangerTick.setChecked(true);
        if (breathTestResult != null)
            mBreathTick.setChecked(true);
    }

    private void setupListeners() {
        System.out.println("setting up listeners");
        System.out.println(this.mMalariaCard);
        this.mMalariaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked malaria");
                Intent intent = new Intent(getActivity(), MalariaActivity.class);
                intent.putExtra(HomeActivity.SESSION_ID_KEY, mSession.getId());
                startActivity(intent);
            }
        });
        this.mDangerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "NYI", Toast.LENGTH_SHORT).show();
            }
        });
        this.mFeverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThermometerActivity.class);
                intent.putExtra(HomeActivity.SESSION_ID_KEY, mSession.getId());
                startActivity(intent);
            }
        });
        this.mBreathCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BreathRateActivity.class);
                intent.putExtra(HomeActivity.SESSION_ID_KEY, mSession.getId());
                startActivity(intent);
            }
        });
        this.mDiarrhoeaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "NYI", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ValidateDiarrhoeaActivity.class);
                intent.putExtra(HomeActivity.SESSION_ID_KEY, mSession.getId());
                startActivity(intent);
            }
        });
    }
}
