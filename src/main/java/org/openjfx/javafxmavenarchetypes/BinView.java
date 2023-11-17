package org.openjfx.javafxmavenarchetypes;

import control.BinControl;
import control.CropControl;
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
import org.InitialFarm.GrainBin;


import java.util.concurrent.atomic.AtomicReference;

public class BinView extends StackPane implements ModelSubscriber {

    private VBox binPage = new VBox();
    private Scene MenuScene ;

    private Scene binScene;
    private Stage stage;

    private TableView<GrainBin> binTable = new TableView<>();
    BinControl binController = new BinControl();
    private ObservableList<GrainBin> grainBinData = binController.binList;


    CropControl cropController = new CropControl();




    public BinView(){
        VBox binCropPage = new VBox(30);
        Scene binCropScene = new Scene(binCropPage, 300, 250);

        Label currentCropLabel = new Label("Current Crop");
        Label currentCropData = new Label("Crop ID: "  + "\nCrop Type: "  +
                "\nCrop Variety: " + "\nBushel Weight: ");

        Label lastCropLabel = new Label("Last Crop");
        Label lastCropData = new Label("Crop ID: "  + "\nCrop Type: "  +
                "\nCrop Variety: " + "\nBushel Weight: ");

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TODO: Bins UI Section
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //todo: Bin table formatting
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final Label binlabel = new Label("Bin Table");
        binlabel.setFont(new Font("Arial", 20));

        binTable.setEditable(true);

        TableColumn<GrainBin, String> binIDCol = new TableColumn<GrainBin, String>("Bin ID");
        binIDCol.setMinWidth(130);
        binIDCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, String>("ID"));

        TableColumn<GrainBin, String> binNameCol = new TableColumn<GrainBin, String>("Bin Name");
        binNameCol.setMinWidth(130);
        binNameCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, String>("binName"));

        TableColumn<GrainBin, String> binLocationCol = new TableColumn<GrainBin, String>("Bin Location");
        binLocationCol.setMinWidth(130);
        binLocationCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, String>("binLocation"));

        TableColumn<GrainBin, Integer> binSizeCol = new TableColumn<GrainBin, Integer>("Bin Size");
        binSizeCol.setMinWidth(70);
        binSizeCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Integer>("binSize"));

        TableColumn<GrainBin, Boolean> binHopperCol = new TableColumn<GrainBin, Boolean>("Bin Hopper");
        binHopperCol.setMinWidth(70);
        binHopperCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Boolean>("hopper"));

        TableColumn<GrainBin, String> binFanCol = new TableColumn<GrainBin, String>("Bin Fan");
        binFanCol.setMinWidth(70);
        binFanCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, String>("fan"));

        TableColumn<GrainBin, Integer> binBushelsCol = new TableColumn<GrainBin, Integer>("Bushels");
        binBushelsCol.setMinWidth(70);
        binBushelsCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Integer>("cropBushels"));

        TableColumn<GrainBin, Integer> binCropLbsCol = new TableColumn<GrainBin, Integer>("Crop Lbs");
        binCropLbsCol.setMinWidth(70);
        binCropLbsCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Integer>("cropLbs"));

        TableColumn<GrainBin, Boolean> binToughCol = new TableColumn<GrainBin, Boolean>("Tough");
        binToughCol.setMinWidth(70);
        binToughCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Boolean>("tough"));

        TableColumn<GrainBin, Boolean> binCleanCol = new TableColumn<GrainBin, Boolean>("Clean");
        binCleanCol.setMinWidth(70);
        binCleanCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Boolean>("clean"));

        binTable.setItems(grainBinData);
        binTable.getColumns().addAll(binIDCol, binNameCol,binLocationCol,binSizeCol,binHopperCol,binFanCol, binBushelsCol, binCropLbsCol, binToughCol, binCleanCol);


        VBox addBinBox = new VBox(30);
        Scene addBinScene = new Scene(addBinBox,300,250);

        TextField binIDInput = new TextField("Bin ID");
        TextField binNameInput = new TextField("Bin Name");
        TextField binSizeInput = new TextField("Bin Size");
        TextField binLocation = new TextField("Bin Location");
        CheckBox hopperInput = new CheckBox("Hopper?");
        CheckBox fanInput = new CheckBox("Fan?");
        Button submitBinInfo = new Button("Submit");


        addBinBox.getChildren().addAll(binIDInput, binNameInput, binSizeInput, binLocation, hopperInput, fanInput, submitBinInfo);
        Button addBin = new Button("Add Bin");
        addBin.setOnMouseClicked(e ->{
            stage.setScene(addBinScene);
        });

        submitBinInfo.setOnMouseClicked(e ->{
            binController.addBin(binIDInput.getText(),binNameInput.getText(), Integer.parseInt(binSizeInput.getText()), binLocation.getText(), hopperInput.isSelected(), fanInput.isSelected() );
            stage.setScene(binScene);
        });

        Button deleteBin = new Button("Delete Bin");
        deleteBin.setOnAction(event -> {
            cropController.deleteCropWhenDeleteBin(binTable.getSelectionModel().getSelectedItem().getDbID());
            binController.deleteBin(binTable.getSelectionModel().getSelectedItem().getID());
            binTable.refresh();
        });


        VBox addCropBox = new VBox(30);
        Scene addCropScene = new Scene(addCropBox,300,250);

        TextField binID = new TextField("Bin ID");
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
        TextField grainInput = new TextField("Grain");
        CheckBox inputBushels = new CheckBox("Measured in bushels?");
        CheckBox cleanCrop = new CheckBox("Crop is clean?");
        CheckBox toughCrop = new CheckBox("Crop is tough?");
        Button submitCropInfo = new Button("Submit");

        addCropBox.getChildren().addAll(binID, cropTypeLabel, cropTypeInput, newCropTypeLabel, newCropTypeInput, cropVarietyInput, bushelWeight, grainInput,
                inputBushels, cleanCrop, toughCrop, submitCropInfo);
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
                crop = new Crop(null, newCropTypeInput.getText(), cropVarietyInput.getValue(), Double.parseDouble(bushelWeight.getText()));
            }
            else {
                crop = new Crop(null, cropTypeInput.getValue(), cropVarietyInput.getValue(), Double.parseDouble(bushelWeight.getText()));
            }
            binController.addCrop(binID.getText(), crop, Integer.parseInt(grainInput.getText()), inputBushels.isSelected(), cleanCrop.isSelected(), toughCrop.isSelected());
            cropController.addCrop(crop);

            // clear the form
            cropTypeInput.setValue(null);
            cropVarietyInput.setValue(null);
            newCropTypeInput.clear();

            binTable.setItems(grainBinData);
            binTable.refresh();
            stage.setScene(binScene);
        });



        Button clearBin = new Button("Clear Bin");
        clearBin.setOnMouseClicked(event -> {
            binController.clearBin(binTable.getSelectionModel().getSelectedItem().getID());
            currentCropData.setText("Crop ID: " + "\nCrop Type: "  +
                    "\nCrop Variety: " + "\nBushel Weight: ");
            binTable.refresh();
        });



        binTable.setOnMouseClicked(event -> {
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2) {
                if (selectedData != null) {
                    if (selectedData.getCurrentCrop() != null){
                        currentCropData.setText("Crop ID: " + selectedData.getCurrentCrop().getDbId() + "\nCrop Type: " + selectedData.getCurrentCrop().getCropType() +
                                "\nCrop Variety: " + selectedData.getCurrentCrop().getCropVariety() + "\nBushel Weight: " + selectedData.getCurrentCrop().getBushelWeight());
                    }
                    if (selectedData.getLastCrop() != null) {
                        lastCropData.setText("Crop ID: " + selectedData.getLastCrop().getDbId() + "\nCrop Type: " + selectedData.getLastCrop().getCropType() +
                                "\nCrop Variety: " + selectedData.getLastCrop().getCropVariety() + "\nBushel Weight: " + selectedData.getLastCrop().getBushelWeight());
                    }
                    stage.setScene(binCropScene);
                }
            }});


        Button cropToBin = new Button("Back To Bin");
        cropToBin.setOnMouseClicked(e -> {
            currentCropData.setText("Crop ID: " + "\nCrop Type: "  +
                    "\nCrop Variety: " + "\nBushel Weight: ");
            lastCropData.setText("Crop ID: " + "\nCrop Type: "  +
                    "\nCrop Variety: " + "\nBushel Weight: ");
            stage.setScene(binScene);
        });


        binCropPage.getChildren().addAll(cropToBin, currentCropLabel, currentCropData, lastCropLabel, lastCropData);

        Button binsBackToMain = new Button("Back To Main");
        binsBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });



        HBox binFunctionBar = new HBox();
        binFunctionBar.getChildren().addAll(addBin, deleteBin, addCrop, clearBin, binsBackToMain);

        binPage.getChildren().addAll(binFunctionBar,binlabel, binTable);
        this.getChildren().addAll(binPage);
    }

    public void setStageMenu(Stage stage, Scene MenuScene, Scene binScene){
        this.stage = stage;
        this.MenuScene = MenuScene;
        this.binScene = binScene;
    }

    @Override
    public void modelChanged() {

    }
}
