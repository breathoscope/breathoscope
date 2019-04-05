package org.inspire.breath.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.camerakit.CameraKitView;

import org.inspire.breath.R;
import org.inspire.breath.activities.HomeActivity;
import org.inspire.breath.activities.Refer_HC;
import org.inspire.breath.activities.TestActivity;
import org.inspire.breath.activities.Understanding_results;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.blobs.MalariaTestResult;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MalariaViewModel.class);
        ImageView image = getView().findViewById(R.id.imageView);
        image.setImageBitmap(mViewModel.image);
        Button ok = (Button) getView().findViewById(R.id.buttonOK);
        Button retry = (Button) getView().findViewById(R.id.buttonRETRY);
        ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(getActivity(), Understanding_results.class));
                //TODO save image
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
}
