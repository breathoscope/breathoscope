package org.inspire.breath;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

/*
    This class is intended to demonstrate interaction with the database,
    *not* to be used as a helper
 */
public class DbExamples {

    // create instance of an entity and add to database
    public static void createAndInsertVolunteer(AppRoomDatabase db, Context context) {

        Volunteer v = new Volunteer();
        // primary key is set to autogenerate
        v.setFirstName("Joe");
        v.setLastName("Bloggs");

        // perform insert operation using DAO
        db.mainDao().insertVolunteer(v);


        Toast.makeText(context, "Inserted volunteer successfully",
                Toast.LENGTH_SHORT).show();

    }

    public static void getVolunteerById (AppRoomDatabase db, Context context, int id) {

        // queries always return a list, even if only one result
        // empty list if query returns empty
        List<Volunteer> volunteer = db.mainDao().getVolunteerById(id);
        Volunteer v = new Volunteer();
        // -1 as error code
        v.setVolunteerId(-1);

        if (volunteer.size() > 0)
            v = volunteer.get(0);

        Toast.makeText(context,
                "Got volunteer id: " + v.getVolunteerId() + ", name " + v.getFirstName()
                + " " + v.getLastName(),
                Toast.LENGTH_SHORT).show();

    }

    // self explanatory.
    public static void resetDB (AppRoomDatabase db, String confirmation) {
        if (confirmation.equalsIgnoreCase("yes i'm sure")) {
            db.clearAllTables();
        }
    }

}
