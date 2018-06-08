package nightbase.nightbase;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nightbase.nightbase.nightbase.model.Event;

public class ItemTwoFragment extends Fragment {

    private ArrayList<Event> EventList = new ArrayList<Event>();
    private RecyclerView recyclerView;
    private EventAdapter mAdapter;

    private static final String TAG = EventActivity.class.getSimpleName();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("yearly_events");

    public static ItemTwoFragment newInstance() {
        ItemTwoFragment fragment = new ItemTwoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_two, container, false);

        // Declare classes
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new EventAdapter(EventList);

        // Set some settings.
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), EventActivity.class);
                intent.putExtra("event", EventList.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        // Call all data.
        prepareEventData();


        return view;
    }

    private void prepareEventData() {

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(ArrayList<String>.class);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Event event = ds.getValue(Event.class);

//                    System.out.println(event.toString());

                    EventList.add(event);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}