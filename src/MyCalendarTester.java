import java.time.LocalDate;
import java.time.Month;

/**
 * Created by SangTo on 8/28/18.
 */
// SAT SEP 8TH
public class MyCalendarTester {
    static String[] weekdays = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
    static LocalDate cal = LocalDate.now();
    public static void main(String[] args) {
        printCalendar(cal);
        System.out.println();

        System.out.println("Select one of the following options:");
        // After the function of an option is done
        // the main menu is displayed again for the user to choose the next option.
        String[] options = {"[L]oad", "[V]iew by", "[C]reate", "[G]o to", "[E]vent list", "[D]elete", "[Q]uit"};
        for (int i = 0; i < options.length; i++) {
            System.out.print(options[i] + "  ");
        }

        // loop exits when use enters quit
        // if ("C") {}

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

    public void viewBy() {

    }

    public void create() {

    }

    public void goTo() {

    }

    public void eventList() {

    }

    public void delete() {

    }

    public void quit(){

    }
}





