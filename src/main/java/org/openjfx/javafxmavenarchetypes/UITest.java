package org.openjfx.javafxmavenarchetypes;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.InitialFarm.Crop;
import org.InitialFarm.GrainBin;
import org.entities.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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


    private TableView<GrainBin> binTable = new TableView<>();
    private final ObservableList<GrainBin> grainBinData =
            FXCollections.observableArrayList(
                    new GrainBin("Tod", "the moon", 400, false, false),
                    new GrainBin("Dave", "the moon", 4000, false, false),
                    new GrainBin("The Big One", "the moon", 40000, false, true),
                    new GrainBin("Nice", "the moon", 69, true, false)
            );

    private TableView<Crop> grainTable = new TableView<Crop>();
    private final ObservableList<Crop> cropData =
            FXCollections.observableArrayList(
                    new Crop("Canola", "LibertyLink", 55),
                    new Crop("Canola", "RoundupReady", 54),
                    new Crop("Durum", "Navigator", 60),
                    new Crop("Red Lentil", "Clearfield", 58),
                    new Crop("Wheat & Barley", "All the other Grains", 45)

            );





    private TableView<Task> taskTable = new TableView<Task>();


    /**
     * new Task("124124", "Do the tasky", "This is my description", LocalDateTime.now()),
     *                     new Task("1243263456", "Do the tasky2", "My second description", LocalDateTime.now())
     */
    private ObservableList<Task> taskData =
            FXCollections.observableArrayList(
                    new Task("124124", "Do the tasky", "This is my description", LocalDateTime.now()),
                    new Task("1243263456", "Do the tasky2", "My second description", LocalDateTime.now())
            );


    private TableView<TaskBar> bars = new TableView<TaskBar>();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        //Making our farm view scene
        Group fieldPage = new Group();
        Scene sceneFields = new Scene(fieldPage,300,250);
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(500);

        VBox taskSelector = new VBox(30);
        Scene MenuScene = new Scene(taskSelector,300,250);



        TableColumn taskID = new TableColumn("Task ID");
        taskID.setMinWidth(130);

        Button bfield = new Button();
        bfield.setText("Fields");
        bfield.setOnAction(e -> stage.setScene(sceneFields));


        //Make bin view scene
        Group binPage = new Group();
        Scene sceneBins = new Scene(binPage,300,250);
        Button bbins = new Button();
        bbins.setText("Bins");
        bbins.setOnAction(e -> stage.setScene(sceneBins));

        VBox taskPage = new VBox(30);
        Scene taskScene = new Scene(taskPage,300,250);

        Button btasks = new Button();
        btasks.setText("Tasks");
        btasks.setOnAction(e -> stage.setScene(taskScene));
        taskSelector.getChildren().addAll(btasks,bfield,bbins);
        taskSelector.setAlignment(Pos.CENTER);


        //Making the add pop up

        VBox userBox = new VBox(30);
        Scene addUserScene = new Scene(userBox,300,250);

        Label albel = new Label("Popup");
        TextField idInput = new TextField("Input ID (optional)");
        TextField taskNameF = new TextField("Please up taskName");
        TextField descriptionF = new TextField("Description");
        DatePicker dueDate = new DatePicker();
        Button submitTask = new Button("submit");

        userBox.getChildren().addAll(taskNameF,descriptionF,dueDate,submitTask);

        // Finished add pop up
        RectButton addTask = new RectButton("","add Task");
        addTask.setOnMouseClicked(e ->{
           stage.setScene(addUserScene);
        });

        submitTask.setOnMouseClicked(e ->{
            Task newTask = new Task(idInput.getText(),taskNameF.getText(),descriptionF.getText(),dueDate.getValue().atTime(LocalTime.now()));
            taskData.add(newTask);
            stage.setScene(taskScene);
        });
        RectButton editTask = new RectButton("","edit task");

        //This actually adds the functionality required.
        editTask.setOnMouseClicked(e ->{
            taskTable.getSelectionModel().getSelectedItem();
        });
        RectButton markComplete = new RectButton("", "Mark Complete");

        RectButton viewCompleted = new RectButton("","newView");
        HBox topBar= new HBox();
        topBar.getChildren().addAll(addTask,editTask,markComplete);

        TableColumn taskIDCol = new TableColumn("Task ID");
        taskIDCol.setMinWidth(130);
        taskIDCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("ID")
        );

        TableColumn taskName = new TableColumn("Task Name");
        taskName.setMinWidth(130);
        taskName.setCellValueFactory(
                new PropertyValueFactory<Task, String>("taskName")
        );

        TableColumn taskDescription = new TableColumn("Task description");
        taskDescription.setMinWidth(130);
        taskDescription.setCellValueFactory(
                new PropertyValueFactory<Task, String>("description")
        );

        TableColumn taskDueDate = new TableColumn("Due date");
        taskDueDate.setMinWidth(130);
        taskDueDate.setCellValueFactory(
                new PropertyValueFactory<Task, LocalDateTime>("dueDate")
        );

        taskTable.setItems(taskData);
        taskTable.getColumns().addAll(taskIDCol,taskName,taskDescription,taskDueDate);
        taskPage.getChildren().addAll(topBar,taskTable);
        //Making out crop page

        final Label label = new Label("Crop Table");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("Crop Type");
        firstNameCol.setMinWidth(130);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropType"));

        TableColumn lastNameCol = new TableColumn("Crop Variety");
        lastNameCol.setMinWidth(130);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropVariety"));

        TableColumn emailCol = new TableColumn("Bushel Weight");
        emailCol.setMinWidth(70);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Crop, Float>("bushelWeight"));

        grainTable.setItems(cropData);
        grainTable.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, grainTable);

        ((Group) sceneFields.getRoot()).getChildren().addAll(vbox);



        final Label binlabel = new Label("Bin Table");
        binlabel.setFont(new Font("Arial", 20));

        binTable.setEditable(true);

        TableColumn binNameCol = new TableColumn("Bin Name");
        binNameCol.setMinWidth(130);
        binNameCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("binName"));

        TableColumn binLocationCol = new TableColumn("Bin Location");
        binLocationCol.setMinWidth(130);
        binLocationCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("binLocation"));

        TableColumn binSizeCol = new TableColumn("Bin Size");
        binSizeCol.setMinWidth(70);
        binSizeCol.setCellValueFactory(
                new PropertyValueFactory<Crop, Float>("binSize"));

        TableColumn binHopperCol = new TableColumn("Bin Hopper");
        binHopperCol.setMinWidth(70);
        binHopperCol.setCellValueFactory(
                new PropertyValueFactory<Crop, Boolean>("hopper"));

        TableColumn binFanCol = new TableColumn("Bin Fan");
        binFanCol.setMinWidth(70);
        binFanCol.setCellValueFactory(
                new PropertyValueFactory<Crop, Float>("fan"));

        binTable.setItems(grainBinData);
        binTable.getColumns().addAll(binNameCol,binLocationCol,binSizeCol,binHopperCol,binFanCol);

        final VBox binvbox = new VBox();
        binvbox.setSpacing(5);
        binvbox.setPadding(new Insets(10, 0, 0, 10));
        binvbox.getChildren().addAll(binlabel, binTable);
        ((Group) sceneBins.getRoot()).getChildren().addAll(binvbox);

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
}