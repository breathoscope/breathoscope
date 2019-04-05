package org.inspire.breath.fragments.home;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.inspire.breath.R;
import org.inspire.breath.data.Patient;
import org.inspire.breath.data.Session;
import org.inspire.breath.utils.FragmentedFragment;
import org.inspire.breath.views.StaticPager;

public class Home extends FragmentedFragment implements StaticPager.Focusable {

    private Patient mPatient;
    private Session mSession;
    private TextView mPatientName;
    private TextView mPatientAge;
    private TextView mPatientSex;
    private ImageView mPatientPicture;



    private void findViews(View root) {
        this.mPatientName = root.findViewById(R.id.home_patient_name);
        this.mPatientAge = root.findViewById(R.id.home_patient_age);
        this.mPatientSex = root.findViewById(R.id.home_patient_sex);
        this.mPatientPicture = root.findViewById(R.id.home_patient_picture);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        replaceFrag(R.id.home_main_container, new History());
    }

    public void setPatient(Patient patient) {
        System.out.println("setting patient");
        System.out.println(patient.getFirstName());
        this.mPatient = patient;
        this.mPatientName.setText(mPatient.getFirstName() + " " + mPatient.getLastName());
        this.mPatientAge.setText(mPatient.getAge());
        this.mPatientSex.setText(mPatient.getSex());
        byte[] bmp = mPatient.getThumb();
        this.mPatientPicture.setImageBitmap(BitmapFactory.decodeByteArray(bmp, 0, bmp.length));
    }

    @Override
    public void onFocus() {

    }
}
