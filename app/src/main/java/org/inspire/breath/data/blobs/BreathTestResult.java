package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

import java.nio.ByteBuffer;

public class BreathTestResult implements IBlobbable {

    public int breathrate;

    public void setBreathrate(int value){
        breathrate = value;
    }

    public int getBreathrate() {
        return breathrate;
    }

    @Override
    public byte[] toBlob() {
        return ByteBuffer.allocate(4).putInt(breathrate).array();
    }

    @Override
    public BreathTestResult consumeBlob(byte[] blob) {
        breathrate = ByteBuffer.wrap(blob).getInt();
        return this;
    }
}
