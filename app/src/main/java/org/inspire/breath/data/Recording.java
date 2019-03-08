package org.inspire.breath.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import org.inspire.breath.data.blobs.FeverTestResult;
import org.inspire.breath.data.blobs.MalariaTestResult;
import org.inspire.breath.interfaces.IBlobbable;

import java.util.Date;

// Encapsulates a session and the actions that can be performed on it
@Entity(foreignKeys = @ForeignKey(entity = Patient.class,
                                    childColumns = "patientId",
                                    parentColumns = "id",
                                    onDelete = ForeignKey.CASCADE),
                                    tableName = "recordings")
public class Recording {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    private int patientId;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "Fever")
    private byte[] feverTestResultBlob;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "Malaria")
    private byte[] malariaTestResultBlob;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FeverTestResult getFeverTestResult() {
        return new FeverTestResult().consumeBlob(feverTestResultBlob);
    }

    public void setFeverTestResult(FeverTestResult result) {
        feverTestResultBlob = result.toBlob();
    }

    public MalariaTestResult getMalariaTestResult() {
        return new MalariaTestResult().consumeBlob(malariaTestResultBlob);
    }

    public void setMalariaTestResult(MalariaTestResult result) {
        malariaTestResultBlob = result.toBlob();
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
      this.patientId = patientId;
    }

    public byte[] getFeverTestResultBlob(){
        return feverTestResultBlob;
    }

    public void setFeverTestResultBlob(byte[] blob) {
        feverTestResultBlob = blob;
    }

    public byte[] getMalariaTestResultBlob(){
        return malariaTestResultBlob;
    }

    public void setMalariaTestResultBlob(byte[] blob) {
        malariaTestResultBlob = blob;
    }

}
