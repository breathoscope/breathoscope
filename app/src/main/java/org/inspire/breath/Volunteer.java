package org.inspire.breath;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "volunteer_table")
public class Volunteer {

    // int is implicitly @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int volunteerId;

    private String firstName, lastName;

    public Volunteer(int volunteerId, String firstName, String lastName) {
        this.volunteerId = volunteerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getVolunteerId() {
        return this.volunteerId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

}
