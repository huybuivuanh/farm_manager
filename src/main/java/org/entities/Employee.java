package org.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;

public class Employee extends User{
    public Employee(String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob){
        super(id, user_email, user_password, first_name, last_name, dob);
    }

    @Override
    public String toString() {
        String userType = this.getClass().getSimpleName();
        return "User type: " + userType + "\n" + super.toString();
    }
    /**
     * testing
     * @param args args
     */
    public static void main(String[] args){
        LocalDate dob = LocalDate.of(2002, Calendar.FEBRUARY,2);
        Employee staff = new Employee("ID_1", "John1@gmail.com", "pass1", "John1", "Josh1", dob);
        Task task1 = new Task("1", "task 1", "task 1 description", LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));
        Task task2 = new Task("2", "task 2", "task 2 description", LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));
        Task task3 = new Task("3", "task 3", "task 3 description", LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));
        LocalDate specificDate = LocalDate.of(2110, Month.JANUARY, 1);

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
