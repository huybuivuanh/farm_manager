package org.openjfx.javafxmavenarchetypes;
import control.CropControl;
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
import javafx.stage.Stage;
import org.InitialFarm.Crop;
import org.entities.Field;

import java.time.LocalTime;
import java.time.Year;
import java.util.Date;


public class FieldView extends StackPane implements ModelSubscriber {

    Stage stage;
    Scene MenuScene ;
    Scene fieldScene;

    Scene cropScene;
    CropView cropPage = new CropView();

    VBox fieldPage = new VBox();

    // field functions bar
    HBox fieldFunctionsBar = new HBox();


    private FieldControl fieldController = new FieldControl();
    private TableView<Field> fieldTable = new TableView<Field>();

    private ObservableList<Field> fieldData = fieldController.fieldList;


    private CropControl cropController = new CropControl();

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

        TableColumn<Field, Double> fieldSizeCol = new TableColumn<Field, Double>("Field Size");
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

        TextField fieldIDInput = new TextField("Field ID");
        TextField fieldNameInput = new TextField("Field Name");
        TextField fieldSizeInput = new TextField("Field Size");
        TextField fieldLocation = new TextField("Field Location");
        Button submitFieldInfo = new Button("Submit");


        addFieldBox.getChildren().addAll(fieldIDInput, fieldNameInput, fieldSizeInput, fieldLocation, submitFieldInfo);
        Button addField = new Button("Add Field");
        addField.setOnMouseClicked(e ->{
            stage.setScene(addFieldScene);
        });

        submitFieldInfo.setOnMouseClicked(e ->{
            fieldController.addField(fieldIDInput.getText(),fieldNameInput.getText(), Double.parseDouble(fieldSizeInput.getText()), fieldLocation.getText());
            stage.setScene(fieldScene);
        });

        VBox fieldEditBox = new VBox(30);
        Scene editFieldScene = new Scene(fieldEditBox,300,250);

        Button editField = new Button("Edit Field");
        editField.setOnMouseClicked(e -> {
            stage.setScene(editFieldScene);
        });


        TextField idInputEdit = new TextField();
        TextField fieldNameFEdit = new TextField();
        TextField fieldSizeEdit = new TextField();
        TextField locationEdit = new TextField();
        Button submitTaskEdit = new Button("Submit");

        fieldEditBox.getChildren().addAll(idInputEdit, fieldNameFEdit, fieldSizeEdit, locationEdit,submitTaskEdit);
        submitTaskEdit.setOnMouseClicked(e ->{
            fieldController.editField(fieldTable.getSelectionModel().getSelectedItem().getID(),
                    idInputEdit.getText(), fieldNameFEdit.getText(), Double.parseDouble(fieldSizeEdit.getText()),
                    locationEdit.getText());
            System.out.println(fieldTable.getSelectionModel().getSelectedItem());
            stage.setScene(fieldScene);
            fieldTable.refresh();
        });

        editField.setOnMouseClicked(e ->{
            idInputEdit.setText(fieldTable.getSelectionModel().getSelectedItem().getID());
            fieldNameFEdit.setText(fieldTable.getSelectionModel().getSelectedItem().getName());
            fieldSizeEdit.setText(Double.toString(fieldTable.getSelectionModel().getSelectedItem().getSize()));
            locationEdit.setText(fieldTable.getSelectionModel().getSelectedItem().getLocation());
            stage.setScene(editFieldScene);
        });

        Button fieldsBackToMain = new Button("Back To Main");
        fieldsBackToMain.setOnMouseClicked(e -> {
            stage.setScene(MenuScene);
        });

        Button deleteField = new Button("Delete Field");
        deleteField.setOnAction(event -> {
            fieldController.deleteField(fieldTable.getSelectionModel().getSelectedItem().getID());
            fieldTable.refresh();
        });

        fieldTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
                if (selectedData != null) {
                    if (selectedData.getYears().isEmpty()){
                        cropPage.sendYear(Year.now().getValue());
                    }else {
                        cropPage.sendYear(selectedData.getCurrent_Year().getYear());
                    }
                    stage.setScene(cropScene);
                }
            }
        });

        VBox addCropBox = new VBox(30);
        Scene addCropScene = new Scene(addCropBox,300,250);

        TextField fieldId = new TextField("Field ID");
        Label newCropTypeLabel = new Label("Add New Crop Type");
        TextField newCropTypeInput = new TextField();

        Label cropTypeLabel = new Label("Select Crop Type");
        ComboBox<String> cropTypeInput = new ComboBox<>();
        cropTypeInput.getItems().addAll(cropController.cropType);

        ComboBox<String> cropVarietyInput = new ComboBox<>();
        cropVarietyInput.getItems().addAll("LibertyLink", "RoundupReady", "Navigator", "ClearField", "All Other Grains");



        cropController.cropType.addListener((ListChangeListener<String>) change -> {
            cropTypeInput.setItems(cropController.cropType);
        });

        TextField bushelWeight = new TextField("Bushel Weight");
        TextField seedingRateInput = new TextField("Seeding Rate (lbs/acre)");
        Label seedingDateLabel = new Label("Seeding Date");
        DatePicker seedingDateInput = new DatePicker();
        Button submitCropInfo = new Button("Submit");

        addCropBox.getChildren().addAll(fieldId, cropTypeLabel, cropTypeInput, newCropTypeLabel, newCropTypeInput,
                cropVarietyInput, bushelWeight, seedingRateInput, seedingDateLabel, seedingDateInput, submitCropInfo);
        Button addCrop = new Button("Add Crop");
        addCrop.setOnMouseClicked(e ->{
            stage.setScene(addCropScene);
        });

        submitCropInfo.setOnMouseClicked(e ->{
            Crop crop;
            if (!newCropTypeInput.getText().isEmpty()){
                cropController.addCropType(newCropTypeInput.getText());
            }
            if (cropTypeInput.getValue() == null){
                crop = new Crop(null, newCropTypeInput.getText(), cropVarietyInput.getValue(), Float.parseFloat(bushelWeight.getText()));
            }
            else {
                crop = new Crop(null, cropTypeInput.getValue(), cropVarietyInput.getValue(), Float.parseFloat(bushelWeight.getText()));
            }
            fieldController.addCrop(fieldId.getText(), crop);
            cropController.addCrop(crop);

            // clear the form
            cropTypeInput.setValue(null);
            cropVarietyInput.setValue(null);
            newCropTypeInput.clear();

            stage.setScene(fieldScene);
        });


        fieldFunctionsBar.getChildren().addAll(addField, editField, deleteField, addCrop, fieldsBackToMain);
        fieldPage.getChildren().addAll(fieldFunctionsBar, fieldTable);
        this.getChildren().addAll(fieldPage);
    }




    public void setStageMainField(Stage stage, Scene main, Scene field){
        this.stage = stage;
        this.MenuScene = main;
        this.fieldScene = field;
        cropPage.setStageField(stage, fieldScene);
        cropScene = new Scene(cropPage,300,250);
    }


    @Override
    public void modelChanged() {

    }
}