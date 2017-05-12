package green.gui.controller;

import green.MainApp;
import green.gui.model.EnergyModel;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class RootController {
    @FXML
    private MenuItem energyCallBtn;
    @FXML
    private MenuItem fileCloseBtn;
    @FXML
    private TableView<EnergyModel> energyTable;
    @FXML
    private TableColumn<EnergyModel, Double> energyColumn;
    @FXML
    private TableColumn<EnergyModel, String> labelColumn;

    private MainApp mainApp;
    private ObservableList<EnergyModel> energyData = FXCollections.observableArrayList();
    private Stage energyCallStage;
    private BooleanProperty isEnergyObtained = new SimpleBooleanProperty(true);

    @FXML
    private void initialize() {
        energyColumn.setCellValueFactory(cellData -> cellData.getValue().energyProperty().asObject());
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
                energyCallStage.setScene(new Scene(gridPane, 400, 400));

                energyCallStage.show();
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

    public TableColumn<EnergyModel, Double> getEnergyColumn() {
        return energyColumn;
    }

    public void setEnergyColumn(TableColumn<EnergyModel, Double> energyColumn) {
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
}
