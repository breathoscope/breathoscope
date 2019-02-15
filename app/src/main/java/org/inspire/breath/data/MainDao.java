package org.inspire.breath.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MainDao {

    @Insert
    void insertVolunteer(Patient volunteer);

    @Query("DELETE FROM patients")
    void deleteAllVolunteers();
    
    @Query("SELECT * FROM patients ORDER BY lastName ASC")
    List<Patient> getAllVolunteers();

    @Query("SELECT * FROM patients WHERE id = :id")
    List<Patient> getVolunteerById(int id);

}
