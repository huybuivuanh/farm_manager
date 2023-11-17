package org.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;


public class Owner extends Employee {

    //public static final Boolean owner = null;

    public Owner(String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob, boolean ownership){
        super(id, user_email, user_password, first_name, last_name, dob, ownership);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
