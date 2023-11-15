package org.openjfx.javafxmavenarchetypes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.InitialFarm.GrainBin;

public class BinView extends StackPane implements ModelSubscriber {

    private VBox binPage = new VBox();
    private Scene MenuScene ;
    private Stage stage;

    private TableView<GrainBin> binTable = new TableView<>();
    private final ObservableList<GrainBin> grainBinData =
            FXCollections.observableArrayList(
                    new GrainBin("Tod", "the moon", 400, false, false),
                    new GrainBin("Dave", "the moon", 4000, false, false),
                    new GrainBin("The Big One", "the moon", 40000, false, true),
                    new GrainBin("Nice", "the moon", 69, true, false)
            );

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

        binTable.setItems(grainBinData);
        binTable.getColumns().addAll(binNameCol,binLocationCol,binSizeCol,binHopperCol,binFanCol);

        Button binsBackToMain = new Button("Back");
        binsBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });

        VBox binvbox = new VBox();
        binvbox.setSpacing(5);
        binvbox.setPadding(new Insets(10, 0, 0, 10));
        binPage.getChildren().addAll(binsBackToMain,binlabel, binTable);
        this.getChildren().addAll(binPage);
    }

    public void setStageMenu(Stage stage, Scene MenuScene){
        this.stage = stage;
        this.MenuScene = MenuScene;
    }

    @Override
    public void modelChanged() {

    }
}
