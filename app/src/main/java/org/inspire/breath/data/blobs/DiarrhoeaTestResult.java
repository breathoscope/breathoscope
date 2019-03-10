package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

public class DiarrhoeaTestResult implements IBlobbable {
    @Override
    public byte[] toBlob() {
        return new byte[0];
    }

    @Override
    public DiarrhoeaTestResult consumeBlob(byte[] blob) {
        return null;
    }
}
