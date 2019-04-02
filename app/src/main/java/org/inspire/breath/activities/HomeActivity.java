package org.inspire.breath.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.inspire.breath.R;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.blobs.BreathTestResult;
import org.inspire.breath.data.blobs.DangerTestResult;
import org.inspire.breath.data.blobs.DiarrhoeaTestResult;
import org.inspire.breath.data.blobs.FeverTestResult;
import org.inspire.breath.data.blobs.MalariaTestResult;
import org.inspire.breath.data.blobs.RecommendActionsResult;

public class HomeActivity extends AppCompatActivity {

    public static final String PATIENT_ID_KEY = "PATIENT_ID_KEY";
    public static final String SESSION_ID_KEY = "SESSION_ID_KEY";
    private static final int TEST_COUNT = 5;

    private Patient mPatient;
    private Session mSession;
    private TextView mPatientName;
    private TextView mPatientAge;
    private TextView mPatientSex;
    private ImageView mPatientPicture;

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

    public void getData() {
        Intent intent = getIntent();
        int patient_id = intent.getIntExtra(PATIENT_ID_KEY, -1);
        int session_id = intent.getIntExtra(SESSION_ID_KEY, -1);

        this.mPatient = AppRoomDatabase.getDatabase().patientDao().getPatientById(patient_id);
        this.mSession = AppRoomDatabase.getDatabase().sessionDao().getRecordingById(session_id);

        // Patient data
        this.mPatientName.setText(mPatient.getFirstName() + " " + mPatient.getLastName());
        this.mPatientAge.setText(mPatient.getAge());
        this.mPatientSex.setText(mPatient.getSex());
        byte[] bmp = mPatient.getThumb();
        this.mPatientPicture.setImageBitmap(BitmapFactory.decodeByteArray(bmp, 0, bmp.length));

        // Test data
        FeverTestResult feverTestResult = this.mSession.getFeverTestResult();
        MalariaTestResult malariaTestResult = this.mSession.getMalariaTestResult();
        BreathTestResult breathTestResult = this.mSession.getBreathTestResult();
        DiarrhoeaTestResult diarrhoeaTestResult = this.mSession.getDiarrhoeaTestResult();
        DangerTestResult dangerTestResult = this.mSession.getDangerTestResult();
        RecommendActionsResult recommendActionsResult = this.mSession.getRecommendedActions();

        if (recommendActionsResult != null) {
            if (recommendActionsResult.isUrgent) {
                Intent i = new Intent(this, RecommendedActionsActivity.class);
                i.putExtra(SESSION_ID_KEY, mSession.getId());
                startActivity(i);
            }

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


    private void findViews() {
        this.mPatientName = findViewById(R.id.home_patient_name);
        this.mPatientAge = findViewById(R.id.home_patient_age);
        this.mPatientSex = findViewById(R.id.home_patient_sex);
        this.mPatientPicture = findViewById(R.id.home_patient_picture);

        this.mBreathTick = findViewById(R.id.home_test_breath_tick);
        this.mDangerTick = findViewById(R.id.home_test_danger_tick);
        this.mDiarrhoeaTick = findViewById(R.id.home_test_diarrhoea_tick);
        this.mFeverTick = findViewById(R.id.home_test_fever_tick);
        this.mMalariaTick = findViewById(R.id.home_test_malaria_tick);

        this.mMalariaCard = findViewById(R.id.home_test_malaria_card);
        this.mBreathCard = findViewById(R.id.home_test_breath_card);
        this.mDiarrhoeaCard = findViewById(R.id.home_test_diarrhoea_card);
        this.mFeverCard = findViewById(R.id.home_test_fever_card);
        this.mDangerCard = findViewById(R.id.home_test_danger_card);
    }

    private void setupListeners() {
        this.mMalariaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MalariaActivity.class);
                intent.putExtra(SESSION_ID_KEY, mSession.getId());
                startActivity(intent);
            }
        });
        this.mDangerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "NYI", Toast.LENGTH_SHORT).show();
            }
        });
        this.mFeverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ThermometerActivity.class);
                intent.putExtra(SESSION_ID_KEY, mSession.getId());
                startActivity(intent);
            }
        });
        this.mBreathCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BreathRateActivity.class);
                intent.putExtra(SESSION_ID_KEY, mSession.getId());
                startActivity(intent);
            }
        });
        this.mDiarrhoeaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "NYI", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, ValidateDiarrhoeaActivity.class);
                intent.putExtra(SESSION_ID_KEY, mSession.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViews();
        setupListeners();
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_home);
        findViews();
        setupListeners();
        getData();
    }
}
