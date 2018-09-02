import java.time.LocalDate;
import java.util.*;

/**
 * Created by SangTo on 8/28/18.
 */
public class MyCalendar {
    //defines an underlying data structure to hold events
    //private List<Event> event = new ArrayList<>();

    private final Map<LocalDate, TreeSet<Event>> eventsOrder = new HashMap<>();

    public void add(Event e) {
        LocalDate eventDay = e.getDate();
        if (!eventsOrder.containsKey(eventDay)) {
            TreeSet<Event> events = new TreeSet<>();
            eventsOrder.put(eventDay, events);
        }
        eventsOrder.get(eventDay).add(e);
    }

    public TreeSet<Event> getEvents(LocalDate day) {
        return eventsOrder.get(day);
    }

    // myCalendar.haveEventsOnDay(March 3rd) // return boolean

    /*
    800 am haricut

    12 pm class

    8 pm library

     { 8pmlib, 8amhaircut, 10am... }
     */
}
