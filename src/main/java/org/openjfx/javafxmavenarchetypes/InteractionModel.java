package org.openjfx.javafxmavenarchetypes;

import java.util.ArrayList;

public class InteractionModel {

    ArrayList<InteractionModelSubscriber> subscribers;


    public InteractionModel(){
        subscribers = new ArrayList<>();
    }

    public void addSubscriber(InteractionModelSubscriber asub){
        subscribers.add(asub);
    }

    public void notifySubscribers(){
        subscribers.forEach(sub ->{
            sub.iModelChanged();
        });
    }
}
