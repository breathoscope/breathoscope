package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

import java.io.ByteArrayOutputStream;

public class HrRecording implements IBlobbable {

    private byte[] data;

    public HrRecording() {

    }

    public HrRecording(ByteArrayOutputStream baos) {
        this.data = baos.toByteArray();
    }

    @Override
    public byte[] toBlob() {
        return data;
    }

    @Override
    public HrRecording consumeBlob(byte[] blob) {
        this.data = blob;
        return this;
    }

}
