package org.inspire.breath.data.blobs;

import android.graphics.Bitmap;

import org.inspire.breath.interfaces.IBlobbable;

public class MalariaTestResult implements IBlobbable {

    private byte[] photo;

    public Bitmap getPhoto() {

    }

    @Override
    public byte[] toBlob() {
        return photo.
    }

    @Override
    public IBlobbable consumeBlob(byte[] blob) {
        return null;
    }
}
