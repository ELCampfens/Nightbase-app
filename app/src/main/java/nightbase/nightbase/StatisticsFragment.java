package nightbase.nightbase;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class StatisticsFragment extends Fragment {

    private PieChart mChart;

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_four, container, false);

        PieChart pieChart = (PieChart) view.findViewById(R.id.p_chart);

        ArrayList<String> array_percent = new ArrayList<String>();
        array_percent.add("Item 1");
        array_percent.add("Item 2");
        array_percent.add("Item 3");
        array_percent.add("Item 4");
        array_percent.add("Item 5");

        // array of graph drawing size according to design
        ArrayList<Float> array_drawGraph = new ArrayList<Float>();
        array_drawGraph.add((float) 15);
        array_drawGraph.add((float) 10);
        array_drawGraph.add((float) 25);
        array_drawGraph.add((float) 20);
        array_drawGraph.add((float) 30);

        // array of graph different colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ContextCompat.getColor(this.getContext(), R.color.colorAccent));
        colors.add(ContextCompat.getColor(this.getContext(), R.color.colorPrimary));
        colors.add(ContextCompat.getColor(this.getContext(), R.color.colorPrimaryDark));
        colors.add(ContextCompat.getColor(this.getContext(), R.color.colorAccent));
        colors.add(ContextCompat.getColor(this.getContext(), R.color.colorPrimary));

        // Now adding array of data to the entery set
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        // count is the number of values you need to display into graph
        int count = 5;

        for (int i=0; i<count; i++) {
            entries.add(new PieEntry(array_drawGraph.get(i), array_percent.get(i)));
        }
        // initializing pie data set
        PieDataSet dataset = new PieDataSet(entries, "");
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
        pieChart.setEntryLabelColor(Color.BLACK);

// If we need to display center text with textStyle








        // creating data values
//        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
//                entries.add(new PieEntry(4f, 0));
//                entries.add(new PieEntry(8f, 1));
//                entries.add(new PieEntry(6f, 2));
//                entries.add(new PieEntry(12f, 3));
//                entries.add(new PieEntry(18f, 4));
//
//        PieDataSet dataset = new PieDataSet(entries,"entries");
//
//        ArrayList<String> labels = new ArrayList<String>();
//        labels.add("January");
//        labels.add("February");
//        labels.add("March");
//        labels.add("April");
//        labels.add("May");
//        labels.add("June");
//
//        PieDataSet data = new PieDataSet(dataset, labels);
//        data.x;
//        pieChart.setData(data);

        return view;
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("TOTAL SPENT\n$ 4000");
        s.setSpan(new RelativeSizeSpan(2f), 11, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 11, s.length(), 0);
        return s;
    }
}