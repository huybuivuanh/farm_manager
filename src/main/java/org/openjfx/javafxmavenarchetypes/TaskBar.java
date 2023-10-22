package org.openjfx.javafxmavenarchetypes;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TaskBar extends Pane {

    private RectButton addTask;
    private RectButton editTask;
    private RectButton markComplete;

    private RectButton viewCompleted;

    InteractionModel iModel;
    RectButton current;


    public TaskBar(){
        VBox root = new VBox();

        root.getChildren().addAll(addTask,editTask,markComplete,viewCompleted);


    }

    public void setInteractionModel(InteractionModel newModel){
        iModel = newModel;
    }

    public void setController(BarController controller){
        addTask.setOnMouseClicked(e ->{
            current = addTask;
            controller.addTaskSelect();
        });

    }

}
