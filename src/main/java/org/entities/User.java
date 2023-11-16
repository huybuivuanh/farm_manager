package org.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class User implements DatabaseInterface<User>{

    /**
     * whether is owner or not
     */
    public Boolean isOwner;
    /**
     * user ID
     */
    String ID;

    /**
     * user email
     */
    String email;

    /**
     * user password
     */
    String password;

    /**
     * user first name
     */
    String firstName;

    /**
     * user last name
     */
    String lastName;

    /**
     * user date of birth
     */
    LocalDate DOB;

    /**
     * list of all the task user is doing
     */
    ObservableList<Task> taskList;

    /**
     * constructor
     * @param id user id
     * @param user_email user email
     * @param user_password user password
     * @param first_name user first name
     * @param last_name user last name
     * @param dob user date of birth
     */
    public User(String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob, boolean ownership){
        ID = id;
        email = user_email;
        password = user_password;
        firstName = first_name;
        lastName = last_name;
        DOB = dob;
        taskList = FXCollections.observableArrayList();;
        isOwner = ownership;
    }

    /**
     * get user ownership
     * @return user ownership boolean
     */
    public boolean getOwner() {
        return isOwner;
    }

    /**
     * set user ownership
     */
    public void setOwner(boolean X) {
        isOwner=X;
    }

    /**
     * get user id
     * @return user id
     */
    public String getID() {
        return ID;
    }

    /**
     * get user email
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * get user password
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * get user first name
     * @return user first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * get user last name
     * @return user last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * get user date of birth
     * @return user date of birth
     */
    public LocalDate getDOB() {
        return DOB;
    }

    /**
     * get task list
     * @return task list
     */
    public ObservableList<Task> getTaskList() {
        return taskList;
    }

    /**
     * set user id
     * @param id new id to be set
     */
    public void setID(String id) {
        ID = id;
    }

    /**
     * set user email
     * @param new_email email to be set
     */
    public void setEmail(String new_email) {
        email = new_email;
    }

    /**
     * set user password
     * @param new_password password to be set
     */
    public void setPassword(String new_password) {
        password = new_password;
    }

    /**
     * set user first name
     * @param first_name first name to be set
     */
    public void setFirstName(String first_name) {
        firstName = first_name;
    }

    /**
     * set user last name
     * @param last_name last name to be set
     */
    public void setLastName(String last_name) {
        lastName = last_name;
    }

    /**
     * set user date of birth
     * @param dob date of birth to be set
     */
    public void setDOB(LocalDate dob) {
        DOB = dob;
    }

    /**
     * add task to task list
     * @param task task to be added
     */
    public void addTask(Task task){
        taskList.add(task);
    }

    /**
     * remove task from task list
     * @param taskID id of task to be removed
     */
    public void removeTask(String taskID){
        for (Task task : taskList){
            if (task.getID().equals(taskID)){
                taskList.remove(task);
                return;
            }
        }
    }

    /**
     * remove all task from task list
     */
    public void removeAllTasks(){
        taskList.clear();
    }

    /**
     * get string representation of User class
     * @return string representation of User class
     */
    public String toString(){
        StringBuilder result = new StringBuilder("User ID: " + getID() +
                "\nEmail: " + getEmail() +
                "\nPassword: " + getPassword() +
                "\nFirst Name: " + getFirstName() +
                "\nLast Name: " + getLastName() +
                "\nDate Of Birth: " + getDOB() +
                "\nTask List: [");
        for (Task task : taskList){
            result.append(task.getTaskName()).append(", ");
        }
        result.append("]");
        return result.toString();
    }


    /**
     * @param user The User, an employee or owner
     * @return the information of a User
     */
    @Override
    public Document classToDoc(User user) {
        Document newDoc= new Document();

        String employeeId = user.getID();
        String email = user.getEmail();
        String password = user.getPassword();
        String fname = user.getFirstName();
        String lname = user.getLastName();
        LocalDate dob= user.getDOB();
        Boolean owner= user.getOwner();

        newDoc.append("employeeId",employeeId);
        newDoc.append("email",email);
        newDoc.append("password",password);
        newDoc.append("firstname",fname);
        newDoc.append("lastname",lname);
        newDoc.append("dob",dob);
        newDoc.append("isOwner",owner);


        return newDoc;
    }

    /**
     * @return
     */
    @Override
    public Document docToClass() {
        return null;
    }

    /**
     *
     */
    @Override
    public void save() {

    }

    /**
     *
     */
    @Override
    public void sync() {

    }

    /**
     * @return
     */
    @Override
    public ObjectId getDbId() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isDatabase() {
        return false;
    }
}
