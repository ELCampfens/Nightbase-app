package nightbase.nightbase;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nightbase.nightbase.Database.MyDBHandler;
import nightbase.nightbase.nightbase.model.Event;

public class ItemThreeFragment extends Fragment {

    private ArrayList<Event> EventList = new ArrayList<Event>();
    private RecyclerView recyclerView;
    private EventAdapter mAdapter;
    private MyDBHandler DBHandler;

    private TextView emptyNotification;

    public static ItemThreeFragment newInstance() {
        ItemThreeFragment fragment = new ItemThreeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_three, container, false);

        emptyNotification = (TextView) view.findViewById(R.id.textView);

        // Declare classes
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new EventAdapter(EventList);
        DBHandler = new MyDBHandler(this.getContext());

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

        //Gets all events, ugly way but works.
        for(Event e : DBHandler.getAll()) {
            EventList.add(new Event(e.getName(), e.getDescription(), e.getDate(), e.getLatitude(), e.getLongitude(), e.getLink(), e.getID(), e.getImage()));
        }

        if(DBHandler.getAll().size() == 0) {
            emptyNotification.setText("Geen events gevonden!");
        }

        mAdapter.notifyDataSetChanged();
    }
}