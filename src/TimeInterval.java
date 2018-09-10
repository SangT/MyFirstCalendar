import java.time.LocalTime;


/**
 * Created by SangTo on 8/28/18.
 */
public class TimeInterval implements Comparable<TimeInterval> {
    // represents an interval of time, suitable for events
    // (such as a meeting on a given date from 10:00 - 11:00).
    // Provide a method to check whether two intervals overlap.

    private LocalTime start;
    private LocalTime end;

    public TimeInterval(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @return start time of the time interval.
     */
    public LocalTime getStart() {
        return start;
    }

    /**
     * @return end time of the time interval.
     */
    public LocalTime getEnd() {
        return end;
    }

    // TimeInterval a;
    // TimeInterval b;
    // a.conflict(b);

    /**
     * Checks whether two events conflict.
     * @param other The other TimeInterval to check.
     * @return true if either event conflicts.
     */
    private boolean conflict(TimeInterval other) {
        // a ends before b
        // a starts after b ends

        if (end.isBefore(other.start) || start.isAfter(other.end)) {
            System.out.println("CONFLICT!");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return start + " - " + end;
    }

    @Override
    public int compareTo(TimeInterval o) {
        return start.compareTo(o.start);
    }
}
