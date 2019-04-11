package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

import java.util.Stack;

public class DangerTestResult implements IBlobbable {

    private Stack<Boolean> mAnswers;

    public DangerTestResult(Stack<Boolean> answers) {
        this.mAnswers = answers;
    }

    @Override
    public byte[] toBlob() {
        byte[] out = new byte[mAnswers.size()];

        for (int i = 0 ; i < mAnswers.size() ; i++) {
            out[i] = (byte) (mAnswers.get(i) ? 1 : 0);
        }
        return out;
    }

    @Override
    public DangerTestResult consumeBlob(byte[] blob) {
        if (blob == null)
            return null;
        mAnswers = new Stack<>();

        for (byte b : blob) {
            mAnswers.push(b == 1);
        }

        return this;
    }
}
