import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Created by SangTo on 8/28/18.
 */
// SAT SEP 8TH
public class MyCalendarTester {
    static String[] weekdays = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
    static LocalDate cal = LocalDate.now();
    static String[] options = {"[L]oad", "[V]iew by", "[C]reate", "[G]o to", "[E]vent list", "[D]elete", "[Q]uit"};

    public static void main(String[] args) {
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

    public static void printCalendar(LocalDate a){
        Month month = a.getMonth();
        System.out.println(month + " " + a.getYear());

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

    private static void load() {
        //Name of Event
        //DaysorDate StartTime EndTime
    }

    private static void viewBy() {
        System.out.println("[D]ay view or [M]onth view ?");
        Scanner inputView = new Scanner(System.in);
        char decision = inputView.next(".").charAt(0);
        MyCalendar myCal = new MyCalendar();
        if (decision == 'D') {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d YYYY");
            System.out.println(" " + formatter.format(cal));
            System.out.println(myCal.getEvents(cal));
        } else if (decision == 'M') {
            printCalendar(cal);
            if (myCal.getEvents(cal) == null) {
                for (MyCalendar myCalendar : myCal) {
                    // put {} around those numbers in a month that has events
                }
            }
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
    }

    private static void quit(){
        System.out.println("Good Bye");
        return;
        //The program prompts "Good Bye" and terminates.
    }
}





