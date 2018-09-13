import java.time.LocalTime;

/**
 * TimeInterval.java - a class that check whether there is a time conflict between the given time intervals
 * @author Sang To
 * @version 1.0
 */
public class TimeInterval implements Comparable<TimeInterval> {
    // represents an interval of time, suitable for events
    // (such as a meeting on a given date from 10:00 - 11:00).
    // Provide a method to check whether two intervals overlap.

    private LocalTime start;
    private LocalTime end;

    /**
     * Create a new TimeInterval with the given start time and end time
     * @param start This is the start time of the event
     * @param end This is the end time of the event
     */
    public TimeInterval(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Checks whether two events conflict
     * @param other The other TimeInterval to check
     * @return true if either event conflicts
     */
    public boolean conflict(TimeInterval other) {
        // a ends before b
        // a starts after b ends
        if (end.isBefore(other.start) || start.isAfter(other.end)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Turns the time interval into a specific format
     * @return format "start time - end time"
     */
    @Override
    public String toString() {
        return start + " - " + end;
    }

    /**
     * Compares to check which time interval starts before the other
     * @param o This is the given time interval
     * @return the order of start time between the time intervals
     */
    @Override
    public int compareTo(TimeInterval o) {
        return start.compareTo(o.start);
    }
}
