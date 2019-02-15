package org.inspire.breath.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MainDao {


    @Insert
    void insertVolunteer(Volunteer volunteer);

    @Query("DELETE FROM volunteer_table")
    void deleteAllVolunteers();
    
    @Query("SELECT * FROM volunteer_table ORDER BY lastName ASC")
    List<Volunteer> getAllVolunteers();

    @Query("SELECT * FROM volunteer_table WHERE id = :id")
    List<Volunteer> getVolunteerById(int id);


}
