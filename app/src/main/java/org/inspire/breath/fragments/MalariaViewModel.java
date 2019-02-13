package org.inspire.breath.fragments;

import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

public class MalariaViewModel extends ViewModel {
    Bitmap image;

    void SetImage(Bitmap b) {
        image = b;
    }
}
