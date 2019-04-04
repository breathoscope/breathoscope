package org.inspire.breath.data.blobs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.inspire.breath.interfaces.IBlobbable;

import java.util.Arrays;

public class MalariaTestResult implements IBlobbable {

    private String answer;
    private byte[] img;

    public MalariaTestResult() {
        this(null, null);
    }

    public MalariaTestResult(String answer, byte[] img) {
        this.answer = answer;
        if (img != null)
            this.img = img.clone();
        else
            this.img = null;
    }

    public MalariaTestResult(byte[] img) {
        this(null, img);
    }

    public MalariaTestResult(String answer) {
        this(answer, null);
    }

    public Bitmap getPhoto() {
        return BitmapFactory.decodeByteArray(img, 0, img.length);
    }

    @Override
    public byte[] toBlob() {
        byte serialisedAnswer;
        byte[] data = img;
        if (img == null)
            data = new byte[0];

        switch (answer) {
            case ("yes"):
                serialisedAnswer = 0;
                break;
            case("no"):
                serialisedAnswer = 1;
                break;
            case("invalid"):
                serialisedAnswer = 2;
                break;
            default:
                serialisedAnswer = -1;
        }

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
                this.answer = "yes";
                break;
            case(1):
                this.answer = "no";
                break;
            case(2):
                this.answer = "invalid";
                break;
            case(-1):
                this.answer = null;
                break;
        }
        this.img = Arrays.copyOfRange(blob, 1, blob.length);
        return this;
    }
}
