package org.openjfx.javafxmavenarchetypes;

import control.BinControl;
import control.GraphControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.InitialFarm.Crop;
import org.InitialFarm.GrainBin;
import javafx.scene.chart.BarChart;
import org.entities.Field;
import org.entities.Year;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;

public class GraphView extends StackPane implements ModelSubscriber {


    private Field selectedField;
    private VBox graphPage = new VBox();
    private Scene MenuScene ;

    private Scene graphScene;
    private Stage stage;

    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    private ComboBox comboBox;
    private BarChart barchart;
    private XYChart.Series dataSeries1;


    GraphControl graphController = new GraphControl();


    private HBox buildLayout(Field field) {
        VBox layout = new VBox();
        HBox.setHgrow(layout, Priority.ALWAYS);
        layout.setStyle("-fx-border-width:1px;-fx-border-color:#444444;");
        layout.setSpacing(5);
        layout.setPadding(new Insets(2));
        HBox topRow = new HBox();
        topRow.setSpacing(5);
        topRow.getChildren().addAll(getLabel("Name :","bold"),getLabel(field.getName(),"normal"), getLabel("Acres :","bold"),getLabel(field.getSize()+"","normal"));
        HBox bottomRow = new HBox();
        bottomRow.setSpacing(5);
        bottomRow.getChildren().addAll(getLabel("Current Crop :","bold"),getLabel(field.getCurrent_Year().getCrop().getCropType(),"normal"));
        layout.getChildren().addAll(topRow, bottomRow);

        HBox pane = new HBox();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setSpacing(5);
        pane.setPadding(new Insets(2));
        Label num = new Label(field.getCurrent_Year().getYear()+"");
        num.setStyle("-fx-font-size:20px;-fx-font-weight:bold;-fx-text-fill:black;");
        pane.getChildren().addAll(num,layout);
        return pane;
    }

    private Label getLabel(String txt, String style){
        Label lblName = new Label(txt);
        lblName.setStyle("-fx-font-weight:"+style+";-fx-text-fill:black;");
        return lblName;
    }
    public GraphView() {

        VBox graphPage= new VBox(30);
        Scene graphScene = new Scene(graphPage, 300, 250);
        graphPage.setPadding(new Insets(10, 10, 10, 10));

        //Initializing the bar chart for all data
        Field testField = new Field(null,"50","fieldy boi",30,"FieldLocation");
        Year newYear1 = new Year(null,2020, LocalDate.of(2020, Month.MAY,15));
        Crop newCrop = new Crop(null,"corn", "factory corn", 400);
        newYear1.setCrop(newCrop);
        testField.addYear(newYear1);
        testField.setCurrentYear(newYear1);

        Field testField2 = new Field(null,"40","boi of the field",60,"FieldLocation up my ass");
        Year newYear2 = new Year(null,2045, LocalDate.of(2024, Month.MAY,15));
        Year newYear3 = new Year(null,2023,LocalDate.of(2023, Month.MAY,15));
        Crop newCrop2 = new Crop(null,"potato", "factory ofthe potatiot", 600);
        Crop newCrop3 = new Crop(null,"leaf","leaf factory",800);
        newYear2.setCrop(newCrop2);
        newYear3.setCrop(newCrop3);
        testField2.addYear(newYear2);
        testField2.addYear(newYear3);
        testField2.setCurrentYear(newYear2);

        //Adding drop down box with options
        ArrayList<Field> allFields = new ArrayList<Field>( );
        allFields.add(testField);
        allFields.add(testField2);
        ObservableList<Field> fieldList = FXCollections.observableArrayList(allFields);

        comboBox = new ComboBox<>();
        comboBox.setItems(fieldList);
        comboBox.setCellFactory(param -> new ListCell<Field>() {
            @Override
            protected void updateItem(Field item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(buildLayout(item));
                } else {
                    setGraphic(null);
                }
            }
        });
        comboBox.setButtonCell(new ListCell<Field>(){
            @Override
            protected void updateItem(Field item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(buildLayout(item));
                } else {
                    setGraphic(null);
                }
            }
        });

        comboBox.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,field)->{
            System.out.println("Field");
        });

        //How to change the combobox because its final

//        comboBox.getItems().addAll(
//                "Option 4",
//                "Option 5",
//                "Option 6"
//        );

        comboBox.getSelectionModel().selectFirst();
        if (comboBox.getValue() != null){
            selectedField = (Field) comboBox.getValue();
        }

        comboBox.setOnAction(e -> {
            this.selectedField = (Field) comboBox.getValue();
            System.out.println(selectedField.getName());

            dataSeries1.getData().clear();


            dataSeries1.setName(selectedField.getName() + "");
            for (int x = 0;x < selectedField.getYears().size();x++){
                dataSeries1.getData().add(new XYChart.Data((Integer.toString(selectedField.getYears().get(x).getYear())),selectedField.getYears().get(x).getCrop().getBushelWeight()));
                System.out.println(selectedField.getYears().get(x).getYear());

            }


        });


        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        barchart = new BarChart(xAxis, yAxis);
        dataSeries1 = new XYChart.Series();
        barchart.setAnimated(false);
        if (selectedField != null){
            for (int x = 0;x < testField.getYears().size();x++){
                barchart.setTitle("Yearly growth");
                dataSeries1.getData().add(new XYChart.Data(" " + selectedField.getYears().get(x).getYear() ,selectedField.getYears().get(x).getCrop().getBushelWeight()));

            }
        }
        //Making the actual bar chart

        yAxis.setLabel("Bushel Weight");
        xAxis.setLabel("Years");
        barchart.getData().add(dataSeries1);

        barchart.setTitle("Yearly growth");

        Button graphToMain = new Button("Back To Main");
        graphToMain.setOnMouseClicked(event -> {
            stage.setScene(MenuScene);
        });

        graphPage.getChildren().addAll(graphToMain ,comboBox,barchart);
        this.getChildren().add(graphPage);
    }


    public void update() {
    }

    public void setStageMenu(Stage stage, Scene MenuScene, Scene graphScene){
        this.stage = stage;
        this.MenuScene = MenuScene;
        this.graphScene = graphScene;
        graphScene.getStylesheets().add(getClass().getClassLoader().getResource("graph.css").toExternalForm());
    }

    @Override
    public void modelChanged() {

    }
}
