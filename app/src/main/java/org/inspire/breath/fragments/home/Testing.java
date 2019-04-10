package org.inspire.breath.fragments.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.inspire.breath.R;
import org.inspire.breath.activities.BreathRateActivity;
import org.inspire.breath.activities.HomeActivity;
import org.inspire.breath.activities.HrRecordingActivity;
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
import org.inspire.breath.data.blobs.HeartRateTestResult;
import org.inspire.breath.data.blobs.HrCountTest;
import org.inspire.breath.data.blobs.MalariaTestResult;
import org.inspire.breath.data.blobs.RecommendActionsResult;
import org.inspire.breath.utils.FragmentedFragment;

public class Testing extends FragmentedFragment {

    private static final int TEST_CODE = 1;
    // Tests
    private AppCompatCheckBox mFeverTick;
    private AppCompatCheckBox mDangerTick;
    private AppCompatCheckBox mDiarrhoeaTick;
    private AppCompatCheckBox mMalariaTick;
    private AppCompatCheckBox mBreathTick;
    private AppCompatCheckBox mHeartTick;

    // Cards
    private CardView mMalariaCard;
    private CardView mFeverCard;
    private CardView mDangerCard;
    private CardView mDiarrhoeaCard;
    private CardView mBreathCard;
    private CardView mHeartCard;

    private Patient mPatient;
    private Session mSession;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_testing, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        getData();
        setupListeners();
    }

    public void reSetup() {
        findViews(getView());
        getData();
        setupListeners();
    }

    private void findViews(View root) {
        this.mBreathTick = root.findViewById(R.id.home_test_breath_tick);
        this.mDangerTick = root.findViewById(R.id.home_test_danger_tick);
        this.mDiarrhoeaTick = root.findViewById(R.id.home_test_diarrhoea_tick);
        this.mFeverTick = root.findViewById(R.id.home_test_fever_tick);
        this.mMalariaTick = root.findViewById(R.id.home_test_malaria_tick);
        this.mHeartTick = root.findViewById(R.id.home_test_heart_tick);

        this.mMalariaCard = root.findViewById(R.id.home_test_malaria_card);
        mMalariaCard.setVisibility(View.GONE);
        this.mBreathCard = root.findViewById(R.id.home_test_breath_card);
        this.mDiarrhoeaCard = root.findViewById(R.id.home_test_diarrhoea_card);
        this.mFeverCard = root.findViewById(R.id.home_test_fever_card);
        this.mDangerCard = root.findViewById(R.id.home_test_danger_card);
        this.mHeartCard = root.findViewById(R.id.home_test_heart_card);
    }

    public void getData() {

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
        HrCountTest heartRateTestResult = this.mSession.getHrCount();
        RecommendActionsResult recommendActionsResult = this.mSession.getRecommendedActions();
        System.out.println(feverTestResult);
        if(feverTestResult != null && feverTestResult.shouldPerformMalariaTest())
            mMalariaCard.setVisibility(View.VISIBLE);

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
        if (heartRateTestResult != null)
            mHeartTick.setChecked(true);
    }

    private void setupListeners() {
        this.mMalariaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MalariaActivity.class);
                intent.putExtra(HomeActivity.SESSION_ID_KEY, mSession.getId());
                getActivity().startActivityForResult(intent, TEST_CODE);
            }
        });
        this.mDangerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        this.mFeverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThermometerActivity.class);
                intent.putExtra(HomeActivity.SESSION_ID_KEY, mSession.getId());
                getActivity().startActivityForResult(intent, TEST_CODE);
            }
        });
        this.mBreathCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BreathRateActivity.class);
                intent.putExtra(HomeActivity.SESSION_ID_KEY, mSession.getId());
                getActivity().startActivityForResult(intent, TEST_CODE);
            }
        });
        this.mDiarrhoeaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ValidateDiarrhoeaActivity.class);
                intent.putExtra(HomeActivity.SESSION_ID_KEY, mSession.getId());
                getActivity().startActivityForResult(intent, TEST_CODE);
            }
        });
        this.mHeartCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HrRecordingActivity.class);
                intent.putExtra(HomeActivity.SESSION_ID_KEY, mSession.getId());
                startActivity(intent);
            }
        });
    }

    public void setData(Session session, Patient patient) {
        this.mSession = session;
        this.mPatient = patient;
    }

    Session getSession() {
        return AppRoomDatabase.getDatabase().sessionDao().getRecordings(mPatient.getPatientId()).get(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSession = getSession();
        getData();
    }
}
