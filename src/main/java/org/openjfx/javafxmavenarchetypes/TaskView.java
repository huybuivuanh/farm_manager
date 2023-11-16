package org.openjfx.javafxmavenarchetypes;

import control.TaskControl;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.entities.Task;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TaskView extends StackPane implements ModelSubscriber {

    private VBox taskPage = new VBox();
    private Stage stage;
    private Scene MenuScene ;
    private Scene taskScene;


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Todo: Task tables and data (done)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private TableView<Task> taskTable = new TableView<Task>();
    private TableView<Task> CompletedTaskTable = new TableView<Task>();
    TaskControl taskController = new TaskControl();

    private ObservableList<Task> taskData = taskController.taskList;
    private ObservableList<Task> CompletedTaskData = taskController.finishedTaskList;

    public TaskView(){
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TODO: Task UI Section ( In progress)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo: Making the add task window pop up (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        VBox userBox = new VBox(30);
        Scene addUserScene = new Scene(userBox,300,250);

        Label albel = new Label("Popup");
        TextField idInput = new TextField("Input ID (optional)");
        TextField taskNameF = new TextField("Input taskName");
        TextField descriptionF = new TextField("Input task description");
        DatePicker dueDate = new DatePicker();
        Button submitTask = new Button("submit");

        userBox.getChildren().addAll(idInput,taskNameF,descriptionF,dueDate,submitTask);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo: Task Addition (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button addTask = new Button("add Task");
        addTask.setOnMouseClicked(e ->{
            idInput.setText("Input ID (optional)");
            taskNameF.setText("Input taskName");
            descriptionF.setText("Input task description");
            dueDate.setValue(null);
            stage.setScene(addUserScene);
        });

        submitTask.setOnMouseClicked(e ->{
            //Task newTask = new Task(idInput.getText(),taskNameF.getText(),descriptionF.getText(),dueDate.getValue().atTime(LocalTime.now()));
            //taskData.add(newTask);
            taskController.addTask(idInput.getText(),taskNameF.getText(),descriptionF.getText(),dueDate.getValue().atTime(LocalTime.now()));
            stage.setScene(taskScene);
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo: Task Editing
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Button editTask = new Button("edit task");
        VBox userEditBox = new VBox(30);
        Scene editUserScene = new Scene(userEditBox,300,250);

        TextField idInputEdit = new TextField();
        TextField taskNameFEdit = new TextField();
        TextField descriptionFEdit = new TextField();
        DatePicker dueDateEdit = new DatePicker();
        Button submitTaskEdit = new Button("submit");

        userEditBox.getChildren().addAll(idInputEdit,taskNameFEdit,descriptionFEdit,dueDateEdit,submitTaskEdit);
        submitTaskEdit.setOnMouseClicked(e ->{
            taskController.editTask(taskTable.getSelectionModel().getSelectedItem().getID(),
                    idInputEdit.getText(), taskNameFEdit.getText(), descriptionFEdit.getText(),
                    dueDateEdit.getValue().atTime(LocalTime.now()));
            System.out.println(taskTable.getSelectionModel().getSelectedItem());
            stage.setScene(taskScene);
            taskTable.refresh();
        });

        editTask.setOnMouseClicked(e ->{
            idInputEdit.setText(taskTable.getSelectionModel().getSelectedItem().getID());
            taskNameFEdit.setText(taskTable.getSelectionModel().getSelectedItem().getTaskName());
            descriptionFEdit.setText(taskTable.getSelectionModel().getSelectedItem().getDescription());
            dueDateEdit.setValue(taskTable.getSelectionModel().getSelectedItem().getDueDate().toLocalDate());
            stage.setScene(editUserScene);
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo: Task Completion (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button markComplete = new Button("Mark Complete");

        markComplete.setOnMouseClicked(e ->{
            taskController.completeTask(taskTable.getSelectionModel().getSelectedItem().getID());
            taskTable.refresh();
        });

        Button taskBackToMain = new Button("back");
        taskBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo: the view of completed tasks
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        VBox completedTaskBox = new VBox(30);
        Scene completedTaskScene = new Scene(completedTaskBox,300,250);

        Button viewCompleted = new Button("Completed Tasks");
        viewCompleted.setOnMouseClicked(e->{
            stage.setScene(completedTaskScene);
        });

        Button completedTaskBackToMain = new Button("back");
        completedTaskBackToMain.setOnMouseClicked(e ->{
            stage.setScene(taskScene);
        });

        Label completedTaskLabel = new Label("Completed Task Popup");

        TableColumn<Task, String> completedTaskIDCol = new TableColumn<Task, String>("Task ID");
        completedTaskIDCol.editableProperty().setValue(true);
        completedTaskIDCol.setMinWidth(130);
        completedTaskIDCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("ID")
        );

        TableColumn<Task, String> completedTaskName = new TableColumn<Task, String>("Task Name");
        completedTaskName.setMinWidth(130);
        completedTaskName.setCellValueFactory(
                new PropertyValueFactory<Task, String>("taskName")
        );

        TableColumn<Task, String> completedTaskDescription = new TableColumn<Task, String>("Task description");
        completedTaskDescription.setMinWidth(130);
        completedTaskDescription.setCellValueFactory(
                new PropertyValueFactory<Task, String>("description")
        );

        TableColumn<Task, LocalDateTime> completedTaskDueDate = new TableColumn<Task, LocalDateTime>("Due date");
        completedTaskDueDate.setMinWidth(130);
        completedTaskDueDate.setCellValueFactory(
                new PropertyValueFactory<Task, LocalDateTime>("dueDate")
        );

        // I could group the buttons into the hbox and then add that and the table to the thing 6 lines later
        //        HBox completedTaskTopBar= new HBox();
        //        completedTaskTopBar.getChildren().addAll(completedTaskBackToMain);

        CompletedTaskTable.setItems(CompletedTaskData);
        CompletedTaskTable.setEditable(true);
        CompletedTaskTable.getColumns().addAll(completedTaskIDCol, completedTaskName, completedTaskDescription,completedTaskDueDate);
        completedTaskBox.getChildren().addAll(completedTaskLabel, completedTaskBackToMain, CompletedTaskTable);




        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo: Task view formatting (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        HBox topBar= new HBox();
        topBar.getChildren().addAll(addTask,editTask,markComplete,viewCompleted ,taskBackToMain);

        TableColumn<Task, String> taskIDCol = new TableColumn<Task, String>("Task ID");

        taskIDCol.editableProperty().setValue(true);
        taskIDCol.setMinWidth(130);
        taskIDCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("ID")
        );

        TableColumn<Task, String> taskName = new TableColumn<Task, String>("Task Name");
        taskName.setMinWidth(130);
        taskName.setCellValueFactory(
                new PropertyValueFactory<Task, String>("taskName")
        );

        TableColumn<Task, String> taskDescription = new TableColumn<Task, String>("Task description");
        taskDescription.setMinWidth(130);
        taskDescription.setCellValueFactory(
                new PropertyValueFactory<Task, String>("description")
        );

        TableColumn<Task, LocalDateTime> taskDueDate = new TableColumn<Task, LocalDateTime>("Due date");
        taskDueDate.setMinWidth(130);
        taskDueDate.setCellValueFactory(
                new PropertyValueFactory<Task, LocalDateTime>("dueDate")
        );

        taskTable.setItems(taskData);
        taskTable.setEditable(true);
        taskTable.getColumns().addAll(taskIDCol,taskName,taskDescription,taskDueDate);

        taskPage.getChildren().addAll(topBar,taskTable);
        this.getChildren().addAll(taskPage);
    }

    public void setStageMenuTask(Stage stage, Scene MenuScene, Scene taskScene){
        this.stage = stage;
        this.MenuScene = MenuScene;
        this.taskScene = taskScene;
    }

    @Override
    public void modelChanged() {

    }
}
