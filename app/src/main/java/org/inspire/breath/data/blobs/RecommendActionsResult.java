package org.inspire.breath.data.blobs;

import org.inspire.breath.activities.RecommendedActionsActivity;
import org.inspire.breath.interfaces.IBlobbable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RecommendActionsResult implements IBlobbable {

    Map<String, String> actions;

    public RecommendActionsResult() {
        actions = new HashMap<>();
    }

    public boolean isUrgent;

    //Because Java doesn't support optional parameters :(
    public void addAction(String cause, String remedy, boolean isUrgent) {
        this.isUrgent = isUrgent;
        actions.put(cause, remedy);
    }

    public void addAction(String cause, String remedy) {
        actions.put(cause, remedy);
    }

    public Map<String, String> getActions() {
        return actions;
    }

    @Override
    public byte[] toBlob() {
        //bit rough and ready but DataOutputStream wanted me to catch IOExceptions
        int byteCount = 0;
        List<String> keys = new ArrayList<>(actions.keySet());
        List<String> values = new ArrayList<>(actions.values());
        for(int i = 0; i < actions.size(); i++)
            byteCount += keys.get(i).getBytes().length + values.get(i).getBytes().length + 8;
        ByteBuffer output = ByteBuffer.allocate(byteCount + 1);

        output.put((byte)(isUrgent ? 1 : 0)); //because god forbid a boolean be stored as anything other than a single bit

        for(int i = 0; i < actions.size(); i++) {
            output.putInt(keys.get(i).getBytes().length);
            output.putInt(values.get(i).getBytes().length);
            output.put(keys.get(i).getBytes());
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
            int keyLength = input.getInt();
            int valueLength = input.getInt();
            String cause, remedy;
            byte[] causeBytes = new byte[keyLength];
            byte[] remedyBytes = new byte[valueLength];
            input.get(causeBytes, 0, keyLength);
            input.get(remedyBytes, 0, valueLength);
            cause = new String(causeBytes);
            remedy = new String(remedyBytes);
            actions.put(cause, remedy);
        }
        return this;
    }
}
