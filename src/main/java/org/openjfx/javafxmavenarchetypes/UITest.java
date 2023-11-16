package org.openjfx.javafxmavenarchetypes;

import control.TaskControl;
import control.UserControl;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.InitialFarm.Chemical;
import org.InitialFarm.Crop;
import org.InitialFarm.GrainBin;
import org.entities.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Boolean.parseBoolean;
import static org.entities.Owner.owner;
import java.util.Iterator;
//HBox root = new HBox();
//root.setStyle("-fx-background-color: Green");
//Button grainButton = new Button("Make it Grain");
//grainButton.setMaxSize(100,200);






public class UITest extends Application {
    private TableView<Person> table = new TableView<Person>();
    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person("Jacob", "Smith", "jacob.smith@example.com"),
                    new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
                    new Person("Ethan", "Williams", "ethan.williams@example.com"),
                    new Person("Emma", "Jones", "emma.jones@example.com"),
                    new Person("Michael", "Brown", "michael.brown@example.com")
            );


//    private TableView<GrainBin> binTable = new TableView<>();
//    private final ObservableList<GrainBin> grainBinData =
//            FXCollections.observableArrayList(
//                    new GrainBin("Tod", "the moon", 400, false, false),
//                    new GrainBin("Dave", "the moon", 4000, false, false),
//                    new GrainBin("The Big One", "the moon", 40000, false, true),
//                    new GrainBin("Nice", "the moon", 69, true, false)
//            );

    private TableView<Crop> grainTable = new TableView<Crop>();
    private final ObservableList<Crop> cropData =
            FXCollections.observableArrayList(
                    new Crop(null,"Canola", "LibertyLink", 55),
                    new Crop(null,"Canola", "RoundupReady", 54),
                    new Crop(null,"Durum", "Navigator", 60),
                    new Crop(null,"Red Lentil", "Clearfield", 58),
                    new Crop(null,"Wheat & Barley", "All the other Grains", 45)

            );




    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Todo: Task tables and data (done)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private TableView<Task> taskTable = new TableView<Task>();
    private TableView<Task> CompletedTaskTable = new TableView<Task>();
    TaskControl taskController = new TaskControl();

    /**
     * new Task("124124", "Do the tasky", "This is my description", LocalDateTime.now()),
     *                     new Task("1243263456", "Do the tasky2", "My second description", LocalDateTime.now())
     */
    private ObservableList<Task> taskData = taskController.taskList;
    private ObservableList<Task> CompletedTaskData = taskController.finishedTaskList;

    private TableView<TaskBar> bars = new TableView<TaskBar>();
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Todo: User tables and data (Need to implement user tasks view)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private TableView<User> userTable = new TableView<User>();
    private TableView<Task> userTasksTable = new TableView<Task>();
    // for the selected user, will:
    // 1) pull out the arrayList (to be changed to observable)
    // 2) set the data of the userTasks TableView above to the contents of that list.
    // 3) need to implement functionality adding and removing tasks to user array ( could use ones already in user class)
    UserControl userController = new UserControl();
    /**
     * new User ...
     */
    private ObservableList<User> userData = userController.allEmployees;
    private ObservableList<Task> userTaskData= FXCollections.observableArrayList();
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private TableView<TaskBar> bars2= new TableView<TaskBar>();

    private TableView<Field> fieldTable = new TableView<Field>();

    private ObservableList<Field> fieldData =
            FXCollections.observableArrayList(new Field(null, "1", "field 1", 69, "Mars"),
                    new Field(null,"1", "field 2", 69, "Venus"),
                    new Field(null, "1", "field 3", 69, "Mercury"));

    private TableView<Record> recordTable = new TableView<>();
    private ObservableList<Record> recordData =
            FXCollections.observableArrayList(new Record("chem1", LocalDate.now(), "seed1", 11.0, LocalDate.now(), "fertilizer 1", LocalDate.now()),
                    new Record("chem2", LocalDate.now(), "seed2", 22.0, LocalDate.now(), "fertilizer 2", LocalDate.now()),
                    new Record("chem3", LocalDate.now(), "seed3", 33.0, LocalDate.now(), "fertilizer 3", LocalDate.now()));
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        //Making our farm view scene
//        Group fieldPageGroup = new Group();
//        Scene sceneFields = new Scene(fieldPageGroup,300,250);
        stage.setTitle("Farm Manager");
        stage.setWidth(1200);
        stage.setHeight(700);

        VBox taskSelector = new VBox(30);
        Image image = new Image("farm image.jpeg");
        ImageView imageView = new ImageView(image);
        StackPane menuStackPane = new StackPane();
        Scene MenuScene = new Scene(menuStackPane,300,250);
        MenuScene.getStylesheets().add(getClass().getClassLoader().getResource("styles.css").toExternalForm());




        TableColumn taskID = new TableColumn("Task ID");
        taskID.setMinWidth(130);

        // bookmark
//        VBox fieldPage = new VBox(30);
        FieldView fieldPage = new FieldView();
        Scene fieldScene = new Scene(fieldPage,300,250);
        fieldPage.setStageMainField(stage, MenuScene, fieldScene);

        Button bfield = new Button();
        bfield.setText("Fields");
        bfield.setOnAction(e -> stage.setScene(fieldScene));


        //Make bin view scene this
//        Group binPage = new Group();
        BinView binPage = new BinView();
        Scene sceneBins = new Scene(binPage,300,250);
        binPage.setStageMenu(stage, MenuScene, sceneBins);

        Button bbins = new Button();
        bbins.setText("Bins");
        bbins.setOnAction(e -> stage.setScene(sceneBins));

//        VBox taskPage = new VBox(30);
        TaskView taskPage = new TaskView();
        Scene taskScene = new Scene(taskPage,300,250);
        taskPage.setStageMenuTask(stage, MenuScene, taskScene);

        Button btasks = new Button();
        btasks.setText("Tasks");
        btasks.setOnAction(e -> stage.setScene(taskScene));
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  TODO : User UI Section ( done )
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        VBox userPage= new VBox(30);
        UserView userPage = new UserView();
        Scene userScene = new Scene(userPage, 300, 250);
        userPage.setStageMenuUser(stage, MenuScene, userScene);

        Button busers= new Button();
        busers.setText("Users");
        busers.setOnAction(e-> {
            stage.setScene(userScene);
        });
        taskSelector.getChildren().addAll(btasks,bfield,bbins,busers);
        taskSelector.setAlignment(Pos.CENTER);

        imageView.fitWidthProperty().bind(menuStackPane.widthProperty());
        imageView.fitHeightProperty().bind(menuStackPane.heightProperty());
        menuStackPane.getChildren().addAll(imageView,taskSelector);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // TODO: adding functionality to the user tab (connecting to tasks)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo: adding users (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        VBox userBox2 = new VBox(30);
//        Scene addUserScene2 = new Scene(userBox2,300,250);
//        Label userLabel = new Label("User Popup");
//        userLabel.setFont(new Font("Arial", 20));
//
//
//        TextField userIdInput = new TextField("Input User ID");
//        TextField emailInput = new TextField("User Email");
//        TextField passwordInput = new TextField("User Password");
//        TextField fNameInput = new TextField("User First Name");
//        TextField lNameInput = new TextField("User Last Name");
//        TextField ownerInput = new TextField("Ownership");
//        DatePicker dob = new DatePicker();
//        Button submitAddUserInfo = new Button("submit");
//
//        userBox2.getChildren().addAll(fNameInput,lNameInput, ownerInput,userIdInput,emailInput,passwordInput,dob,submitAddUserInfo);
//
//        Button addUser = new Button("add User");
//        addUser.setOnMouseClicked(e ->{
//                userIdInput.setText("Input User ID");
//                emailInput.setText("User Email");
//                passwordInput.setText("User Password");
//                fNameInput.setText("User First Name");
//                lNameInput.setText("User Last Name");
//                ownerInput.setText("Ownership");
//                dob.setValue(null);
//            stage.setScene(addUserScene2);
//        });
//        submitAddUserInfo.setOnMouseClicked(e ->{
//            //User newUser = new Employee(userIdInput.getText(),emailInput.getText(), passwordInput.getText(), fNameInput.getText(), lNameInput.getText() ,dob.getValue(), parseBoolean(ownerInput.getText()));
//            //userData.add(newUser);
//            userController.addUser(userIdInput.getText(),emailInput.getText(), passwordInput.getText(), fNameInput.getText(), lNameInput.getText() ,dob.getValue(), parseBoolean(ownerInput.getText()));
//            stage.setScene(userScene);
//            // not sure if below line is necessary
//            userTable.refresh();
//        });
//
//        Button userBackToMain = new Button("back");
//        userBackToMain.setOnMouseClicked(e ->{
//            stage.setScene(MenuScene);
//        });
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        //Todo 1: remove user (done - might need revisiting later)
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        Button removeUser = new Button("Remove User");
//        removeUser.setOnMouseClicked(e-> {
//            // not sure if any additional work is needed here within the class itself
//            //userTable.getSelectionModel().getSelectedItem().
//            //userTable.getItems().remove(userTable.getSelectionModel().getSelectedItem());
//            userController.removeUser(userTable.getSelectionModel().getSelectedItem().getID());
//            userTable.refresh();
//            System.out.println(userController.allEmployees);
//        });
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        //Todo 2: edit user (done)
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        Button editUser = new Button("edit user");
//
//        //Making editPopup
//        VBox actualUserEditBox = new VBox(30);
//        Scene actualUserEditScene = new Scene(actualUserEditBox,300,250);
//
//        TextField userIdEdit = new TextField("");
//        TextField emailEdit = new TextField("");
//        TextField passwordEdit = new TextField("");
//        TextField fNameEdit = new TextField("");
//        TextField lNameEdit = new TextField("");
//        TextField ownerEdit = new TextField("");
//        DatePicker dobEdit = new DatePicker();
//        Button submitUserInfoEdit = new Button("submit");
//
//        actualUserEditBox.getChildren().addAll(fNameEdit,lNameEdit,ownerEdit,userIdEdit, emailEdit, passwordEdit, dobEdit,submitUserInfoEdit);
//        submitUserInfoEdit.setOnMouseClicked(e-> {
//            userController.editUser(userTable.getSelectionModel().getSelectedItem().getID(),
//                    userIdEdit.getText(), fNameEdit.getText(),lNameEdit.getText(),
//                    parseBoolean(ownerEdit.getText()),emailEdit.getText(),
//                    passwordEdit.getText(), dobEdit.getValue());
//            System.out.println(userTable.getSelectionModel().getSelectedItem());
//            stage.setScene(userScene);
//            userTable.refresh();
//        });
//
//        editUser.setOnMouseClicked(e-> {
//            fNameEdit.setText(userTable.getSelectionModel().getSelectedItem().getFirstName());
//            lNameEdit.setText(userTable.getSelectionModel().getSelectedItem().getLastName());
//            ownerEdit.setText(String.valueOf(userTable.getSelectionModel().getSelectedItem().getOwner()));
//            userIdEdit.setText(userTable.getSelectionModel().getSelectedItem().getID());
//            emailEdit.setText(userTable.getSelectionModel().getSelectedItem().getEmail());
//            passwordEdit.setText(userTable.getSelectionModel().getSelectedItem().getPassword());
//            dobEdit.setValue(userTable.getSelectionModel().getSelectedItem().getDOB());
//
//            stage.setScene(actualUserEditScene);
//            System.out.println(userController.allEmployees);
//        });
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        //Todo 3: promote user (done - revisit changes to employee hierarchy)
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        Button promoteUser = new Button("Promote User");
//        promoteUser.setOnMouseClicked(e-> {
//            // not sure if any additional work is needed here within the class itself
//            //userTable.getSelectionModel().getSelectedItem().
//            userController.promoteUser(userTable.getSelectionModel().getSelectedItem().getID());
//            //userTable.getSelectionModel().getSelectedItem().setOwner(true);
//            userTable.refresh();
//            System.out.println(userController.allEmployees);
//        });
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        //Todo 3: task addition and removal.
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        // for the selected user, will:
//        // 1) pull out the arrayList (to be changed to observable)
//        // 2) set the data of the userTasks TableView above to the contents of that list.
//        // 3) need to implement functionality adding and removing tasks to user array ( could use ones already in user class)
//        // todo: first, create bogus tasks for bogus use just to test out view, then add functionality.
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        Button userTaskAssigner = new Button("Assign Task");
//        // this assign task should show a list of tasks from which we can choose one to be assigned to the selected user
//
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        //Todo 3: Employee Tasks view.
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        VBox employeeTasksBox = new VBox(30);
//        Scene employeeTasksScene = new Scene(employeeTasksBox,300,250);
//        Label employeeTasksLabel = new Label("Employee Tasks Popup view");
//        Label employeeNameLabel = new Label();
//
//        Button employeeTasks = new Button("View Tasks");
//        employeeTasks.setOnMouseClicked(e->{
//            userTaskData= userTable.getSelectionModel().getSelectedItem().getTaskList();
//            String firstName = userTable.getSelectionModel().getSelectedItem().getFirstName();
//            String lastName =  userTable.getSelectionModel().getSelectedItem().getLastName();
//            employeeNameLabel.setText("Employee: " + firstName +" " + lastName);
//            stage.setScene(employeeTasksScene);
//        });
//
//        Button employeeTasksBackToMain = new Button("back");
//        employeeTasksBackToMain.setOnMouseClicked(e ->{
//            stage.setScene(userScene);
//        });
//
//        TableColumn<Task, String> userTaskIDCol = new TableColumn<Task, String>("Task ID");
//        userTaskIDCol.editableProperty().setValue(true);
//        userTaskIDCol.setMinWidth(130);
//        userTaskIDCol.setCellValueFactory(
//                new PropertyValueFactory<Task, String>("ID")
//        );
//
//        TableColumn<Task, String> userTaskName = new TableColumn<Task, String>("Task Name");
//        userTaskName.setMinWidth(130);
//        userTaskName.setCellValueFactory(
//                new PropertyValueFactory<Task, String>("taskName")
//        );
//
//        TableColumn<Task, String> userTaskDescription = new TableColumn<Task, String>("Task description");
//        userTaskDescription.setMinWidth(130);
//        userTaskDescription.setCellValueFactory(
//                new PropertyValueFactory<Task, String>("description")
//        );
//
//        TableColumn<Task, LocalDateTime> userTaskDueDate = new TableColumn<Task, LocalDateTime>("Due date");
//        userTaskDueDate.setMinWidth(130);
//        userTaskDueDate.setCellValueFactory(
//                new PropertyValueFactory<Task, LocalDateTime>("dueDate")
//        );
//
//        // I could group the buttons into the hbox and then add that and the table to the thing 6 lines later
//        //        HBox completedTaskTopBar= new HBox();
//        //        completedTaskTopBar.getChildren().addAll(completedTaskBackToMain);
//
//        userTasksTable.setItems(userTaskData);
//        userTasksTable.setEditable(true);
//        userTasksTable.getColumns().addAll(userTaskIDCol, userTaskName, userTaskDescription,userTaskDueDate);
//
//        employeeTasksBox.getChildren().addAll(employeeTasksLabel, employeeNameLabel, employeeTasksBackToMain, userTasksTable);
//
//
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        // Todo: User Table Formatting (done)
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        HBox topUserBar= new HBox();
//        topUserBar.getChildren().addAll(addUser,editUser,removeUser, promoteUser ,employeeTasks ,userBackToMain);
//
//        TableColumn<User, String> userIDCol = new TableColumn<User, String>("User ID");
//        userIDCol.setMinWidth(130);
//        userIDCol.setCellValueFactory(
//                new PropertyValueFactory<User, String>("ID")
//        );
//
//        TableColumn<User, String> userEmailCol = new TableColumn<User, String>("User Email");
//        userEmailCol.setMinWidth(130);
//        userEmailCol.setCellValueFactory(
//                new PropertyValueFactory<User, String>("email")
//        );
//        TableColumn<User, String> userPasswordCol = new TableColumn<User, String>("User Password");
//        userPasswordCol.setMinWidth(130);
//        userPasswordCol.setCellValueFactory(
//                new PropertyValueFactory<User, String>("password")
//        );
//        TableColumn<User, String> userFirstNameCol = new TableColumn<User, String>("User First Name");
//        userFirstNameCol.setMinWidth(130);
//        userFirstNameCol.setCellValueFactory(
//                new PropertyValueFactory<User, String>("firstName")
//        );
//            TableColumn<User, String> userLastNameCol = new TableColumn<User, String>("User Last Name");
//            userLastNameCol.setMinWidth(130);
//            userLastNameCol.setCellValueFactory(
//                    new PropertyValueFactory<User, String>("lastName")
//            );
//
//        TableColumn<User, LocalDate> userDOBCol = new TableColumn<User, LocalDate>("User Date of Birth");
//        userDOBCol.setMinWidth(130);
//        userDOBCol.setCellValueFactory(
//                new PropertyValueFactory<User, LocalDate>("DOB")
//        );
//
//        TableColumn<User, Boolean> userOwnership = new TableColumn<User, Boolean>("Owner (T/F)");
//        userOwnership.setMinWidth(130);
//        userOwnership.setCellValueFactory(
//                new PropertyValueFactory<User, Boolean>("owner")
//        );
//
//        userTable.setItems(userData);
//        userTable.setEditable(true);
//        userTable.getColumns().addAll(userFirstNameCol, userLastNameCol, userOwnership,userIDCol,userEmailCol,userPasswordCol, userDOBCol);
//        userPage.getChildren().addAll(topUserBar,userTable);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TODO: Task UI Section ( In progress)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo: Making the add task window pop up (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        VBox userBox = new VBox(30);
//        Scene addUserScene = new Scene(userBox,300,250);
//
//        Label albel = new Label("Popup");
//        TextField idInput = new TextField("Input ID (optional)");
//        TextField taskNameF = new TextField("Input taskName");
//        TextField descriptionF = new TextField("Input task description");
//        DatePicker dueDate = new DatePicker();
//        Button submitTask = new Button("submit");
//
//        userBox.getChildren().addAll(idInput,taskNameF,descriptionF,dueDate,submitTask);
//
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        //Todo: Task Addition (done)
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        Button addTask = new Button("add Task");
//        addTask.setOnMouseClicked(e ->{
//           idInput.setText("Input ID (optional)");
//           taskNameF.setText("Input taskName");
//           descriptionF.setText("Input task description");
//           dueDate.setValue(null);
//           stage.setScene(addUserScene);
//        });
//
//        submitTask.setOnMouseClicked(e ->{
//            //Task newTask = new Task(idInput.getText(),taskNameF.getText(),descriptionF.getText(),dueDate.getValue().atTime(LocalTime.now()));
//            //taskData.add(newTask);
//            taskController.addTask(idInput.getText(),taskNameF.getText(),descriptionF.getText(),dueDate.getValue().atTime(LocalTime.now()));
//            stage.setScene(taskScene);
//        });
//
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        //Todo: Task Editing
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        Button editTask = new Button("edit task");
//        VBox userEditBox = new VBox(30);
//        Scene editUserScene = new Scene(userEditBox,300,250);
//
//        TextField idInputEdit = new TextField();
//        TextField taskNameFEdit = new TextField();
//        TextField descriptionFEdit = new TextField();
//        DatePicker dueDateEdit = new DatePicker();
//        Button submitTaskEdit = new Button("submit");
//
//        userEditBox.getChildren().addAll(idInputEdit,taskNameFEdit,descriptionFEdit,dueDateEdit,submitTaskEdit);
//        submitTaskEdit.setOnMouseClicked(e ->{
//            taskController.editTask(taskTable.getSelectionModel().getSelectedItem().getID(),
//                    idInputEdit.getText(), taskNameFEdit.getText(), descriptionFEdit.getText(),
//                    dueDateEdit.getValue().atTime(LocalTime.now()));
//            System.out.println(taskTable.getSelectionModel().getSelectedItem());
//            stage.setScene(taskScene);
//            taskTable.refresh();
//        });
//
//        editTask.setOnMouseClicked(e ->{
//            idInputEdit.setText(taskTable.getSelectionModel().getSelectedItem().getID());
//            taskNameFEdit.setText(taskTable.getSelectionModel().getSelectedItem().getTaskName());
//            descriptionFEdit.setText(taskTable.getSelectionModel().getSelectedItem().getDescription());
//            dueDateEdit.setValue(taskTable.getSelectionModel().getSelectedItem().getDueDate().toLocalDate());
//
//            stage.setScene(editUserScene);
//        });
//
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        // Todo: Task Completion (done)
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        Button markComplete = new Button("Mark Complete");
//
//        markComplete.setOnMouseClicked(e ->{
//            taskController.completeTask(taskTable.getSelectionModel().getSelectedItem().getID());
//            taskTable.refresh();
//        });
//
//        Button taskBackToMain = new Button("back");
//        taskBackToMain.setOnMouseClicked(e ->{
//            stage.setScene(MenuScene);
//        });
//
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        // Todo: the view of completed tasks
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        VBox completedTaskBox = new VBox(30);
//        Scene completedTaskScene = new Scene(completedTaskBox,300,250);
//
//        Button viewCompleted = new Button("Completed Tasks");
//        viewCompleted.setOnMouseClicked(e->{
//            stage.setScene(completedTaskScene);
//        });
//
//        Button completedTaskBackToMain = new Button("back");
//        completedTaskBackToMain.setOnMouseClicked(e ->{
//            stage.setScene(taskScene);
//        });
//
//        Label completedTaskLabel = new Label("Completed Task Popup");
//
//        TableColumn<Task, String> completedTaskIDCol = new TableColumn<Task, String>("Task ID");
//        completedTaskIDCol.editableProperty().setValue(true);
//        completedTaskIDCol.setMinWidth(130);
//        completedTaskIDCol.setCellValueFactory(
//                new PropertyValueFactory<Task, String>("ID")
//        );
//
//        TableColumn<Task, String> completedTaskName = new TableColumn<Task, String>("Task Name");
//        completedTaskName.setMinWidth(130);
//        completedTaskName.setCellValueFactory(
//                new PropertyValueFactory<Task, String>("taskName")
//        );
//
//        TableColumn<Task, String> completedTaskDescription = new TableColumn<Task, String>("Task description");
//        completedTaskDescription.setMinWidth(130);
//        completedTaskDescription.setCellValueFactory(
//                new PropertyValueFactory<Task, String>("description")
//        );
//
//        TableColumn<Task, LocalDateTime> completedTaskDueDate = new TableColumn<Task, LocalDateTime>("Due date");
//        completedTaskDueDate.setMinWidth(130);
//        completedTaskDueDate.setCellValueFactory(
//                new PropertyValueFactory<Task, LocalDateTime>("dueDate")
//        );
//
//        // I could group the buttons into the hbox and then add that and the table to the thing 6 lines later
//        //        HBox completedTaskTopBar= new HBox();
//        //        completedTaskTopBar.getChildren().addAll(completedTaskBackToMain);
//
//        CompletedTaskTable.setItems(CompletedTaskData);
//        CompletedTaskTable.setEditable(true);
//        CompletedTaskTable.getColumns().addAll(completedTaskIDCol, completedTaskName, completedTaskDescription,completedTaskDueDate);
//        completedTaskBox.getChildren().addAll(completedTaskLabel, completedTaskBackToMain, CompletedTaskTable);
//
//
//
//
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        // Todo: Task view formatting (done)
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        HBox topBar= new HBox();
//        topBar.getChildren().addAll(addTask,editTask,markComplete,viewCompleted ,taskBackToMain);
//
//        TableColumn<Task, String> taskIDCol = new TableColumn<Task, String>("Task ID");
//
//        taskIDCol.editableProperty().setValue(true);
//        taskIDCol.setMinWidth(130);
//        taskIDCol.setCellValueFactory(
//                new PropertyValueFactory<Task, String>("ID")
//        );
//
//        TableColumn<Task, String> taskName = new TableColumn<Task, String>("Task Name");
//        taskName.setMinWidth(130);
//        taskName.setCellValueFactory(
//                new PropertyValueFactory<Task, String>("taskName")
//        );
//
//        TableColumn<Task, String> taskDescription = new TableColumn<Task, String>("Task description");
//        taskDescription.setMinWidth(130);
//        taskDescription.setCellValueFactory(
//                new PropertyValueFactory<Task, String>("description")
//        );
//
//        TableColumn<Task, LocalDateTime> taskDueDate = new TableColumn<Task, LocalDateTime>("Due date");
//        taskDueDate.setMinWidth(130);
//        taskDueDate.setCellValueFactory(
//                new PropertyValueFactory<Task, LocalDateTime>("dueDate")
//        );
//
//        taskTable.setItems(taskData);
//        taskTable.setEditable(true);
//        taskTable.getColumns().addAll(taskIDCol,taskName,taskDescription,taskDueDate);
//        taskPage.getChildren().addAll(topBar,taskTable);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // TODO: Fields UI Section (in progress)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//bookmark
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // todo: field Table formatting
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        TableColumn<Field, String> fieldIDCol = new TableColumn<Field, String>("Field ID");
//        fieldIDCol.setMinWidth(130);
//        fieldIDCol.setCellValueFactory(
//                new PropertyValueFactory<Field, String>("ID")
//        );
//
//        TableColumn<Field, String> fieldNameCol = new TableColumn<Field, String>("Field Name");
//        fieldNameCol.setMinWidth(130);
//        fieldNameCol.setCellValueFactory(
//                new PropertyValueFactory<Field, String>("name")
//        );
//
//        TableColumn<Field, Double> fieldSizeCol = new TableColumn<Field, Double>("Field Size");
//        fieldSizeCol.setMinWidth(130);
//        fieldSizeCol.setCellValueFactory(
//                new PropertyValueFactory<Field, Double>("size")
//        );
//
//        TableColumn<Field, String> fieldLocationCol = new TableColumn<Field, String>("Field Location");
//        fieldLocationCol.setMinWidth(130);
//        fieldLocationCol.setCellValueFactory(
//                new PropertyValueFactory<Field, String>("location")
//        );
//
//        fieldTable.setItems(fieldData);
//        fieldTable.getColumns().addAll(fieldIDCol, fieldNameCol, fieldSizeCol, fieldLocationCol);
//
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        // Todo: field addition
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        VBox addFieldBox = new VBox(30);
//        Scene addFieldScene = new Scene(addFieldBox,300,250);
//
//        TextField fieldIDInput = new TextField("Field ID");
//        TextField fieldNameInput = new TextField("Field Name");
//        TextField fieldSizeInput = new TextField("Field Size");
//        TextField fieldLocation = new TextField("Field Location");
//        Button submitFieldInfo = new Button("Submit");
//
//        Button fieldBackToMain = new Button("back");
//        fieldBackToMain.setOnMouseClicked(e ->{
//            stage.setScene(MenuScene);
//        });
//
//        addFieldBox.getChildren().addAll(fieldIDInput, fieldNameInput, fieldSizeInput, fieldLocation, submitFieldInfo);
//
//        Button addField = new Button("Add Field");
//        addField.setOnMouseClicked(e ->{
//            stage.setScene(addFieldScene);
//        });
//        submitFieldInfo.setOnMouseClicked(e ->{
//            Field newField = new Field(fieldIDInput.getText(),fieldNameInput.getText(), Double.parseDouble(fieldSizeInput.getText()), fieldLocation.getText());
//            fieldData.add(newField);
//            stage.setScene(fieldScene);
//        });
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        // todo: crop and record view page within field
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        VBox cropPage = new VBox(30);
//        Scene cropScene = new Scene(cropPage,300,250);
//
//        Label yearLabel = new Label("Year: 2023");
//        yearLabel.setFont(new Font("Arial", 20));
//        yearLabel.setStyle("-fx-font-weight: bold;");
//
//        Label cropLabel = new Label("Crop Table");
//        cropLabel.setFont(new Font("Arial", 20));
//
//        Label recordLabel = new Label("Records");
//        recordLabel.setFont(new Font("Arial", 20));
//
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        // todo: crop table formatting
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        TableColumn<Crop, String> cropTypeCol = new TableColumn<Crop, String>("Crop Type");
//        cropTypeCol.setMinWidth(130);
//        cropTypeCol.setCellValueFactory(
//                new PropertyValueFactory<Crop, String>("cropType"));
//
//        TableColumn<Crop, String> cropVarietyCol = new TableColumn<Crop, String>("Crop Variety");
//        cropVarietyCol.setMinWidth(130);
//        cropVarietyCol.setCellValueFactory(
//                new PropertyValueFactory<Crop, String>("cropVariety"));
//
//        TableColumn<Crop, Float> bushelWeightCol = new TableColumn<Crop, Float>("Bushel Weight");
//        bushelWeightCol.setMinWidth(70);
//        bushelWeightCol.setCellValueFactory(
//                new PropertyValueFactory<Crop, Float>("bushelWeight"));
//
//        grainTable.setItems(cropData);
//        grainTable.getColumns().addAll(cropTypeCol, cropVarietyCol, bushelWeightCol);
//        fieldTable.setOnMouseClicked(event -> {
//            if (event.getClickCount() == 2) {
//                Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
//                if (selectedData != null) {
//                    stage.setScene(cropScene);
//                }
//            }
//        });
//
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        // todo: record table formatting
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        TableColumn<Record, String> chemCol = new TableColumn<Record, String>("Chemical Sprayed");
//        chemCol.setMinWidth(130);
//        chemCol.setCellValueFactory(
//                new PropertyValueFactory<Record, String>("chemSprayed"));
//
//        TableColumn<Record, LocalDate> sprayingDateCol = new TableColumn<Record, LocalDate>("Spraying Date");
//        sprayingDateCol.setMinWidth(130);
//        sprayingDateCol.setCellValueFactory(
//                new PropertyValueFactory<Record, LocalDate>("sprayingDate"));
//
//        TableColumn<Record, String> seedPlantedCol = new TableColumn<Record, String>("Seed Planted");
//        seedPlantedCol.setMinWidth(130);
//        seedPlantedCol.setCellValueFactory(
//                new PropertyValueFactory<Record, String>("seedPlanted"));
//
//        TableColumn<Record, Double> seedingRateCol = new TableColumn<Record, Double>("Seeding Rate (lbs/acre)");
//        seedingRateCol.setMinWidth(150);
//        seedingRateCol.setCellValueFactory(
//                new PropertyValueFactory<Record, Double>("seedingRate"));
//
//        TableColumn<Record, LocalDate> seedingDateCol = new TableColumn<Record, LocalDate>("Seeding Date");
//        seedingDateCol.setMinWidth(130);
//        seedingDateCol.setCellValueFactory(
//                new PropertyValueFactory<Record, LocalDate>("seedingDate"));
//
//        TableColumn<Record, String> fertilizerCol = new TableColumn<Record, String>("Fertilizer");
//        fertilizerCol.setMinWidth(130);
//        fertilizerCol.setCellValueFactory(
//                new PropertyValueFactory<Record, String>("fertilizer"));
//
//        TableColumn<Record, LocalDate> fertilizerDateCol = new TableColumn<Record, LocalDate>("Fertilizer Date");
//        fertilizerDateCol.setMinWidth(130);
//        fertilizerDateCol.setCellValueFactory(
//                new PropertyValueFactory<Record, LocalDate>("fertilizerDate"));
//
//
//        recordTable.setItems(recordData);
//        recordTable.getColumns().addAll(chemCol, sprayingDateCol, seedPlantedCol, seedingRateCol, seedingDateCol, fertilizerCol, fertilizerDateCol);
//
//        Button cropBackToFields = new Button("Back");
//        cropBackToFields.setOnMouseClicked(e ->{
//            stage.setScene(fieldScene);
//        });
//
//        HBox cropFunctionsBar = new HBox();
//        cropFunctionsBar.getChildren().addAll(cropBackToFields);
//        cropPage.getChildren().addAll(cropFunctionsBar, yearLabel, cropLabel, grainTable, recordLabel, recordTable);
//
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        // todo: field deletion
//        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        VBox deleteFieldBox = new VBox(30);
//        Scene deleteFieldScene = new Scene(deleteFieldBox,300,250);
//
//        Button deleteField = new Button("Delete Field");
//        deleteField.setOnMouseClicked(e ->{
//            stage.setScene(deleteFieldScene);
//        });
//
//        Label deleteLabel = new Label("Enter Field ID");
//        deleteLabel.setFont(new Font("Arial", 20));
//        TextField IDInput = new TextField();
//        Button submitID = new Button("Submit");
//
//        submitID.setOnMousePressed(e -> {
//            String deleteID = IDInput.getText();
//            Iterator<Field> iterator = fieldData.iterator();
//            while (iterator.hasNext()) {
//                Field field = iterator.next();
//                if (deleteID.equals(field.getID())) {
//                    iterator.remove();
//                    stage.setScene(fieldScene);
//                    return;
//                }
//            }
//            stage.setScene(fieldScene);
//        });
//
//        deleteFieldBox.getChildren().addAll(deleteLabel, IDInput, submitID);
//
//        // fields back to menu button
//        Button fieldsBackToMain = new Button("Back");
//        fieldsBackToMain.setOnMouseClicked(e ->{
//            stage.setScene(MenuScene);
//        });
//
//
//        // field functions bar
//        HBox fieldFunctionsBar = new HBox();
//        fieldFunctionsBar.getChildren().addAll(addField, deleteField, fieldsBackToMain);
//        fieldPage.getChildren().addAll(fieldFunctionsBar, fieldTable);


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TODO: Bins UI Section
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //todo: Bin table formatting
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        final Label binlabel = new Label("Bin Table");
//        binlabel.setFont(new Font("Arial", 20));
//
//        binTable.setEditable(true);
//
//        TableColumn<GrainBin, String> binNameCol = new TableColumn<GrainBin, String>("Bin Name");
//        binNameCol.setMinWidth(130);
//        binNameCol.setCellValueFactory(
//                new PropertyValueFactory<GrainBin, String>("binName"));
//
//        TableColumn<GrainBin, String> binLocationCol = new TableColumn<GrainBin, String>("Bin Location");
//        binLocationCol.setMinWidth(130);
//        binLocationCol.setCellValueFactory(
//                new PropertyValueFactory<GrainBin, String>("binLocation"));
//
//        TableColumn<GrainBin, Integer> binSizeCol = new TableColumn<GrainBin, Integer>("Bin Size");
//        binSizeCol.setMinWidth(70);
//        binSizeCol.setCellValueFactory(
//                new PropertyValueFactory<GrainBin, Integer>("binSize"));
//
//        TableColumn<GrainBin, Boolean> binHopperCol = new TableColumn<GrainBin, Boolean>("Bin Hopper");
//        binHopperCol.setMinWidth(70);
//        binHopperCol.setCellValueFactory(
//                new PropertyValueFactory<GrainBin, Boolean>("hopper"));
//
//        TableColumn<GrainBin, String> binFanCol = new TableColumn<GrainBin, String>("Bin Fan");
//        binFanCol.setMinWidth(70);
//        binFanCol.setCellValueFactory(
//                new PropertyValueFactory<GrainBin, String>("fan"));
//
//        binTable.setItems(grainBinData);
//        binTable.getColumns().addAll(binNameCol,binLocationCol,binSizeCol,binHopperCol,binFanCol);
//
//        Button binsBackToMain = new Button("Back");
//        binsBackToMain.setOnMouseClicked(e ->{
//            stage.setScene(MenuScene);
//        });
//
//        final VBox binvbox = new VBox();
//        binvbox.setSpacing(5);
//        binvbox.setPadding(new Insets(10, 0, 0, 10));
//        binvbox.getChildren().addAll(binsBackToMain,binlabel, binTable);
//        ((Group) sceneBins.getRoot()).getChildren().addAll(binvbox);

        stage.setScene(MenuScene);
        stage.show();
    }

    public static class Person {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;

        private Person(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fName) {
            email.set(fName);
        }
    }

    public static class Record{
        private String chemSprayed;
        private LocalDate sprayingDate;
        private String seedPlanted;
        private Double seedingRate;
        private LocalDate seedingDate;
        private String fertilizer;
        private LocalDate fertilizerDate;
        private Record(String chem, LocalDate cDate, String seed, Double rate, LocalDate sDate, String fer, LocalDate fDate){
            chemSprayed = chem;
            sprayingDate = cDate;
            seedPlanted = seed;
            seedingRate = rate;
            seedingDate = sDate;
            fertilizer = fer;
            fertilizerDate = fDate;
        }

        public String getChemSprayed() {
            return chemSprayed;
        }

        public LocalDate getSprayingDate() {
            return sprayingDate;
        }

        public String getSeedPlanted() {
            return seedPlanted;
        }

        public Double getSeedingRate() {
            return seedingRate;
        }

        public LocalDate getSeedingDate() {
            return seedingDate;
        }

        public String getFertilizer() {
            return fertilizer;
        }

        public LocalDate getFertilizerDate() {
            return fertilizerDate;
        }
    }
}