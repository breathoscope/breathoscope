package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

import java.nio.ByteBuffer;

public class HrCountTest implements IBlobbable {


    int result;
    @Override
    public byte[] toBlob() {
        return ByteBuffer
                .allocate(4)
                .putInt(result)
                .array();
    }

    @Override
    public HrCountTest consumeBlob(byte[] blob) {

        if (blob == null)
            return null;

        result = ByteBuffer.wrap(blob).getInt();
        return this;
    }

    public int getAnswer(){
        return result;
    }

    public void setResult(int res) {
        result = res;
    }
}