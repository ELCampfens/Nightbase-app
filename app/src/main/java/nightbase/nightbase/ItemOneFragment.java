package nightbase.nightbase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nightbase.nightbase.nightbase.model.Event;
import nightbase.nightbase.nightbase.model.Ticket;


public class ItemOneFragment extends Fragment {

    private ArrayList<Event> EventList = new ArrayList<Event>();
    private RecyclerView recyclerView;
    private EventAdapter mAdapter;

    public static ItemOneFragment newInstance() {
        ItemOneFragment fragment = new ItemOneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_one, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new EventAdapter(EventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), EventActivity.class);

//                intent.putExtra("id", EventList.get(position).getID());

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMovieData();


        return view;
    }

    private void prepareMovieData() {

        Ticket ticket = new Ticket("sale", "https://tibbaa.com/order/ionl9cez2t?lang=nl", 10.50);


        Event event = new Event("Toeval", "HIPHOP . R&B . FEELGOOD . FUTURE", "DONDERDAG 24 MEI 2018");
        EventList.add(event);

        event  = new Event("Naaz","ZANGERES, SONGWRITER EN PRODUCER","VRIJDAG 25 MEI 2018");

        EventList.add(event);

        mAdapter.notifyDataSetChanged();
    }
}