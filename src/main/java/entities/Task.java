package entities;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Task implements DatabaseInterface<Task>{
    /**
     * task ID
     */
    private String ID;

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
    private final Date date;

    /**
     * task due date
     */
    private Date dueDate;

    /**
     * status of task
     */
    private final Status status;

    /**
     * list of staffs working on this task
     */
    private ArrayList<User> staffList = new ArrayList<>();

    /**
     * helper class to keep track of the status
     */
    private static class Status {

        // status and date attributes
        private boolean isCompleted = false;
        private Date completionDate;
        private boolean inProgress = false;
        private Date inProgressDate;
        private boolean isPaused = false;

        private ArrayList<Date> pauseHistory = new ArrayList<>();

        private Status(){}

        public boolean isCompleted() {
            return isCompleted;
        }

        public Date getCompletionDate() {
            return completionDate;
        }

        public boolean inProgress() {
            return inProgress;
        }

        public Date getInProgressDate() {
            return inProgressDate;
        }

        public boolean isPaused() {
            return isPaused;
        }

        public ArrayList<Date> getPauseHistory() {
            return pauseHistory;
        }

        public void setCompleted(boolean completed) {
            isCompleted = completed;
            inProgress = !completed;
            if (isPaused == completed) {
                setPaused(!completed);
            }
            setCompletionDate(new Date());
        }

        public void setCompletionDate(Date completion_date) {
            completionDate = completion_date;
        }

        public void setInProgress(boolean in_progress) {
            inProgress = in_progress;
            isCompleted = !inProgress;
            if (isPaused() == in_progress){
                setPaused(!in_progress);
            }
            setInProgressDate(new Date());
        }

        public void setInProgressDate(Date inProgressDate) {
            this.inProgressDate = inProgressDate;
        }

        public void setPaused(boolean paused) {
            isPaused = paused;
            inProgress = !paused;
            isCompleted = !paused;
            pauseHistory.add(new Date());
        }

        public String toString(){
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
            StringBuilder result = new StringBuilder("Status: " + status +
                    "\nIn Progress Date: " + getInProgressDate() +
                    "\nCompletion Date: " + getCompletionDate() +
                    "\nPause History: [" );
            for (Date date : getPauseHistory()){
                result.append(date).append(", ");
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
    public Task(String id, String task_name, String descr, Date due_date){
        ID = id;
        taskName = task_name;
        description = descr;
        dueDate = due_date;
        status = new Status();
        date = new Date();
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
    public Date getDate() {
        return date;
    }

    /**
     * get task due date
     * @return task due date
     */
    public Date getDueDate() {
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
    public void setDueDate(Date dueDate) {
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
        Date today = new Date();
        return dueDate.before(today);
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
    public void removeAllStaffs(){
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
        result.append( "]");
        return result.toString();
    }



    /**
     * Translates an object into a JSON Document representation of itself.
     * @param task : an employee object that is going to be translated into a doc.
     * @return: a document that is a representation of the task passed.
     */
    @Override
    public Document classToDoc(Task task) {
        Document newDoc = new Document();

        //  ObjectId task_Id= task.taskId; => this is for the database

        String task_id = task.getID();
        String task_name= task.getTaskName();
        String task_description= task.getDescription();
        Date task_date= task.getDate();
        Date task_dueDate = task.getDueDate();
        String task_status= task.getStatus();


        // might need to add the objectID here still
        newDoc.append("_id", task_id);
        newDoc.append("task_name", task_name);
        newDoc.append("task_description", task_description);
        newDoc.append("task_date", task_dueDate);
        newDoc.append("task_dueDate", task_dueDate);
        // not sure how to include status below
        //newDoc.append("task_status", task_status);
        Date added = new Date();
        newDoc.append("Date Added:",added.getTime());

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


    /**
     * testing
     * @param args args
     */
    public static void main(String[] args){
        Task task = new Task("1", "task 1", "task 1 description", new Date());
        Date dob = new Date(2002 - 1900, Calendar.FEBRUARY, 2, 2, 2, 2);
        User staff1 = new User("ID_1", "John1@gmail.com", "pass1", "John1", "Josh1", dob);
        User staff2 = new User("ID_2", "John2@gmail.com", "pass2", "John2", "Josh2", dob);
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
        System.out.println("Detailed Status:\n" + task.status);



        // testing setters
        User staff3 = new User("ID_3", "John3@gmail.com", "pass3", "John3", "Josh3", dob);
        Date specificDate = new Date(2111 - 1900, Calendar.JANUARY, 1, 1, 1, 1);

        task.setID("2");
        task.setTaskName("task 2");
        task.setDescription("task 2 description");
        task.setDueDate(specificDate);
        task.pauseTask(true);
        task.markAsCompleted(true);
        task.setInProgress(true);
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
        System.out.println("Detailed Status:\n" + task.status);
    }
}
