package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.entities.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskControl {

    // Should contain a list of tasks
    // should be able to:
        //  display list of tasks
        //  add a task
        //  view a task
        //  edit a task
        //  delete a task


    /**
     * list of tasks
     */
    public ObservableList<Task> taskList;
    public ObservableList<Task> finishedTaskList;

    /**
     * constructor
     */
    public TaskControl(){
        taskList = FXCollections.observableArrayList();
        finishedTaskList=  FXCollections.observableArrayList();
    }

    public void addTask (String id, String taskName, String description, LocalDateTime dueDate)
    {
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
            taskList.add(task);
        }
        else {
            System.out.println("There already is a task with the desired ID");
        }
    }

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
            }
            else{
                System.out.println("The suggested new task ID is already in use.");
            }
        }
    }

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
        }
    }




}
