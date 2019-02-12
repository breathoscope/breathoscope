package org.inspire.breath;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomDatabase;

import java.util.List;

@Dao
public interface MainDao {

    @Insert
    void insertVolunteer(String firstName, String lastName);

    @Query("DELETE FROM volunteer_table")
    void deleteAllVolunteers();
    
    @Query("SELECT * FROM volunteer_table ORDER BY lastName ASC")
    List<Volunteer> getAllVolunteers();


}
