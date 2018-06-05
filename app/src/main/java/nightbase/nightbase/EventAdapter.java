package nightbase.nightbase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nightbase.nightbase.nightbase.model.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private List<Event> EventList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, description;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.genre);
            date = (TextView) view.findViewById(R.id.year);
        }
    }


    public EventAdapter(List<Event> eventList) {
        this.EventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = EventList.get(position);
        holder.title.setText(event.getName());
        holder.description.setText(event.getDescription());
        holder.date.setText(event.getDate());
    }

    @Override
    public int getItemCount() {
        return EventList.size();
    }
}