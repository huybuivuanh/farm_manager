package control;

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
    private ArrayList<Task> taskList;
    private ArrayList<Task> finishedTaskList;

    /**
     * constructor
     */
    public TaskControl(){
        taskList = new ArrayList<>();
        finishedTaskList=  new ArrayList<>();
    }

    public void addTask (String id, String taskName, String description, LocalDateTime dueDate)
    {
        Task task = new Task(  id, taskName , description, dueDate);
        taskList.add(task);
    }

    public void editTask(String id, String newTaskName, String newDescription, LocalDateTime newDueDate){
        Task edited = null;

        for (Task task : taskList) {
            if (task.getID().equals(id)) {
                edited = task;
            }
        }

        if (edited == null)
        {
            System.out.println("Task to be edited could not be found!");

        }
        else{
            edited.setTaskName(newTaskName);
            edited.setDescription(newDescription);
            edited.setDueDate(newDueDate);
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
                finishedTaskList.add(task);
                taskList.remove(task);
            }
        }
        if (completed == null)
        {
            System.out.println("Task to be edited could not be found!");
        }
    }




}
