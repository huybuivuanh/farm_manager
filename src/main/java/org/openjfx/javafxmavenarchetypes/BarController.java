package org.openjfx.javafxmavenarchetypes;

public class BarController {



    TaskModel model;
    protected enum State{
        READY,MOVING,PREPARE_CREATE,SELECTED,CREATING,RESIZE
    }


    public void handlePressed(){

    }

    public void addTaskSelect(){
        model.addTask();
    }
    public void editTaskSelect(){
        model.editTask();
    }
    public void markCompleteTaskSelect(){
        model.markComplete();
    }
    public void viewTaskSelect(){
        model.viewTask();
    }
}
