package comp1206.sushi.Controller;

import comp1206.sushi.common.Postcode;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PostcodeController {
    @FXML private TableView<Postcode> tableView;
    @FXML private TextField postcodeField;
    @FXML private TextField distanceField;


@FXML
    protected void addPostcode(ActionEvent event){
        ObservableList<Postcode> data = tableView.getItems();
        data.add(new Postcode(postcodeField.getText()));
        postcodeField.setText("");
    }


}