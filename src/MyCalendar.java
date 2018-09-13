import java.time.LocalDate;
import java.util.*;

/**
 * MyCalendar.java - a class that can add events, get the Events, and check if there is event on a given date
 * @author Sang To
 * @version 1.0
 */
public class MyCalendar {

    private final Map<LocalDate, TreeSet<Event>> eventsOrder = new HashMap<>();

    /**
     * Adds the event to the calendar on a specific day by getting that event's date
     * @param e This is the given event
     */
    public void add(Event e) {
        LocalDate eventDay = e.getDate();
        if (!eventsOrder.containsKey(eventDay)) {
            TreeSet<Event> events = new TreeSet<>();
            eventsOrder.put(eventDay, events);
        }
        eventsOrder.get(eventDay).add(e);
    }

    /**
     * Gets the set of events on a given day
     * @param day This is the given day to get the events
     * @return a set of events on that day if applicable
     */
    public TreeSet<Event> getEvents(LocalDate day) {
        return eventsOrder.get(day);
    }

    /**
     * Checks whether there is any event on a given day
     * @param day This is the given day
     * @return true if there is any event on that day, false if there is no event
     */
    public boolean hasEvent(LocalDate day) {
        TreeSet<Event> s = eventsOrder.get(day);
        if (s == null)
            return false;
        else if (s.isEmpty())
            return false;
        return true;
    }
}
