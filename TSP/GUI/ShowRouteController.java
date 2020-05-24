package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class ShowRouteController extends AnchorPane implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TextArea routeTextArea;

    private DestinationsController destinationsController;

    void setParentController(final DestinationsController destinationsController){
        this.destinationsController=destinationsController;
    }

    void setRouteString(final String routeString){
        routeTextArea.setText(routeString);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void backToDestinationMenu(ActionEvent event) {
        destinationsController.closeWindow();
    }


}
