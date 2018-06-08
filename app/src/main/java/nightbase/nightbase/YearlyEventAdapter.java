package nightbase.nightbase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.List;

import nightbase.nightbase.nightbase.model.Event;

public class YearlyEventAdapter extends RecyclerView.Adapter<YearlyEventAdapter.MyViewHolder> {

    private List<Event> EventList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, description;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.eventTitle);
            description = (TextView) view.findViewById(R.id.eventDesc);
            date = (TextView) view.findViewById(R.id.eventDate);
            image = (ImageView) view.findViewById(R.id.eventImageView);
        }
    }


    public YearlyEventAdapter(List<Event> eventList, Context context) {
        this.EventList = eventList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.yearly_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = EventList.get(position);
        holder.title.setText(event.getName());
        holder.description.setText(event.getDescription());
        holder.date.setText(event.getDate());
        Glide.with(this.context).load(event.getImage()).into(holder.image);
    }

    public Event getEvent(int pos) {
        return EventList.get(pos);
    }

    @Override
    public int getItemCount() {
        return EventList.size();
    }

}