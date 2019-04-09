package org.inspire.breath.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.inspire.breath.R;
import org.inspire.breath.activities.RecommendedActionsActivity;
import org.inspire.breath.activities.TestActivity;
import org.inspire.breath.adapters.SessionListAdapter;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.blobs.RecommendActionsResult;
import org.inspire.breath.utils.FragmentedFragment;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static org.inspire.breath.data.AppRoomDatabase.getDatabase;

public class History extends FragmentedFragment implements SessionListAdapter.SessionCallback {

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
            logger.warning("" + sessions.size());
            mRecycler.setAdapter(new SessionListAdapter(sessions, this));
        }
    }

    private void findViews(View root) {
        mRecycler = root.findViewById(R.id.fragment_history_recycler);
    }

    public void setupDummyData(Patient patient) {
        Session session = new Session();
        session.setPatientId(patient.getPatientId());
        RecommendActionsResult recommendActionsResult = new RecommendActionsResult();
        Random random = new Random();
        for (RecommendActionsResult.Test test : RecommendActionsResult.Test.values()) {
            RecommendActionsResult.Action action = new RecommendActionsResult.Action("Refer to HC for " + test.name() + " treatment", random.nextInt(3));
            recommendActionsResult.addAction(test, action, random.nextBoolean());
        }
        session.setRecommendedActionsResult(recommendActionsResult);
        AppRoomDatabase.getDatabase().sessionDao().insertRecording(session);
    }

    public void setPatient(Patient mPatient) {
        this.currentPatient = mPatient;
        setupDummyData(currentPatient);
        sessions = getDatabase()
                .sessionDao()
                .getRecordings(currentPatient.getPatientId());
        if (adapter == null)
            adapter = new SessionListAdapter(sessions, this);
        adapter.setSessions(sessions);
        mRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onSession(Session session) {
        Intent intent = new Intent(getActivity(), RecommendedActionsActivity.class);
        intent.putExtra(TestActivity.SESSION_ID_KEY, session.getId());
        startActivity(intent);
    }
}
