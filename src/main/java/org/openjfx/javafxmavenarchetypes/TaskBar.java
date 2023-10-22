package org.openjfx.javafxmavenarchetypes;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TaskBar extends Pane {

    private RectButton addTask;
    private RectButton editTask;
    private RectButton markComplete;

    private RectButton viewCompleted;

    public TaskBar(){
        VBox root = new VBox();

        root.getChildren().addAll(addTask,editTask,markComplete,viewCompleted);


    }

}
