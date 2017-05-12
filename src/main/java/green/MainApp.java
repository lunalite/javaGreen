package green;
import com.fasterxml.jackson.databind.ObjectMapper;
import green.database.MongoConnect;
import green.gui.controller.GraphicLayoutController;
import green.gui.controller.RootController;
import green.gui.model.DecompilationModel;
import green.gui.model.DecompileModel;
import green.gui.model.Report;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<DecompileModel> decompileData = FXCollections.observableArrayList();

    public static void main(String[] args){
        MongoConnect.connect();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Energy Debug Graphic");
        initRootLayout();
        showGraphic();
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getClassLoader().getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            RootController rootController = loader.getController();
            rootController.setMainApp(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showGraphic() {
        try {
            // Load GraphicLayout.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getClassLoader().getResource("view/GraphicLayout.fxml"));
            AnchorPane energyOverview = loader.load();

            // Set GraphicLayout into the center of root layout.
            rootLayout.setCenter(energyOverview);

            GraphicLayoutController gLController = loader.getController();
            gLController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateCol() {
        decompileData.removeAll(decompileData);
        List<String> decompleList = Report.getDecompiledAmbly();
        List<DecompileModel> decompileModels = new ArrayList<>();
        //String[] l¡ines = x.split("\\r?\\n");
        if (decompleList != null) {
            String[] lines = decompleList.toArray(new String[decompleList.size()]);
            boolean isInstruction = false;
            int count = 0;
            for (String i : lines) {
                decompileData.add(new DecompileModel(i, isInstruction));
                decompileModels.add(new DecompileModel(i, isInstruction));
                if (i.contains("Code:")) {
                    isInstruction = true;
                } else if (isInstruction && lines[count + 1].contains(":")) {
                } else {
                    isInstruction = false;
                }
                count++;
            }

            DecompilationModel dm = new DecompilationModel(decompileModels);
            ObjectMapper mapper = new ObjectMapper();
            final OutputStream out = new ByteArrayOutputStream();
            String json = "";
            try {
                mapper.writeValue(out, dm);
                json = out.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Document document = Document.parse(json);
            Report.setReportDocument(document);
            Report.insertReportToDatabase();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public ObservableList<DecompileModel> getDecompileData() {
        return this.decompileData;
    }
}
