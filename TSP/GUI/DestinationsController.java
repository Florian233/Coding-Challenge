package GUI;

import CommonDatastructures.Adress;
import Exceptions.DestinationNotFoundException;
import Exceptions.NoStartPointSecifiedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by tablettt on 20.02.2017.
 */
public class DestinationsController extends AnchorPane implements Initializable {

    private Stage dialog;

    @FXML
    private TextField stop7StreetNbTextField;

    @FXML
    private TextField stop2StreetTextField;

    @FXML
    private TextField stop10PlzTextField;

    @FXML
    private TextField stop3StreetTextField;

    @FXML
    private TextField startTownTextField;

    @FXML
    private Button showRouteButton;

    @FXML
    private TextField stop7StreetTextField;

    @FXML
    private TextField stop10StreetTextField;

    @FXML
    private TextField stop2StreetNbTextField;

    @FXML
    private TextField startPlzTextField;

    @FXML
    private TextField stop1StreetNbTextField;

    @FXML
    private TextField stop6StreetNbTextField;

    @FXML
    private TextField stop6TownTextField;

    @FXML
    private TextField stop9StreetNbTextField;

    @FXML
    private TextField stop7TownTextField;

    @FXML
    private TextField stop4StreetTextField;

    @FXML
    private TextField stop5StreetNbTextField;

    @FXML
    private TextField stop9TownTextField;

    @FXML
    private TextField stop8StreetTextField;

    @FXML
    private TextField stop8TownTextField;

    @FXML
    private TextField stop10StreetNbTextField;

    @FXML
    private TextField stop3PlzTextField;

    @FXML
    private TextField stop2PlzTextField;

    @FXML
    private TextField stop4PlzTextField;

    @FXML
    private TextField stop5StreetTextField;

    @FXML
    private TextField stop1PlzTextField;

    @FXML
    private TextField stop5PlzTextField;

    @FXML
    private TextField stop1TownTextField;

    @FXML
    private TextField stop5TownTextField;

    @FXML
    private TextField stop7PlzTextField;

    @FXML
    private TextField stop4StreetNbTextField;

    @FXML
    private TextField stop4TownTextField;

    @FXML
    private TextField stop6PlzTextField;

    @FXML
    private TextField stop8PlzTextField;

    @FXML
    private TextField stop2TownTextField;

    @FXML
    private TextField stop3TownTextField;

    @FXML
    private TextField stop9StreetTextField;

    @FXML
    private TextField stop6StreetTextField;

    @FXML
    private TextField stop10TownTextField;

    @FXML
    private TextField startStreetNbTextField;

    @FXML
    private TextField stop8StreetNbTextField;

    @FXML
    private TextField stop9PlzTextField;

    @FXML
    private TextField stop1StreetTextField;

    @FXML
    private TextField startStreetTextField;

    @FXML
    private TextField stop3StreetNbTextField;

    @FXML
    private Label errorLabel;

    private Initializable createShowRouteView() throws IOException {
        String fxml = "ShowRoute.fxml";
        dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(Main.stage);
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        ScrollPane page = null;
        try {
            page = (ScrollPane) loader.load(in); //Auch wenn IntelliJ sagt der Cast ist redundant, das muss bleiben!
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Scene scene = new Scene(page, 800, 600);
        dialog.setScene(scene);
        dialog.sizeToScene();
        return (Initializable)loader.getController();

    }

    public void closeWindow(){
        dialog.close();
        dialog = null;
    }


    @FXML
    void showRoute(ActionEvent event){
        try {
            List<Adress> adressList = readAdresses();

            if(adressList.size() == 1){
                errorLabel.setText("Bitte Zwischenstationen angeben!");
            }else {
                ShowRouteController showRouteController = null;
                try {
                    showRouteController = (ShowRouteController) createShowRouteView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (showRouteController != null) {
                    showRouteController.setParentController(this);
                    try {
                        showRouteController.setRouteString(new BuildRoute().buildRouteStringRepresentation(adressList));
                        dialog.show();
                    } catch (DestinationNotFoundException e) {
                        errorLabel.setText(e.getMessage());
                    }
                }
            }
        } catch(NoStartPointSecifiedException e){
            errorLabel.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    private List<Adress> readAdresses() throws NoStartPointSecifiedException {
        List<Adress> adressList = new ArrayList<>();
        int anz = 0;


        String startStreet = startStreetTextField.getText();
        String startStreetNb = startStreetNbTextField.getText();
        String startPlz = startPlzTextField.getText();
        String startTown = startTownTextField.getText();

        if(startTown.equals("")||startPlz.equals("")||startStreetNb.equals("")||startStreet.equals("") ){
            throw new NoStartPointSecifiedException("Bitte einen Startpunkt angeben!");
        }

        String stop1Street = stop1StreetTextField.getText();
        String stop1StreetNb = stop1StreetNbTextField.getText();
        String stop1Plz = stop1PlzTextField.getText();
        String stop1Town = stop1TownTextField.getText();

        if(!(stop1Plz.equals("") && stop1Street.equals("") && stop1StreetNb.equals("") && stop1Town.equals(""))){
            adressList.add(new Adress(stop1StreetNb,stop1Street,stop1Plz,stop1Town,anz));
            anz++;
        }

        String stop2Street = stop2StreetTextField.getText();
        String stop2StreetNb = stop2StreetNbTextField.getText();
        String stop2Plz = stop2PlzTextField.getText();
        String stop2Town = stop2TownTextField.getText();

        if(!(stop2Plz.equals("") && stop2Street.equals("") && stop2StreetNb.equals("") && stop2Town.equals(""))){
            adressList.add(new Adress(stop2StreetNb,stop2Street,stop2Plz,stop2Town,anz));
            anz++;
        }

        String stop3Street = stop3StreetTextField.getText();
        String stop3StreetNb = stop3StreetNbTextField.getText();
        String stop3Plz = stop3PlzTextField.getText();
        String stop3Town = stop3TownTextField.getText();

        if(!(stop3Plz.equals("") && stop3Street.equals("") && stop3StreetNb.equals("") && stop3Town.equals(""))){
            adressList.add(new Adress(stop3StreetNb,stop3Street,stop3Plz,stop3Town,anz));
            anz++;
        }


        String stop4Street = stop4StreetTextField.getText();
        String stop4StreetNb = stop4StreetNbTextField.getText();
        String stop4Plz = stop4PlzTextField.getText();
        String stop4Town = stop4TownTextField.getText();

        if(!(stop4Plz.equals("") && stop4Street.equals("") && stop4StreetNb.equals("") && stop4Town.equals(""))){
            adressList.add(new Adress(stop4StreetNb,stop4Street,stop4Plz,stop4Town,anz));
            anz++;
        }


        String stop5Street = stop5StreetTextField.getText();
        String stop5StreetNb = stop5StreetNbTextField.getText();
        String stop5Plz = stop5PlzTextField.getText();
        String stop5Town = stop5TownTextField.getText();

        if(!(stop5Plz.equals("") && stop5Street.equals("") && stop5StreetNb.equals("") && stop5Town.equals(""))){
            adressList.add(new Adress(stop5StreetNb,stop5Street,stop5Plz,stop5Town,anz));
            anz++;
        }

        String stop6Street = stop6StreetTextField.getText();
        String stop6StreetNb = stop6StreetNbTextField.getText();
        String stop6Plz = stop6PlzTextField.getText();
        String stop6Town = stop6TownTextField.getText();

        if(!(stop6Plz.equals("") && stop6Street.equals("") && stop6StreetNb.equals("") && stop6Town.equals(""))){
            adressList.add(new Adress(stop6StreetNb,stop6Street,stop6Plz,stop6Town,anz));
            anz++;
        }

        String stop7Street = stop7StreetTextField.getText();
        String stop7StreetNb = stop7StreetNbTextField.getText();
        String stop7Plz = stop7PlzTextField.getText();
        String stop7Town = stop7TownTextField.getText();

        if(!(stop7Plz.equals("") && stop7Street.equals("") && stop7StreetNb.equals("") && stop7Town.equals(""))){
            adressList.add(new Adress(stop7StreetNb,stop7Street,stop7Plz,stop7Town,anz));
            anz++;
        }


        String stop8Street = stop8StreetTextField.getText();
        String stop8StreetNb = stop8StreetNbTextField.getText();
        String stop8Plz = stop8PlzTextField.getText();
        String stop8Town = stop8TownTextField.getText();

        if(!(stop8Plz.equals("") && stop8Street.equals("") && stop8StreetNb.equals("") && stop8Town.equals(""))){
            adressList.add(new Adress(stop8StreetNb,stop8Street,stop8Plz,stop8Town,anz));
            anz++;
        }


        String stop9Street = stop9StreetTextField.getText();
        String stop9StreetNb = stop9StreetNbTextField.getText();
        String stop9Plz = stop9PlzTextField.getText();
        String stop9Town = stop9TownTextField.getText();

        if(!(stop9Plz.equals("") && stop9Street.equals("") && stop9StreetNb.equals("") && stop9Town.equals(""))){
            adressList.add(new Adress(stop9StreetNb,stop9Street,stop9Plz,stop9Town,anz));
            anz++;
        }


        String stop10Street = stop10StreetTextField.getText();
        String stop10StreetNb = stop10StreetNbTextField.getText();
        String stop10Plz = stop10PlzTextField.getText();
        String stop10Town = stop10TownTextField.getText();

        if(!(stop10Plz.equals("") && stop10Street.equals("") && stop10StreetNb.equals("") && stop10Town.equals(""))){
            adressList.add(new Adress(stop10StreetNb,stop10Street,stop10Plz,stop10Town,anz));
            anz++;
        }


        adressList.add(new Adress(startStreet,startStreetNb,startPlz,startTown,anz,true));


        return adressList;
    }
}
