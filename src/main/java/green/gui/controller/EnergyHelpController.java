package green.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.event.ActionEvent;

public class EnergyHelpController {
    @FXML
    WebView webView;
    @FXML
    public void initialize(){
        System.out.println("Initializing...");
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getClassLoader().getResource("documentation/index.html").toExternalForm());

    }
}
