package org.openjfx.javafxmavenarchetypes;

import control.ControllerInitializer;
import control.TableViewsAndDataInitializer;
import control.TaskControl;
import control.UserControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.entities.Task;
import org.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TaskView extends StackPane implements ModelSubscriber {

    private VBox taskPage = new VBox();
    private Stage stage;
    private Scene MenuScene ;
    private Scene taskScene;

    // todo: initializer

    TableViewsAndDataInitializer initer = new TableViewsAndDataInitializer();

    public TaskControl taskController = initer.getTaskController();
    public UserControl userController = initer.getUserController();

    public TableView<Task> taskTable = initer.getTaskTable();
    public TableView<Task> CompletedTaskTable = initer.getCompletedTaskTable();
    public ObservableList<Task> taskData = initer.getTaskData();
    public ObservableList<Task> CompletedTaskData = initer.getCompletedTaskData();
    public TableView<TaskBar> bars = initer.getBars();

    // Todo: User tables and data (Need to implement user tasks view)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TableView<User> userTable = initer.getUserTable();
    public ObservableList<User> userData = initer.getUserData();

    // Todo: tableviews and data for users assigned to tasks
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TableView<User> taskUsersTable =initer.getTaskUsersTable();
    public TableView<User> allUsersInTaskViewTable =initer.getAllUsersInTaskViewTable();
    public ObservableList<User> taskUserData= initer.getTaskUserData();
    public ObservableList<User> allUserDataInTaskViewTable = initer.getAllUserDataInTaskViewTable();

    // Todo: tableviews and data for tasks assigned to users
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TableView<Task> userTasksTable =initer.getUserTasksTable();
    public TableView<Task> allTasksInUserViewTable = initer.getAllTasksInUserViewTable();
    public ObservableList<Task> userTaskData= initer.getUserTaskData();
    public ObservableList<Task> allTaskDataInUserViewTable =  initer.getAllTaskDataInUserViewTable();




    public TaskView(){
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TODO: Task UI Section ( In progress)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 1 : Making the add task window pop up (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        VBox userBox = new VBox(30);
        Scene addUserScene = new Scene(userBox,300,250);


        TextField idInput = new TextField("Input ID (optional)");
        TextField taskNameF = new TextField("Input taskName");
        TextField descriptionF = new TextField("Input task description");
        DatePicker dueDate = new DatePicker();
        Button submitTask = new Button("Submit");
        Button cancelAddTask = new Button("Cancel");
        cancelAddTask.setOnMouseClicked(event -> {
            stage.setScene(taskScene);
        });

        userBox.getChildren().addAll(idInput,taskNameF,descriptionF,dueDate,submitTask, cancelAddTask);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 2: Task Addition (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button addTask = new Button("Add Task");
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
            try {
                if (dueDate.getValue() != null){
                    taskController.addTask(idInput.getText(),taskNameF.getText(),descriptionF.getText(),dueDate.getValue().atTime(LocalTime.now()));
                } else {
                    System.out.println("Please Select Due Date");
                }

            } catch (NoSuchFieldException ex) {
                throw new RuntimeException(ex);
            }
            if (dueDate.getValue() != null){
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();

                stage.setScene(taskScene);
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 3: Task Editing
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Button editTask = new Button("Edit Task");
        VBox userEditBox = new VBox(30);
        Scene editUserScene = new Scene(userEditBox,300,250);

        TextField idInputEdit = new TextField();
        TextField taskNameFEdit = new TextField();
        TextField descriptionFEdit = new TextField();
        DatePicker dueDateEdit = new DatePicker();
        Button submitTaskEdit = new Button("Submit");
        Button cancelEditTask = new Button("Cancel");
        cancelEditTask.setOnMouseClicked(event -> {
            stage.setScene(taskScene);
        });

        userEditBox.getChildren().addAll(idInputEdit,taskNameFEdit,descriptionFEdit,dueDateEdit,submitTaskEdit, cancelEditTask);
        submitTaskEdit.setOnMouseClicked(e ->{
            taskController.editTask(taskTable.getSelectionModel().getSelectedItem().getID(),
                    idInputEdit.getText(), taskNameFEdit.getText(), descriptionFEdit.getText(),
                    dueDateEdit.getValue().atTime(LocalTime.now()));
            System.out.println(taskTable.getSelectionModel().getSelectedItem());
            taskTable.refresh();
            allTasksInUserViewTable.refresh();
            userTasksTable.refresh();
            CompletedTaskTable.refresh();
            taskUsersTable.refresh();
            allUsersInTaskViewTable.refresh();
            userTable.refresh();

            stage.setScene(taskScene);
        });

        editTask.setOnMouseClicked(e ->{
            if (taskTable.getSelectionModel().getSelectedItem() != null){
                idInputEdit.setText(taskTable.getSelectionModel().getSelectedItem().getID());
                taskNameFEdit.setText(taskTable.getSelectionModel().getSelectedItem().getTaskName());
                descriptionFEdit.setText(taskTable.getSelectionModel().getSelectedItem().getDescription());
                dueDateEdit.setValue(taskTable.getSelectionModel().getSelectedItem().getDueDate().toLocalDate());
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
                stage.setScene(editUserScene);
            } else {
                System.out.println("Need to select a task");
            }

        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 4: Task Completion (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button markComplete = new Button("Mark Complete");

        markComplete.setOnMouseClicked(e ->{
            if (taskTable.getSelectionModel().getSelectedItem() != null){
                taskController.completeTask(taskTable.getSelectionModel().getSelectedItem().getID());
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
            } else {
                System.out.println("Need to select a task");
            }

        });

        Button taskBackToMain = new Button("Back To Main");
        taskBackToMain.setOnMouseClicked(e ->{
            taskTable.refresh();
            allTasksInUserViewTable.refresh();
            userTasksTable.refresh();
            CompletedTaskTable.refresh();
            taskUsersTable.refresh();
            allUsersInTaskViewTable.refresh();
            userTable.refresh();
            stage.setScene(MenuScene);
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 5: the view of completed tasks
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        VBox completedTaskBox = new VBox(30);
        Scene completedTaskScene = new Scene(completedTaskBox,300,250);

        Button viewCompleted = new Button("Completed Tasks");
        viewCompleted.setOnMouseClicked(e->{
            stage.setScene(completedTaskScene);
        });

        Button completedTaskBackToMain = new Button("Back");
        completedTaskBackToMain.setOnMouseClicked(e ->{
            stage.setScene(taskScene);
        });

        Label completedTaskLabel = new Label("Completed Task Popup");
        completedTaskLabel.getStyleClass().add("page-label");

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
        // Todo 6: Task Employees view
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        VBox taskEmployeesBox = new VBox(30);
        Scene taskEmployeesScene = new Scene(taskEmployeesBox,300,250);
        Label taskEmployeesLabel = new Label("Task Employees Popup view");
        taskEmployeesLabel.getStyleClass().add("page-label");
        Label taskNameLabel = new Label();
        taskNameLabel.getStyleClass().add("page-label");
        Label employeeLabelWithinTaskEmployees = new Label("All employees:");
        employeeLabelWithinTaskEmployees.getStyleClass().add("page-label");

        Button taskEmployees = new Button("View Employees");
        taskEmployees.setOnMouseClicked(e->{
            if (taskTable.getSelectionModel().getSelectedItem() != null){
                taskUserData= taskTable.getSelectionModel().getSelectedItem().getStaffList();
                taskUsersTable.setItems(taskUserData);
                String taskName = taskTable.getSelectionModel().getSelectedItem().getTaskName();
                taskNameLabel.setText("Task Name: " + taskName);
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
                stage.setScene(taskEmployeesScene);
            } else {
                System.out.println("Need to select a task");
            }

        });

        Button taskAddEmployees = new Button("Assign Employee");
        taskAddEmployees.setOnMouseClicked(e->{
            //taskTable.getSelectionModel().getSelectedItem().addStaff(allUsersInTaskViewTable.getSelectionModel().getSelectedItem());
            taskController.assignEmployee(taskTable.getSelectionModel().getSelectedItem().getID(),allUsersInTaskViewTable.getSelectionModel().getSelectedItem() );
            taskTable.refresh();
            allTasksInUserViewTable.refresh();
            userTasksTable.refresh();
            CompletedTaskTable.refresh();
            taskUsersTable.refresh();
            allUsersInTaskViewTable.refresh();
            userTable.refresh();
            System.out.println( "the add Employee button has been clicked");
        });

        Button taskRemoveEmployees = new Button("Remove Employee");
        taskRemoveEmployees.setOnMouseClicked(e->{
            //taskTable.getSelectionModel().getSelectedItem().removeStaff(taskUsersTable.getSelectionModel().getSelectedItem().getID());
            taskController.unAssignEmployee(taskTable.getSelectionModel().getSelectedItem().getID(),allUsersInTaskViewTable.getSelectionModel().getSelectedItem() );
            taskTable.refresh();
            allTasksInUserViewTable.refresh();
            userTasksTable.refresh();
            CompletedTaskTable.refresh();
            taskUsersTable.refresh();
            allUsersInTaskViewTable.refresh();
            userTable.refresh();
            System.out.println( "the remove Employee button has been clicked");
        });


        Button taskEmployeesBackToTask = new Button("Back");
        taskEmployeesBackToTask.setOnMouseClicked(e ->{
            stage.setScene(taskScene);
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 6.2: Task's employees inside of employee task view (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        TableColumn<User, String> taskUserIDCol = new TableColumn<User, String>("User ID");
        taskUserIDCol.setMinWidth(130);
        taskUserIDCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("ID")
        );

        TableColumn<User, String> taskUserEmailCol = new TableColumn<User, String>("User Email");
        taskUserEmailCol.setMinWidth(130);
        taskUserEmailCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("email")
        );
        TableColumn<User, String> taskUserPasswordCol = new TableColumn<User, String>("User Password");
        taskUserPasswordCol.setMinWidth(130);
        taskUserPasswordCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("password")
        );
        TableColumn<User, String> taskUserFirstNameCol = new TableColumn<User, String>("User First Name");
        taskUserFirstNameCol.setMinWidth(130);
        taskUserFirstNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("firstName")
        );
        TableColumn<User, String> taskUserLastNameCol = new TableColumn<User, String>("User Last Name");
        taskUserLastNameCol.setMinWidth(130);
        taskUserLastNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("lastName")
        );

        TableColumn<User, LocalDate> taskUserDOBCol = new TableColumn<User, LocalDate>("User Date of Birth");
        taskUserDOBCol.setMinWidth(130);
        taskUserDOBCol.setCellValueFactory(
                new PropertyValueFactory<User, LocalDate>("DOB")
        );

        TableColumn<User, Boolean> taskUserOwnership = new TableColumn<User, Boolean>("Owner (T/F)");
        taskUserOwnership.setMinWidth(130);
        taskUserOwnership.setCellValueFactory(
                new PropertyValueFactory<User, Boolean>("owner")
        );

        taskUsersTable.setItems(taskUserData);
        taskUsersTable.setEditable(true);
        taskUsersTable.getColumns().addAll(taskUserFirstNameCol, taskUserLastNameCol, taskUserOwnership,taskUserIDCol,taskUserEmailCol,taskUserPasswordCol, taskUserDOBCol);


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 6.3: All employee's inside task employee view (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        TableColumn<User, String> allUserIDColInTask = new TableColumn<User, String>("User ID");
        allUserIDColInTask.setMinWidth(130);
        allUserIDColInTask.setCellValueFactory(
                new PropertyValueFactory<User, String>("ID")
        );

        TableColumn<User, String> allUserEmailColInTask = new TableColumn<User, String>("User Email");
        allUserEmailColInTask.setMinWidth(130);
        allUserEmailColInTask.setCellValueFactory(
                new PropertyValueFactory<User, String>("email")
        );

        TableColumn<User, String> allUserPasswordColInTask = new TableColumn<User, String>("User Password");
        allUserPasswordColInTask.setMinWidth(130);
        allUserPasswordColInTask.setCellValueFactory(
                new PropertyValueFactory<User, String>("password")
        );
        TableColumn<User, String> allUserFirstNameColInTask = new TableColumn<User, String>("User First Name");
        allUserFirstNameColInTask.setMinWidth(130);
        allUserFirstNameColInTask.setCellValueFactory(
                new PropertyValueFactory<User, String>("firstName")
        );

        TableColumn<User, String> allUserLastNameColInTask = new TableColumn<User, String>("User Last Name");
        allUserLastNameColInTask.setMinWidth(130);
        allUserLastNameColInTask.setCellValueFactory(
                new PropertyValueFactory<User, String>("lastName")
        );

        TableColumn<User, LocalDate> allUserDOBColInTask = new TableColumn<User, LocalDate>("User Date of Birth");
        allUserDOBColInTask.setMinWidth(130);
        allUserDOBColInTask.setCellValueFactory(
                new PropertyValueFactory<User, LocalDate>("DOB")
        );

        TableColumn<User, Boolean> allUserOwnershipInTask = new TableColumn<User, Boolean>("Owner (T/F)");
        allUserOwnershipInTask.setMinWidth(130);
        allUserOwnershipInTask.setCellValueFactory(
                new PropertyValueFactory<User, Boolean>("owner")
        );

        allUsersInTaskViewTable.setItems(allUserDataInTaskViewTable);
        allUsersInTaskViewTable.setEditable(true);
        allUsersInTaskViewTable.getColumns().addAll(allUserFirstNameColInTask, allUserLastNameColInTask, allUserOwnershipInTask,
                allUserIDColInTask,allUserEmailColInTask,allUserPasswordColInTask, allUserDOBColInTask);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // todo 6.4:  creating top bar for the view of employee view inside of task and adding buttons to it (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        HBox topTaskAddUserBar= new HBox();
        topTaskAddUserBar.getChildren().addAll(taskAddEmployees, taskRemoveEmployees, taskEmployeesBackToTask);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // todo 6.5: adding the top bar itself into the page (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        taskEmployeesBox.getChildren().addAll(taskEmployeesLabel,taskNameLabel,topTaskAddUserBar, taskUsersTable,employeeLabelWithinTaskEmployees, allUsersInTaskViewTable);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 7: Task view formatting (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        HBox topBar= new HBox();
        topBar.getChildren().addAll(addTask,editTask,markComplete,viewCompleted,taskEmployees ,taskBackToMain);

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

        //css
        addUserScene.getStylesheets().add(getClass().getClassLoader().getResource("task.css").toExternalForm());
        completedTaskScene.getStylesheets().add(getClass().getClassLoader().getResource("task.css").toExternalForm());
        taskEmployeesScene.getStylesheets().add(getClass().getClassLoader().getResource("task.css").toExternalForm());
        editUserScene.getStylesheets().add(getClass().getClassLoader().getResource("task.css").toExternalForm());
        this.getChildren().addAll(taskPage);
    }

    /**
     * Sets the primary stage and scenes of the application. Specifically handles the
     * menu and task scenes. Additionally, applies a stylesheet to the task scene.
     *
     * @param stage The primary JavaFX stage where scenes will be displayed.
     * @param MenuScene The scene representing the application's main menu.
     * @param taskScene The scene representing the task view of the application and to which the style sheet is to be applied.
     *
     */
    public void setStageMenuTask(Stage stage, Scene MenuScene, Scene taskScene){
        this.stage = stage;
        this.MenuScene = MenuScene;
        this.taskScene = taskScene;
        taskScene.getStylesheets().add(getClass().getClassLoader().getResource("task.css").toExternalForm());
    }

    @Override
    public void modelChanged() {

    }
}
