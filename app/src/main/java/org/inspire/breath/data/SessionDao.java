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
public abstract class SessionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insertRecording(Session session);

    @Delete
    public abstract void deleteRecording(Session session);

    @Update
    public abstract void updateRecording(Session session);

    @Transaction
    public long upsertRecording(Session session) {
        long id = insertRecording(session);
        if(id == -1)
            updateRecording(session);
        return id;
    }

    @Query("SELECT * FROM sessions ORDER BY id ASC")
    public abstract List<Session> getAllRecordings();

    @Query("SELECT * FROM sessions WHERE id = :id LIMIT 1")
    public abstract Session getRecordingById(int id);

    @Query("SELECT * FROM sessions WHERE patientId = :p_id ORDER BY timestamp DESC")
    public abstract List<Session> getRecordings(int p_id);
}
