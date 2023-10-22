package control;

import entities.Employee;
import entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserControl {

    /**
     * list of tasks
     */
    private ArrayList<entities.User> allEmployees;
    private ArrayList<User> owners;

    /**
     * constructor
     */
    public UserControl(){
        allEmployees = new ArrayList<>();
        owners =  new ArrayList<>();
    }

    // (String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob)
    public void addUser (String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob, Boolean owner)
    {
        Employee employee = new Employee(  id, user_email , user_password, first_name, last_name, dob, owner);
        allEmployees.add(employee);
        if (owner){
            owners.add(employee);
        }
    }


    public void editUser(String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob, Boolean owner){
        Employee edited = null;

        for (User employee: allEmployees)
        {
            if (employee.getID().equals(id))
            {
                edited = (Employee) employee;
            }
        }
        if (edited == null)
        {
            System.out.println("User to be edited could not be found!");

        }
        else {
            edited.setID(id);
            edited.setEmail(user_email);
            edited.setPassword(user_password);
            edited.setFirstName(first_name);
            edited.setLastName(last_name);
            edited.setDOB(dob);
            edited.isOwner = owner;
        }
    }

    public void promoteUser(String id){

        Employee promoted = null;
        for (User employee: allEmployees)
        {
            if (employee.getID().equals(id))
            {
                promoted = (Employee) employee;
            }
        }
        if (promoted == null)
        {
            System.out.println("User to be promoted could not be found!");

        }
        else {
            if (promoted.isOwner)
            {
                System.out.println("User to be promoted is already an owner!");
            }
            else {
                promoted.isOwner = true;
                owners.add(promoted);
            }
        }
    }

    public void removeUser(String id){
        Employee removed = null;
        for (User employee: allEmployees)
        {
            if (employee.getID().equals(id))
            {
                removed = (Employee) employee;
            }
        }
        if (removed != null)
        {
            if (removed.isOwner)
            {
                owners.remove(removed);
                allEmployees.remove(removed);
            }
            else {
                allEmployees.remove(removed);
            }
        }
        else {
            System.out.println("User to be removed wasn't in the system!");
        }
    }
    public String viewUser (String id)
    {
        Employee viewed = null;
        String returned = null;

        for (User employee: allEmployees)
        {
            if (employee.getID().equals(id))
            {
                viewed = (Employee) employee;
            }
        }
        if (viewed == null)
        {
            System.out.println("Employee to be viewed could not be found!");
        }
        else
        {
            returned = viewed.toString();
        }
          return returned;
    }
}
