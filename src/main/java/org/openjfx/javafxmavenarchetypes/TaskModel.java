package org.openjfx.javafxmavenarchetypes;

import java.util.ArrayList;

public class TaskModel {




    private ArrayList<ModelSubscriber> subs;
    public void addTask() {
        notifySubscribers();

    }

    public void addSubscriber(ModelSubscriber aSub ){
        subs.add(aSub);
    }

    public void editTask() {

    }

    public void markComplete() {

    }

    public void viewTask(){

    }

    private void notifySubscribers(){
        subs.forEach(sub -> sub.modelChanged());
    }
}
