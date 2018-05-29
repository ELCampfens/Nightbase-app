package nightbase.nightbase.nightbase.model;

public class Ticket {

    private String status, link;
    private double price;

    public Ticket() {

    }

    public Ticket(String status, String link, double price) {

        this.status = status;
        this.link = link;
        this.price = price;

    }
}
