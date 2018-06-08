package nightbase.nightbase;

import android.content.pm.LabeledIntent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nightbase.nightbase.nightbase.model.Event;

public class ItemFourFragment extends Fragment {

    private PieChart mChart;
    private int year_2018, year_2017;
    private ArrayList<Event> EventList = new ArrayList<Event>();

    private static final String TAG = EventActivity.class.getSimpleName();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("events");

    private View view;

    public static ItemFourFragment newInstance() {
        ItemFourFragment fragment = new ItemFourFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_four, container, false);

        prepareEventData();

        return view;
    }

    private void drawPie() {
        PieChart pieChart = (PieChart) view.findViewById(R.id.p_chart);

        ArrayList<String> array_percent = new ArrayList<String>();
        array_percent.add("2017");
        array_percent.add("2018");

        // array of graph drawing size according to design
        ArrayList<Integer> array_drawGraph = new ArrayList<Integer>();
        array_drawGraph.add(this.year_2017);
        array_drawGraph.add(this.year_2018);

        // array of graph different colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ContextCompat.getColor(this.getContext(), R.color.colorAccent));
        colors.add(ContextCompat.getColor(this.getContext(), R.color.colorPrimary));

        // Now adding array of data to the entery set
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        // count is the number of values you need to display into graph
        int count = 2;

        for (int i=0; i<count; i++) {
            entries.add(new PieEntry(array_drawGraph.get(i), array_percent.get(i)));
        }

        // initializing pie data set
        PieDataSet dataset = new PieDataSet(entries, "");
        dataset.setValueTextColor(Color.WHITE);
        dataset.setValueTextSize(16f);
        // set the data
        PieData data = new PieData(dataset);        // initialize Piedata
        pieChart.setData(data);
        // colors according to the dataset
        dataset.setColors(colors);
        // adding legends to the desigred positions
        Legend l = pieChart.getLegend();
        l.setTextSize(14f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTextColor(Color.WHITE);
        l.setEnabled(true);
        // calling method to set center text
        pieChart.setCenterText(generateCenterSpannableText());
        // if no need to add description
        pieChart.getDescription().setEnabled(false);
        // animation and the center text color
        pieChart.animateY(1000);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(10f);
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("Events per jaar");
        s.setSpan(new RelativeSizeSpan(1.5f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, s.length(), 0);
        return s;
    }

    private void prepareEventData() {

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Event event = ds.getValue(Event.class);
                    EventList.add(event);
                }

                countYears();
                drawPie();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void countYears() {

        for(Event e : EventList) {
            if(e.getDate().contains("2018")){
                this.year_2018++;
            } else if(e.getDate().contains("2017")) {
                this.year_2017++;
            }
        }
    }
}