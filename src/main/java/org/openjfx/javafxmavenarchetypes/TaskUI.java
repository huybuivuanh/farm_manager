package org.openjfx.javafxmavenarchetypes;

import javafx.scene.layout.StackPane;

public class TaskUI extends StackPane implements InteractionModelSubscriber {

    TaskModel model;

    InteractionModel iModel;

    BarController controller;

    TaskBar bar;


    @Override
    public void iModelChanged() {

    }
}
