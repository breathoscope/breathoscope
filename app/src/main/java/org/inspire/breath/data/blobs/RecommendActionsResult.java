package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendActionsResult implements IBlobbable {

    Map<Test, String> actions;

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
    public void addAction(Test owner, String remedy, boolean isUrgent) {
        this.isUrgent = isUrgent;
        actions.put(owner, remedy);
    }

    public void addAction(Test owner, String remedy) {
        actions.put(owner, remedy);
    }

    public String getActions(Test t) {
        return actions.get(t);
    }

    @Override
    public byte[] toBlob() {
        //bit rough and ready but DataOutputStream wanted me to catch IOExceptions
        int byteCount = 0;
        List<Test> keys = new ArrayList<>(actions.keySet());
        List<String> values = new ArrayList<>(actions.values());
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
            String remedy;
            byte[] remedyBytes = new byte[valueLength];
            input.get(remedyBytes, 0, valueLength);
            remedy = new String(remedyBytes);
            actions.put(Test.values()[key], remedy);
        }
        return this;
    }
}
