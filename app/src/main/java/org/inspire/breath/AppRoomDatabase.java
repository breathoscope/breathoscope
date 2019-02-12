package org.inspire.breath;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Volunteer.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {
    public abstract MainDao mainDao();

    private static final String DATABASE_NAME = "breathoscope_database";

    private static volatile AppRoomDatabase INSTANCE;

    static AppRoomDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}
