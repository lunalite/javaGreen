package green.gui.controller;

import green.MainApp;
import green.gui.model.EnergyModel;
import green.gui.model.Report;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

import javax.tools.Tool;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class RootController {
    @FXML
    private MenuItem energyCallBtn;
    @FXML
    private MenuItem fileCloseBtn;
    @FXML
    private MenuItem exportBtn;
    @FXML
    private MenuItem energyDebuggerHelpBtn;
    @FXML
    private TableColumn<EnergyModel, String> energyColumn;
    @FXML
    private TableView<EnergyModel> energyTable;
    @FXML
    private TableColumn<EnergyModel, String> labelColumn;

    private MainApp mainApp;
    private ObservableList<EnergyModel> energyData = FXCollections.observableArrayList();
    private Stage energyCallStage;
    private Stage energyHelpStage;
    private BooleanProperty isEnergyObtained = new SimpleBooleanProperty(true);
    private BooleanProperty isEnergyObtainedFirst = new SimpleBooleanProperty(false);
    private Report report;

    @FXML
    private void initialize() {
        energyColumn.setCellValueFactory(cellData -> cellData.getValue().energyRoundingProperty());
        labelColumn.setCellValueFactory(cellData -> cellData.getValue().labelProperty());
        energyTable.setItems(energyData);
        energyCallBtn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getClassLoader().getResource("view/EnergyCall.fxml"));
                GridPane gridPane = loader.load();

                EnergyCallController eCController = loader.getController();
                eCController.setRootController(this);
                eCController.bindSubmitButton();

                energyCallStage = new Stage();
                energyCallStage.setTitle("M");
                energyCallStage.setScene(new Scene(gridPane, 640, 480));

                energyCallStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        BooleanBinding iEOFBB = new BooleanBinding() {
            {
                super.bind(isEnergyObtainedFirstProperty());
            }

            @Override
            protected boolean computeValue() {
                return !isIsEnergyObtainedFirst();
            }
        };

        exportBtn.disableProperty().bind(iEOFBB);

        exportBtn.setOnAction(event -> {
            if (getReport().isIsOverallEnergyListObtained()) {
                List<Double> overallEnergyList = getReport().getOverallEnergyList();
                try {
                    FileWriter writer = new FileWriter("energyExport.csv");
                    for (Double v : overallEnergyList) {
                        writer.write(v.toString() + ",");
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        energyDebuggerHelpBtn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getClassLoader().getResource("view/EnergyHelp.fxml"));
                BorderPane borderPane= loader.load();

                EnergyHelpController eHController= loader.getController();
//                eCController.setRootController(this);
//                eCController.bindSubmitButton();

                energyHelpStage = new Stage();
                energyHelpStage .setTitle("Energy Deubbger Help");
                energyHelpStage .setScene(new Scene(borderPane, 640, 480));

                energyHelpStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void fileCloseHandler() {
        getMainApp().getPrimaryStage().close();
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public Stage getEnergyCallStage() {
        return energyCallStage;
    }

    public void setEnergyCallStage(Stage energyCallStage) {
        this.energyCallStage = energyCallStage;
    }

    public TableView<EnergyModel> getEnergyTable() {
        return energyTable;
    }

    public void setEnergyTable(TableView<EnergyModel> energyTable) {
        this.energyTable = energyTable;
    }

    public TableColumn<EnergyModel, String> getEnergyColumn() {
        return energyColumn;
    }

    public void setEnergyColumn(TableColumn<EnergyModel, String> energyColumn) {
        this.energyColumn = energyColumn;
    }

    public ObservableList<EnergyModel> getEnergyData() {
        return energyData;
    }

    public void setEnergyData(ObservableList<EnergyModel> energyData) {
        this.energyData = energyData;
    }

    public boolean isIsEnergyObtained() {
        return isEnergyObtained.get();
    }

    public BooleanProperty isEnergyObtainedProperty() {
        return isEnergyObtained;
    }

    public void setIsEnergyObtained(boolean isEnergyObtained) {
        this.isEnergyObtained.set(isEnergyObtained);
    }

    public boolean isIsEnergyObtainedFirst() {
        return isEnergyObtainedFirst.get();
    }

    public BooleanProperty isEnergyObtainedFirstProperty() {
        return isEnergyObtainedFirst;
    }

    public void setIsEnergyObtainedFirst(boolean isEnergyObtainedFirst) {
        this.isEnergyObtainedFirst.set(isEnergyObtainedFirst);
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
