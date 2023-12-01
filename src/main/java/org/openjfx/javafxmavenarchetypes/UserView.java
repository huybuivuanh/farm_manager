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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.entities.Task;
import org.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.lang.Boolean.parseBoolean;

public class UserView extends StackPane {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Todo: User tables and data (Need to implement user tasks view)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private VBox userPage = new VBox();
    private Stage stage;
    private Scene MenuScene ;
    private Scene userScene;

    // todo: initializer

    TableViewsAndDataInitializer initer = new TableViewsAndDataInitializer();

    public TaskControl taskController = initer.getTaskController();
    public UserControl userController = initer.getUserController();

    public TableView<Task> taskTable = initer.getTaskTable();
    public TableView<Task> CompletedTaskTable = initer.getCompletedTaskTable();
    public ObservableList<Task> taskData = initer.getTaskData();
    public ObservableList<Task> CompletedTaskData = initer.getCompletedTaskData();

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




    public UserView(){
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  TODO : User UI Section ( done )
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Button busers= new Button();
        busers.setText("Users");
        busers.setOnAction(e-> {
            stage.setScene(userScene);
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // TODO: adding functionality to the user tab (connecting to tasks) (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo: adding users (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        VBox userBox2 = new VBox(30);
        Scene addUserScene2 = new Scene(userBox2,300,250);
        Label userLabel = new Label("User Popup");
        userLabel.setFont(new Font("Arial", 20));


        TextField userIdInput = new TextField("Input User ID");
        TextField emailInput = new TextField("User Email");
        TextField passwordInput = new TextField("User Password");
        TextField fNameInput = new TextField("User First Name");
        TextField lNameInput = new TextField("User Last Name");
        TextField ownerInput = new TextField("Ownership");
        DatePicker dob = new DatePicker();
        Button submitAddUserInfo = new Button("Submit");
        Button cancelAddUser = new Button("Cancel");
        cancelAddUser.setOnMouseClicked(event -> {
            stage.setScene(userScene);
        });

        userBox2.getChildren().addAll(fNameInput,lNameInput, ownerInput,userIdInput,emailInput,passwordInput,dob,submitAddUserInfo, cancelAddUser);

        Button addUser = new Button("Add User");
        addUser.setOnMouseClicked(e ->{
            userIdInput.setText("Input User ID");
            emailInput.setText("User Email");
            passwordInput.setText("User Password");
            fNameInput.setText("User First Name");
            lNameInput.setText("User Last Name");
            ownerInput.setText("Ownership");
            dob.setValue(null);
            stage.setScene(addUserScene2);
        });
        submitAddUserInfo.setOnMouseClicked(e ->{
            //User newUser = new Employee(userIdInput.getText(),emailInput.getText(), passwordInput.getText(), fNameInput.getText(), lNameInput.getText() ,dob.getValue(), parseBoolean(ownerInput.getText()));
            //userData.add(newUser);
            try {
                if (dob.getValue() != null){
                    userController.addUser(userIdInput.getText(),emailInput.getText(), passwordInput.getText(), fNameInput.getText(), lNameInput.getText() ,dob.getValue(), parseBoolean(ownerInput.getText()));

                } else {
                    System.out.println("Select Date Of Birth");
                }
            } catch (NoSuchFieldException ex) {
                throw new RuntimeException(ex);
            }
            if (dob.getValue() != null){
                stage.setScene(userScene);
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
            }
        });

        Button userBackToMain = new Button("Back To Main");
        userBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 1: remove user (done - might need revisiting later)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button removeUser = new Button("Remove User");
        removeUser.setOnMouseClicked(e-> {
            if (userTable.getSelectionModel().getSelectedItem() != null){
                // not sure if any additional work is needed here within the class itself
                //userTable.getSelectionModel().getSelectedItem().
                //userTable.getItems().remove(userTable.getSelectionModel().getSelectedItem());
                userController.removeUser(userTable.getSelectionModel().getSelectedItem().getID());
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
                System.out.println(userController.allEmployees);
            } else {
                System.out.println("Need to select a user");
            }

        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 2: edit user (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button editUser = new Button("Edit User");

        //Making editPopup
        VBox actualUserEditBox = new VBox(30);
        Scene actualUserEditScene = new Scene(actualUserEditBox,300,250);

        TextField userIdEdit = new TextField("");
        TextField emailEdit = new TextField("");
        TextField passwordEdit = new TextField("");
        TextField fNameEdit = new TextField("");
        TextField lNameEdit = new TextField("");
        TextField ownerEdit = new TextField("");
        DatePicker dobEdit = new DatePicker();
        Button submitUserInfoEdit = new Button("Submit");
        Button cancelEditUser = new Button("Cancel");
        cancelEditUser.setOnMouseClicked(event -> {
            stage.setScene(userScene);
        });

        actualUserEditBox.getChildren().addAll(fNameEdit,lNameEdit,ownerEdit,userIdEdit, emailEdit, passwordEdit, dobEdit,submitUserInfoEdit, cancelEditUser);
        submitUserInfoEdit.setOnMouseClicked(e-> {
            userController.editUser(userTable.getSelectionModel().getSelectedItem().getID(),
                    userIdEdit.getText(), fNameEdit.getText(),lNameEdit.getText(),
                    parseBoolean(ownerEdit.getText()),emailEdit.getText(),
                    passwordEdit.getText(), dobEdit.getValue());
            System.out.println(userTable.getSelectionModel().getSelectedItem());

            taskTable.refresh();
            allTasksInUserViewTable.refresh();
            userTasksTable.refresh();
            CompletedTaskTable.refresh();
            taskUsersTable.refresh();
            allUsersInTaskViewTable.refresh();
            userTable.refresh();

            stage.setScene(userScene);
        });

        editUser.setOnMouseClicked(e-> {
            if (userTable.getSelectionModel().getSelectedItem() != null){
                fNameEdit.setText(userTable.getSelectionModel().getSelectedItem().getFirstName());
                lNameEdit.setText(userTable.getSelectionModel().getSelectedItem().getLastName());
                ownerEdit.setText(String.valueOf(userTable.getSelectionModel().getSelectedItem().getOwner()));
                userIdEdit.setText(userTable.getSelectionModel().getSelectedItem().getID());
                emailEdit.setText(userTable.getSelectionModel().getSelectedItem().getEmail());
                passwordEdit.setText(userTable.getSelectionModel().getSelectedItem().getPassword());
                dobEdit.setValue(userTable.getSelectionModel().getSelectedItem().getDOB());

                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();

                stage.setScene(actualUserEditScene);
                System.out.println(userController.allEmployees);
            } else {
                System.out.println("Need to select a user");
            }

        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 3: promote user (done - revisit changes to employee hierarchy) (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button promoteUser = new Button("Promote User");
        promoteUser.setOnMouseClicked(e-> {
            if (userTable.getSelectionModel().getSelectedItem() != null){
                // not sure if any additional work is needed here within the class itself
                //userTable.getSelectionModel().getSelectedItem().
                userController.promoteUser(userTable.getSelectionModel().getSelectedItem().getID());
                //userTable.getSelectionModel().getSelectedItem().setOwner(true);
                System.out.println(userController.allEmployees);

                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
            } else {
                System.out.println("Need to select a user");
            }

        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 4: Employee Tasks view. ( assigning and unassigning tasks to Employee) (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        VBox employeeTasksBox = new VBox(30);
        Scene employeeTasksScene = new Scene(employeeTasksBox,300,250);
        Label employeeTasksLabel = new Label("Employee Tasks Popup view");
        employeeTasksLabel.getStyleClass().add("page-label");
        Label employeeNameLabel = new Label();
        employeeNameLabel.getStyleClass().add("page-label");

        Button employeeTasks = new Button("View Tasks");
        employeeTasks.setOnMouseClicked(e->{
            if (userTable.getSelectionModel().getSelectedItem() != null){
                userTaskData= userTable.getSelectionModel().getSelectedItem().getTaskList();
                userTasksTable.setItems(userTaskData);
                String firstName = userTable.getSelectionModel().getSelectedItem().getFirstName();
                String lastName =  userTable.getSelectionModel().getSelectedItem().getLastName();
                employeeNameLabel.setText("Employee: " + firstName +" " + lastName);
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
                stage.setScene(employeeTasksScene);
            } else {
                System.out.println("Need to select a user");
            }

        });

        Button employeeAddTasks = new Button("Add Task");
        employeeAddTasks.setOnMouseClicked(e->{
            //userTable.getSelectionModel().getSelectedItem().addTask(allTasksInUserViewTable.getSelectionModel().getSelectedItem());
            userController.assignTask(userTable.getSelectionModel().getSelectedItem().getID(),allTasksInUserViewTable.getSelectionModel().getSelectedItem() );
            taskTable.refresh();
            allTasksInUserViewTable.refresh();
            userTasksTable.refresh();
            CompletedTaskTable.refresh();
            taskUsersTable.refresh();
            allUsersInTaskViewTable.refresh();
            userTable.refresh();
            System.out.println( "the add task button has been clicked");
        });

        Button employeeRemoveTasks = new Button("Remove Task");
        employeeRemoveTasks.setOnMouseClicked(e->{
            //userTable.getSelectionModel().getSelectedItem().removeTask(userTasksTable.getSelectionModel().getSelectedItem().getID());
            userController.unAssignTask( userTable.getSelectionModel().getSelectedItem().getID(), userTasksTable.getSelectionModel().getSelectedItem());
            taskTable.refresh();
            allTasksInUserViewTable.refresh();
            userTasksTable.refresh();
            CompletedTaskTable.refresh();
            taskUsersTable.refresh();
            allUsersInTaskViewTable.refresh();
            userTable.refresh();
            System.out.println( "the remove task button has been clicked");
        });

        Button employeeTasksBackToMain = new Button("Back");
        employeeTasksBackToMain.setOnMouseClicked(e ->{
            stage.setScene(userScene);
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 4.1: Employee's tasks inside of employee task view (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        TableColumn<Task, String> userTaskIDCol = new TableColumn<Task, String>("Task ID");
        userTaskIDCol.editableProperty().setValue(true);
        userTaskIDCol.setMinWidth(130);
        userTaskIDCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("ID")
        );

        TableColumn<Task, String> userTaskName = new TableColumn<Task, String>("Task Name");
        userTaskName.setMinWidth(130);
        userTaskName.setCellValueFactory(
                new PropertyValueFactory<Task, String>("taskName")
        );

        TableColumn<Task, String> userTaskDescription = new TableColumn<Task, String>("Task description");
        userTaskDescription.setMinWidth(130);
        userTaskDescription.setCellValueFactory(
                new PropertyValueFactory<Task, String>("description")
        );

        TableColumn<Task, LocalDateTime> userTaskDueDate = new TableColumn<Task, LocalDateTime>("Due date");
        userTaskDueDate.setMinWidth(130);
        userTaskDueDate.setCellValueFactory(
                new PropertyValueFactory<Task, LocalDateTime>("dueDate")
        );

        // userTaskData= userTable.getSelectionModel().getSelectedItem().getTaskList();
        userTasksTable.setItems(userTaskData);
        userTasksTable.setEditable(true);
        userTasksTable.getColumns().addAll(userTaskIDCol, userTaskName, userTaskDescription,userTaskDueDate);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 4.2: all tasks inside of employee task view (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        TableColumn<Task, String> allTaskIDColInUser = new TableColumn<Task, String>("Task ID");
        allTaskIDColInUser.editableProperty().setValue(true);
        allTaskIDColInUser.setMinWidth(130);
        allTaskIDColInUser.setCellValueFactory(
                new PropertyValueFactory<Task, String>("ID")
        );

        TableColumn<Task, String> allTaskNameColInUser = new TableColumn<Task, String>("Task Name");
        allTaskNameColInUser.setMinWidth(130);
        allTaskNameColInUser.setCellValueFactory(
                new PropertyValueFactory<Task, String>("taskName")
        );

        TableColumn<Task, String> allTaskDescriptionColInUser = new TableColumn<Task, String>("Task description");
        allTaskDescriptionColInUser.setMinWidth(130);
        allTaskDescriptionColInUser.setCellValueFactory(
                new PropertyValueFactory<Task, String>("description")
        );

        TableColumn<Task, LocalDateTime> allTaskDueDateColInUser = new TableColumn<Task, LocalDateTime>("Due date");
        allTaskDueDateColInUser.setMinWidth(130);
        allTaskDueDateColInUser.setCellValueFactory(
                new PropertyValueFactory<Task, LocalDateTime>("dueDate")
        );

        allTasksInUserViewTable.setItems(allTaskDataInUserViewTable);
        allTasksInUserViewTable.setEditable(true);
        allTasksInUserViewTable.getColumns().addAll(allTaskIDColInUser,allTaskNameColInUser, allTaskDescriptionColInUser, allTaskDueDateColInUser);

        Label taskLabelWithinEmployeeTasks = new Label("All tasks:");
        taskLabelWithinEmployeeTasks.getStyleClass().add("page-label");
        HBox topUserAddTaskBar= new HBox();
        topUserAddTaskBar.getChildren().addAll( employeeAddTasks, employeeRemoveTasks ,employeeTasksBackToMain);


        employeeTasksBox.getChildren().addAll(employeeTasksLabel, employeeNameLabel, topUserAddTaskBar, userTasksTable, taskLabelWithinEmployeeTasks, allTasksInUserViewTable);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 4.3: User Table Formatting (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        HBox topUserBar= new HBox();
        topUserBar.getChildren().addAll(addUser,editUser,removeUser, promoteUser ,employeeTasks ,userBackToMain);

        TableColumn<User, String> userIDCol = new TableColumn<User, String>("User ID");
        userIDCol.setMinWidth(130);
        userIDCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("ID")
        );

        TableColumn<User, String> userEmailCol = new TableColumn<User, String>("User Email");
        userEmailCol.setMinWidth(130);
        userEmailCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("email")
        );
        TableColumn<User, String> userPasswordCol = new TableColumn<User, String>("User Password");
        userPasswordCol.setMinWidth(130);
        userPasswordCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("password")
        );
        TableColumn<User, String> userFirstNameCol = new TableColumn<User, String>("User First Name");
        userFirstNameCol.setMinWidth(130);
        userFirstNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("firstName")
        );
        TableColumn<User, String> userLastNameCol = new TableColumn<User, String>("User Last Name");
        userLastNameCol.setMinWidth(130);
        userLastNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("lastName")
        );

        TableColumn<User, LocalDate> userDOBCol = new TableColumn<User, LocalDate>("User Date of Birth");
        userDOBCol.setMinWidth(130);
        userDOBCol.setCellValueFactory(
                new PropertyValueFactory<User, LocalDate>("DOB")
        );

        TableColumn<User, Boolean> userOwnership = new TableColumn<User, Boolean>("Owner (T/F)");
        userOwnership.setMinWidth(130);
        userOwnership.setCellValueFactory(
                new PropertyValueFactory<User, Boolean>("owner")
        );

        userTable.setItems(userData);
        userTable.setEditable(true);
        userTable.getColumns().addAll(userFirstNameCol, userLastNameCol, userOwnership,userIDCol,userEmailCol,userPasswordCol, userDOBCol);
        userPage.getChildren().addAll(topUserBar,userTable);

        // css
        addUserScene2.getStylesheets().add(getClass().getClassLoader().getResource("user.css").toExternalForm());
        actualUserEditScene.getStylesheets().add(getClass().getClassLoader().getResource("user.css").toExternalForm());
        employeeTasksScene.getStylesheets().add(getClass().getClassLoader().getResource("user.css").toExternalForm());

        this.getChildren().add(userPage);
    }


    /**
     * Sets the primary stage and scenes of the application. Specifically handles the
     * menu and user scenes. Additionally, applies a stylesheet to the user scene.
     *
     * @param stage The primary JavaFX stage where scenes will be displayed.
     * @param MenuScene The scene representing the application's main menu.
     * @param userScene The scene representing the user view of the application and to which the style sheet is to be applied.
     *
     */
    public void setStageMenuUser(Stage stage, Scene MenuScene, Scene userScene){
        this.stage = stage;
        this.MenuScene = MenuScene;
        this.userScene = userScene;
        userScene.getStylesheets().add(getClass().getClassLoader().getResource("user.css").toExternalForm());
    }

}
