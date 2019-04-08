package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

import java.io.ByteArrayOutputStream;

public class HeartRateTestResult implements IBlobbable {

    private byte[] data;

    public HeartRateTestResult() {

    }

    public HeartRateTestResult(ByteArrayOutputStream baos) {
        this.data = baos.toByteArray();
    }

    @Override
    public byte[] toBlob() {
        return data;
    }

    @Override
    public HeartRateTestResult consumeBlob(byte[] blob) {
        this.data = blob;
        return this;
    }

}
