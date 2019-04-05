package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

import java.nio.ByteBuffer;

public class DiarrhoeaTestResult implements IBlobbable {

    private String mAnswers [] = {
            "No need for treatment",
            "Refer to HC",
            "Uncomplicated diarrhoea: give ORS and Zinc sulfate according to age, plus Albendazole (if not received within last 6 months)"
    };

    int result;
    @Override
    public byte[] toBlob() {

        return ByteBuffer
                .allocate(4)
                .putInt(result)
                .array();
    }

    @Override
    public DiarrhoeaTestResult consumeBlob(byte[] blob) {
        if (blob == null)
            return null;

        result = ByteBuffer.wrap(blob).getInt();
        return this;
    }


    public String getAnswer(){
        return mAnswers[result];
    }

    public void setResult(int res) {
        result = res;
    }
}
