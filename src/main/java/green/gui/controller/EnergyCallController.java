package green.gui.controller;

import green.energycollector.DecompileService;
import green.energycollector.NetworkService;
import green.gui.model.Report;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ListChangeListener;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnergyCallController {
    //    private EnergyCall energyCall;
    private boolean confirmed;
    private RootController rootController;
    private DecompileService decompileService;
    private NetworkService serverListener;

    @FXML
    private TextFlow infoText;

    @FXML
    private TextField folderInput;

    @FXML
    private TextField mainInput;

    @FXML
    private Button submitButton;

    @FXML
    private Button folderSelectBtn;

    @FXML
    private Button mainSelectBtn;

    @FXML
    private ScrollPane scroll;

    private enum FILE_VALIDITY {EXIST, WRONG_FOLDER, WRONG_MAIN}

    public EnergyCallController() {
        this.confirmed = false;
    }

    @FXML
    private void initialize() {
        scroll.setVvalue(1.0);           //1.0 means 100% at the bottom

        submitButton.setOnAction(event -> {
            handleSubmit(folderInput.getText(), mainInput.getText());
        });

        folderInput.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                submitButton.fire();
            }
        });

        mainInput.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                submitButton.fire();
            }
        });

        Text t1 = new Text();
        //TODO: Change the default folder from javaGreen
        t1.setText("Starting java energy debugger process... \n" +
                "-------------- Notes ---------------\n" +
                "Currently still under development. \n" +
                "Version 1.1\n");

        infoText.getChildren().addAll(t1);
        infoText.getChildren().addListener((ListChangeListener<Node>) c -> {
            scroll.layout();
            scroll.setVvalue(1.0d);
        });

        DirectoryChooser directoryChooser = new DirectoryChooser();
        FileChooser fileChooser = new FileChooser();

        folderSelectBtn.setOnAction(event -> {
            File selectedDirectory = directoryChooser.showDialog(rootController.getEnergyCallStage());
            if (selectedDirectory == null) {
                System.out.println("Hi");
            } else {
                folderInput.setText(selectedDirectory.getAbsolutePath());
            }
        });

        mainSelectBtn.setOnAction(event -> {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JAVA", "*.java"));
            File selectedFile = fileChooser.showOpenDialog(rootController.getEnergyCallStage());

            if (selectedFile == null) {
            } else {
                mainInput.setText(selectedFile.getAbsolutePath());
            }
        });

        folderInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            File initialDirectory = new File(newValue);
            if (initialDirectory.isDirectory()) {
                directoryChooser.setInitialDirectory(initialDirectory);
                mainInput.textProperty().set(initialDirectory.getAbsolutePath());
            }
        }));

        mainInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            File initialDirectory = new File(newValue);
            if (initialDirectory.isDirectory()) {
                fileChooser.setInitialDirectory(initialDirectory);
            }
        }));
    }

    public void bindSubmitButton() {
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(getRootController().isEnergyObtainedProperty(), folderInput.textProperty(), mainInput.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return folderInput.getText().isEmpty() || mainInput.getText().isEmpty() || !getRootController().isIsEnergyObtained();
            }
        };
        submitButton.disableProperty().bind(bb);
    }

    @FXML
    private void handleSubmit(String _folderInput, String _mainInput) {
        switch (isFileValid(_folderInput, _mainInput)) {
            case EXIST:
                Report report = new Report();
                Report.setRootController(rootController);

//                _mainInput = convertMainInput(_folderInput, _mainInput);
                getRootController().setIsEnergyObtained(false);
                getRootController().setIsEnergyObtainedFirst(false);
                getRootController().getEnergyData().removeAll(getRootController().getEnergyData());
                getRootController().setReport(report);
                infoText.getChildren().add(new Text("\n" +
                        "Decompiling and obtaining energy report...\n" +
                        "Please wait...\n"));
                this.decompileService = new DecompileService(_folderInput, _mainInput);
                if (!decompileService.isRunning()) {
                    decompileService.reset();
                    decompileService.start();
                }

                serverListener = new NetworkService();
                serverListener.setRootController(getRootController());
                serverListener.start();

                decompileService.setOnSucceeded((WorkerStateEvent t) -> {
                    Report.setDecompiledAmbly((ArrayList<String>) t.getSource().getValue());
                    getRootController().getMainApp().populateCol();
                });
                break;
            case WRONG_FOLDER:
                Alert folderMissingError = new Alert(Alert.AlertType.ERROR);
                folderMissingError.initOwner(rootController.getEnergyCallStage());
                folderMissingError.setTitle("Missing Folder");
                folderMissingError.setHeaderText("Folder not found");
                folderMissingError.setContentText("Please input the right folder name in the right directory.");
                folderMissingError.showAndWait();
                break;
            case WRONG_MAIN:
                Alert fileMissingError = new Alert(Alert.AlertType.ERROR);
                fileMissingError.initOwner(rootController.getEnergyCallStage());
                fileMissingError.setTitle("Missing File");
                fileMissingError.setHeaderText("File not found");
                fileMissingError.setContentText("Please input the right filename in the right directory.");
                fileMissingError.showAndWait();
                break;
        }
//        return report;
    }

    private FILE_VALIDITY isFileValid(String _folderInput, String _mainInput) {
        File dir = new File(_folderInput);
        if (!dir.isDirectory()) {
            return FILE_VALIDITY.WRONG_FOLDER;
        }
        File f = new File(_mainInput);
//        File[] fs = new File(f.getParent()).listFiles();
        if (f.exists() && !f.isDirectory()) {
            return FILE_VALIDITY.EXIST;
        } else {
            return FILE_VALIDITY.WRONG_MAIN;
        }
    }

    private String convertMainInput(String _folderInput, String _mainInput) {
        Pattern p = Pattern.compile("((?<=[/])[\\w\\d]+$)|(^\\w\\d$)");
        Matcher m = p.matcher(_mainInput);
        String fileName = "";
        if (m.find()) {
            fileName = m.group(0);
        }
        File f = new File("src/javaGreen/" + _folderInput + "/" + _mainInput + ".java");
        File[] fs = new File(f.getParent()).listFiles();
        for (File fss : fs) {
            if (fss.getName().equalsIgnoreCase(fileName + ".java")) {
                _mainInput = _mainInput.replace(fileName, fss.getName());
                _mainInput = _mainInput.replace(".java", "");
            }
        }
        return _mainInput;
    }

    public TextFlow getInfoText() {
        return infoText;
    }

    public void setInfoText(TextFlow infoText) {
        this.infoText = infoText;
    }

    public TextField getMainInput() {
        return mainInput;
    }

    public void setMainInput(TextField mainInput) {
        this.mainInput = mainInput;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public void setSubmitButton(Button submitButton) {
        this.submitButton = submitButton;
    }

    public RootController getRootController() {
        return rootController;
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
