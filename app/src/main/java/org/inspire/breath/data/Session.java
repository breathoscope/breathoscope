package org.inspire.breath.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import org.inspire.breath.data.blobs.BreathTestResult;
import org.inspire.breath.data.blobs.DangerTestResult;
import org.inspire.breath.data.blobs.DiarrhoeaTestResult;
import org.inspire.breath.data.blobs.FeverTestResult;
import org.inspire.breath.data.blobs.MalariaTestResult;
import org.inspire.breath.interfaces.IBlobbable;

import java.util.Date;

// Encapsulates a session and the actions that can be performed on it
@Entity(foreignKeys = @ForeignKey(entity = Patient.class,
                                    childColumns = "patientId",
                                    parentColumns = "id",
                                    onDelete = ForeignKey.CASCADE),
                                    tableName = "sessions")
public class Session {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    private int patientId;

    private long timestamp;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "Fever")
    private byte[] feverTestResultBlob;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "Malaria")
    private byte[] malariaTestResultBlob;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "diarrhoea")
    private byte[] diarrhoeaTestResultBlob;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "Breath")
    private byte[] breathTestResultBlob;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "Danger")
    private byte[] dangerTestResultBlob;



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

    public byte[] getDangerTestResultBlob() {
        return dangerTestResultBlob;
    }

    public void setDangerTestResultBlob(byte[] dangerTestResultBlob) {
        this.dangerTestResultBlob = dangerTestResultBlob;
    }

    public DangerTestResult getDangerTestResult() {
        return new DangerTestResult().consumeBlob(this.dangerTestResultBlob);
    }

    public byte[] getBreathTestResultBlob() {
        return breathTestResultBlob;
    }

    public void setBreathTestResultBlob(byte[] breathTestResultBlob) {
        this.breathTestResultBlob = breathTestResultBlob;
    }

    public BreathTestResult getBreathTestResult() {
        if(this.breathTestResultBlob != null)
            return new BreathTestResult().consumeBlob(this.breathTestResultBlob);
        else
            return null;
    }

    public byte[] getDiarrhoeaTestResultBlob() {
        return diarrhoeaTestResultBlob;
    }

    public void setDiarrhoeaTestResultBlob(byte[] diarrhoeaTestResultBlob) {
        this.diarrhoeaTestResultBlob = diarrhoeaTestResultBlob;
    }

    public DiarrhoeaTestResult getDiarrhoeaTestResult() {
        return new DiarrhoeaTestResult().consumeBlob(this.diarrhoeaTestResultBlob);
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }








}
