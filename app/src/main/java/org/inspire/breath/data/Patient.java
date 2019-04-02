package org.inspire.breath.data;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "patients")
public class Patient implements Comparable {

    public static final String FEMALE = "female";
    public static final String MALE = "male";

    // int is implicitly @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int patientId;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "thumb")
    private byte[] thumb;
    private String firstName, lastName;
    private String birthDay;
    private String sex;

    // public getters and setters required for Room to work

    public int getPatientId() {
        return this.patientId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getBirthDay() { return this.birthDay; }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDay(String birthDay) { this.birthDay = birthDay; }

    @Override
    public int compareTo(Object o) {
        int id = ((Patient) o).getPatientId();
        if (id == this.getPatientId())
            return 0;
        else {
            return id < this.getPatientId() ? 1 : -1;
        }
    }

    public byte[] getThumb() {
        return thumb;
    }

    public void setThumb(byte[] thumb) {
        this.thumb = thumb;
    }
}
