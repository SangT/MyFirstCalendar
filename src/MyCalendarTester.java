import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * MyCalendarTester.java - the main class that has the methods to print and handle different tasks of calendar
 * @author Sang To
 * @version 1.0
 */
public class MyCalendarTester {

    static LocalDate cal = LocalDate.now();
    static MyCalendar myCal = new MyCalendar();
    static Scanner sc = new Scanner(System.in);

    // format date as mm/dd/yy
    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yy");
    // format date as mm/dd/yyyy
    static final DateTimeFormatter fullYearFormatter = DateTimeFormatter.ofPattern("M/d/y");

    /**
     * The main method prints the calendar and get the user's input to load the options
     * @param args Unused
     * @throws FileNotFoundException if the file is not found
     * @see #load()
     */
    public static void main(String[] args) throws FileNotFoundException{

        printCalendar(cal);
        System.out.println();

        // After the function of an option is done
        // the main menu is displayed again for the user to choose the next option.
        boolean check = true;
        while (check) {
            printMenu();
            char input = sc.next(".").charAt(0);
            switch (input) {
                case 'L': load();break;
                case 'V': viewBy();break;
                case 'C': create();break;
                case 'G': goTo();break;
                case 'E': eventList();break;
                case 'D': delete();break;
                case 'Q': check = false;quit();break;
                default: System.out.println("Please input the correct option!");break;
            }
        }
    }

    private static void printMenu() {
        String[] options = {"[L]oad", "[V]iew by", "[C]reate", "[G]o to", "[E]vent list", "[D]elete", "[Q]uit"};
        System.out.println("Select one of the following options:");
        for (int i = 0; i < options.length; i++) {
            System.out.print(options[i] + "  ");
        }
        System.out.println();
    }

    private static void printCalendar(LocalDate a){
        String[] weekdays = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        Month month = a.getMonth();
        System.out.println("\t" + month + " " + a.getYear());

        for (int i = 0; i < weekdays.length; i++) {
            System.out.print(weekdays[i] + "\t");
        }
        System.out.println();

        // look for the first day of that month
        LocalDate firstDay = LocalDate.of(a.getYear(), a.getMonth(), 1);

        // get value of the dayOfWeek of the firstDay
        int order = firstDay.getDayOfWeek().getValue();

        // print the calendar
        for (int j = 0; j < order; j++) {
            System.out.print("\t");
        }
        for (int i = 1; i <= month.length(a.isLeapYear()); i++) {
            if ((i + order - 1)%7 == 0) {
                System.out.println();
            }

            /*
            case 1: today is the iterated day and you have events on this day   ==> [{day}]
            case 2: today is the iterated day                                   ==> [day]
            case 3: you have events on this day                                 ==> {day}
            case 4: base case                                                   ==> day
            */

            LocalDate iteratedDay = LocalDate.of(a.getYear(), month, i);
            if (i == cal.getDayOfMonth() && month == cal.getMonth() && myCal.hasEvent(iteratedDay)) {
                System.out.print("[{" + i + "}] ");
            } else if (i == cal.getDayOfMonth() && month == cal.getMonth()) { // case 2
                System.out.print("[" + i + "]  ");
            } else if (myCal.hasEvent(iteratedDay)) {
                System.out.print("{" + i + "} ");
            }else{
                if (i < 10)
                    System.out.print(" " + i + "  ");
                else
                    System.out.print(i + "  ");
            }
        }
    }

    private static void load() throws FileNotFoundException {
        Scanner fin = new Scanner(new File("events.txt"));

        while (fin.hasNextLine()) {
            String nameEvent = fin.nextLine().trim();
            String eventRead = fin.nextLine().trim();
            String[] separation = eventRead.split(" ");

            // { "TFS", "12:00", "15:00" }
            // { "12/24/2018", "5:00", "19:00" }
            //      0          1          2

            LocalTime startTime = LocalTime.parse(separation[1], DateTimeFormatter.ISO_LOCAL_TIME);
            LocalTime endTime = LocalTime.parse(separation[2], DateTimeFormatter.ISO_LOCAL_TIME);
            TimeInterval timeInterval = new TimeInterval(startTime, endTime);

            int firstDay1 = LocalDate.of(cal.getYear(), cal.getMonth(), 1).getDayOfWeek().getValue();

            try {
                LocalDate date = LocalDate.parse(separation[0], dateTimeFormatter);
                Event event = new Event(nameEvent, date, timeInterval);
                myCal.add(event);
            } catch (DateTimeParseException e) {
                //if parsing fails, this is a multiple-day event
                for ( char a : separation[0].toCharArray())
                {
                    DayOfWeek dw;
                    switch (a)
                    {
                        case 'M': dw = DayOfWeek.MONDAY; break;
                        case 'T': dw = DayOfWeek.TUESDAY; break;
                        case 'W': dw = DayOfWeek.WEDNESDAY; break;
                        case 'R': dw = DayOfWeek.THURSDAY; break;
                        case 'F': dw = DayOfWeek.FRIDAY; break;
                        case 'A': dw = DayOfWeek.SATURDAY; break;
                        default: dw = DayOfWeek.SUNDAY; break;
                    }
                    // Get the DayOfMonth of the first DayOfWeek in the month
                    int firstSearchDay = ( (dw.getValue() - firstDay1 +7) % 7 ) +1;
                    while (firstSearchDay <= cal.getMonth().length(cal.isLeapYear())) {
                        Event event = new Event(nameEvent, LocalDate.of(cal.getYear(),
                                cal.getMonth(), firstSearchDay), timeInterval);
                        myCal.add(event);
                        firstSearchDay += 7;
                    }
                }
            }
        }
    }

    private static void viewBy() {
        System.out.println("[D]ay view or [M]onth view ?");
        char decision = sc.next(".").charAt(0);

        LocalDate flexDay = cal;

        if (decision == 'D') {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d YYYY");
            while (decision != 'G') {
                System.out.println(" " + formatter.format(flexDay));
                if (myCal.getEvents(flexDay)!=null) {
                    for (Event event : myCal.getEvents(flexDay)) {
                        System.out.println(event.getName() + " : " + event.getTime());
                    }
                }
                System.out.println("[P]revious or [N]ext or [G]o back to main menu ?");
                decision = sc.next(".").charAt(0);
                if (decision == 'P') {
                    flexDay = flexDay.minusDays(1);
                } else if (decision == 'N') {
                    flexDay = flexDay.plusDays(1);
                }
            }
        } else if (decision == 'M') {
            while (decision != 'G') {
                printCalendar(flexDay);
                System.out.println();
                System.out.println("[P]revious or [N]ext or [G]o back to main menu ?");
                decision = sc.next(".").charAt(0);
                if (decision == 'P') {
                    flexDay = flexDay.minusMonths(1);
                } else if (decision == 'N') {
                    flexDay = flexDay.plusMonths(1);
                }
            }
        }
    }

    private static void create() {
        System.out.print("Enter title: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.print("Enter date MM/DD/YYYY: ");
        String date = sc.next();
        LocalDate day = LocalDate.parse(date, fullYearFormatter);
        System.out.print("Enter start time: ");
        String start = sc.next();
        System.out.print("Enter end time: ");
        String end = sc.next();
        TimeInterval timeInterval = new TimeInterval(LocalTime.parse(start, DateTimeFormatter.ISO_LOCAL_TIME),
                LocalTime.parse(end, DateTimeFormatter.ISO_LOCAL_TIME));
        if (myCal.getEvents(day) == null) {
            Event event = new Event(name, day, timeInterval);
            myCal.add(event);
        } else {
            if (myCal.hasEvent(day)) {
                boolean conflict = false;
                for (Event events : myCal.getEvents(day)) {
                    boolean correct = events.getTime().conflict(timeInterval);
                    if (correct) {
                        System.out.println("CONFLICT! UNABLE TO ADD THE EVENT.");
                        conflict = true;
                        break;
                    }
                }
                if (!conflict) {
                    Event event = new Event(name, day, timeInterval);
                    myCal.add(event);
                }
            }
        }
    }

    private static void goTo() {
        //With this option, the user is asked to enter a date in the form of MM/DD/YYYY
        // and then the calendar displays the Day view of the requested date including
        // an event scheduled on that day in the order of starting time.
        System.out.print("Enter the date [mm/dd/yyyy]: ");
        String date = sc.next();
        LocalDate day = LocalDate.parse(date, fullYearFormatter);
        for (Event event : myCal.getEvents(day)) {
            System.out.println(event.getName() + ": " + event.getTime());
        }
    }

    private static void eventList() {
        //The user can browse scheduled events. The calendar displays all the events
        // scheduled in the calendar in the order of starting date and starting time.
        // An example presentation of events is as follows:
        System.out.println("2018");
        DateTimeFormatter specialFormat = DateTimeFormatter.ofPattern("eeee MMMM d");
        LocalDate start = LocalDate.of(cal.getYear(), 1, 1);
        LocalDate end = LocalDate.of(cal.getYear(), 12, 31);

        for (LocalDate day = start; day.isBefore(end); day = day.plusDays(1)) {
            if (myCal.hasEvent(day)) {
                for (Event event : myCal.getEvents(day)) {
                    System.out.println(specialFormat.format(day) + " " + event.getTime() + " " + event.getName());
                }
            }
        }
    }

    private static void delete() {
        //[S]elected: the user specifies the date and name of the event.
        // The specific event will be deleted.
        //[A]ll: the user specifies a date and all the events scheduled on the date will be deleted.
        System.out.println("[S]elected or [A]ll?");
        char decision = sc.next(".").charAt(0);
        if (decision == 'S') {
            System.out.println("Enter the date [mm/dd/yyyy]");
            sc.nextLine();
            String dayInput = sc.nextLine();
            // view by Day here! List all the events of that day here!!!!!!!!!!!!!!
            LocalDate day = LocalDate.parse(dayInput, fullYearFormatter);
            if (myCal.hasEvent(day)) {
                for (Event event : myCal.getEvents(day)) {
                    System.out.println(event.getTime() + " " +  event.getName());
                }
            }

            System.out.print("Enter the name of the event to delete:");
            String nameInput = sc.nextLine();
            // should delete the event named and show the remain one
            Event target = null;
            for (Event event : myCal.getEvents(day)) {
                if (nameInput.equals(event.getName())) {
                    target = event;
                    break;
                }
            }
            if (target != null) {
                myCal.getEvents(day).remove(target);
                System.out.println("The event is deleted. Here is the current scheduled event: ");
                System.out.println(dayInput);
                for (Event event : myCal.getEvents(day)) {
                    System.out.println(event.getTime() + " " + event.getName());
                }
            }
        } else if (decision == 'A') {
            System.out.println("Enter the date [mm/dd/yyyy]");
            sc.nextLine();
            String dayInput = sc.nextLine();
            LocalDate day = LocalDate.parse(dayInput, fullYearFormatter);
            if (myCal.hasEvent(day)) {
                myCal.getEvents(day).clear();
                System.out.println("All the events on " + dayInput + " has been deleted.");
            } else {
                System.out.println("ERROR: NO EVENTS TO DELETE!");
            }
        }
    }

    private static void quit(){
        System.out.println("Good Bye");
        sc.close();
        return;
    }
}





