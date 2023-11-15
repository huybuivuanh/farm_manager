package org.entities;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Task implements DatabaseInterface<Task>{
    /**
     * task ID
     */
    private String ID;

    /**
     * The unique ID of the Task for the DataBase
     */
    private ObjectId dbID;

    /**
     * task name
     */
    private String taskName;

    /**
     * task description
     */
    private String description;

    /**
     * date created
     */
    private final LocalDateTime date;

    /**
     * task due date
     */
    private LocalDateTime dueDate;


    /**
     * list of staffs working on this task
     */
    private final ArrayList<User> staffList;

    private boolean isCompleted = false;
    private LocalDateTime completionDate;
    private boolean isPaused = false;
    private LocalDateTime pauseDate;



    /**
     * constructor
     * @param id task ID
     * @param task_name task name
     * @param descr task description
     * @param due_date task due date
     */
    public Task(ObjectId iddb,String id, String task_name, String descr, LocalDateTime due_date){
        System.out.println("object id null" + iddb);
        dbID = iddb;
        ID = id;
        taskName = task_name;
        description = descr;
        dueDate = due_date;
        date = LocalDateTime.now();
        staffList = new ArrayList<>();
    }

    /**
     * get task ID
     * @return task id
     */
    public String getID() {
        return ID;
    }

    /**
     * get task name
     * @return task name
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * get task description
     * @return task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * get task creation date
     * @return creation date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * get task due date
     * @return task due date
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * get list of all staffs working on this task
     * @return list of staffs
     */
    public ArrayList<User> getStaffList() {
        return staffList;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public LocalDateTime getPauseDate() {
        return pauseDate;
    }


    /**
     * set task status to completed
     */
    public void markAsCompleted(boolean completed){
        if (isPaused) {
            isPaused = false;
        }
        if (completed){
            completionDate = LocalDateTime.now();
        }
        isCompleted = completed;
    }

    /**
     * pause task
     * @param is_paused true/false
     */
    public void pauseTask(boolean is_paused){
        if (is_paused){
            isCompleted = false;
            pauseDate = LocalDateTime.now();
        }
        isPaused = is_paused;
    }


    /**
     * set task ID
     * @param ID task ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * set task name
     * @param taskName task name
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * set task description
     * @param description task description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * set due date
     * @param dueDate due date
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }


    /**
     * check to see if task is overdue
     * @return true/false
     */
    public boolean isOverDue(){
        LocalDateTime today = LocalDateTime.now();
        return dueDate.isBefore(today);
    }

    /**
     * add staff to this task
     * @param staff staff to be added
     */
    public void addStaff(User staff){
        staffList.add(staff);
    }


    /**
     * remove a staff from list with staff id
     * @param staff_id id of staff
     */
    public void removeStaff(String staff_id) {
        for (User user : staffList) {
            if (user.getID().equals(staff_id)) {
                staffList.remove(user);
                return;
            }
        }
    }

    /**
     * remove all staffs from the list
     */
    public void removeAllStaff(){
        staffList.clear();
    }

    /**
     * get status of task
     * @return status of task
     */
    public String getStatus(){
        String taskStatus;
        if (isCompleted()){
            taskStatus = "Completed";
        }
        else if (isPaused()){
            taskStatus = "Paused";
        }
        else{
            taskStatus = "Incomplete";
        }
        return taskStatus;
    }


    public void setStatus(String status){
        if (status.equals("Completed")){
            markAsCompleted(true);
        }
        else if (status.equals("Paused")) {
            pauseTask(true);
        }
        else if (status.equals("Incomplete")){
            markAsCompleted(false);
        }
        else{
            System.out.println("Status parameter must be (Completed/Paused/In Progress/Incomplete");
        }
    }

    /**
     * get a string representation of task
     * @return string representation of task
     */
    public String toString(){
        StringBuilder result = new StringBuilder("Task ID: " + getID() +
                "\nTask name: " + getTaskName() +
                "\nTask Description: " + getDescription() +
                "\nCreated: " + getDate() +
                "\nStatus: " + getStatus() +
                "\nCompletion Date: " + getCompletionDate() +
                "\nPause Date: " + getPauseDate() +
                "\nStaffs: [");
        if (!staffList.isEmpty()) {
            for (User staff : staffList) {
                result.append(staff.getFirstName()).append(", ");
            }
        }
        else
            result.append(" empty ");
        result.append( "]");
        return result.toString();
    }


    /**
     * @return
     *
     *  newObj =  new Task(objectDoc.getObjectId("_id"),objectDoc.getString("taskID"), objectDoc.getString("task_name"),
     *                     objectDoc.getString("task_description"), objectDoc.getDate("task_dueDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
     *         }
     */
    @Override
    public Document classToDoc() {


        String taskID = this.getID();



        Document newDoc = new Document();
        ArrayList<ObjectId> staffList = new ArrayList<ObjectId>();
        for (User staff : this.getStaffList()) {
            staffList.add(staff.getDbId());
        }

        newDoc.append("TaskID",this.getDbId());
        newDoc.append("task_name",this.getTaskName());
        newDoc.append("task_description",this.getDescription());
        newDoc.append("task_dueDate",this.getDueDate());
        newDoc.append("task_date",this.getDate());
        newDoc.append("stafflist",staffList);
        return newDoc;
    }

    @Override
    public Document docToClass() {
        return null;
    }

    @Override
    public void save() {

    }

    @Override
    public void sync() {

    }

    @Override
    public ObjectId getDbId() {
        return dbID;
    }

    @Override
    public boolean isDatabase() {
        return false;
    }

    /**
     * testing
     * @param args args
     */
    public static void main(String[] args){
        Task task = new Task(null,"1", "task 1", "task 1 description", LocalDateTime.now());
        LocalDate dob = LocalDate.of(2002, Calendar.FEBRUARY, 2);
        User staff1 = new User("ID_1", "John1@gmail.com", "pass1", "John1", "Josh1", dob, true);
        User staff2 = new User("ID_2", "John2@gmail.com", "pass2", "John2", "Josh2", dob, true);
        task.addStaff(staff1);
        task.addStaff(staff2);

        // testing getters
        System.out.println("Testing getters\n");
        System.out.println("Task ID: " + task.getID());
        System.out.println("Task name :" + task.getTaskName());
        System.out.println("Task Description: " + task.getDescription());
        System.out.println("Created: " + task.getDate());
        System.out.println("Due Date: " + task.getDueDate());
        System.out.println("Is overdue: " + task.isOverDue());
        System.out.println("Completed: " + task.isCompleted());
        System.out.println("Paused: " + task.isPaused());


        StringBuilder result = new StringBuilder();
        for (User staff : task.getStaffList()) {
            result.append(staff.getFirstName()).append(", ");
        }
        System.out.println("Staff List: [" + result + "]\n");




        // testing setters
        System.out.println("Testing setters\n");
        User staff3 = new User("ID_3", "John3@gmail.com", "pass3", "John3", "Josh3", dob, false);
        LocalDateTime specificDate = LocalDateTime.of(2012, Month.JANUARY, 2, 0, 32, 43);

        task.setID("2");
        task.setTaskName("task 2");
        task.setDescription("task 2 description");
        task.setDueDate(specificDate);

        task.pauseTask(true);
        task.markAsCompleted(true);
        task.addStaff(staff3);
        task.removeStaff(staff2.getID());
//        task.removeAllStaffs();

        System.out.println("Task ID: " + task.getID());
        System.out.println("Task name: " + task.getTaskName());
        System.out.println("Task Description: " + task.getDescription());
        System.out.println("Created: " + task.getDate());
        System.out.println("Due Date: " + task.getDueDate());
        System.out.println("Is overdue: " + task.isOverDue());
        System.out.println("Completed: " + task.isCompleted());
        System.out.println("Paused: " + task.isPaused());


        StringBuilder result2 = new StringBuilder();
        for (User staff : task.getStaffList()) {
            result2.append(staff.getFirstName()).append(", ");
        }
        System.out.println("Staff List: [" + result2 + "]\n");


        task.removeAllStaff();
        System.out.println("Staff List: " + task.getStaffList());
        System.out.println(task);

    }
}
