package org.openjfx.javafxmavenarchetypes;
import control.BinControl;
import control.FieldControl;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.InitialFarm.Crop;
import org.bson.types.ObjectId;
import org.entities.ChemicalRecord;
import org.entities.Field;
import org.entities.Year;

import java.time.LocalDate;
import java.util.*;


public class FieldView extends StackPane implements ModelSubscriber {

    private Stage stage;
    private Scene MenuScene ;
    private Scene fieldScene;
    private VBox cropPage = new VBox();
    private Scene cropScene = new Scene(cropPage);

    private VBox fieldPage = new VBox();

    // field functions bar
    private HBox fieldFunctionsBar = new HBox();


    private FieldControl fieldController = new FieldControl();
    private TableView<Field> fieldTable = new TableView<Field>();

    private ObservableList<Field> fieldData = fieldController.fieldList;

    private TableView<Year> yearTable = new TableView<>();
    private ObservableList<Year> yearData = fieldController.yearList;

    private TableView<Year> currentYearTable = new TableView<>();

    private ObservableList<Year> currentYearData = FXCollections.observableArrayList();




    public FieldView(){

        TableColumn<Field, String> fieldIDCol = new TableColumn<Field, String>("Field ID");
        fieldIDCol.setMinWidth(130);
        fieldIDCol.setCellValueFactory(
                new PropertyValueFactory<Field, String>("ID")
        );

        TableColumn<Field, String> fieldNameCol = new TableColumn<Field, String>("Field Name");
        fieldNameCol.setMinWidth(130);
        fieldNameCol.setCellValueFactory(
                new PropertyValueFactory<Field, String>("name")
        );

        TableColumn<Field, Double> fieldSizeCol = new TableColumn<Field, Double>("Field Size (Acre)");
        fieldSizeCol.setMinWidth(130);
        fieldSizeCol.setCellValueFactory(
                new PropertyValueFactory<Field, Double>("size")
        );

        TableColumn<Field, String> fieldLocationCol = new TableColumn<Field, String>("Field Location");
        fieldLocationCol.setMinWidth(130);
        fieldLocationCol.setCellValueFactory(
                new PropertyValueFactory<Field, String>("location")
        );



        fieldTable.setItems(fieldData);
        fieldTable.getColumns().addAll(fieldIDCol, fieldNameCol, fieldSizeCol, fieldLocationCol);




        VBox addFieldBox = new VBox(30);
        Scene addFieldScene = new Scene(addFieldBox,300,250);

        Label addFieldPageTitle = new Label("Add New Field");
        addFieldPageTitle.getStyleClass().add("page-label");
        TextField fieldIDInput = new TextField("Field ID");
        TextField fieldNameInput = new TextField("Field Name");
        TextField fieldSizeInput = new TextField("Field Size (acre) ei: 20");
        TextField fieldLocation = new TextField("Field Location");
        Button submitFieldInfo = new Button("Submit");
        Button addFieldCancel = new Button("Cancel");
        addFieldCancel.setOnMouseClicked(event -> {
            stage.setScene(fieldScene);
        });

        Label space1 = new Label("\t\t");
        HBox submitAndCancelBox1 = new HBox(submitFieldInfo, space1, addFieldCancel);


        addFieldBox.getChildren().addAll(addFieldPageTitle, fieldIDInput, fieldNameInput, fieldSizeInput, fieldLocation, submitAndCancelBox1);
        Button addField = new Button("Add Field");
        addField.setOnMouseClicked(e ->{
            stage.setScene(addFieldScene);
        });

        submitFieldInfo.setOnMouseClicked(e ->{
            double fieldSize = -1.0;
            try {
                fieldSize = Double.parseDouble(fieldSizeInput.getText());
            } catch (Exception b){
                System.out.println("Invalid field size");
                showErrorPopup("Invalid field Size");
            }
            if (fieldSize != -1.0){
                fieldController.addField(fieldIDInput.getText(),fieldNameInput.getText(), fieldSize, fieldLocation.getText());
                stage.setScene(fieldScene);
            }
        });

        VBox fieldEditBox = new VBox(30);
        Scene editFieldScene = new Scene(fieldEditBox,300,250);

        Button editField = new Button("Edit Field");
        editField.setOnMouseClicked(e -> {
            stage.setScene(editFieldScene);
        });


        Label editFieldPageTitle = new Label();
        TextField idInputEdit = new TextField();
        TextField fieldNameFEdit = new TextField();
        TextField fieldSizeEdit = new TextField();
        TextField locationEdit = new TextField();
        Button submitFieldEdit = new Button("Submit");
        Button editFieldCancel = new Button("Cancel");
        editFieldCancel.setOnMouseClicked(event -> {
            stage.setScene(fieldScene);
        });

        Label space2 = new Label("\t\t");
        HBox submitAndCancelBox2 = new HBox(submitFieldEdit, space2, editFieldCancel);


        fieldEditBox.getChildren().addAll(editFieldPageTitle, idInputEdit, fieldNameFEdit, fieldSizeEdit, locationEdit,submitAndCancelBox2);
        submitFieldEdit.setOnMouseClicked(e ->{
            double fieldSize = -1.0;
            try {
                fieldSize = Double.parseDouble(fieldSizeEdit.getText());
            } catch (Exception b){
                System.out.println("Invalid field size");
                showErrorPopup("Invalid field size");
            }
            if (fieldSize != -1.0){
                fieldController.editField(fieldTable.getSelectionModel().getSelectedItem().getID(),
                        idInputEdit.getText(), fieldNameFEdit.getText(), fieldSize,
                        locationEdit.getText());
                stage.setScene(fieldScene);
                fieldTable.refresh();
            }
        });

        editField.setOnMouseClicked(e ->{
            Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                editFieldPageTitle.setText("Edit Field With ID (" + selectedData.getID() + ")");
                editFieldPageTitle.getStyleClass().add("page-label");
                idInputEdit.setText(selectedData.getID());
                fieldNameFEdit.setText(selectedData.getName());
                fieldSizeEdit.setText(Double.toString(selectedData.getSize()));
                locationEdit.setText(selectedData.getLocation());
                stage.setScene(editFieldScene);
            }
            else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }
        });

        Button fieldsBackToMain = new Button("Back To Main");
        fieldsBackToMain.setOnMouseClicked(e -> {
            stage.setScene(MenuScene);
        });

        Button deleteField = new Button("Delete Field");
        deleteField.setOnAction(event -> {
            if (fieldTable.getSelectionModel().getSelectedItem() != null){
                fieldController.deleteField(fieldTable.getSelectionModel().getSelectedItem().getID());
                fieldTable.refresh();
            }
            else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }
        });


        VBox addCropBox = new VBox(30);
        Scene addCropScene = new Scene(addCropBox,300,250);

        Label addCropPageTitle = new Label();
        TextField addCropfieldId = new TextField();
        Label newCropTypeLabel = new Label("Or Add New Crop Type");
        TextField newCropTypeInput = new TextField();

        Label cropTypeLabel = new Label("Select Crop Type");
        ComboBox<String> cropTypeInput = new ComboBox<>();
        cropTypeInput.getItems().addAll(fieldController.cropType);

        Label space5 = new Label("\t\t");
        Label space6 = new Label("\t\t");
        HBox groupBox1 = new HBox(cropTypeLabel, space5, cropTypeInput);
        HBox groupBox2 = new HBox(newCropTypeLabel, space6, newCropTypeInput);

        Label cropVarietyLabel = new Label("Select Crop Variety");

        ComboBox<String> cropVarietyInput = new ComboBox<>();
        cropVarietyInput.getItems().addAll("LibertyLink", "RoundupReady", "Navigator", "ClearField", "All Other Grains");



        fieldController.cropType.addListener((ListChangeListener<String>) change -> {
            cropTypeInput.setItems(fieldController.cropType);
        });

        TextField bushelWeight = new TextField("Bushel Weight (lbs) ie: 20");
        TextField seedingRateInput = new TextField("Seeding Rate (lbs/acre) ie: 20");
        Label seedingDateLabel = new Label("Seeding Date");
        DatePicker seedingDateInput = new DatePicker();
        Button submitCropInfo = new Button("Submit");
        Button addCropCancel = new Button("Cancel");
        addCropCancel.setOnMouseClicked(event -> {
            stage.setScene(fieldScene);
        });

        Label space3 = new Label("\t\t");
        HBox submitAndCancelBox3 = new HBox(submitCropInfo, space3, addCropCancel);

        addCropBox.getChildren().addAll(addCropPageTitle, groupBox1, groupBox2, cropVarietyLabel,
                cropVarietyInput, bushelWeight, seedingRateInput, seedingDateLabel, seedingDateInput, submitAndCancelBox3);
        Button addCrop = new Button("Add Crop");
        addCrop.setOnMouseClicked(e ->{
            Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                if (selectedData.getCurrent_Year() != null){
                    System.out.println("Farm is currently full of crop");
                    showErrorPopup("Farm is currently full of crop");
                } else {
                    addCropfieldId.setText(selectedData.getID());
                    addCropPageTitle.setText("Add Crop to Field with ID (" + selectedData.getID() + ")");
                    addCropPageTitle.getStyleClass().add("page-label");
                    stage.setScene(addCropScene);
                }
            }
            else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }

        });

        submitCropInfo.setOnMouseClicked(e ->{
            double bWeight = -1.0;
            double seedingRate = -1.0;
            try {
                bWeight = Double.parseDouble(bushelWeight.getText());
                seedingRate = Double.parseDouble(seedingRateInput.getText());
            } catch (Exception error){
                System.out.println("Invalid bushel weight or seeding rate input");
                showErrorPopup("Invalid bushel weight or seeding rate input");
            }
            if (seedingDateInput.getValue() == null){
                System.out.println("Need to pick a seeding date");
                showErrorPopup("Need to pick a seeding date");
            } else {
                if (seedingRate != -1.0 && bWeight != -1.0) {
                    Crop crop;
                    if (!newCropTypeInput.getText().isEmpty()){
                        fieldController.addCropType(newCropTypeInput.getText());
                    }
                    if (cropTypeInput.getValue() == null){
                        crop = fieldController.makeCrop(null, newCropTypeInput.getText(), cropVarietyInput.getValue(), bWeight);
                    }
                    else {
                        crop = fieldController.makeCrop(null, cropTypeInput.getValue(), cropVarietyInput.getValue(), bWeight);
                    }
                    fieldController.addCrop(addCropfieldId.getText(), crop, seedingRate, seedingDateInput.getValue());

                    // clear the form
                    cropTypeInput.setValue(null);
                    cropVarietyInput.setValue(null);
                    newCropTypeInput.clear();
                    stage.setScene(fieldScene);
                }
            }
        });


        Button harvest = new Button("Harvest");
        harvest.setOnMouseClicked(event ->{
            Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                fieldController.harvest(selectedData.getID());
                showPopup("Harvested field (" + selectedData.getName() + ") successfully");
            }
            else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }
        });



        VBox addChemPage = new VBox(30);
        Scene addChemScene = new Scene(addChemPage,300,250);



        Label addChemPageTitle = new Label();
        TextField chemFieldID = new TextField();
        TextField fertilizerInput = new TextField("Fertilizer Rate (lbs/acre)");
        TextField chemSprayedInput = new TextField("Chemical Sprayed");
        TextField chemGroupInput = new TextField("Chemical Group");
        DatePicker sprayDate = new DatePicker();
        Button submitChemInfo = new Button("Submit");
        Button addChemCancel = new Button("Cancel");
        addChemCancel.setOnMouseClicked(event -> {
            stage.setScene(fieldScene);
        });

        Label space4 = new Label("\t\t");
        HBox submitAndCancelBox4 = new HBox(submitChemInfo, space4, addChemCancel);

        Button sprayChemical = new Button("Add Chemical Sprayed");
        sprayChemical.setOnMouseClicked(event -> {
            Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                if (selectedData.getCurrent_Year() != null) {
                    chemFieldID.setText(selectedData.getID());
                    addChemPageTitle.setText("Add Chemical to Field with Field ID (" + selectedData.getID() + ")");
                    addChemPageTitle.getStyleClass().add("page-label");
                    stage.setScene(addChemScene);
                } else {
                    System.out.println("Field with ID (" + selectedData.getID() + ") current has no crop to spray chemical");
                    showErrorPopup("Field with ID (" + selectedData.getID() + ") current has no crop to spray chemical");
                }
            }
            else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }
        });

        submitChemInfo.setOnMouseClicked(event -> {
            double fertilizerRate = -1.0;
            try {
                fertilizerRate = Double.parseDouble(fertilizerInput.getText());
            } catch(Exception b){
                System.out.println("Invalid fertilizer rate");
                showErrorPopup("Invalid fertilizer rate");
            }

            if (sprayDate.getValue() == null){
                System.out.println("Need to pick a spraying date");
                showErrorPopup("Need to pick a spraying date");
            } else {
                if (fertilizerRate != -1.0){
                    fieldController.addChemical(chemFieldID.getText(), fertilizerRate,
                            chemSprayedInput.getText(), chemGroupInput.getText(), sprayDate.getValue());
                    stage.setScene(fieldScene);
                }
            }
        });

        addChemPage.getChildren().addAll(addChemPageTitle, fertilizerInput, chemSprayedInput, chemGroupInput, sprayDate, submitAndCancelBox4);


        Label cropPageTitle = new Label();
        cropPageTitle.getStyleClass().add("page-label");
        fieldTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
                cropPageTitle.setText("Field page with field named (" + selectedData.getName() + ")");
                if (selectedData.getCurrent_Year() != null){
                    currentYearData.clear();
                    currentYearData.add(selectedData.getCurrent_Year());
                }
                else {
                    currentYearData.clear();
                }
                currentYearTable.setItems(currentYearData);
                currentYearTable.refresh();
                yearTable.refresh();
                stage.setScene(cropScene);
            }
        });


        Label cropHistoryLabel = new Label("All Crop History");
        cropHistoryLabel.getStyleClass().add("page-label");

        Button cropBackToField = new Button("Back To Field");
        cropBackToField.setOnMouseClicked(event -> {
            stage.setScene(fieldScene);
        });

        Button viewField = new Button("View Field");
        viewField.setOnMouseClicked(event -> {
            Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
            if (selectedData != null) {
                if (selectedData.getCurrent_Year() != null){
                    currentYearData.clear();
                    currentYearData.add(selectedData.getCurrent_Year());
                }
                else {
                    currentYearData.clear();
                }
                currentYearTable.setItems(currentYearData);
                currentYearTable.refresh();
                yearTable.refresh();
                cropPageTitle.setText("Field page with field named (" + selectedData.getName() + ")");
                stage.setScene(cropScene);
            } else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }
        });


        TableColumn<Year, String> fieldNameCol2 = new TableColumn<Year, String>("Field Name");
        fieldNameCol2.setPrefWidth(100);
        fieldNameCol2.setCellValueFactory(
                new PropertyValueFactory<Year, String>("fieldName")
        );

        TableColumn<Year, ObjectId> yearIDCol = new TableColumn<Year, ObjectId>("Year ID");
        yearIDCol.setPrefWidth(70);
        yearIDCol.setCellValueFactory(
                new PropertyValueFactory<Year, ObjectId>("DbId")
        );

        TableColumn<Year, Integer> yearCol = new TableColumn<Year, Integer>("Year");
        yearCol.setMinWidth(70);
        yearCol.setCellValueFactory(
                new PropertyValueFactory<Year, Integer>("year")
        );

        TableColumn<Year, String> cropTypeCol = new TableColumn<Year, String>("Crop Type");
        cropTypeCol.setMinWidth(100);
        cropTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCrop().getCropType()));

        TableColumn<Year, String> cropVarietyCol = new TableColumn<Year, String>("Crop Variety");
        cropVarietyCol.setMinWidth(100);
        cropVarietyCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCrop().getCropVariety()));

        TableColumn<Year, Double> bushelWeightCol = new TableColumn<Year, Double>("Bushel Weight");
        bushelWeightCol.setMinWidth(110);
        bushelWeightCol.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getCrop().getBushelWeight()).asObject()
        );

        TableColumn<Year, Double> seedingRateCol = new TableColumn<Year, Double>("Seeding Rate");
        seedingRateCol.setMinWidth(100);
        seedingRateCol.setCellValueFactory(
                new PropertyValueFactory<Year, Double>("seeding_rate")
        );

        TableColumn<Year, LocalDate> seedingDateCol = new TableColumn<Year, LocalDate>("Seeding Date");
        seedingDateCol.setMinWidth(100);
        seedingDateCol.setCellValueFactory(
                new PropertyValueFactory<Year, LocalDate>("seeding_date")
        );

        TableColumn<Year, Double> fertilizerRateCol = new TableColumn<Year, Double>("Fertilizer Rate");
        fertilizerRateCol.setMinWidth(100);
        fertilizerRateCol.setCellValueFactory(
                new PropertyValueFactory<Year, Double>("fertilizer_rate")
        );

        TableColumn<Year, String> chemicalRecordCol = new TableColumn<>("Chemical Records");
        chemicalRecordCol.setMinWidth(350);
        chemicalRecordCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getChemicalRecordData(cellData.getValue().getChemical_records()))
        );

        TableColumn<Year, LocalDate> harvestDateCol = new TableColumn<Year, LocalDate>("Harvest Date");
        harvestDateCol.setMinWidth(100);
        harvestDateCol.setCellValueFactory(
                new PropertyValueFactory<Year, LocalDate>("HarvestDate")
        );

        yearTable.setItems(yearData);
        yearTable.getColumns().addAll(fieldNameCol2, yearIDCol, yearCol, cropTypeCol, cropVarietyCol,
                bushelWeightCol, seedingRateCol, seedingDateCol, fertilizerRateCol, chemicalRecordCol, harvestDateCol);


        // current year table columns
        Label currentYearLabel = new Label("Current Year Crop");
        currentYearLabel.getStyleClass().add("page-label");

        TableColumn<Year, String> fieldNameCol3 = new TableColumn<Year, String>("Field Name");
        fieldNameCol3.setPrefWidth(100);
        fieldNameCol3.setCellValueFactory(
                new PropertyValueFactory<Year, String>("fieldName")
        );

        TableColumn<Year, ObjectId> yearIDCol2 = new TableColumn<Year, ObjectId>("Year ID");
        yearIDCol2.setPrefWidth(70);
        yearIDCol2.setCellValueFactory(
                new PropertyValueFactory<Year, ObjectId>("DbId")
        );

        TableColumn<Year, Integer> yearCol2 = new TableColumn<Year, Integer>("Year");
        yearCol2.setMinWidth(70);
        yearCol2.setCellValueFactory(
                new PropertyValueFactory<Year, Integer>("year")
        );

        TableColumn<Year, String> cropTypeCol2 = new TableColumn<Year, String>("Crop Type");
        cropTypeCol2.setMinWidth(100);
        cropTypeCol2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCrop().getCropType()));

        TableColumn<Year, String> cropVarietyCol2 = new TableColumn<Year, String>("Crop Variety");
        cropVarietyCol2.setMinWidth(100);
        cropVarietyCol2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCrop().getCropVariety()));

        TableColumn<Year, Double> bushelWeightCol2 = new TableColumn<Year, Double>("Bushel Weight");
        bushelWeightCol2.setMinWidth(110);
        bushelWeightCol2.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getCrop().getBushelWeight()).asObject()
        );

        TableColumn<Year, Double> seedingRateCol2 = new TableColumn<Year, Double>("Seeding Rate");
        seedingRateCol2.setMinWidth(100);
        seedingRateCol2.setCellValueFactory(
                new PropertyValueFactory<Year, Double>("seeding_rate")
        );

        TableColumn<Year, LocalDate> seedingDateCol2 = new TableColumn<Year, LocalDate>("Seeding Date");
        seedingDateCol2.setMinWidth(100);
        seedingDateCol2.setCellValueFactory(
                new PropertyValueFactory<Year, LocalDate>("seeding_date")
        );

        TableColumn<Year, Double> fertilizerRateCol2 = new TableColumn<Year, Double>("Fertilizer Rate");
        fertilizerRateCol2.setMinWidth(100);
        fertilizerRateCol2.setCellValueFactory(
                new PropertyValueFactory<Year, Double>("fertilizer_rate")
        );

        TableColumn<Year, String> chemicalRecordCol2 = new TableColumn<>("Chemical Records");
        chemicalRecordCol2.setMinWidth(350);
        chemicalRecordCol2.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getChemicalRecordData(cellData.getValue().getChemical_records()))
        );

        TableColumn<Year, LocalDate> harvestDateCol2 = new TableColumn<Year, LocalDate>("Harvest Date");
        harvestDateCol2.setMinWidth(100);
        harvestDateCol2.setCellValueFactory(
                new PropertyValueFactory<Year, LocalDate>("HarvestDate")
        );

        currentYearTable.setItems(currentYearData);
        currentYearTable.getColumns().addAll(fieldNameCol3, yearIDCol2, yearCol2, cropTypeCol2, cropVarietyCol2,
                bushelWeightCol2, seedingRateCol2, seedingDateCol2, fertilizerRateCol2, chemicalRecordCol2, harvestDateCol2);


        // Map to store colors for each crop type
        Map<String, String> cropTypeColors = new HashMap<>();
        // Custom row factory to set the background color based on crop type
        yearTable.setRowFactory(new Callback<TableView<Year>, TableRow<Year>>() {
            @Override
            public TableRow<Year> call(TableView<Year> param) {
                return new TableRow<Year>() {
                    @Override
                    protected void updateItem(Year item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setStyle(""); // Reset style for empty cells
                        } else {
                            String cropType = item.getCrop().getCropType();

                            // Check if the color is already assigned for this crop type
                            if (!cropTypeColors.containsKey(cropType)) {
                                // If not, generate a random color and store it in the map
                                String randomColor = generateRandomColor();
                                cropTypeColors.put(cropType, randomColor);
                            }

                            // Set the background color based on the stored color for this crop type
                            setStyle("-fx-background-color: " + cropTypeColors.get(cropType) + ";");
                        }
                    }
                };
            }
        });

//        // Custom cell factory to set the background color based on crop type
//        cropTypeCol.setCellFactory(new Callback<TableColumn<Year, String>, TableCell<Year, String>>() {
//            @Override
//            public TableCell<Year, String> call(TableColumn<Year, String> param) {
//                return new TableCell<Year, String>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//
//                        if (item == null || empty) {
//                            setText(null);
//                            setStyle(""); // Reset style for empty cells
//                        } else {
//                            setText(item);
//
//                            // Check if the color is already assigned for this crop type
//                            if (!cropTypeColors.containsKey(item)) {
//                                // If not, generate a random color and store it in the map
//                                String randomColor = generateRandomColor();
//                                cropTypeColors.put(item, randomColor);
//                            }
//
//                            // Set the background color based on the stored color for this crop type
//                            setStyle("-fx-background-color: " + cropTypeColors.get(item) + ";");
//                        }
//                    }
//                };
//            }
//        });


        cropPage.getChildren().addAll(cropBackToField, cropPageTitle, currentYearLabel, currentYearTable, cropHistoryLabel, yearTable);


        fieldFunctionsBar.getChildren().addAll(addField, editField, viewField, deleteField, addCrop, harvest, sprayChemical, fieldsBackToMain);
        fieldPage.getChildren().addAll(fieldFunctionsBar, fieldTable);

        // css
        addFieldScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
        editFieldScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
        addChemScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
        addCropScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
        cropScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
        this.getChildren().addAll(fieldPage);
    }




    public void setStageMainField(Stage stage, Scene main, Scene field){
        this.stage = stage;
        this.MenuScene = main;
        this.fieldScene = field;
        fieldScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
    }

    private void showErrorPopup(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR MESSAGE");
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showPopup(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("MESSAGE");
        alert.setHeaderText("CONFIRM MESSAGE");
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Method to generate a random color in hexadecimal format
    private String generateRandomColor() {
        int minThreshold = 600;
        int r, g, b;
        Random random = new Random();
        int sum;
        do {
            r = random.nextInt(180,256);
            g = random.nextInt(180,256);
            b = random.nextInt(180,256);
            sum = r + g + b;
        } while (sum < minThreshold);
        return String.format("#%02x%02x%02x", r, g, b);
    }

    @Override
    public void modelChanged() {

    }
}