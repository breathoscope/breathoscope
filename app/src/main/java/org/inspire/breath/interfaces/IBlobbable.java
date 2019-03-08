package org.inspire.breath.interfaces;

public interface IBlobbable {

    public byte[] toBlob();
    public IBlobbable consumeBlob(byte[] blob);
}
