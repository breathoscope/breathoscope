package org.inspire.breath.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PatientDao {

    @Insert
    void insertPatient(Patient patient);

    @Query("DELETE FROM patients")
    void deleteAllPatients();
    
    @Query("SELECT * FROM patients ORDER BY lastName ASC")
    List<Patient> getAllPatients();

    @Query("SELECT * FROM patients WHERE id = :id LIMIT 1")
    Patient getPatientById(int id);

}
