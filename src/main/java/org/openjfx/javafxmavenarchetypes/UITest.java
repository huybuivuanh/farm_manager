package org.openjfx.javafxmavenarchetypes;

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
import javafx.scene.layout.HBox;
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
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private TableView<User> userTable = new TableView<User>();


    /**
     * new User ...
     */
    private ObservableList<User> userData =
            FXCollections.observableArrayList(
                    new Employee("ID_1", "John1@gmail.com", "pass1", "John1", "Josh1", LocalDate.of(2002, Calendar.FEBRUARY,2), true),
                    new Employee("ID_2", "notJohn@gmail.com", "notpass1", "John'nt", "Josh'nt", LocalDate.of(2012, Calendar.MAY,2), false)
                    );


    private TableView<TaskBar> bars2= new TableView<TaskBar>();

    private TableView<Field> fieldTable = new TableView<Field>();

    private ObservableList<Field> fieldData =
            FXCollections.observableArrayList(new Field("1", "field 1", 69, "Mars"),
                    new Field("2", "field 2", 69, "Venus"),
                    new Field("3", "field 3", 69, "Mercury"));

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
        Group fieldPageGroup = new Group();
        Scene sceneFields = new Scene(fieldPageGroup,300,250);
        stage.setTitle("Table View Sample");
        stage.setWidth(925);
        stage.setHeight(500);

        VBox taskSelector = new VBox(30);
        Scene MenuScene = new Scene(taskSelector,300,250);



        TableColumn taskID = new TableColumn("Task ID");
        taskID.setMinWidth(130);

        VBox fieldPage = new VBox(30);
        Scene fieldScene = new Scene(fieldPage,300,250);

        Button bfield = new Button();
        bfield.setText("Fields");
        bfield.setOnAction(e -> stage.setScene(fieldScene));


        //Make bin view scene this
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
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        VBox userPage= new VBox(30);
        Scene userScene = new Scene(userPage, 300, 250);
        Button busers= new Button();
        busers.setText("Users");
        busers.setOnAction(e-> {
            stage.setScene(userScene);
        });
        taskSelector.getChildren().addAll(btasks,bfield,bbins,busers);
        taskSelector.setAlignment(Pos.CENTER);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo: adding functionality to the user tab (done)

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
        Button submitAddUserInfo = new Button("submit");

        userBox2.getChildren().addAll(fNameInput,lNameInput, ownerInput,userIdInput,emailInput,passwordInput,dob,submitAddUserInfo);

        Button addUser = new Button("add User");
        addUser.setOnMouseClicked(e ->{
            stage.setScene(addUserScene2);
        });
        submitAddUserInfo.setOnMouseClicked(e ->{
            User newUser = new Employee(userIdInput.getText(),emailInput.getText(), passwordInput.getText(), fNameInput.getText(), lNameInput.getText() ,dob.getValue(), parseBoolean(ownerInput.getText()));
            userData.add(newUser);
            stage.setScene(userScene);
        });

        Button userBackToMain = new Button("back");
        userBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// TODO: Complete the functionality of the user tab:
        // Todo: remove user, edit user , promote user, add & remove ( tasks or responsibilities)

        //Todo 1: remove user (done - might need revisiting later)

        Button removeUser = new Button("Remove User");
        removeUser.setOnMouseClicked(e-> {
            // not sure if any additional work is needed here within the class itself
            //userTable.getSelectionModel().getSelectedItem().
            userTable.getItems().remove(userTable.getSelectionModel().getSelectedItem());
            userTable.refresh();
        });

        //Todo 2: edit user (done)
        Button editUser = new Button("edit user");

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
        Button submitUserInfoEdit = new Button("submit");

        actualUserEditBox.getChildren().addAll(fNameEdit,lNameEdit,ownerEdit,userIdEdit, emailEdit, passwordEdit, dobEdit,submitUserInfoEdit);
        submitUserInfoEdit.setOnMouseClicked(e-> {
            userTable.getSelectionModel().getSelectedItem().setFirstName(fNameEdit.getText());
            userTable.getSelectionModel().getSelectedItem().setLastName(lNameEdit.getText());
            userTable.getSelectionModel().getSelectedItem().setOwner(parseBoolean(ownerEdit.getText()));
            userTable.getSelectionModel().getSelectedItem().setID(userIdEdit.getText());
            userTable.getSelectionModel().getSelectedItem().setEmail(emailEdit.getText());
            userTable.getSelectionModel().getSelectedItem().setPassword(passwordEdit.getText());
            userTable.getSelectionModel().getSelectedItem().setDOB(dobEdit.getValue());
            System.out.println(userTable.getSelectionModel().getSelectedItem());
            stage.setScene(userScene);
            userTable.refresh();
        });

        editUser.setOnMouseClicked(e-> {
            fNameEdit.setText(userTable.getSelectionModel().getSelectedItem().getFirstName());
            lNameEdit.setText(userTable.getSelectionModel().getSelectedItem().getLastName());
            ownerEdit.setText(String.valueOf(userTable.getSelectionModel().getSelectedItem().getOwner()));
            userIdEdit.setText(userTable.getSelectionModel().getSelectedItem().getID());
            emailEdit.setText(userTable.getSelectionModel().getSelectedItem().getEmail());
            passwordEdit.setText(userTable.getSelectionModel().getSelectedItem().getPassword());
            dobEdit.setValue(userTable.getSelectionModel().getSelectedItem().getDOB());

            stage.setScene(actualUserEditScene);
        });

        //Todo 3: promote user (done - revisit changes to employee hierarchy)

        Button promoteUser = new Button("Promote User");
        promoteUser.setOnMouseClicked(e-> {
            // not sure if any additional work is needed here within the class itself
            //userTable.getSelectionModel().getSelectedItem().
            userTable.getSelectionModel().getSelectedItem().setOwner(true);
            userTable.refresh();
        });

        //Todo 3: task addition and removal.

        Button userTaskAssigner = new Button("Assign Task");




// TODO: -----------------------------------------------------------------------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        HBox topUserBar= new HBox();
        topUserBar.getChildren().addAll(addUser,editUser,removeUser, promoteUser ,userBackToMain);

        TableColumn userIDCol = new TableColumn("User ID");
        userIDCol.setMinWidth(130);
        userIDCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("ID")
        );

        TableColumn userEmailCol = new TableColumn("User Email");
        userEmailCol.setMinWidth(130);
        userEmailCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("email")
        );
        TableColumn userPasswordCol = new TableColumn("User Password");
        userPasswordCol.setMinWidth(130);
        userPasswordCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("password")
        );
        TableColumn userFirstNameCol = new TableColumn("User First Name");
        userFirstNameCol.setMinWidth(130);
        userFirstNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("firstName")
        );
        TableColumn userLastNameCol = new TableColumn("User Last Name");
        userLastNameCol.setMinWidth(130);
        userLastNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("lastName")
        );


        TableColumn userDOBCol = new TableColumn("User Date of Birth");
        userDOBCol.setMinWidth(130);
        userDOBCol.setCellValueFactory(
                new PropertyValueFactory<User, LocalDate>("DOB")
        );

        TableColumn userOwnership = new TableColumn("Owner (T/F)");
        userOwnership.setMinWidth(130);
        userOwnership.setCellValueFactory(
                new PropertyValueFactory<User, Boolean>("owner")
        );

        userTable.setItems(userData);
        userTable.setEditable(true);
        userTable.getColumns().addAll(userFirstNameCol, userLastNameCol, userOwnership,userIDCol,userEmailCol,userPasswordCol, userDOBCol);
        userPage.getChildren().addAll(topUserBar,userTable);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo: Making the add task window pop up (done)

        VBox userBox = new VBox(30);
        Scene addUserScene = new Scene(userBox,300,250);

        Label albel = new Label("Popup");
        TextField idInput = new TextField("Input ID (optional)");
        TextField taskNameF = new TextField("Please up taskName");
        TextField descriptionF = new TextField("Description");
        DatePicker dueDate = new DatePicker();
        Button submitTask = new Button("submit");

        userBox.getChildren().addAll(idInput,taskNameF,descriptionF,dueDate,submitTask);

        //Todo: Finished add task pop up (done)
        Button addTask = new Button("add Task");
        addTask.setOnMouseClicked(e ->{
           stage.setScene(addUserScene);
        });

        submitTask.setOnMouseClicked(e ->{
            Task newTask = new Task(idInput.getText(),taskNameF.getText(),descriptionF.getText(),dueDate.getValue().atTime(LocalTime.now()));
            taskData.add(newTask);
            stage.setScene(taskScene);
        });
        Button editTask = new Button("edit task");

        //Todo: Making edit task Popup (done)
        VBox userEditBox = new VBox(30);
        Scene editUserScene = new Scene(userEditBox,300,250);

        TextField idInputEdit = new TextField();
        TextField taskNameFEdit = new TextField();
        TextField descriptionFEdit = new TextField();
        DatePicker dueDateEdit = new DatePicker();
        Button submitTaskEdit = new Button("submit");

        userEditBox.getChildren().addAll(idInputEdit,taskNameFEdit,descriptionFEdit,dueDateEdit,submitTaskEdit);
        submitTaskEdit.setOnMouseClicked(e ->{
            taskTable.getSelectionModel().getSelectedItem().setID(idInputEdit.getText());
            taskTable.getSelectionModel().getSelectedItem().setTaskName(taskNameFEdit.getText());
            taskTable.getSelectionModel().getSelectedItem().setDescription(descriptionFEdit.getText());
            taskTable.getSelectionModel().getSelectedItem().setDueDate(dueDateEdit.getValue().atTime(LocalTime.now()));
            System.out.println(taskTable.getSelectionModel().getSelectedItem());
            stage.setScene(taskScene);
            taskTable.refresh();
        });



        //Todo: adding functionality required for editing task.
        editTask.setOnMouseClicked(e ->{
            idInputEdit.setText(taskTable.getSelectionModel().getSelectedItem().getID());
            taskNameFEdit.setText(taskTable.getSelectionModel().getSelectedItem().getTaskName());
            descriptionFEdit.setText(taskTable.getSelectionModel().getSelectedItem().getDescription());
            dueDateEdit.setValue(taskTable.getSelectionModel().getSelectedItem().getDueDate().toLocalDate());

            stage.setScene(editUserScene);
        });


        Button markComplete = new Button("Mark Complete");

        markComplete.setOnMouseClicked(e ->{
            taskTable.getSelectionModel().getSelectedItem().markAsCompleted(true);
//            taskTable.getSelectionModel().getSelectedItem().setInProgress(false);
            taskTable.getItems().remove(taskTable.getSelectionModel().getSelectedItem());
            taskTable.refresh();
        });

        Button viewCompleted = new Button("newView");

        Button taskBackToMain = new Button("back");
        taskBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });

        HBox topBar= new HBox();
        topBar.getChildren().addAll(addTask,editTask,markComplete, taskBackToMain);

        TableColumn taskIDCol = new TableColumn("Task ID");

        taskIDCol.editableProperty().setValue(true);
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
        taskTable.setEditable(true);
        taskTable.getColumns().addAll(taskIDCol,taskName,taskDescription,taskDueDate);
        taskPage.getChildren().addAll(topBar,taskTable);

        //Bin page
        
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // fields page

        TableColumn fieldIDCol = new TableColumn("Field ID");
        fieldIDCol.setMinWidth(130);
        fieldIDCol.setCellValueFactory(
                new PropertyValueFactory<Field, String>("ID")
        );

        TableColumn fieldNameCol = new TableColumn("Field Name");
        fieldNameCol.setMinWidth(130);
        fieldNameCol.setCellValueFactory(
                new PropertyValueFactory<Field, String>("name")
        );

        TableColumn fieldSizeCol = new TableColumn("Field Size");
        fieldSizeCol.setMinWidth(130);
        fieldSizeCol.setCellValueFactory(
                new PropertyValueFactory<Field, Double>("size")
        );

        TableColumn fieldLocationCol = new TableColumn("Field Location");
        fieldLocationCol.setMinWidth(130);
        fieldLocationCol.setCellValueFactory(
                new PropertyValueFactory<Field, String>("location")
        );

        fieldTable.setItems(fieldData);
        fieldTable.getColumns().addAll(fieldIDCol, fieldNameCol, fieldSizeCol, fieldLocationCol);


        // add fields page
        VBox addFieldBox = new VBox(30);
        Scene addFieldScene = new Scene(addFieldBox,300,250);

        TextField fieldIDInput = new TextField("Field ID");
        TextField fieldNameInput = new TextField("Field Name");
        TextField fieldSizeInput = new TextField("Field Size");
        TextField fieldLocation = new TextField("Field Location");
        Button submitFieldInfo = new Button("Submit");
        //Making out crop page
        /////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////

        Button fieldBackToMain = new Button("back");
        fieldBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });

        addFieldBox.getChildren().addAll(fieldIDInput, fieldNameInput, fieldSizeInput, fieldLocation, submitFieldInfo);

        Button addField = new Button("Add Field");
        addField.setOnMouseClicked(e ->{
            stage.setScene(addFieldScene);
        });
        submitFieldInfo.setOnMouseClicked(e ->{
            Field newField = new Field(fieldIDInput.getText(),fieldNameInput.getText(), Double.parseDouble(fieldSizeInput.getText()), fieldLocation.getText());
            fieldData.add(newField);
            stage.setScene(fieldScene);
        });

        // crop pop up
        VBox cropPage = new VBox(30);
        Scene cropScene = new Scene(cropPage,300,250);

        Label yearLabel = new Label("Year: 2023");
        yearLabel.setFont(new Font("Arial", 20));
        yearLabel.setStyle("-fx-font-weight: bold;");

        Label cropLabel = new Label("Crop Table");
        cropLabel.setFont(new Font("Arial", 20));

        Label recordLabel = new Label("Records");
        recordLabel.setFont(new Font("Arial", 20));

        // crop table
        TableColumn cropTypeCol = new TableColumn("Crop Type");
        cropTypeCol.setMinWidth(130);
        cropTypeCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropType"));

        TableColumn cropVarietyCol = new TableColumn("Crop Variety");
        cropVarietyCol.setMinWidth(130);
        cropVarietyCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropVariety"));

        TableColumn bushelWeightCol = new TableColumn("Bushel Weight");
        bushelWeightCol.setMinWidth(70);
        bushelWeightCol.setCellValueFactory(
                new PropertyValueFactory<Crop, Float>("bushelWeight"));

        grainTable.setItems(cropData);
        grainTable.getColumns().addAll(cropTypeCol, cropVarietyCol, bushelWeightCol);


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        Label test = new Label("Field");
        vbox.getChildren().addAll(test,fieldBackToMain, grainTable);
        fieldTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
                if (selectedData != null) {
                    stage.setScene(cropScene);
                }
            }
        });

        // record table
        TableColumn chemCol = new TableColumn("Chemical Sprayed");
        chemCol.setMinWidth(130);
        chemCol.setCellValueFactory(
                new PropertyValueFactory<Record, String>("chemSprayed"));

        TableColumn sprayingDateCol = new TableColumn("Spraying Date");
        sprayingDateCol.setMinWidth(130);
        sprayingDateCol.setCellValueFactory(
                new PropertyValueFactory<Record, LocalDate>("sprayingDate"));

        TableColumn seedPlantedCol = new TableColumn("Seed Planted");
        seedPlantedCol.setMinWidth(130);
        seedPlantedCol.setCellValueFactory(
                new PropertyValueFactory<Record, String>("seedPlanted"));

        TableColumn seedingRateCol = new TableColumn("Seeding Rate (lbs/acre)");
        seedingRateCol.setMinWidth(150);
        seedingRateCol.setCellValueFactory(
                new PropertyValueFactory<Record, Double>("seedingRate"));

        TableColumn seedingDateCol = new TableColumn("Seeding Date");
        seedingDateCol.setMinWidth(130);
        seedingDateCol.setCellValueFactory(
                new PropertyValueFactory<Record, LocalDate>("seedingDate"));

        TableColumn fertilizerCol = new TableColumn("Fertilizer");
        fertilizerCol.setMinWidth(130);
        fertilizerCol.setCellValueFactory(
                new PropertyValueFactory<Record, String>("fertilizer"));

        TableColumn fertilizerDateCol = new TableColumn("Fertilizer Date");
        fertilizerDateCol.setMinWidth(130);
        fertilizerDateCol.setCellValueFactory(
                new PropertyValueFactory<Record, LocalDate>("fertilizerDate"));


        recordTable.setItems(recordData);
        recordTable.getColumns().addAll(chemCol, sprayingDateCol, seedPlantedCol, seedingRateCol, seedingDateCol, fertilizerCol, fertilizerDateCol);

        Button cropBackToFields = new Button("Back");
        cropBackToFields.setOnMouseClicked(e ->{
            stage.setScene(fieldScene);
        });

        HBox cropFunctionsBar = new HBox();
        cropFunctionsBar.getChildren().addAll(cropBackToFields);
        cropPage.getChildren().addAll(cropFunctionsBar, yearLabel, cropLabel, grainTable, recordLabel, recordTable);

        // delete field
        VBox deleteFieldBox = new VBox(30);
        Scene deleteFieldScene = new Scene(deleteFieldBox,300,250);


        Button deleteField = new Button("Delete Field");
        deleteField.setOnMouseClicked(e ->{
            stage.setScene(deleteFieldScene);
        });

        Label deleteLabel = new Label("Enter Field ID");
        deleteLabel.setFont(new Font("Arial", 20));
        TextField IDInput = new TextField();
        Button submitID = new Button("Submit");

        submitID.setOnMousePressed(e -> {
            String deleteID = IDInput.getText();
            Iterator<Field> iterator = fieldData.iterator();
            while (iterator.hasNext()) {
                Field field = iterator.next();
                if (deleteID.equals(field.getID())) {
                    iterator.remove();
                    stage.setScene(fieldScene);
                    return;
                }
            }
            stage.setScene(fieldScene);
        });

        deleteFieldBox.getChildren().addAll(deleteLabel, IDInput, submitID);

        // fields back to menu button
        Button fieldsBackToMain = new Button("Back");
        fieldsBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });


        // field functions bar
        HBox fieldFunctionsBar = new HBox();
        fieldFunctionsBar.getChildren().addAll(addField, deleteField, fieldsBackToMain);
        fieldPage.getChildren().addAll(fieldFunctionsBar, fieldTable);



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