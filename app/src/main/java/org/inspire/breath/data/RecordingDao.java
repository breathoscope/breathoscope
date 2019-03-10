package org.inspire.breath.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public abstract class RecordingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insertRecording(Recording recording);

    @Delete
    public abstract void deleteRecording(Recording recording);

    @Update
    public abstract void updateRecording(Recording recording);

    @Transaction
    public long upsertRecording(Recording recording) {
        long id = insertRecording(recording);
        if(id == -1)
            updateRecording(recording);
        return id;
    }

    @Query("Select * from recordings ORDER BY id ASC")
    public abstract List<Recording> getAllRecordings();

    @Query("SELECT * from recordings where id = :id LIMIT 1")
    public abstract Recording getRecordingById(long id);

}
