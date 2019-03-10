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

    private String firstName, lastName;
    private int age;
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

    public int getAge() { return this.age; }

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

    public void setAge(int age) { this.age = age; }

    @Override
    public int compareTo(Object o) {
        int id = ((Patient) o).getPatientId();
        if (id == this.getPatientId())
            return 0;
        else {
            return id < this.getPatientId() ? 1 : -1;
        }
    }
}
