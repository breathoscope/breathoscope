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

// Encapsulates any sort of recording and the actions that can be performed on it
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
    private byte[] feverTestResult;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "Malaria")
    private byte[] malariaTestResult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FeverTestResult getFeverTestResult() {
        return new FeverTestResult().consumeBlob(feverTestResult);
    }

    public void setFeverTestResult(FeverTestResult result) {
        feverTestResult = result.toBlob();
    }

    public MalariaTestResult getMalariaTestResult() {
        return new MalariaTestResult().consumeBlob(malariaTestResult);
    }

    public void setMalariaTestResult(MalariaTestResult result) {
        malariaTestResult = result.toBlob();
    }
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
