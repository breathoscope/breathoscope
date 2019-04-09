package org.inspire.breath.fragments;

import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.net.Uri;

public class MalariaViewModel extends ViewModel {

    public int sessionID;
    public int patientID;

    Bitmap image;

    void SetImage(Bitmap b) {
        image = b;
    }
}
