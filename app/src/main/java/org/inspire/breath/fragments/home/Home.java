package org.inspire.breath.fragments.home;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.inspire.breath.R;
import org.inspire.breath.activities.RecommendedActionsActivity;
import org.inspire.breath.activities.TestActivity;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.SessionDao;
import org.inspire.breath.data.blobs.RecommendActionsResult;
import org.inspire.breath.utils.FragmentedFragment;
import org.inspire.breath.views.StaticPager;

import java.util.Date;

public class Home extends FragmentedFragment implements StaticPager.Focusable {

    private Patient mPatient;
    private Session mSession;
    private TextView mPatientName;
    private TextView mPatientAge;
    private TextView mPatientSex;
    private ImageView mPatientPicture;

    public FragmentedFragment current;

    public History history;
    public Testing testing;

    public FloatingActionButton fab;
    public Button diagnoseBtn;


    private void findViews(View root) {
        this.mPatientName = root.findViewById(R.id.home_patient_name);
        this.mPatientAge = root.findViewById(R.id.home_patient_age);
        this.mPatientSex = root.findViewById(R.id.home_patient_sex);
        this.mPatientPicture = root.findViewById(R.id.home_patient_picture);
        this.diagnoseBtn = root.findViewById(R.id.viewDiagnosis);
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
        diagnoseBtn.setVisibility(View.INVISIBLE);
        diagnoseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diagnose();
            }
        });
        history = new History();
        testing = new Testing();
        this.current = history;
        replaceFrag(R.id.home_main_container, history);

    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
    }

    public void setSession(Session session) {
        this.mSession = session;
    }

    public void setPatient(Patient patient) {
        this.mPatient = patient;
        this.mPatientName.setText(mPatient.getFirstName() + " " + mPatient.getLastName());
        this.mPatientAge.setText(mPatient.getAge());
        this.mPatientSex.setText(mPatient.getSex());
        byte[] bmp = mPatient.getThumb();
        this.mPatientPicture.setImageBitmap(BitmapFactory.decodeByteArray(bmp, 0, bmp.length));
        history.setPatient(mPatient);
    }

    public void onAdd() {
        mSession = new Session();
        mSession.setRecommendedActionsResult(new RecommendActionsResult());
        mSession.setPatientId(mPatient.getPatientId());
        SessionDao dao = AppRoomDatabase.getDatabase().sessionDao();
        dao.insertRecording(mSession);
        // gen id
        mSession =  dao.getRecordings(mPatient.getPatientId()).get(0);
        testing.setData(mSession, mPatient);
        fab.hide();
        this.current = testing;
        replaceFrag(R.id.home_main_container, testing);
        diagnoseBtn.setVisibility(View.VISIBLE);
    }

    public void diagnose() {
        if (this.current.equals(testing)) {
            Intent intent = new Intent(getActivity(), RecommendedActionsActivity.class);
            intent.putExtra(TestActivity.SESSION_ID_KEY, mSession.getId());
            startActivity(intent);
        }
    }

    @Override
    public boolean onBackPressed() {
        if (this.current.equals(testing)) {
            fab.show();
            history.setPatient(mPatient);
            current = history;
            replaceFrag(R.id.home_main_container, history);
            diagnoseBtn.setVisibility(View.INVISIBLE);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onFocus() {

    }

}
