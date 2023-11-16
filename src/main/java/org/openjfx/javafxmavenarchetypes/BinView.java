package org.openjfx.javafxmavenarchetypes;

import control.BinControl;
import control.CropControl;
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
import org.InitialFarm.GrainBin;
import org.bson.types.ObjectId;

public class BinView extends StackPane implements ModelSubscriber {

    private VBox binPage = new VBox();
    private Scene MenuScene ;

    private Scene binScene;
    private Stage stage;

    private TableView<GrainBin> binTable = new TableView<>();
    BinControl binController = new BinControl();
    private ObservableList<GrainBin> grainBinData = binController.binList;

    TableView<Crop> cropTable = new TableView<>();
    CropControl cropController = new CropControl();
    ObservableList<Crop> cropData = cropController.currentCrop;

    TableView<Crop> lastCropTable = new TableView<>();
    ObservableList<Crop> lastCropData = cropController.lastCrop;

    public BinView(){
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
            binController.deleteBin(binTable.getSelectionModel().getSelectedItem().getID());
            binTable.refresh();
        });


        VBox addCropBox = new VBox(30);
        Scene addCropScene = new Scene(addCropBox,300,250);

        TextField binID = new TextField("Bin ID");
        TextField cropID = new TextField("Crop ID");
        Label cropTypeLabel = new Label("Add New Crop Type");
        TextField newCropTypeInput = new TextField();

        ComboBox<String> cropVarietyInput = new ComboBox<>();
        cropVarietyInput.getItems().addAll(cropController.cropVariety);

        cropController.cropVariety.addListener((ListChangeListener<String>) change -> {
            cropVarietyInput.setItems(cropController.cropVariety);
        });

        TextField bushelWeight = new TextField("Bushel Weight");
        TextField grainInput = new TextField("Grain");
        CheckBox inputBushels = new CheckBox("Measured in bushels?");
        CheckBox cleanCrop = new CheckBox("Crop is clean?");
        CheckBox toughCrop = new CheckBox("Crop is tough?");
        Button submitCropInfo = new Button("Submit");

        addCropBox.getChildren().addAll(binID, cropID, cropVarietyInput, cropTypeLabel, newCropTypeInput, bushelWeight, grainInput,
                inputBushels, cleanCrop, toughCrop, submitCropInfo);
        Button addCrop = new Button("Add Crop");
        addCrop.setOnMouseClicked(e ->{
            stage.setScene(addCropScene);
        });

        submitCropInfo.setOnMouseClicked(e ->{
            Crop crop;
            if (!newCropTypeInput.getText().isEmpty()){
                cropController.addCropVariety(newCropTypeInput.getText());
            }
            if (cropVarietyInput.getValue() == null){
                crop = new Crop(cropID.getText(), newCropTypeInput.getText(), newCropTypeInput.getText(), Float.parseFloat(bushelWeight.getText()));
            }
            else {
                crop = new Crop(cropID.getText(), cropVarietyInput.getValue(), cropVarietyInput.getValue(), Float.parseFloat(bushelWeight.getText()));
            }
            binController.addCrop(binID.getText(), crop, Integer.parseInt(grainInput.getText()), inputBushels.isSelected(), cleanCrop.isSelected(), toughCrop.isSelected());
            cropController.addCrop(crop);

            // clear the form
            cropVarietyInput.setValue(null);
            newCropTypeInput.clear();

            stage.setScene(binScene);
        });

        VBox binCropPage = new VBox(30);
        Scene binCropScene = new Scene(binCropPage,300,250);



        binTable.setOnMouseClicked(event -> {
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2) {
                if (selectedData != null) {
                    cropController.setCurrentCrop(selectedData.getCurrentCrop().getID());
                    cropTable.setItems(cropData);
                    if (selectedData.getLastCrop() != null) {
                        cropController.setLastCrop(selectedData.getLastCrop().getID());
                    }
                    lastCropTable.setItems(lastCropData);
                    stage.setScene(binCropScene);
                }
            }});



        TableColumn<Crop, String> cropIDCol = new TableColumn<Crop, String>("Crop ID");
        cropIDCol.setMinWidth(130);
        cropIDCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("ID"));

        TableColumn<Crop, String> cropTypeCol = new TableColumn<Crop, String>("Crop Type");
        cropTypeCol.setMinWidth(130);
        cropTypeCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropType"));

        TableColumn<Crop, String> cropVarietyCol = new TableColumn<Crop, String>("Crop Variety");
        cropVarietyCol.setMinWidth(130);
        cropVarietyCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropVariety"));

        TableColumn<Crop, Float> bushelWeightCol = new TableColumn<Crop, Float>("Bushel Weight");
        bushelWeightCol.setMinWidth(130);
        bushelWeightCol.setCellValueFactory(
                new PropertyValueFactory<Crop, Float>("bushelWeight"));

        cropTable.getColumns().addAll(cropIDCol, cropTypeCol, cropVarietyCol, bushelWeightCol);


        TableColumn<Crop, String> cropIDCol2 = new TableColumn<Crop, String>("Crop ID");
        cropIDCol.setMinWidth(130);
        cropIDCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("ID"));

        TableColumn<Crop, String> cropTypeCol2 = new TableColumn<Crop, String>("Crop Type");
        cropTypeCol.setMinWidth(130);
        cropTypeCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropType"));

        TableColumn<Crop, String> cropVarietyCol2 = new TableColumn<Crop, String>("Crop Variety");
        cropVarietyCol.setMinWidth(130);
        cropVarietyCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropVariety"));

        TableColumn<Crop, Float> bushelWeightCol2 = new TableColumn<Crop, Float>("Bushel Weight");
        bushelWeightCol.setMinWidth(130);
        bushelWeightCol.setCellValueFactory(
                new PropertyValueFactory<Crop, Float>("bushelWeight"));
        lastCropTable.getColumns().addAll(cropIDCol2, cropTypeCol2, cropVarietyCol2, bushelWeightCol2);


        Button cropToBin = new Button("Back To Bin");
        cropToBin.setOnMouseClicked(e -> {
            cropData.clear();
            cropTable.refresh();
            stage.setScene(binScene);
        });
        Label currentCropLabel = new Label("Current Crop");
        Label lastCropLabel = new Label("Last Crop");
        binCropPage.getChildren().addAll(cropToBin, currentCropLabel, cropTable, lastCropLabel, lastCropTable);

        Button binsBackToMain = new Button("Back To Main");
        binsBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });



        HBox binFunctionBar = new HBox();
        binFunctionBar.getChildren().addAll(addBin, deleteBin, addCrop, binsBackToMain);

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
