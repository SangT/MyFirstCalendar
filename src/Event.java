import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Created by SangTo on 8/28/18.
 */
public class Event implements Comparable<Event> {
    //represent an event.
    // An event consists of its name and a TimeInterval of the event.
    private String name;
    private LocalDate date;
//    private DayOfWeek date1;
    private TimeInterval time;

    public Event(String name, LocalDate date, TimeInterval time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

//    public Event(String name, DayOfWeek date, TimeInterval time) {
//        this.name = name;
//        this.date1 = date;
//        this.time = time;
//    }

    /**
     * @return the name of the event.
     */
    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public TimeInterval getTime() {
        return time;
    }

    @Override
    public int compareTo(Event o) {
        return time.compareTo(o.time);
        // returns -1 if this is smaller than o
        // return 0 if equal
        // returns 1 if we are greater than o
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
