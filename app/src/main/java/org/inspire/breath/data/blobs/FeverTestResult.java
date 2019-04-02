package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FeverTestResult implements IBlobbable {

    float temperature;

    @Override
    public byte[] toBlob() {
        return ByteBuffer
                .allocate(4)
                .putFloat(temperature)
                .array();
    }

    @Override
    public FeverTestResult consumeBlob(byte[] blob) {
        if (blob == null)
            return null;
        temperature = ByteBuffer.wrap(blob).getFloat();
        return this;
    }

    public float getTemperature(){
        return temperature;
    }

    public boolean hasFever() {
        return temperature > 37.5f;
    }

    public void setTemperature(float temp) {
        temperature = temp;
    }
}
