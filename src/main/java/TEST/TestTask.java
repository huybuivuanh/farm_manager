package TEST;

import org.entities.Employee;
import org.entities.User;
import org.entities.Owner;
import org.entities.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;

public class TestTask {
    static String error_message(String test, String expected, String result) {
        return "Test Error: " + test + ". Expected: " + expected +
                ". Returned: " + result + " instead. :(";
    }


    public static void main(String[] args) {

        int count = 0, failed = 0;

        LocalDate dob = LocalDate.of(2002, Calendar.FEBRUARY, 2);
        LocalDate specificDate = LocalDate.of(2111, Month.JANUARY, 1);

        Owner staff1 = new Owner("ID_1", "John1@gmail.com", "pass1", "John1", "Josh1", dob, true);
        Owner staff2 = new Owner("ID_2", "John2@gmail.com", "pass2", "John2", "Josh2", dob, true);

        LocalDateTime duDate = LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43);

        String reason1 = "Constructor + getTaskName()";
        Task task1 = new Task("1", "task 1", "task 1 description",  duDate);
        String result = task1.getTaskName();
        String expected = "task 1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason1, expected, result));}

        String reason2 = "Testing getTaskName()";
        result = task1.getTaskName();
        expected = "task 1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason2, expected, result));}

        String reason3 = "Testing getID()";
        result = task1.getID();
        expected = "1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason3, expected, result));}

        String reason4 = "Testing getDescription()";
        result = task1.getDescription();
        expected = "task 1 description";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason4, expected, result));}

        String reason5 = "Testing getDueDate()";
        LocalDateTime result2 = task1.getDueDate();
        if (!result2.equals(duDate)) { failed ++ ;System.out.println(error_message(reason5, String.valueOf(duDate), String.valueOf(result2)));}

        String reason6 = "Testing getStatus() without setStatus()";
        result = task1.getStatus();
        expected = "Incomplete";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason6, expected, result));}


    }
}