package org.inspire.breath.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

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

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] blob;

    @Embedded
    private String kind;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
