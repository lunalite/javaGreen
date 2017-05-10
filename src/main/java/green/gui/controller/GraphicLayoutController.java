package green.gui.controller;

import green.gui.model.DecompileModel;
import green.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GraphicLayoutController {
    @FXML
    private TableView<DecompileModel> decompileTable;
    @FXML
    private TableColumn<DecompileModel, String> codeColumn;
    @FXML
    private TableColumn<DecompileModel, String> opCodeColumn;
    @FXML
    private TableColumn<DecompileModel, String> movementColumn;
    @FXML
    private TableColumn<DecompileModel, String> commentColumn;

    private MainApp mainApp;

    public GraphicLayoutController() {
    }

    @FXML
    private void initialize() {
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        opCodeColumn.setCellValueFactory(cellData -> cellData.getValue().opCodeProperty());
        movementColumn.setCellValueFactory(cellData -> cellData.getValue().movementProperty());
        commentColumn.setCellValueFactory(cellData -> cellData.getValue().commentProperty());

        decompileTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDecompileDetails(newValue));
    }

    public void showDecompileDetails(DecompileModel decompileLine){
        if (decompileLine != null) {
            System.out.println(decompileLine);
        } else {
            System.out.println("I am being affected.2");
        }
    }

    public void setMainApp(MainApp MainApp) {
        this.mainApp = MainApp;
        decompileTable.setItems(MainApp.getDecompileData());
    }
}
