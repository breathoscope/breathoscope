package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendActionsResult implements IBlobbable {

    public static class Action {
        public static final int OK = 0;
        public static final int MED = 1;
        public static final int SEVERE = 2;

        private String mAction;
        private int mSeverity;

        public Action() {}

        public Action(String action, int severity) {
            mAction = action;
            mSeverity = severity;
        }

        public String getAction() {
            return mAction;
        }

        public int getSeverity() {
            return mSeverity;
        }

        public byte[] getBytes() {
            return ByteBuffer.allocate(8 + mAction.getBytes().length)
                    .putInt(mAction.getBytes().length)
                    .put(mAction.getBytes())
                    .putInt(mSeverity)
                    .array();
        }

        public Action fromBytes(byte[] bytes) {
            ByteBuffer bfr = ByteBuffer.wrap(bytes);
            int length = bfr.getInt();
            byte[] remedyBytes = new byte[length];
            bfr.get(remedyBytes);

            int severity = bfr.getInt();

            this.mAction = new String(remedyBytes);
            this.mSeverity = severity;
            return this;
        }

        @Override
        public String toString() {
            return getAction() + " " + getSeverity();
        }
    }

    Map<Test, Action> actions;

    public RecommendActionsResult() {
        actions = new HashMap<>();
    }

    public boolean isUrgent;

    public enum Test {
        FEVER,
        BREATH,
        MALARIA,
        DIARRHOEA,
        DANGER,
        HEART
    }

    //Because Java doesn't support optional parameters :(
    public void addAction(Test owner, Action remedy, boolean isUrgent) {
        this.isUrgent = isUrgent;
        actions.put(owner, remedy);
    }

    public void addAction(Test owner, Action remedy) {
        actions.put(owner, remedy);
    }

    public Action getAction(Test t) {
        return actions.get(t);
    }

    @Override
    public byte[] toBlob() {
        //bit rough and ready but DataOutputStream wanted me to catch IOExceptions
        int byteCount = 0;
        List<Test> keys = new ArrayList<>(actions.keySet());
        List<Action> values = new ArrayList<>(actions.values());
        for(int i = 0; i < actions.size(); i++)
            byteCount += values.get(i).getBytes().length + 8;
        ByteBuffer output = ByteBuffer.allocate(byteCount + 1);

        output.put((byte)(isUrgent ? 1 : 0)); //because god forbid a boolean be stored as anything other than a single bit

        for(int i = 0; i < actions.size(); i++) {
            output.putInt(keys.get(i).ordinal());
            output.putInt(values.get(i).getBytes().length);
            output.put(values.get(i).getBytes());
        }
        return output.array();
    }

    @Override
    public RecommendActionsResult consumeBlob(byte[] blob) {
        if(blob == null)
            return null;
        actions = new HashMap<>();
        ByteBuffer input = ByteBuffer.allocate(blob.length).put(blob);
        input.rewind();
        isUrgent = input.get() == 1 ? true : false;
        while(input.hasRemaining()) {
            int key = input.getInt();
            int valueLength = input.getInt();
            Action remedy;
            byte[] remedyBytes = new byte[valueLength];
            input.get(remedyBytes, 0, valueLength);
            remedy = new Action().fromBytes(remedyBytes);
            actions.put(Test.values()[key], remedy);
        }
        return this;
    }

    @Override
    public String toString() {
        return isUrgent + " " + actions.toString();
    }
}
