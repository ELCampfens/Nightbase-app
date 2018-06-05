package nightbase.nightbase.shared;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nightbase.nightbase.EventActivity;
import nightbase.nightbase.nightbase.model.Event;

public class FirebaseController {

    private static final String TAG = EventActivity.class.getSimpleName();
    FirebaseDatabase database;
    // Call to the events reference only
    DatabaseReference myRef;

    public FirebaseController() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("events");
    }


    public List<Event> getEventList(){
        final List<Event> events = new ArrayList<Event>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Event event = postSnapshot.getValue(Event.class);
                    events.add(event);
                }
                Log.d(TAG, "hoi");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return events;

    }
}
