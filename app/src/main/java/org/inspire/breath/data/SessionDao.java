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
    public abstract long insertSession(Session session);

    @Delete
    public abstract void deleteSession(Session session);

    @Update
    public abstract void updateSession(Session session);

    @Transaction
    public long upsertSession(Session session) {
        long id = insertSession(session);
        if(id == -1)
            updateSession(session);
        return id;
    }

    @Query("SELECT * FROM sessions ORDER BY id ASC")
    public abstract List<Session> getAllSessions();

    @Query("SELECT * FROM sessions WHERE id = :id LIMIT 1")
<<<<<<< HEAD
    public abstract Session getRecordingById(int id);
=======
    public abstract Session getSessionById(long id);

    @Query("SELECT * FROM sessions WHERE id = :id")
    public abstract List<Session> getSessionById(int id);
>>>>>>> Initial actions reporting

    @Query("SELECT * FROM sessions WHERE patientId = :p_id ORDER BY timestamp DESC")
    public abstract List<Session> getSessions(int p_id);
}
