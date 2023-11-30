package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.entities.Employee;
import org.entities.Task;
import org.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.InitialFarm.DataFetch;
import org.InitialFarm.dataManager;

public class TaskControl {

    // Should contain a list of tasks
    // should be able to:
        //  display list of tasks
        //  add a task
        //  view a task
        //  edit a task
        //  delete a task


    /**
     * list of unifinished tasks
     */
    public ObservableList<Task> taskList;
    /**
     * list of finished tasks
     */
    public ObservableList<Task> finishedTaskList;
    /**
     * instance of data manager class.
     */
    public dataManager dataManager = new dataManager();

    /**
     * constructor
     */
    public TaskControl(){
        taskList = dataManager.initializeTasksFromDB();
        finishedTaskList=  FXCollections.observableArrayList();
    }

    /**
     * Adds a task into the system given its id, name, description, and duedate.
     * @param id: the id of the task to be added.
     * @param taskName: the name of the task to be added.
     * @param description: the description of the task to be added.
     * @param dueDate: the due date of the task to be added.
     */
    public void addTask (String id, String taskName, String description, LocalDateTime dueDate) throws NoSuchFieldException {
        //check if task already exists
        boolean taskExists= false;

        for (Task task : taskList) {
            if (task.getID().equals(id)) {
                taskExists=true;
            }
        }
        // if it doesn't, add it. If it does, report it.
        if (!taskExists){
            Task task = new Task( null, id, taskName , description, dueDate);
            task = dataManager.saveClass(task);
            taskList.add(task);
        }
        else {
            System.out.println("There already is a task with the desired ID");
        }
    }

    /**
     * Adds a task into the system given its id, name, description, and duedate.
     * @param oldId: the old id of the task to be edited.
     * @param newId: the potential new id of the task to be edited.
     * @param newTaskName: the new name of the task to be edited.
     * @param newDescription: the new description of the task to be added.
     * @param newDueDate: the new due date of the task to be edited.
     */
    public void editTask(String oldId, String newId, String newTaskName, String newDescription, LocalDateTime newDueDate){
        Task edited = null;
        boolean taskIdAlreadyExists = false;

        // check if task to be edited exists at all
        for (Task task : taskList) {
            if (task.getID().equals(oldId)) {
                edited = task;
            }
        }
        if (edited == null)
        {
            System.out.println("Task to be edited could not be found!");

        }
        // if it does
        else{
            // check if its new id is identical to another task's id.
            for (Task task : taskList) {
                if (task.getID().equals(newId) && task !=edited) {
                    taskIdAlreadyExists=true;
                    break;
                }
            }
            if (!taskIdAlreadyExists)
            {
                edited.setID(newId);
                edited.setTaskName(newTaskName);
                edited.setDescription(newDescription);
                edited.setDueDate(newDueDate);
                dataManager.updateClass(edited);
            }
            else{
                System.out.println("The suggested new task ID is already in use.");
            }
        }
    }

    /**
     * Marks a task as complete, removes it from the list of tasks, and adds it to list of completed tasks.
     * @param id: The id of the task to be marked as complete
     */
    public void completeTask(String id)
    {
        Task completed = null;
        for (Task task : taskList) {
            if (task.getID().equals(id)) {
                completed = task;
            }
        }
        if (completed == null)
        {
            System.out.println("Task to be edited could not be found!");
        }
        else{
            completed.markAsCompleted(true);
            //completed.setInProgress(false);
            finishedTaskList.add(completed);
            taskList.remove(completed);
            dataManager.removeClass(completed);
        }
    }


    /**
     * Assigns a user to a task in the list of tasks that the controller is keeping track of
     * @param taskId: The id of the task to which a user is to be assigned
     * @param user: The user to be assigned to the task
     */
    public void assignEmployee(String taskId, User user )
    {
        Task task = null;
        // check if task Id is found
        for (Task iter: taskList)
        {
            if (iter.getID().equals(taskId))
            {
                task = (Task) iter;
            }
        }
        if (task == null)
        {
            System.out.println("Task you are trying to assign a user to wasn't in the list of tasks!");
        }
        else{
            task.addStaff(user);
            dataManager.updateClass(task);
            dataManager.updateClass(user);
        }
    }


    /**
     * unAssigns a user to a task in the list of tasks that the controller is keeping track of
     * @param taskId: The id of the task to which a user is to be unassigned
     * @param user: The user to be unassigned to the task
     */
    public void unAssignEmployee(String taskId, User user )
    {
        Task task = null;
        // check if task Id is found
        for (Task iter: taskList)
        {
            if (iter.getID().equals(taskId))
            {
                task = (Task) iter;
            }
        }
        if (task == null)
        {
            System.out.println("Task you are trying to unassign a user to wasn't in the list of tasks");
        }
        else{
            task.removeStaff(user.getID());
            dataManager.updateClass(task);
            dataManager.updateClass(user);
        }
    }


    /**
     * Returns a string value of the task to be viewed .
     * @param id: The id of the task to which a user is to be unassigned
     * @return returned: a string representation of the task to be viewed.
     */
    public String viewTask (String id)
    {
        Task viewed = null;
        String returned = null;

        for (Task task : taskList) {
            if (task.getID().equals(id)) {
                viewed = task;
            }
        }

        if (viewed == null)
        {
            System.out.println("Task to be edited could not be found!");

        }
        else
        {
            returned = viewed.toString();
        }
        return returned;
    }

}
