package org.entities;

import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;


public class Owner extends Employee {

    public static final Boolean owner = false;

    public Owner(ObjectId iddb, String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob){
        super(iddb,id, user_email, user_password, first_name, last_name, dob, owner);
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
        LocalDate dob = LocalDate.of(2002, Calendar.FEBRUARY, 2);
        Owner staff = new Owner(null,"ID_1", "John1@gmail.com", "pass1", "John1", "Josh1", dob);
        Task task1 = new Task(null,"1", "task 1", "task 1 description",  LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));
        Task task2 = new Task(null,"2", "task 2", "task 2 description",  LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));
        Task task3 = new Task(null,"3", "task 3", "task 3 description",  LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));
        LocalDate specificDate = LocalDate.of(2111, Month.JANUARY, 1);
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
