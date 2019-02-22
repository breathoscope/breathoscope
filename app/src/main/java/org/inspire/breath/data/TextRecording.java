package org.inspire.breath.data;

import android.os.Parcel;

// Mainly for debugging
public class TextRecording extends Recording {
    public TextRecording(int patientId) {
        this.setKind("Text");
        this.setPatient_id(patientId);
    }
}
