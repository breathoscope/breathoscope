package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

public class HrRecording implements IBlobbable {

    private byte[] data;

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
