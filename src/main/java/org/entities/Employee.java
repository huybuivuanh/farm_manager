package org.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;

public class Employee extends User{

    public Employee(String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob, boolean owner){
        super(id, user_email, user_password, first_name, last_name, dob, owner);
    }

    @Override
    public String toString() {
        String userType = this.getClass().getSimpleName();
        return "User type: " + userType + "\n" + super.toString();
    }
}
