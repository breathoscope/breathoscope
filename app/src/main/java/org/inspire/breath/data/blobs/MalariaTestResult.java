package org.inspire.breath.data.blobs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.inspire.breath.interfaces.IBlobbable;

public class MalariaTestResult implements IBlobbable {

    private byte[] data;

    public Bitmap getPhoto() {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    @Override
    public byte[] toBlob() {
        return data;
    }

    @Override
    public MalariaTestResult consumeBlob(byte[] blob) {
        data = blob;
        return this;
    }
}
