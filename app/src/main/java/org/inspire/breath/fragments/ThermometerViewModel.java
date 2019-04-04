package org.inspire.breath.fragments;

import android.arch.lifecycle.ViewModel;

import org.inspire.breath.data.Session;

public class ThermometerViewModel extends ViewModel {
    public int sessionID;
    public int patientID;

    public float temperature;
}
