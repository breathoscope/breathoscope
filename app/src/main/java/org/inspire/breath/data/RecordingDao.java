package org.inspire.breath.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecordingDao {

    @Insert
    void insertRecording(Recording recording);

    @Delete
    void deleteRecording(Recording recording);

    @Update
    void updateRecording(Recording recording);

    @Query("Select * from recordings ORDER BY id ASC")
    List<Recording> getAllRecordings();

    @Query("SELECT * from recordings where id = :id LIMIT 1")
    Recording getRecordingById(int id);

}
