package org.openjfx.javafxmavenarchetypes;

import control.BinControl;
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

    private final int binMaxCapacity = 20;

    public BinView(){
        VBox binCropPage = new VBox(30);
        Scene binCropScene = new Scene(binCropPage, 300, 250);

        Label currentCropLabel = new Label("Current Crop");
        currentCropLabel.getStyleClass().add("page-label");
        Label currentCropData = new Label("Crop ID: "  + "\nCrop Type: "  +
                "\nCrop Variety: " + "\nBushel Weight: ");

        Label lastCropLabel = new Label("Last Crop");
        lastCropLabel.getStyleClass().add("page-label");
        Label lastCropData = new Label("Crop ID: "  + "\nCrop Type: "  +
                "\nCrop Variety: " + "\nBushel Weight: ");


        binTable.setEditable(true);

        TableColumn<GrainBin, ObjectId> binIDCol = new TableColumn<GrainBin, ObjectId>("Bin ID");
        binIDCol.setMinWidth(70);
        binIDCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, ObjectId>("DbId"));

        TableColumn<GrainBin, String> binNameCol = new TableColumn<GrainBin, String>("Bin Name");
        binNameCol.setMinWidth(100);
        binNameCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, String>("binName"));

        TableColumn<GrainBin, String> binLocationCol = new TableColumn<GrainBin, String>("Bin Location");
        binLocationCol.setMinWidth(100);
        binLocationCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, String>("binLocation"));

        TableColumn<GrainBin, Integer> binSizeCol = new TableColumn<GrainBin, Integer>("Bin Size (bushel)");
        binSizeCol.setMinWidth(130);
        binSizeCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Integer>("binSize"));

        TableColumn<GrainBin, Boolean> binHopperCol = new TableColumn<GrainBin, Boolean>("Bin Hopper");
        binHopperCol.setMinWidth(100);
        binHopperCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Boolean>("hopper"));

        TableColumn<GrainBin, String> binFanCol = new TableColumn<GrainBin, String>("Bin Fan");
        binFanCol.setMinWidth(100);
        binFanCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, String>("fan"));

        TableColumn<GrainBin, String> cropTypeCol = new TableColumn<GrainBin, String>("Crop Type");
        cropTypeCol.setMinWidth(100);
        cropTypeCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, String>("currentCropType"));

        TableColumn<GrainBin, Integer> binBushelsCol = new TableColumn<GrainBin, Integer>("Bushels");
        binBushelsCol.setMinWidth(100);
        binBushelsCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Integer>("cropBushels"));

        TableColumn<GrainBin, Integer> binCropLbsCol = new TableColumn<GrainBin, Integer>("Crop (lbs)");
        binCropLbsCol.setMinWidth(100);
        binCropLbsCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Integer>("cropLbs"));

        TableColumn<GrainBin, Boolean> binToughCol = new TableColumn<GrainBin, Boolean>("Tough");
        binToughCol.setMinWidth(100);
        binToughCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Boolean>("tough"));

        TableColumn<GrainBin, Boolean> binCleanCol = new TableColumn<GrainBin, Boolean>("Clean");
        binCleanCol.setMinWidth(100);
        binCleanCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Boolean>("clean"));

        binTable.setItems(grainBinData);
        binTable.getColumns().addAll(binIDCol, binNameCol,binLocationCol,binSizeCol,binHopperCol,binFanCol, cropTypeCol, binBushelsCol, binCropLbsCol, binToughCol, binCleanCol);


        VBox addBinBox = new VBox(30);
        Scene addBinScene = new Scene(addBinBox,300,250);

        Label addBinPageTitle = new Label("Add New Bin");
        addBinPageTitle.getStyleClass().add("page-label");
        TextField binNameInput = new TextField("Bin Name");
        TextField binSizeInput = new TextField("Bin Size (bushel) ie: 20");
        TextField binLocation = new TextField("Bin Location");
        CheckBox hopperInput = new CheckBox("Hopper?");
        CheckBox fanInput = new CheckBox("Fan?");
        Button submitBinInfo = new Button("Submit");
        Button cancelAddBin = new Button("Cancel");
        cancelAddBin.setOnMouseClicked(event -> {
            stage.setScene(binScene);
        });

        Label space1 = new Label("\t\t");
        HBox submitAndCancelBox1 = new HBox(submitBinInfo, space1, cancelAddBin);


        addBinBox.getChildren().addAll(addBinPageTitle ,binNameInput, binSizeInput, binLocation, hopperInput, fanInput, submitAndCancelBox1);
        Button addBin = new Button("Add Bin");
        addBin.setOnMouseClicked(e ->{
            stage.setScene(addBinScene);
        });

        submitBinInfo.setOnMouseClicked(e ->{
            int binSize = -1;
            try {
                binSize = Integer.parseInt(binSizeInput.getText());
            } catch (Exception error){
                System.out.println("Invalid bin size");
            }
            if (binSize != -1){
                if (binSize > binMaxCapacity){
                    System.out.println("Maximum bin capacity is 20 bushels");
                } else {
                    binController.addBin(binNameInput.getText(), binSize, binLocation.getText(), hopperInput.isSelected(), fanInput.isSelected() );
                    stage.setScene(binScene);
                }
            }
        });

        Button deleteBin = new Button("Delete Bin");
        deleteBin.setOnAction(event -> {
            if (binTable.getSelectionModel().getSelectedItem() != null){
                binController.deleteBin(binTable.getSelectionModel().getSelectedItem().getDbId());
                binTable.refresh();
            }
            else {
                System.out.println("Select a Bin");
            }
        });


        VBox addCropBox = new VBox(30);
        Scene addCropScene = new Scene(addCropBox,300,250);
        TextField addCropBinID = new TextField();

        Label addCropPageTitle = new Label();
        Label cropTypeLabel = new Label("Select Crop Type");
        ComboBox<String> cropTypeInput = new ComboBox<>();
        cropTypeInput.getItems().addAll(binController.cropType);

        Label newCropTypeLabel = new Label("Or Add New Crop Type");
        TextField newCropTypeInput = new TextField();

        Label space2 = new Label("\t\t");
        Label space3 = new Label("\t\t");
        HBox groupBox1 = new HBox(cropTypeLabel, space2, cropTypeInput);
        HBox groupBox2 = new HBox(newCropTypeLabel, space3, newCropTypeInput);

        Label cropVarietyLabel = new Label("Select Crop Variety");
        ComboBox<String> cropVarietyInput = new ComboBox<>();
        cropVarietyInput.getItems().addAll("LibertyLink", "RoundupReady", "Navigator", "ClearField", "All Other Grains");

        binController.cropType.addListener((ListChangeListener<String>) change -> {
            cropTypeInput.setItems(binController.cropType);
        });

        TextField bushelWeight = new TextField("Bushel Weight (lbs)");
        TextField grainInput = new TextField("Grain ie: 10");
        CheckBox inputBushels = new CheckBox("Crop is in bushels?");
        CheckBox cleanCrop = new CheckBox("Crop is clean?");
        CheckBox toughCrop = new CheckBox("Crop is tough?");
        Label space10 = new Label("\t");
        Label space11 = new Label("\t");
        HBox groupBox3 = new HBox(inputBushels, space10, cleanCrop, space11, toughCrop);
        Button submitCropInfo = new Button("Submit");
        Button cancelAddCrop = new Button("Cancel");
        cancelAddCrop.setOnMouseClicked(event -> {
            stage.setScene(binScene);
        });

        Label space4 = new Label("\t\t");
        HBox submitAndCancelBox2 = new HBox(submitCropInfo, space4, cancelAddCrop);

        addCropBox.getChildren().addAll(addCropPageTitle, groupBox1, groupBox2, cropVarietyLabel, cropVarietyInput, bushelWeight, grainInput,
                groupBox3, submitAndCancelBox2);


        Button addCrop = new Button("Add Crop");
        addCrop.setOnMouseClicked(e ->{
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                addCropBinID.setText(selectedData.getDbId().toString());
                addCropPageTitle.setText("Add Crop to bin named (" + selectedData.getBinName() + ")");
                addCropPageTitle.getStyleClass().add("page-label");
                stage.setScene(addCropScene);
            }
            else{
                System.out.println("Select a Bin");
            }

        });

        submitCropInfo.setOnMouseClicked(e ->{
            int grain = -1;
            double bWeight = -1.0;
            try {
                grain = Integer.parseInt(grainInput.getText());
                bWeight = Double.parseDouble(bushelWeight.getText());
            } catch (Exception error){
                System.out.println("Invalid grain/bushel input");
            }
            if (grain != -1 && bWeight != -1.0) {
                GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
                Crop crop = null;
                if (!newCropTypeInput.getText().isEmpty()) {
                    binController.addCropType(newCropTypeInput.getText());
                }
                if (cropTypeInput.getValue() == null) {
                    if (!selectedData.getCurrentCropType().equals(newCropTypeInput.getText()) && !selectedData.isEmpty()){
                        System.out.println("Can not add different crop type to non-empty bin");
                    } else {
                        crop = binController.makeCrop(new ObjectId(addCropBinID.getText()), Integer.parseInt(grainInput.getText()), inputBushels.isSelected(), null, newCropTypeInput.getText(), cropVarietyInput.getValue(), bWeight);
                    }
                } else {
                    if (!selectedData.getCurrentCropType().equals(cropTypeInput.getValue()) && !selectedData.isEmpty()){
                        System.out.println("Can not add different crop type to non-empty bin");
                    } else {
                        crop = binController.makeCrop(new ObjectId(addCropBinID.getText()), Integer.parseInt(grainInput.getText()), inputBushels.isSelected(), null, cropTypeInput.getValue(), cropVarietyInput.getValue(), bWeight);
                    }
                }
                if (crop != null) {
                    binController.addCrop(new ObjectId(addCropBinID.getText()), crop, grain, inputBushels.isSelected(), cleanCrop.isSelected(), toughCrop.isSelected());
                    // clear the form
                    cropTypeInput.setValue(null);
                    cropVarietyInput.setValue(null);
                    newCropTypeInput.clear();

                    binTable.setItems(grainBinData);
                    binTable.refresh();
                    stage.setScene(binScene);
                }
            }
        });



        Button clearBin = new Button("Clear Bin");
        clearBin.setOnMouseClicked(event -> {
            if (binTable.getSelectionModel().getSelectedItem() != null){
                binController.clearBin(binTable.getSelectionModel().getSelectedItem().getDbId());
                currentCropData.setText("Crop ID: " + "\nCrop Type: "  +
                        "\nCrop Variety: " + "\nBushel Weight: ");
                binTable.refresh();
            }
            else {
                System.out.println("Select a Bin");
            }
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

        Button viewBin = new Button("View Bin");
        viewBin.setOnMouseClicked(event -> {
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
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
            } else {
                System.out.println("Select a Bin");
            }
        });

        VBox unloadPage = new VBox(30);
        Scene unloadScene = new Scene(unloadPage);

        Label unloadCropPageTitle = new Label();
        TextField unloadBinID = new TextField();
        TextField unloadGrainInput = new TextField("Grain");
        CheckBox unloadInputBushels = new CheckBox("Crop is in bushels?");
        Button submitUnloadInfo = new Button("Submit");
        Button cancelUnloadCrop = new Button("Cancel");
        cancelUnloadCrop.setOnMouseClicked(event -> {
            stage.setScene(binScene);
        });

        Label space5 = new Label("\t\t");
        HBox submitAndCancelBox3 = new HBox(submitUnloadInfo, space5, cancelUnloadCrop);

        submitUnloadInfo.setOnMouseClicked(event -> {
            if (!unloadGrainInput.getText().isEmpty()){
                binController.unload(new ObjectId(unloadBinID.getText()), Integer.parseInt(unloadGrainInput.getText()), unloadInputBushels.isSelected());
                stage.setScene(binScene);
            } else {
                System.out.println("Enter amount of grain to unload");
            }
            unloadGrainInput.setText("");
            binTable.refresh();
            stage.setScene(binScene);
        });

        Button unload = new Button("Unload");
        unload.setOnMouseClicked(event -> {
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                if (!selectedData.isEmpty()){
                    unloadBinID.setText(selectedData.getDbId().toString());
                    unloadCropPageTitle.setText("Unload crop from bin named (" + selectedData.getBinName() + ")");
                    unloadCropPageTitle.setFont(new Font("Arial", 20));
                    unloadCropPageTitle.setStyle("-fx-font-weight: bold;");
                    stage.setScene(unloadScene);
                } else {
                    System.out.println("Bin is empty");
                }

            } else {
                System.out.println("Select a bin");
            }

        });

        unloadPage.getChildren().addAll(unloadCropPageTitle, unloadGrainInput, unloadInputBushels, submitAndCancelBox3);


        HBox binFunctionBar = new HBox();
        binFunctionBar.getChildren().addAll(addBin, deleteBin, addCrop, viewBin, unload, clearBin, binsBackToMain);

        binPage.getChildren().addAll(binFunctionBar, binTable);

        //css
        addBinScene.getStylesheets().add(getClass().getClassLoader().getResource("bin.css").toExternalForm());
        binCropScene.getStylesheets().add(getClass().getClassLoader().getResource("bin.css").toExternalForm());
        addCropScene.getStylesheets().add(getClass().getClassLoader().getResource("bin.css").toExternalForm());
        unloadScene.getStylesheets().add(getClass().getClassLoader().getResource("bin.css").toExternalForm());
        this.getChildren().addAll(binPage);
    }

    public void setStageMenu(Stage stage, Scene MenuScene, Scene binScene){
        this.stage = stage;
        this.MenuScene = MenuScene;
        this.binScene = binScene;
        binScene.getStylesheets().add(getClass().getClassLoader().getResource("bin.css").toExternalForm());
    }

    @Override
    public void modelChanged() {

    }
}
