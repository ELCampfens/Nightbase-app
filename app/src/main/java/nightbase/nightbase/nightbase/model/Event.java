package nightbase.nightbase.nightbase.model;


import java.io.Serializable;

public class Event implements Serializable {

    private int id;
    private String name, description, link, date, image;
    private double latitude, longitude;

    // Empty constructor
    public Event() {

    }

    public Event(String name, String description,
                 String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    // Constructor for the list on the home page.
    public Event(String name, String description,
                 String date, double latitude, double longitude,
                 String link, int id) {

        this.id = id;
        this.link = link;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String toString() {
        return "id : " + this.id + "\nUrl : " + this.link + "\nLat : " + this.latitude + "\nLong : " + this.longitude + "\nName : " + this.name + "\nDesc : " + this.description + "\nDate : " + this.date;
    }

    public double getLatitude() { return this.latitude; }

    public double getLongitude() { return this.longitude; }

    public String getLink() { return this.link; }

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
