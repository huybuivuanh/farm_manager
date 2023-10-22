package org.openjfx.javafxmavenarchetypes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class AddTaskView {


    VBox root;
    Label albel = new Label("Popup");
    TextField taskNameF = new TextField("Please up taskName");
    TextField descriptionF = new TextField("Description");
    TextField dueDate = new TextField("due date");
    Button submitTask = new Button("submit");
    Popup addTaskPop = new Popup();

}
