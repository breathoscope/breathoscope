package org.inspire.breath.data.blobs;

import org.inspire.breath.interfaces.IBlobbable;

import java.nio.ByteBuffer;

public class DangerTestResult implements IBlobbable {

    private String reviewAnswers[][] = {
            {"The patient doesn't have convulsions.", "The patient has convulsions."},
            {"The patient is not lethargic or unconscious.", "The patient is lethargic or unconscious."},
            {"The patient is not vomiting everything.", "The patient is vomiting everything."},
            {"The patient is able to breastfeed or drink.", "The patient is unable to breastfeed or drink."}
    };
    public byte results[] = null;

    public void setResults(byte value[]){
        this.results = value;
    }

    public String getResults(){
        String ret = null;
        for(int i = 0; i<results.length; i++){
            ret += reviewAnswers[i][results[i]]+" ";
        }
        return ret;
    }

    @Override
    public byte[] toBlob() {
        return this.results;
    }

    @Override
    public DangerTestResult consumeBlob(byte[] blob) {
        if(blob == null){
            return null;
        }
        this.results = blob;
        return this;
    }
}