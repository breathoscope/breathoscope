package org.inspire.breath.fragments.patients;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.inspire.breath.R;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;

import java.util.Calendar;

public class Create extends PatientsFragment {

    private static final int IMAGE_REQUEST_CODE = 1;

    EditText mFirstName;
    EditText mSurName;
    EditText mBirthday;
    Spinner mSexSpinner;

    ConstraintLayout mPictureHolder;
    ImageView mProfilePicture;

    Button mSave;

    void findViews(View root) {
        this.mFirstName = root.findViewById(R.id.create_first_name);
        this.mSurName = root.findViewById(R.id.create_surname);
        this.mBirthday = root.findViewById(R.id.create_birthday);
        this.mSexSpinner = root.findViewById(R.id.create_sex_spinner);

        this.mPictureHolder = root.findViewById(R.id.create_picture_holder);
        this.mProfilePicture = root.findViewById(R.id.create_picture);

        this.mSave = root.findViewById(R.id.create_save);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_patient_create, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setupListeners();
        setupData();
    }

    private void setupListeners() {
        this.mPictureHolder.setOnClickListener(v -> {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), IMAGE_REQUEST_CODE);
        });
        this.mBirthday.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            new DatePickerDialog(getActivity(),
                    (view, year, month, dayOfMonth) -> {
                        mBirthday.setText(new String(dayOfMonth + "/" + (month+1) + "/" + year));
                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)).show();
        });
        this.mSave.setOnClickListener(v -> {
            String fName = mFirstName.getText().toString();
            String sName = mSurName.getText().toString();
            String bDay = mBirthday.getText().toString();
            String sex = mSexSpinner.getSelectedItem().toString();
            Patient patient = new Patient();
            patient.setSex(sex);
            patient.setBirthDay(bDay);
            patient.setLastName(sName);
            patient.setFirstName(fName);
            AppRoomDatabase.getDatabase()
                    .patientDao()
                    .insertPatient(patient);
            getActivity().onBackPressed();
        });
    }

    private void setupData() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sexes_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mSexSpinner.setAdapter(adapter);

    }

    @Override
    public void onFocus() {
        super.onFocus();
        getPatientsActivity().mAddPatientFAB.hide();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && data != null) {
            Bundle extras = data.getExtras();
            Bitmap bmp = (Bitmap) extras.get("data");
            mProfilePicture.setImageBitmap(bmp);
            mProfilePicture.setLayoutParams(new ConstraintLayout.LayoutParams(Constraints.LayoutParams.MATCH_PARENT,Constraints.LayoutParams.MATCH_PARENT));
        }
    }
}
