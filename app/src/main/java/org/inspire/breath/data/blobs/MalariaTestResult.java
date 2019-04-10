package org.inspire.breath.data.blobs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.inspire.breath.fragments.MalariaReviewFragment;
import org.inspire.breath.interfaces.IBlobbable;

import java.util.Arrays;

public class MalariaTestResult implements IBlobbable {

    public enum Results {
        POSITIVE,
        NEGATIVE,
        INVALID

    }

    private Results answer;
    private byte[] img;

    public MalariaTestResult() {
        this(null, null);
    }

    public MalariaTestResult(Results answer, byte[] img) {
        this.answer = answer;
        if (img != null)
            this.img = img.clone();
        else
            this.img = null;
    }

    public MalariaTestResult(byte[] img) {
        this(null, img);
    }

    public MalariaTestResult(Results answer) {
        this(answer, null);
    }

    public Bitmap getPhoto() {
        return BitmapFactory.decodeByteArray(img, 0, img.length);
    }

    public String getTestResult() { return answer.name(); }

    public void setTestResult(Results r) { answer = r; }

    @Override
    public byte[] toBlob() {
        byte serialisedAnswer;
        byte[] data = img;
        if (img == null)
            data = new byte[0];

        serialisedAnswer = (byte)answer.ordinal();


        byte[] out = new byte[data.length + 1];
        out[0] = serialisedAnswer;

        for (int i = 0 ; i < data.length ; i++)
            out[i + 1] = data[i];
        return out;
    }

    @Override
    public MalariaTestResult consumeBlob(byte[] blob) {
        if (blob == null)
            return null;

        switch (blob[0]) {
            case(0):
                this.answer = Results.POSITIVE;
                break;
            case(1):
                this.answer = Results.NEGATIVE;
                break;
            case(2):
                this.answer = Results.INVALID;
                break;
            case(-1):
                this.answer = null;
                break;
        }
        this.img = Arrays.copyOfRange(blob, 1, blob.length);
        return this;
    }
}
