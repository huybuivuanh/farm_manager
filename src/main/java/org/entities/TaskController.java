package org.entities;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;

public class TaskController {

    /**
     * list of tasks
     */
    private ArrayList<Task> taskList;

    /**
     * constructor
     */
    public TaskController(){
        taskList = new ArrayList<>();
    }

    /**
     * create a new task and add to task list
     * @param id task id
     * @param taskName task name
     * @param description task description
     * @param due_date task due date
     */
    public void createTask(String id, String taskName, String description, LocalDateTime due_date) {
        Task newTask = new Task(id, taskName, description, due_date);
        taskList.add(newTask);
    }

    /**
     * retrieve task from task list with task id
     * @param taskId task id
     * @return task retrieved
     */
    public Task getTaskByID(String taskId) {
        for (Task task : taskList) {
            if (task.getID().equals(taskId)) {
                return task;
            }
        }
        System.out.println("Task with ID \"" + taskId + "\" not found!");
        return null;
    }

    /**
     * set task status to completed/incomplete
     * @param taskId task id
     * @param completed true/false
     */
    public void markTaskAsCompleted(String taskId, boolean completed) {
        Task task = getTaskByID(taskId);
        if (task != null) {
            task.markAsCompleted(completed);
        }
        else {
            System.out.println("Task with ID \"" + taskId + "\" not found!");
        }
    }

    /**
     * set status of task (in progress)
     * @param taskId task id
     * @param inProgress true/false
     */
    public void setTaskInProgress(String taskId, boolean inProgress) {
        Task task = getTaskByID(taskId);
        if (task != null) {
            task.setInProgress(inProgress);
        }
        else {
            System.out.println("Task with ID \"" + taskId + "\" not found!");
        }
    }

    /**
     * pause task
     * @param taskId task id
     * @param isPaused true/false
     */
    public void pauseTask(String taskId, boolean isPaused) {
        Task task = getTaskByID(taskId);
        if (task != null) {
            task.pauseTask(isPaused);
        }
        else {
            System.out.println("Task with ID \"" + taskId + "\" not found!");
        }
    }


    /**
     * add staff to a task
     * @param taskId task id
     * @param staff staff to be added
     */
    public void addStaffToTask(String taskId, User staff) {
        Task task = getTaskByID(taskId);
        if (task != null) {
            task.addStaff(staff);
        }
        else {
            System.out.println("Task with ID \"" + taskId + "\" not found!");
        }
    }

    /**
     * remove staff from a task
     * @param taskId task id
     * @param staffId staff id
     */
    public void removeStaffFromTask(String taskId, String staffId) {
        Task task = getTaskByID(taskId);
        if (task != null) {
            task.removeStaff(staffId);
        }
        else {
            System.out.println("Task with ID \"" + taskId + "\" not found!");
        }
    }


    /**
     * add a task to task list
     * @param task task to be added to the list
     */
    public void addTask(Task task){
        taskList.add(task);
    }

    /**
     * remove a task from task list
     * @param taskId task id
     */
    public void removeFromTaskList(String taskId){
        for (Task task : taskList){
            if (task.getID().equals(taskId)){
                taskList.remove(task);
                return;
            }
        }
    }

    /**
     * clear task list
     */
    public void clearTaskList(){
        taskList.clear();
    }

    /**
     * get a list of tasks by task status
     * @param status status of tasks
     * @return list of task with specified status
     */
    public ArrayList<Task> getTaskByStatus(String status){
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskList){
            if (task.getStatus().equals(status)){
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * get a list of overdue tasks
     * @return a list of overdue tasks
     */
    public ArrayList<Task> getOverDueTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskList){
            if (task.isOverDue()){
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * get task details
     * @param taskId task id
     * @return a string of task details
     */
    public String getTaskDetails(String taskId){
        return this.getTaskByID(taskId).toString();
    }

    /**
     * edit task details
     * @param taskId task id
     * @param new_name new task name
     * @param new_description new task description
     * @param new_due_date new task due date
     * @param status new task status (completed/incomplete/pause/inprogress/
     */
    public void editTaskDetails(String taskId, String new_name, String new_description, LocalDateTime new_due_date, String status){
        Task task = this.getTaskByID(taskId);
        task.setTaskName(new_name);
        task.setDescription(new_description);
        task.setDueDate(new_due_date);
        task.setStatus(status);
    }

    /**
     * get a list of staffs assigned to task
     * @param staffId staff id
     * @return list of staffs assigned to task
     */
    public ArrayList<User> getAssignedStaffs(String staffId){
        return this.getTaskByID(staffId).getStaffList();
    }

    /**
     * get the list of all tasks
     * @return list of all tasks
     */
    public ArrayList<Task> getAllTasks() {
        return taskList;
    }

    /**
     * get total the number of tasks in task list
     * @return total number of tasks
     */
    public int getTotalTaskNum(){
        return taskList.size();
    }

    // modify after testing
    @Override
    public String toString() {
        if (taskList.isEmpty()){
            return "0 tasks found in task list";
        }
        StringBuilder result = new StringBuilder();
        for (Task task : taskList){
            result.append(task.toString()).append("\n\n");
        }
        return result.toString();
    }

    // testing
    public static void main(String[] args){

        TaskController controller = new TaskController();

        LocalDateTime localDate = LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43);
        Task task1 = new Task("1", "task 1", "task 1 description", localDate);
        Task task2 = new Task("2", "task 2", "task 2 description", localDate);
        Task task3 = new Task("3", "task 3", "task 3 description", localDate);

        LocalDate dob = LocalDate.of(2002, Calendar.FEBRUARY,2);
        User staff1 = new User("ID_1", "John1@gmail.com", "pass1", "John1", "Josh1", dob,true);
        User staff2 = new User("ID_2", "John2@gmail.com", "pass2", "John2", "Josh2", dob,true);
        User staff3 = new User("ID_3", "John3@gmail.com", "pass3", "John3", "Josh3", dob,true);

        // createTask and add staff
        controller.createTask("0", "task 0", "task 0 description", localDate);

        controller.addTask(task1);
        controller.addTask(task2);
        controller.addTask(task3);

        controller.addStaffToTask("1", staff1);
        controller.addStaffToTask("2", staff2);
        controller.addStaffToTask("3", staff3);

        // status check
//        System.out.println(controller.getTaskByID("1"));
//        System.out.println();
        controller.pauseTask("1", true);
        controller.markTaskAsCompleted("1", true);
        controller.setTaskInProgress("1", true);
        controller.setTaskInProgress("1", true);
//        System.out.println(controller.getTaskByID("1"));

        controller.removeFromTaskList("0");
        controller.removeStaffFromTask("1", "ID_1");
//        controller.clearTaskList();
        controller.setTaskInProgress("2", true);
        controller.setTaskInProgress("3", true);
//        System.out.println(controller.getTaskByStatus("In Progress"));

//        System.out.println(controller.getTaskDetails("1"));
//        System.out.println();
        controller.editTaskDetails("1", "new", "new", localDate, "Completed");
//        System.out.println(controller.getTaskDetails("1"));

//        System.out.println(controller.getAssignedStaffs("2"));
        System.out.println(controller.getTotalTaskNum());
        System.out.println(controller);
    }
}