package nightbase.nightbase.nightbase.model;


import java.io.Serializable;

public class Event implements Serializable {

    private int id;
    private String name, description, url, date, image;
    private double latitude, longitude;

    // Empty constructor
    public Event() {

    }

    // Constructor for the list on the home page.
    public Event(String name, String description,
                 String date, double latitude, double longitude,
                 String url) {

        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public double getLatitude() { return this.latitude; }

    public double getLongitude() { return this.longitude; }

    public String getUrl() { return this.url; }

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
