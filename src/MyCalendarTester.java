import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by SangTo on 8/28/18.
 */

public class MyCalendarTester {
    static String[] weekdays = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
    static LocalDate cal = LocalDate.now();
    static String[] options = {"[L]oad", "[V]iew by", "[C]reate", "[G]o to", "[E]vent list", "[D]elete", "[Q]uit"};
    static MyCalendar myCal = new MyCalendar();

    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yy");
//    static final DateTimeFormatter multipleDays = DateTimeFormatter.ofPattern("EEE");

    public static void main(String[] args) throws FileNotFoundException{
        printCalendar(cal);
        System.out.println();

        // After the function of an option is done
        // the main menu is displayed again for the user to choose the next option.

        Scanner sc = new Scanner(System.in);
        boolean check = true;
        while (check) {
            printMenu();
            char input = sc.next(".").charAt(0);
            switch (input) {
                case 'L':
                    load();
                    break;
                case 'V':
                    viewBy();
                    break;
                case 'C':
                    create();
                    break;
                case 'G':
                    goTo();
                    break;
                case 'E':
                    eventList();
                    break;
                case 'D':
                    delete();
                    break;
                case 'Q':
                    check = false;
                    quit();
                    break;
                default:
                    System.out.println("Please input the correct option!");
                    break;
            }
        }
    }


    public static void printMenu() {
        System.out.println("Select one of the following options:");
        for (int i = 0; i < options.length; i++) {
            System.out.print(options[i] + "  ");
        }
        System.out.println();
    }

    /**
     *
     * @param a
     */
    public static void printCalendar(LocalDate a){
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

            if (i == cal.getDayOfMonth() && month == cal.getMonth()) {
                    System.out.print("[" + i + "]" + "  ");
            } else {
                if (i < 10)
                    System.out.print(" " + i + "  ");
                else
                    System.out.print(i + "  ");
            }
        }
    }

    private static void load() throws FileNotFoundException {
        //Name of Event
        //DaysorDate StartTime EndTime
        Scanner fin = new Scanner(new File("events.txt"));

        String nameEvent = fin.nextLine();
        String eventRead = fin.nextLine();
        String[] separation = eventRead.split(" ");

        // { "TFS", "12:00", "15:00" }
        // { "12/24/2018", "5:00", "19:00" }
        //      0          1          2

        /**
         * Get the startTime and endTime from the String
         */
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
                //DayOfWeek date = LocalDate.parse(String.valueOf(a), multipleDays).getDayOfWeek();
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

    private static void viewBy() {
        System.out.println("[D]ay view or [M]onth view ?");
        Scanner inputView = new Scanner(System.in);
        char decision = inputView.next(".").charAt(0);
        if (decision == 'D') {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d YYYY");
            System.out.println(" " + formatter.format(cal));
            System.out.println(myCal.getEvents(cal)); // Need to print in the form: CS151 Lecture : 10:30 - 11:45
            System.out.println("[P]revious or [N]ext or [G]o back to main menu ?");

        } else if (decision == 'M') {
            printCalendar(cal);
//            if (myCal.getEvents(cal) == null) {
//                for (MyCalendar myCalendar : myCal) {
//                    // put {} around those numbers in a month that has events
//                }
//            }

            System.out.println("[P]revious or [N]ext or [G]o back to main menu ?");
        }
    }

    private static void create() {
        //
    }

    private static void goTo() {
        //With this option, the user is asked to enter a date in the form of MM/DD/YYYY
        // and then the calendar displays the Day view of the requested date including
        // an event scheduled on that day in the order of starting time.
    }

    private static void eventList() {
        //The user can browse scheduled events. The calendar displays all the events
        // scheduled in the calendar in the order of starting date and starting time.
        // An example presentation of events is as follows: (The format of event strings does not have to be exactly like this.)

    }

    private static void delete() {
        //[S]elected: the user specifies the date and name of the event.
        // The specific event will be deleted.
        //[A]ll: the user specifies a date and all the events scheduled on the date will be deleted.
        System.out.println("[S]elected or [A]ll?");
        Scanner inputView = new Scanner(System.in);
        char decision = inputView.next(".").charAt(0);
        if (decision == 'S') {
            System.out.println("Enter the date [mm/dd/yyyy]");
            Scanner inputDay = new Scanner(System.in);
            String dayInput = inputDay.nextLine();
            // view by Day here! List all the events of that day here!!!!!!!!!!!!!!
            LocalDate day = LocalDate.parse(dayInput, dateTimeFormatter);
            System.out.print("Enter the name of the event to delete:");
            Scanner inputName = new Scanner(System.in);
            String nameInput = inputName.next();
            // should delete the event named and show the remain one
            System.out.println(dayInput);
            // show the remaining event here!!!!!!!!!!!!!!!!!!!!!!!!!!!

        } else if (decision == 'A') {
            System.out.println("Enter the date [mm/dd/yyyy]");
            Scanner inputDay = new Scanner(System.in);
            String dayInput = inputDay.nextLine();
            LocalDate day = LocalDate.parse(dayInput, dateTimeFormatter);
            // delete all the event here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            System.out.println("All the events on " + dayInput + " has been deleted.");
        }
    }

    private static void quit(){
        System.out.println("Good Bye");
        return;
        //The program prompts "Good Bye" and terminates.
    }
}





