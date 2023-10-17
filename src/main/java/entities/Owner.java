package entities;

import java.util.Calendar;
import java.util.Date;

public class Owner extends Employee{
    public Owner(String id,String user_email, String user_password, String first_name, String last_name, Date dob){
        super(id, user_email, user_password, first_name, last_name, dob);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * testing
     * @param args args
     */
    public static void main(String[] args){
        Date dob = new Date(2002 - 1900, Calendar.FEBRUARY, 2, 2, 2, 2);
        Owner staff = new Owner("ID_1", "John1@gmail.com", "pass1", "John1", "Josh1", dob);
        Task task1 = new Task("1", "task 1", "task 1 description", new Date());
        Task task2 = new Task("2", "task 2", "task 2 description", new Date());
        Task task3 = new Task("3", "task 3", "task 3 description", new Date());
        Date specificDate = new Date(2111 - 1900, Calendar.JANUARY, 1, 1, 1, 1);

        // testing getters method with toString();
        System.out.println(staff + "\n");


        // testing setters method
        staff.setID("new");
        staff.setEmail("new");
        staff.setPassword("new");
        staff.setFirstName("new");
        staff.setLastName("new");
        staff.setDOB(specificDate);
        staff.addTask(task1);
        staff.addTask(task2);
        staff.addTask(task3);
        staff.removeTask(task1.getID());
//        staff.removeAllTasks();
        System.out.println(staff);
    }
}
