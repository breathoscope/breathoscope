package org.inspire.breath.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.widget.EditText;

import com.commonsware.cwac.saferoom.SafeHelperFactory;

@Database(entities = {Patient.class, Session.class}, version = 7, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {

    // DAOs
    public abstract PatientDao patientDao();
    public abstract SessionDao sessionDao();

    private static final String DATABASE_NAME = "breathoscope_database";

    private static volatile AppRoomDatabase INSTANCE;

    public static AppRoomDatabase getDatabase() {
        return INSTANCE;
    }

    public static AppRoomDatabase initDatabase(final Context context, EditText password) {

        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {

                    SafeHelperFactory factory= SafeHelperFactory.fromUser(password.getText());

                    // allowing main thread queries for testing
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, DATABASE_NAME).allowMainThreadQueries()

                                                                    .fallbackToDestructiveMigration()

                            .openHelperFactory(factory)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
