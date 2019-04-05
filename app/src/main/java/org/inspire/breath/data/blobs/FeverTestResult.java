package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FeverTestResult implements IBlobbable {

    float temperature;
    boolean hasFever;
    boolean shouldPerformMalaria;

    @Override
    public byte[] toBlob() {
        return ByteBuffer
                .allocate(6)
                .put((byte)(shouldPerformMalaria ? 1 : 0))
                .put((byte)(hasFever ? 1 : 0))
                .putFloat(temperature)
                .array();
    }

    @Override
    public FeverTestResult consumeBlob(byte[] blob) {
        if (blob == null)
            return null;
        ByteBuffer buffer = ByteBuffer.wrap(blob);
        shouldPerformMalaria = buffer.get() == 1 ? true : false;
        hasFever = buffer.get() == 1 ? true : false;
        temperature = buffer.getFloat();
        return this;
    }

    public float getTemperature(){
        return temperature;
    }

    public boolean hasFever() {
        return temperature > 37.5f || hasFever;
    }

    public boolean shouldPerformMalariaTest() { return shouldPerformMalaria; }

    public void setTemperature(float temp) {
        temperature = temp;
    }

    public void setHasFever(boolean fever) { hasFever = fever; };

    public void setShouldPerformMalaria(boolean should) { shouldPerformMalaria = should; }
}
