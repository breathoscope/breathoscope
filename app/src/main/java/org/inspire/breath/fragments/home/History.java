package org.inspire.breath.fragments.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.inspire.breath.R;
import org.inspire.breath.adapters.SessionListAdapter;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;
import org.inspire.breath.data.Session;
import org.inspire.breath.utils.FragmentedFragment;

import java.util.LinkedList;
import java.util.List;

public class History extends FragmentedFragment {

    private RecyclerView mRecycler;
    public Patient currentPatient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    private void findViews(View root) {
        mRecycler = root.findViewById(R.id.fragment_history_recycler);
    }

    public void setPatient(Patient mPatient) {
        this.currentPatient = mPatient;
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Session> sessions = AppRoomDatabase.getDatabase()
                .sessionDao()
                .getRecordings(currentPatient.getPatientId());
        mRecycler.setAdapter(new SessionListAdapter(sessions));
    }
}
