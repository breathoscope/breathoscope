package org.inspire.breath.fragments.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v4.content.ContextCompat;
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
import android.support.v4.app.Fragment;
import org.inspire.breath.views.StaticPager;

import java.io.ByteArrayOutputStream;

public class Create extends Fragment implements StaticPager.Focusable {

    private static final int IMAGE_REQUEST_CODE = 1;

    EditText mFirstName;
    EditText mSurName;
    EditText mAge;
    Spinner mSexSpinner;

    ConstraintLayout mPictureHolder;
    ImageView mProfilePicture;

    Button mSave;

    void findViews(View root) {
        this.mFirstName = root.findViewById(R.id.create_first_name);
        this.mSurName = root.findViewById(R.id.create_surname);
        this.mAge = root.findViewById(R.id.create_age);
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
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},0);
        }
    }

    private void setupListeners() {
        this.mPictureHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), IMAGE_REQUEST_CODE);
            }
        });

        this.mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = mFirstName.getText().toString();
                String sName = mSurName.getText().toString();
                String age = mAge.getText().toString();
                String sex = mSexSpinner.getSelectedItem().toString();
                if (!(mProfilePicture.getDrawable() instanceof BitmapDrawable)) {
                    return;
                }
                Bitmap bmp = ((BitmapDrawable) mProfilePicture.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Patient patient = new Patient();
                patient.setSex(sex);
                patient.setAge(age);
                patient.setLastName(sName);
                patient.setFirstName(fName);
                patient.setThumb(stream.toByteArray());

                AppRoomDatabase.getDatabase()
                        .patientDao()
                        .insertPatient(patient);

                getActivity().onBackPressed();
            }
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
        clearFields();
    }

    private void clearFields() {
        mSurName.setText("");
        mFirstName.setText("");
        mAge.setText("");
        mSexSpinner.setSelection(0);
        Resources.Theme appTheme = getActivity().getTheme();
        mProfilePicture.setImageDrawable(getResources()
                .getDrawable(R.drawable.ic_baseline_add_photo_alternate_24px, appTheme));
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(mProfilePicture.getLayoutParams());
        layoutParams.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        mProfilePicture.setLayoutParams(layoutParams);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && data != null) {
            Bundle extras = data.getExtras();
            Bitmap bmp = (Bitmap) extras.get("data");
            mProfilePicture.setImageBitmap(bmp);
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(mProfilePicture.getLayoutParams());
            layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            layoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            mProfilePicture.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
