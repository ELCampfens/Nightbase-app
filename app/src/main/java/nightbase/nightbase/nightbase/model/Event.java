package nightbase.nightbase.nightbase.model;

import java.util.ArrayList;
import java.util.List;

public class Event {

    private int id;
    private String name, description, url, date, image;

    List<Ticket> tickets = new ArrayList<Ticket>();
//    List<String> tags = new ArrayList<String>();

    // Empty constructor
    public Event() {

    }

    // Constructor for the list on the home page.
    public Event(String name, String description,
                 String date) {

        this.name = name;
        this.description = description;
        this.date = date;
//        this.tags = tags;
    }

    // Constructor for all the info if possible.
    public Event(String name, String description,
                 String url, String date,
                 String image,
                 ArrayList<Ticket> tickets) {

        this.name = name;
        this.description = description;
        this.url = url;
        this.date = date;

        this.image = image;
        this.tickets = tickets;
//        this.tags = tags;

    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDate() {
        return this.date;
    }

    public int getID() { return this.id; }
}
