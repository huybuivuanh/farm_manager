package org.openjfx.javafxmavenarchetypes;
import control.BinControl;
import control.FieldControl;
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
import org.InitialFarm.Crop;
import org.entities.ChemicalRecord;
import org.entities.Field;
import org.entities.Year;
import java.util.*;


public class FieldView extends StackPane implements ModelSubscriber {

    Stage stage;
    Scene MenuScene ;
    Scene fieldScene;
    VBox cropPage = new VBox();
    Scene cropScene = new Scene(cropPage);

    VBox fieldPage = new VBox();

    // field functions bar
    HBox fieldFunctionsBar = new HBox();


    private FieldControl fieldController = new FieldControl();
    private TableView<Field> fieldTable = new TableView<Field>();

    private ObservableList<Field> fieldData = fieldController.fieldList;


    private BinControl binController = new BinControl();




    public FieldView(){
        ArrayList<String> recordData = new ArrayList<>();
        ObservableList<String> observableList = FXCollections.observableArrayList(recordData);
        ListView<String> listView = new ListView<>(observableList);

        ArrayList<String> chemicalData = new ArrayList<>();
        ObservableList<String> chemicalObservableList = FXCollections.observableArrayList(chemicalData);
        ListView<String> listView2 = new ListView<>(chemicalObservableList);

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
        cropTypeInput.getItems().addAll(binController.cropType);

        Label space5 = new Label("\t\t");
        Label space6 = new Label("\t\t");
        HBox groupBox1 = new HBox(cropTypeLabel, space5, cropTypeInput);
        HBox groupBox2 = new HBox(newCropTypeLabel, space6, newCropTypeInput);

        Label cropVarietyLabel = new Label("Select Crop Variety");

        ComboBox<String> cropVarietyInput = new ComboBox<>();
        cropVarietyInput.getItems().addAll("LibertyLink", "RoundupReady", "Navigator", "ClearField", "All Other Grains");



        binController.cropType.addListener((ListChangeListener<String>) change -> {
            cropTypeInput.setItems(binController.cropType);
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
                addCropfieldId.setText(selectedData.getID());
                addCropPageTitle.setText("Add Crop to Field with ID (" + selectedData.getID() + ")");
                addCropPageTitle.getStyleClass().add("page-label");
                stage.setScene(addCropScene);
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
                        binController.addCropType(newCropTypeInput.getText());
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
            if (fieldTable.getSelectionModel().getSelectedItem() != null){
                fieldController.harvest(fieldTable.getSelectionModel().getSelectedItem().getID());
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


        fieldTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
                if (selectedData != null) {
                    if (!selectedData.getYears().isEmpty()){
                        for (Year year : selectedData.getYears()){
                            Crop crop = year.getCrop();
                            String cropHistory = "Crop ID: "+ crop.getDbId() + "\nCrop Type: " + crop.getCropType() + "\nCrop Variety: " +
                                    crop.getCropVariety() + "\nBushel Weight (lbs): " + crop.getBushelWeight() +
                                    "\nHarvested: " + (year.getHarvestDate() != null) + "\nHarvest Date: " + year.getHarvestDate() +
                                    "\nSeeding Rate (lbs/acre): " + year.getSeeding_rate() +
                                    "\nSeeding Date: " + year.getSeeding_date();
                            recordData.add(cropHistory);

                            StringBuilder chemHistory = new StringBuilder();
                            if (!year.getChemical_records().isEmpty()){
                                chemHistory.append("Crop ID Chemical Sprayed on: ").append(year.getCrop().getDbId()).append("\nFertilizer Rate (lbs/acre): ").append(year.getFertilizer_rate()).append("\n");
                                for (ChemicalRecord record : year.getChemical_records()){
                                    chemHistory.append(record.toString()).append("\n\n");
                                }

                            } else {
                                chemHistory.append("Crop ID Chemical Sprayed on: ").append(year.getCrop().getDbId()).append("\nFertilizer Rate(lbs/acre): None\nChemical Sprayed: None\nSpraying Date: None");
                            }
                            chemicalData.add(chemHistory.toString());
                        }
                    }
                }
                Collections.reverse(recordData);
                observableList.clear();
                observableList.addAll(recordData);

                Collections.reverse(chemicalData);
                chemicalObservableList.clear();
                chemicalObservableList.addAll(chemicalData);
                stage.setScene(cropScene);
            }
        });


        Label cropLabel = new Label("Crop Record (most recent at the top)");
        cropLabel.getStyleClass().add("page-label");
        cropLabel.setFont(new Font("Arial", 20));
        cropLabel.setStyle("-fx-font-weight: bold;");


        Label chemLabel = new Label("Chemical Record (most recent at the top)");
        chemLabel.getStyleClass().add("page-label");
        chemLabel.setFont(new Font("Arial", 20));
        chemLabel.setStyle("-fx-font-weight: bold;");



        Button cropBackToField = new Button("Back To Field");
        cropBackToField.setOnMouseClicked(event -> {
            recordData.clear();
            chemicalData.clear();
            stage.setScene(fieldScene);
        });

        Button viewField = new Button("View Field");
        viewField.setOnMouseClicked(event -> {
            Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
            if (selectedData != null) {
                if (!selectedData.getYears().isEmpty()){
                    for (Year year : selectedData.getYears()){
                        Crop crop = year.getCrop();
                        String cropHistory = "Crop ID: "+ crop.getDbId() + "\nCrop Type: " + crop.getCropType() + "\nCrop Variety: " +
                                crop.getCropVariety() + "\nBushel Weight (lbs): " + crop.getBushelWeight() +
                                "\nHarvested: " + (year.getHarvestDate() != null) + "\nHarvest Date: " + year.getHarvestDate() +
                                "\nSeeding Rate (lbs/acre): " + year.getSeeding_rate() +
                                "\nSeeding Date: " + year.getSeeding_date();
                        recordData.add(cropHistory);

                        StringBuilder chemHistory = new StringBuilder();
                        if (!year.getChemical_records().isEmpty()){
                            chemHistory.append("Crop ID Chemical Sprayed on: ").append(year.getCrop().getDbId()).append("\nFertilizer Rate (lbs/acre): ").append(year.getFertilizer_rate()).append("\n");
                            for (ChemicalRecord record : year.getChemical_records()){
                                chemHistory.append(record.toString()).append("\n\n");
                            }

                        } else {
                            chemHistory.append("Crop ID Chemical Sprayed on: ").append(year.getCrop().getDbId()).append("\nFertilizer Rate(lbs/acre): None\nChemical Sprayed: None\nSpraying Date: None");
                        }
                        chemicalData.add(chemHistory.toString());
                    }
                }
                Collections.reverse(recordData);
                observableList.clear();
                observableList.addAll(recordData);

                Collections.reverse(chemicalData);
                chemicalObservableList.clear();
                chemicalObservableList.addAll(chemicalData);
                stage.setScene(cropScene);
            } else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }
        });


        cropPage.getChildren().addAll(cropBackToField, cropLabel, listView, chemLabel, listView2);


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

    @Override
    public void modelChanged() {

    }
}