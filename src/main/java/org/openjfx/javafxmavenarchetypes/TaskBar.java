package org.openjfx.javafxmavenarchetypes;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TaskBar extends Pane {

    private Button addTask;
    private Button editTask;
    private Button markComplete;

    private Button viewCompleted;

    InteractionModel iModel;
    Button current;


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
