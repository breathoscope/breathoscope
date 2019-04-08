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
import org.inspire.breath.data.blobs.RecommendActionsResult;
import org.inspire.breath.utils.FragmentedFragment;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static org.inspire.breath.data.AppRoomDatabase.getDatabase;

public class History extends FragmentedFragment {

    private static Logger logger = Logger.getLogger(History.class.toString());

    private RecyclerView mRecycler;
    public Patient currentPatient;

    private List<Session> sessions;
    SessionListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        if (this.currentPatient != null) {
            setPatient(currentPatient);
            sessions = AppRoomDatabase.getDatabase()
                    .sessionDao()
                    .getRecordings(currentPatient.getPatientId());
            mRecycler.setAdapter(new SessionListAdapter(sessions));
        }
    }

    private void findViews(View root) {
        mRecycler = root.findViewById(R.id.fragment_history_recycler);
    }

    public void setupDummyData(Patient patient) {
        Session session = new Session();
        session.setPatientId(patient.getPatientId());
        RecommendActionsResult recommendActionsResult = new RecommendActionsResult();
        RecommendActionsResult.Action action = new RecommendActionsResult.Action("don't die lol", RecommendActionsResult.Action.SEVERE);
        recommendActionsResult.addAction(RecommendActionsResult.Test.BREATH, action);
        action = new RecommendActionsResult.Action("don't die lol", RecommendActionsResult.Action.MED);
        recommendActionsResult.addAction(RecommendActionsResult.Test.DIARRHOEA, action);
        session.setRecommendedActionsResult(recommendActionsResult);
        AppRoomDatabase.getDatabase().sessionDao().insertRecording(session);
    }

    public void setPatient(Patient mPatient) {
        this.currentPatient = mPatient;
        sessions = getDatabase()
                .sessionDao()
                .getRecordings(currentPatient.getPatientId());
        if (adapter == null)
            adapter = new SessionListAdapter(sessions);
        adapter.setSessions(sessions);
        mRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
