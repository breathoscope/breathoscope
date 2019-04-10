package org.inspire.breath.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.camerakit.CameraKitView;

import org.inspire.breath.R;
import org.inspire.breath.activities.HomeActivity;
import org.inspire.breath.activities.TestActivity;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.SessionDao;
import org.inspire.breath.data.blobs.MalariaTestResult;
import org.inspire.breath.data.blobs.RecommendActionsResult;

public class MalariaReviewFragment extends TestFragment {

    private MalariaViewModel mViewModel;

    public static MalariaFragment newInstance() {
        return new MalariaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_malaria_review, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MalariaViewModel.class);
        ImageView image = v.findViewById(R.id.imageView);
        image.setImageBitmap(mViewModel.image);
        Button retry = getView().findViewById(R.id.buttonRETRY);
        ImageButton positive = getView().findViewById(R.id.positive);
        ImageButton invalid = getView().findViewById(R.id.invalid);
        ImageButton negative = getView().findViewById(R.id.negative);

        positive.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                handleSelection(MalariaTestResult.Results.POSITIVE);
            }
        });

        negative.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                handleSelection(MalariaTestResult.Results.NEGATIVE);
            }
        });

        invalid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                handleSelection(MalariaTestResult.Results.INVALID);
            }
        });

        retry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment mFrag = new MalariaFragment();
                t.replace(R.id.container, mFrag);
                t.commit();
                getFragmentManager().popBackStack();
            }
        });
    }

    public void handleSelection(MalariaTestResult.Results result) {
        MalariaViewModel mViewModel =  ViewModelProviders.of(getActivity()).get(MalariaViewModel.class);
        SessionDao dao = AppRoomDatabase.getDatabase().sessionDao();
        Session session = AppRoomDatabase.getDatabase().sessionDao().getRecordingById(mViewModel.sessionID);
        MalariaTestResult testResult = new MalariaTestResult();
        testResult.setTestResult(result);
        session.setMalariaTestResult(testResult);
        RecommendActionsResult recommendActionsResult = session.getRecommendedActions();
        switch (result) {
            case POSITIVE:
                recommendActionsResult.addAction(RecommendActionsResult.Test.MALARIA, new RecommendActionsResult.Action("Give Rectal Artesunate and Refer",
                        RecommendActionsResult.Action.SEVERE));
                break;
            case NEGATIVE:
                recommendActionsResult.addAction(RecommendActionsResult.Test.MALARIA, new RecommendActionsResult.Action("Fever not Malaria-caused",
                        RecommendActionsResult.Action.OK));
                break;
            case INVALID:
                recommendActionsResult.addAction(RecommendActionsResult.Test.MALARIA, new RecommendActionsResult.Action("Repeat MRDT",
                        RecommendActionsResult.Action.MED));
                break;
        }
        session.setRecommendedActionsResultBlob(recommendActionsResult.toBlob());
        dao.upsertRecording(session);
        getActivity().finish();
    }
}
