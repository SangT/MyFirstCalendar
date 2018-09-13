import java.time.LocalDate;

/**
 * Event.java - a class that get name, date, and time of an event
 * @author Sang To
 * @version 1.0
 */
public class Event implements Comparable<Event> {
    //represent an event.
    // An event consists of its name and a TimeInterval of the event.
    private String name;
    private LocalDate date;
    private TimeInterval time;

    /**
     * Create a new event with the given name, date, and time interval
     * @param name This is the name of the event
     * @param date This is the date of the event
     * @param time This is the time interval of the event
     */
    public Event(String name, LocalDate date, TimeInterval time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    /**
     * Gets the name of the event
     * @return the name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the date of the event
     * @return the date of the event
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get the time interval of the event
     * @return the time interval of the event
     */
    public TimeInterval getTime() {
        return time;
    }

    /**
     * Compares the time intervals of the events
     * @param o This is the given event
     * @return the order of event based on time interval
     */
    @Override
    public int compareTo(Event o) {
        return time.compareTo(o.time);
        // returns -1 if this is smaller than o
        // return 0 if equal
        // returns 1 if greater than o
    }
}
