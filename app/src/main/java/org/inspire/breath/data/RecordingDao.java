package org.inspire.breath.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecordingDao {

    @Insert
    void insertRecording(Recording recording);

    @Delete
    void deleteRecording(Recording recording);

    @Query("Select * from recordings ORDER BY id ASC")
    List<Recording> getAllRecordings();

}
