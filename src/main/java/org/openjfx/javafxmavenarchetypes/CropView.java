package org.openjfx.javafxmavenarchetypes;


import control.CropControl;
import javafx.collections.FXCollections;
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


import java.time.LocalDate;

public class CropView extends StackPane implements ModelSubscriber {

    private VBox container = new VBox();

    private Scene fieldScene;

    private Stage stage;
    private int year;

    public CropControl cropController = new CropControl();


    private TableView<Crop> grainTable = new TableView<Crop>();
//    private final ObservableList<Crop> cropData =
//            FXCollections.observableArrayList(
//                    new Crop(null, "Canola", "LibertyLink", 55),
//                    new Crop(null, "Canola", "RoundupReady", 54),
//                    new Crop(null, "Durum", "Navigator", 60),
//                    new Crop(null, "Red Lentil", "Clearfield", 58),
//                    new Crop(null, "Wheat & Barley", "All the other Grains", 45)
//
//            );
    private ObservableList<Crop> cropData = cropController.cropList;


    private TableView<Record> recordTable = new TableView<>();
//    private ObservableList<Record> recordData =
//            FXCollections.observableArrayList(new Record("chem1", LocalDate.now(), "seed1", 11.0, LocalDate.now(), "fertilizer 1", LocalDate.now()),
//                    new Record("chem2", LocalDate.now(), "seed2", 22.0, LocalDate.now(), "fertilizer 2", LocalDate.now()),
//                    new Record("chem3", LocalDate.now(), "seed3", 33.0, LocalDate.now(), "fertilizer 3", LocalDate.now()));
    private ObservableList<Record> recordData;



    public CropView(){
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // todo: crop and record view page within field
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Label yearLabel = new Label("Year: " + year);
        yearLabel.setFont(new Font("Arial", 20));
        yearLabel.setStyle("-fx-font-weight: bold;");

        Label cropLabel = new Label("Crop Table");
        cropLabel.setFont(new Font("Arial", 20));

        Label recordLabel = new Label("Records");
        recordLabel.setFont(new Font("Arial", 20));

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // todo: crop table formatting
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        TableColumn<Crop, String> cropTypeCol = new TableColumn<Crop, String>("Crop Type");
        cropTypeCol.setMinWidth(130);
        cropTypeCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropType"));

        TableColumn<Crop, String> cropVarietyCol = new TableColumn<Crop, String>("Crop Variety");
        cropVarietyCol.setMinWidth(130);
        cropVarietyCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropVariety"));

        TableColumn<Crop, Float> bushelWeightCol = new TableColumn<Crop, Float>("Bushel Weight");
        bushelWeightCol.setMinWidth(70);
        bushelWeightCol.setCellValueFactory(
                new PropertyValueFactory<Crop, Float>("bushelWeight"));

        grainTable.setItems(cropData);
        grainTable.getColumns().addAll(cropTypeCol, cropVarietyCol, bushelWeightCol);



        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // todo: record table formatting
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        TableColumn<Record, String> chemCol = new TableColumn<Record, String>("Chemical Sprayed");
        chemCol.setMinWidth(130);
        chemCol.setCellValueFactory(
                new PropertyValueFactory<Record, String>("chemSprayed"));

        TableColumn<Record, LocalDate> sprayingDateCol = new TableColumn<Record, LocalDate>("Spraying Date");
        sprayingDateCol.setMinWidth(130);
        sprayingDateCol.setCellValueFactory(
                new PropertyValueFactory<Record, LocalDate>("sprayingDate"));

        TableColumn<Record, String> seedPlantedCol = new TableColumn<Record, String>("Seed Planted");
        seedPlantedCol.setMinWidth(130);
        seedPlantedCol.setCellValueFactory(
                new PropertyValueFactory<Record, String>("seedPlanted"));

        TableColumn<Record, Double> seedingRateCol = new TableColumn<Record, Double>("Seeding Rate (lbs/acre)");
        seedingRateCol.setMinWidth(150);
        seedingRateCol.setCellValueFactory(
                new PropertyValueFactory<Record, Double>("seedingRate"));

        TableColumn<Record, LocalDate> seedingDateCol = new TableColumn<Record, LocalDate>("Seeding Date");
        seedingDateCol.setMinWidth(130);
        seedingDateCol.setCellValueFactory(
                new PropertyValueFactory<Record, LocalDate>("seedingDate"));

        TableColumn<Record, String> fertilizerCol = new TableColumn<Record, String>("Fertilizer");
        fertilizerCol.setMinWidth(130);
        fertilizerCol.setCellValueFactory(
                new PropertyValueFactory<Record, String>("fertilizer"));

        TableColumn<Record, LocalDate> fertilizerDateCol = new TableColumn<Record, LocalDate>("Fertilizer Date");
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
        container.getChildren().addAll(cropFunctionsBar, yearLabel, cropLabel, grainTable, recordLabel, recordTable);
        this.getChildren().addAll(container);
    }

    public void setStageField(Stage stage, Scene field){
        this.stage = stage;
        this.fieldScene = field;
    }
    @Override
    public void modelChanged() {
        
    }

    public void sendYear(int year){
        this.year = year;
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
