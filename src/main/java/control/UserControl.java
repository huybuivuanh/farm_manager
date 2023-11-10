package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.entities.Employee;
import org.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserControl {

    /**
     * list of tasks
     */
    public ObservableList<User> allEmployees;
    public  ObservableList<User>owners;

    /**
     * constructor
     */
    public UserControl(){
        allEmployees = FXCollections.observableArrayList();
        owners =  FXCollections.observableArrayList();
    }

    // (String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob)
    public void addUser (String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob, Boolean owner)
    {
        boolean exists= false;
        for (User employee: allEmployees)
        {
            if (employee.getID().equals(id))
            {
                exists = true;
            }
        }
        if (!exists)
        {
            Employee employee = new Employee(  id, user_email , user_password, first_name, last_name, dob, owner);
            allEmployees.add(employee);
            if (owner){
                owners.add(employee);
            }
        }
        else {System.out.println("User ID already exists");}
    }


    public void editUser(String oldId,String newId,  String first_name, String last_name,  Boolean owner, String user_email, String user_password , LocalDate dob){
        Employee edited = null;
        boolean newIdAlreadyInUse = false;

        // check if the user to be edited exists
        for (User employee: allEmployees) {
            if (employee.getID().equals(oldId))
            {
                edited = (Employee) employee;
            }
        }
        if (edited == null)
        {
            System.out.println("User to be edited could not be found!");

        }
        else {
            // check if the suggested new ID is already in use by a DIFFERENT USER
            for (User employee: allEmployees) {
                if (employee.getID().equals(newId) && employee != edited) {
                    newIdAlreadyInUse = true;
                    break;
                }
            }
            if (!newIdAlreadyInUse)
            {
                edited.setID(newId);
                edited.setEmail(user_email);
                edited.setPassword(user_password);
                edited.setFirstName(first_name);
                edited.setLastName(last_name);
                edited.setDOB(dob);
                edited.isOwner = owner;
            }
            else {
                System.out.println("The proposed userId is already in Use!");
            }
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
                System.out.println(removed);
            }
        }
        if (removed == null)
        {
            System.out.println("User to be removed wasn't in the system!");
        }
        else {
            if (removed.isOwner== null)
            {
                System.out.println("owner boolean is null");
            }
            else {
                if (removed.isOwner)
                {
                    owners.remove(removed);
                    allEmployees.remove(removed);
                }
                else {
                    allEmployees.remove(removed);
                }
            }
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
