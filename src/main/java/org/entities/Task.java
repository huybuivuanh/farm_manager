package org.entities;

import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class Task {
    /**
     * task ID
     */
    private String ID;

    /**
     * The unique ID of the Task for the DataBase
     */
    private final ObjectId dbID = null;

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
     * status of task
     */
    private final Status status;

    /**
     * list of staffs working on this task
     */
    private final ArrayList<User> staffList;


    /**
     * helper class to keep track of the status
     */
    private static class Status {

        // status and date attributes
        private boolean isCompleted = false;
        private LocalDateTime completionDate;
        private boolean inProgress = false;
        private LocalDateTime inProgressDate;
        private boolean isPaused = false;

        private final ArrayList<LocalDateTime> pauseHistory;

        private Status(){ pauseHistory = new ArrayList<>();}

        public boolean isCompleted() {
            return isCompleted;
        }

        public LocalDateTime getCompletionDate() {
            return completionDate;
        }

        public boolean inProgress() {
            return inProgress;
        }

        public LocalDateTime getInProgressDate() {
            return inProgressDate;
        }

        public boolean isPaused() {
            return isPaused;
        }

        public ArrayList<LocalDateTime> getPauseHistory() {
            return pauseHistory;
        }

        public void setCompleted(boolean completed) {
            isCompleted = completed;
            inProgress = false;
            if (completed && isPaused){
                setPaused(false);
            }
            setCompletionDate(LocalDateTime.now());
        }

        public void setCompletionDate(LocalDateTime completion_date) {
            completionDate = completion_date;
        }

        public void setInProgress(boolean in_progress) {
            inProgress = in_progress;
            isCompleted = false;
            if (in_progress && isPaused) {
                setPaused(false);
            }
            setInProgressDate(LocalDateTime.now());
        }

        public void setInProgressDate(LocalDateTime inProgressDate) {
            this.inProgressDate = inProgressDate;
        }

        public void setPaused(boolean paused) {
            isPaused = paused;
            if (paused){
                inProgress = false;
                isCompleted = false;
            }
            pauseHistory.add(LocalDateTime.now());
        }

        public String toString(){
            DateTimeFormatter formatter
                    = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm a");
            String status;
            if (isCompleted()){
                status = "Completed";
            }
            else if (inProgress()){
                status = "In Progress";
            }
            else if (isPaused()){
                status = "Paused";
            }
            else{
                status = "Incomplete";
            }
            String formatProgressDate = null;
            String formatCompletionDate = null;
            if(getInProgressDate() != null)
            {formatProgressDate = getInProgressDate().format(formatter);}
            if (getCompletionDate() != null)
            {formatCompletionDate = getCompletionDate().format(formatter);}
            StringBuilder result = new StringBuilder("Status: " + status +
                    "\nIn Progress Date: " + formatProgressDate +
                    "\nCompletion Date: " + formatCompletionDate +
                    "\nPause History: [");
            for (LocalDateTime dateTime : getPauseHistory()){
                String formatPauseHistory = dateTime.format(formatter);
                result.append(formatPauseHistory).append(", ");
            }
            result.append("]");
            return result.toString();
        }
    }

    /**
     * constructor
     * @param id task ID
     * @param task_name task name
     * @param descr task description
     * @param due_date task due date
     */
    public Task(String id, String task_name, String descr, LocalDateTime due_date){
        ID = id;
        taskName = task_name;
        description = descr;
        dueDate = due_date;
        status = new Status();
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


    /**
     * task status (completed)
     * @return true/false
     */
    public boolean isCompleted(){
        return status.isCompleted();
    }

    /**
     * task status (in progress)
     * @return true/false
     */
    public boolean inProgress(){
        return status.inProgress();
    }

    /**
     * task status (paused)
     * @return true/false
     */
    public boolean isPaused(){
        return status.isPaused();
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
     * set task status to completed
     */
    public void markAsCompleted(boolean completed){
        status.setCompleted(completed);
    }

    /**
     * set task status to in progress
     * @param inProgress true/false
     */
    public void setInProgress(boolean inProgress){
        status.setInProgress(inProgress);
    }

    /**
     * pause task
     * @param is_paused true/false
     */
    public void pauseTask(boolean is_paused){
        status.setPaused(is_paused);
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
        else if (inProgress()){
            taskStatus = "In Progress";
        }
        else if (isPaused()){
            taskStatus = "Paused";
        }
        else{
            taskStatus = "Incomplete";
        }
        return taskStatus;
    }

    public Status getStatusObject(){
        return status;
    }

    // this method may not be necessary
    public void setStatus(String status){
        if (status.equals("Completed")){
            markAsCompleted(true);
        }
        else if (status.equals("Paused")) {
            pauseTask(true);
        }
        else if (status.equals("In Progress")){
            setInProgress(true);
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
        StringBuilder result = new StringBuilder("Task ID: " + ID +
                "\nTask name: " + taskName +
                "\nTask Description: " + description +
                "\nCreated: " + date +
                "\nDue: " + dueDate +
                "\nStatus: " + getStatus() +
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
     * testing
     * @param args args
     */
    public static void main(String[] args){
        Task task = new Task("1", "task 1", "task 1 description", LocalDateTime.now());
        LocalDate dob = LocalDate.of(2002, Calendar.FEBRUARY, 2);
        User staff1 = new User("ID_1", "John1@gmail.com", "pass1", "John1", "Josh1", dob, true);
        User staff2 = new User("ID_2", "John2@gmail.com", "pass2", "John2", "Josh2", dob, true);
        task.addStaff(staff1);
        task.addStaff(staff2);

        // testing getters
        System.out.println("Task ID: " + task.getID());
        System.out.println("Task name :" + task.getTaskName());
        System.out.println("Task Description: " + task.getDescription());
        System.out.println("Created: " + task.getDate());
        System.out.println("Due Date: " + task.getDueDate());
        System.out.println("Is overdue: " + task.isOverDue());
        System.out.println("Completed: " + task.isCompleted());
        System.out.println("Paused: " + task.isPaused());
        System.out.println("In Progress: " + task.inProgress());


        StringBuilder result = new StringBuilder("[");
        for (User staff : task.getStaffList()) {
            result.append(staff.getFirstName()).append(", ");
        }
        System.out.println("Staff List: " + result + "]\n");
        System.out.println("Detailed Status:\n" + task.getStatusObject());



        // testing setters
        User staff3 = new User("ID_3", "John3@gmail.com", "pass3", "John3", "Josh3", dob, false);
        LocalDateTime specificDate = LocalDateTime.of(2012, Month.JANUARY, 2, 0, 32, 43);

        task.setID("2");
        task.setTaskName("task 2");
        task.setDescription("task 2 description");
        task.setDueDate(specificDate);

        task.setInProgress(true);
        task.pauseTask(true);
        task.markAsCompleted(true);
        task.addStaff(staff3);
        task.removeStaff(staff2.getID());
//        task.removeAllStaffs();

        System.out.println("\n");
        System.out.println("Task ID: " + task.getID());
        System.out.println("Task name: " + task.getTaskName());
        System.out.println("Task Description: " + task.getDescription());
        System.out.println("Created: " + task.getDate());
        System.out.println("Due Date: " + task.getDueDate());
        System.out.println("Is overdue: " + task.isOverDue());
        System.out.println("Completed: " + task.isCompleted());
        System.out.println("Paused: " + task.isPaused());
        System.out.println("In Progress: " + task.inProgress());

        result = new StringBuilder();
        for (User staff : task.getStaffList()) {
            result.append(staff.getFirstName()).append(", ");
        }
        System.out.println("Staff List: [" + result + "]\n");
        System.out.println("Detailed Status:\n" + task.getStatusObject().toString());

        task.removeAllStaff();
        System.out.println("Staff List: [" + result + "]\n");
    }
}
