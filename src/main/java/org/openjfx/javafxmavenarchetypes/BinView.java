package org.openjfx.javafxmavenarchetypes;

import control.BinControl;
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
    private BinControl binController = new BinControl();
    private ObservableList<GrainBin> grainBinData = binController.binList;

    private TableView<Crop> currentCropTable = new TableView<>();
    private ObservableList<Crop> currentCropData = FXCollections.observableArrayList();
    private TableView<Crop> lastCropTable = new TableView<>();
    private ObservableList<Crop> lastCropData = FXCollections.observableArrayList();

    private final int binMaxCapacity = 20;

    public BinView(){
        VBox binCropPage = new VBox(15);
        Scene binCropScene = new Scene(binCropPage, 300, 250);

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

        TableColumn<GrainBin, Boolean> binToughCol = new TableColumn<GrainBin, Boolean>("Tough Crop?");
        binToughCol.setMinWidth(100);
        binToughCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Boolean>("tough"));

        TableColumn<GrainBin, Boolean> binCleanCol = new TableColumn<GrainBin, Boolean>("Clean Crop?");
        binCleanCol.setMinWidth(100);
        binCleanCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Boolean>("clean"));

        binTable.setItems(grainBinData);
        binTable.getColumns().addAll(binIDCol, binNameCol, binLocationCol, binSizeCol, binBushelsCol, binCropLbsCol, cropTypeCol, binHopperCol, binFanCol, binToughCol, binCleanCol);


        VBox addBinBox = new VBox(15);
        Scene addBinScene = new Scene(addBinBox,300,250);

        Label addBinPageTitle = new Label("Add New Bin");
        addBinPageTitle.getStyleClass().add("page-label");

        Label binNameInputLabel = new Label("Bin Name:");
        binNameInputLabel.getStyleClass().add("text-field-label");
        TextField binNameInput = new TextField();

        Label binSizeInputLabel = new Label("Bin Size (bushel):");
        binSizeInputLabel.getStyleClass().add("text-field-label");
        TextField binSizeInput = new TextField();

        Label binLocationLabel = new Label("Bin Location");
        binLocationLabel.getStyleClass().add("text-field-label");
        TextField binLocation = new TextField();

        CheckBox hopperInput = new CheckBox("Hopper?");
        CheckBox fanInput = new CheckBox("Fan?");
        Button submitBinInfo = new Button("Submit");
        Button cancelAddBin = new Button("Cancel");
        cancelAddBin.setOnMouseClicked(event -> {
            stage.setScene(binScene);
        });

        Label space1 = new Label("\t\t");
        HBox submitAndCancelBox1 = new HBox(submitBinInfo, space1, cancelAddBin);


        addBinBox.getChildren().addAll(addBinPageTitle, binNameInputLabel, binNameInput, binSizeInputLabel, binSizeInput,
                binLocationLabel, binLocation, hopperInput, fanInput, submitAndCancelBox1);
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
                showErrorPopup( "Please enter valid bin size.");
            }
            if (binSize != -1){
                if (binSize > binMaxCapacity){
                    System.out.println("Maximum bin capacity is 20 bushels");
                    showErrorPopup("Maximum bin capacity is 20 bushels");
                } else {
                    binController.addBin(binNameInput.getText(), binSize, binLocation.getText(), hopperInput.isSelected(), fanInput.isSelected() );
                    stage.setScene(binScene);
                }
            }
            binNameInput.setText("");
            binSizeInput.setText("");
            binLocation.setText("");
            hopperInput.setSelected(false);
            fanInput.setSelected(false);
        });

        Button deleteBin = new Button("Delete Bin");
        deleteBin.setOnAction(event -> {
            if (binTable.getSelectionModel().getSelectedItem() != null){
                binController.deleteBin(binTable.getSelectionModel().getSelectedItem().getDbId());
                binTable.refresh();
            }
            else {
                System.out.println("Select a Bin");
                showErrorPopup( "Maximum bin capacity is 20 bushels");
            }
        });


        VBox addCropBox = new VBox(15);
        Scene addCropScene = new Scene(addCropBox,300,250);
        TextField addCropBinID = new TextField();

        Label addCropPageTitle = new Label();
        Label cropTypeLabel = new Label("Select Crop Type");
        cropTypeLabel.getStyleClass().add("text-field-label");
        ComboBox<String> cropTypeInput = new ComboBox<>();
        cropTypeInput.getItems().addAll(binController.cropType);

        Label newCropTypeLabel = new Label("Or Add New Crop Type");
        newCropTypeLabel.getStyleClass().add("text-field-label");
        TextField newCropTypeInput = new TextField();

        Label space2 = new Label("\t");
        Label space3 = new Label("\t\t");
        Label space20 = new Label("\t");
        HBox groupBox1 = new HBox(cropTypeLabel, space2, cropTypeInput, space3, newCropTypeLabel, space20, newCropTypeInput );
//        HBox groupBox2 = new HBox(newCropTypeLabel, space3, newCropTypeInput);

        Label cropVarietyLabel = new Label("Select Crop Variety");
        cropVarietyLabel.getStyleClass().add("text-field-label");
        ComboBox<String> cropVarietyInput = new ComboBox<>();
        cropVarietyInput.getItems().addAll("LibertyLink", "RoundupReady", "Navigator", "ClearField", "All Other Grains");

        binController.cropType.addListener((ListChangeListener<String>) change -> {
            cropTypeInput.setItems(binController.cropType);
        });

        Label bushelWeighLabel = new Label("Bushel Weight (lbs):");
        bushelWeighLabel.getStyleClass().add("text-field-label");
        TextField bushelWeight = new TextField();

        Label grainInputLabel = new Label("Grain ie: 10");
        grainInputLabel.getStyleClass().add("text-field-label");
        TextField grainInput = new TextField();
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

        addCropBox.getChildren().addAll(addCropPageTitle, groupBox1, cropVarietyLabel, cropVarietyInput, bushelWeighLabel,
                bushelWeight, grainInputLabel, grainInput, groupBox3, submitAndCancelBox2);


        Button addCrop = new Button("Add Crop");
        addCrop.setOnMouseClicked(e ->{
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                if (selectedData.getBinSize() <= selectedData.getCropBushels()){
                    addCropBinID.setText(selectedData.getDbId().toString());
                    addCropPageTitle.setText("Add Crop to bin named (" + selectedData.getBinName() + ")");
                    addCropPageTitle.getStyleClass().add("page-label");
                    stage.setScene(addCropScene);
                } else {
                    System.out.println("Bin is full");
                    showErrorPopup("Bin is full");
                }
            }
            else{
                System.out.println("Select a Bin");
                showErrorPopup("Select a Bin");
            }

        });

        submitCropInfo.setOnMouseClicked(e ->{
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            Crop selectedCrop = selectedData.getCurrentCrop();
            Crop crop;
            int grain = -1;
            double bWeight = -1.0;
            try {
                grain = Integer.parseInt(grainInput.getText());
                bWeight = Double.parseDouble(bushelWeight.getText());
            } catch (Exception error){
                System.out.println("Invalid grain/bushel input");
                showErrorPopup("Invalid grain/bushel input");
            }

            if (!newCropTypeInput.getText().isEmpty()) {
                binController.addCropType(newCropTypeInput.getText());
            }
            String cropType;
            if (cropTypeInput.getValue() != null) {
                cropType = cropTypeInput.getValue();
            } else {
                cropType = newCropTypeInput.getText();
            }

            if (grain != -1 && bWeight != -1.0) {
                if (selectedData.isEmpty()) {
                    if (inputBushels.isSelected() && grain + selectedData.getCropBushels() <= selectedData.getBinSize() || !inputBushels.isSelected() && lbsToBushels(grain, bWeight) + selectedData.getCropBushels() <= selectedData.getBinSize()) {
                        if (selectedCrop == null) {
                            crop = binController.makeCrop(new ObjectId(addCropBinID.getText()), grain, inputBushels.isSelected(), null, cropType, cropVarietyInput.getValue(), bWeight);
                            binController.addCrop(new ObjectId(addCropBinID.getText()), crop, grain, inputBushels.isSelected(), cleanCrop.isSelected(), toughCrop.isSelected());
                        } else {
                            if (selectedCrop.getCropType().equals(cropType) && selectedCrop.getCropVariety().equals(cropVarietyInput.getValue()) && selectedCrop.getBushelWeight() == bWeight) {
                                binController.addCrop(new ObjectId(addCropBinID.getText()), selectedCrop, grain, inputBushels.isSelected(), cleanCrop.isSelected(), toughCrop.isSelected());

                            } else {
                                crop = binController.makeCrop(new ObjectId(addCropBinID.getText()), grain, inputBushels.isSelected(), null, cropType, cropVarietyInput.getValue(), bWeight);
                                binController.addCrop(new ObjectId(addCropBinID.getText()), crop, grain, inputBushels.isSelected(), cleanCrop.isSelected(), toughCrop.isSelected());

                            }
                        }
                        cropTypeInput.setValue(null);
                        newCropTypeInput.setText("");
                        cropVarietyInput.setValue(null);
                        bushelWeight.setText("");
                        grainInput.setText("");
                        inputBushels.setSelected(false);
                        cleanCrop.setSelected(false);
                        toughCrop.setSelected(false);
                        binTable.setItems(grainBinData);
                        binTable.refresh();
                        stage.setScene(binScene);
                    } else {
                        System.out.println("Maximum capacity exceeded");
                        showErrorPopup("Maximum capacity exceeded");
                    }
                } else {
                    if (selectedCrop.getCropType().equals(cropType) && selectedCrop.getCropVariety().equals(cropVarietyInput.getValue()) && selectedCrop.getBushelWeight() == bWeight) {
                        if (inputBushels.isSelected() && grain + selectedData.getCropBushels() <= selectedData.getBinSize() || !inputBushels.isSelected() && lbsToBushels(grain, bWeight) + selectedData.getCropBushels() <= selectedData.getBinSize()) {
                            binController.addCrop(new ObjectId(addCropBinID.getText()), selectedCrop, grain, inputBushels.isSelected(), cleanCrop.isSelected(), toughCrop.isSelected());
                            cropTypeInput.setValue(null);
                            newCropTypeInput.setText("");
                            cropVarietyInput.setValue(null);
                            bushelWeight.setText("");
                            grainInput.setText("");
                            inputBushels.setSelected(false);
                            cleanCrop.setSelected(false);
                            toughCrop.setSelected(false);
                            binTable.setItems(grainBinData);
                            binTable.refresh();
                            stage.setScene(binScene);
                        } else {
                            System.out.println("Maximum capacity exceeded");
                            showErrorPopup("Maximum capacity exceeded");
                        }
                    } else {
                        System.out.println("Can not add a different crop to non-empty bin");
                        showErrorPopup("Can not add a different crop to non-empty bin");
                    }
                }
            }
        });



        Button clearBin = new Button("Clear Bin");
        clearBin.setOnMouseClicked(event -> {
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                if (!selectedData.isEmpty()){
                    binController.clearBin(binTable.getSelectionModel().getSelectedItem().getDbId());
                    binTable.refresh();
                } else {
                    System.out.println("Bin is empty");
                    showErrorPopup("Bin is empty");
                }
            }
            else {
                System.out.println("Select a bin");
                showErrorPopup("Select a bin");
            }
        });


        // current crop table
        Label currentCropLabel = new Label("Current Crop");
        currentCropLabel.getStyleClass().add("page-label");

        TableColumn<Crop, ObjectId> currentCropIDCol = new TableColumn<Crop, ObjectId>("Crop ID");
        currentCropIDCol.setPrefWidth(70);
        currentCropIDCol.setCellValueFactory(
                new PropertyValueFactory<Crop, ObjectId>("DbId"));

        TableColumn<Crop, String> currentCropTypeCol = new TableColumn<Crop, String>("Crop Type");
        currentCropTypeCol.setMinWidth(70);
        currentCropTypeCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropType"));

        TableColumn<Crop, String> currentCropVarietyCol = new TableColumn<Crop, String>("Crop Variety");
        currentCropVarietyCol.setMinWidth(110);
        currentCropVarietyCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropVariety"));

        TableColumn<Crop, Double> currentBushelWeightCol = new TableColumn<Crop, Double>("Bushel Weight");
        currentBushelWeightCol.setMinWidth(110);
        currentBushelWeightCol.setCellValueFactory(
                new PropertyValueFactory<Crop, Double>("bushelWeight"));

        currentCropTable.getColumns().addAll(currentCropIDCol, currentCropTypeCol, currentCropVarietyCol, currentBushelWeightCol);


        // last crop table
        Label lastCropLabel = new Label("Last Crop");
        lastCropLabel.getStyleClass().add("page-label");

        TableColumn<Crop, ObjectId> lastCropIDCol = new TableColumn<Crop, ObjectId>("Crop ID");
        lastCropIDCol.setMinWidth(70);
        lastCropIDCol.setCellValueFactory(
                new PropertyValueFactory<Crop, ObjectId>("DbId"));

        TableColumn<Crop, String> lastCropTypeCol = new TableColumn<Crop, String>("Crop Type");
        lastCropTypeCol.setMinWidth(70);
        lastCropTypeCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropType"));

        TableColumn<Crop, String> lastCropVarietyCol = new TableColumn<Crop, String>("Crop Variety");
        lastCropVarietyCol.setMinWidth(110);
        lastCropVarietyCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropVariety"));

        TableColumn<Crop, Double> lastBushelWeightCol = new TableColumn<Crop, Double>("Bushel Weight");
        lastBushelWeightCol.setMinWidth(110);
        lastBushelWeightCol.setCellValueFactory(
                new PropertyValueFactory<Crop, Double>("bushelWeight"));

        lastCropTable.getColumns().addAll(lastCropIDCol, lastCropTypeCol, lastCropVarietyCol, lastBushelWeightCol);

        binTable.setOnMouseClicked(event -> {
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2) {
                if (selectedData.getCurrentCrop() != null){
                    currentCropData.clear();
                    currentCropData.add(selectedData.getCurrentCrop());
                } else {
                    currentCropData.clear();
                }
                if (selectedData.getLastCrop() != null){
                    lastCropData.clear();
                    lastCropData.add(selectedData.getLastCrop());
                } else {
                    lastCropData.clear();
                }
                currentCropTable.setItems(currentCropData);
                currentCropTable.refresh();

                lastCropTable.setItems(lastCropData);
                lastCropTable.refresh();

                stage.setScene(binCropScene);

            }});


        Button cropToBin = new Button("Back To Bin");
        cropToBin.setOnMouseClicked(e -> {
            stage.setScene(binScene);
        });


        binCropPage.getChildren().addAll(cropToBin, currentCropLabel, currentCropTable, lastCropLabel, lastCropTable);

        Button binsBackToMain = new Button("Back To Main");
        binsBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });

        Button viewBin = new Button("View Bin");
        viewBin.setOnMouseClicked(event -> {
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (selectedData != null) {
                stage.setScene(binCropScene);
            } else {
                System.out.println("Select a Bin");
                showErrorPopup("Select a bin");
            }
        });

        VBox unloadPage = new VBox(15);
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
            int grain = -1;
            try {
                grain = Integer.parseInt(unloadGrainInput.getText());
            } catch (Exception b){
                System.out.println("Invalid grain input");
                showErrorPopup("Invalid grain input");
            }
            if (grain != -1) {
                binController.unload(new ObjectId(unloadBinID.getText()), Integer.parseInt(unloadGrainInput.getText()), unloadInputBushels.isSelected());
                stage.setScene(binScene);
                unloadGrainInput.setText("");
                binTable.refresh();
                stage.setScene(binScene);
            }
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
                    showErrorPopup("Bin is empty");
                }
            } else {
                System.out.println("Select a bin");
                showErrorPopup("Select a bin");
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

    private void showErrorPopup(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText("INVALID");
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Double lbsToBushels(double lbs, double bushelWeight){
        return (lbs/bushelWeight);
    }
    @Override
    public void modelChanged() {

    }
}
