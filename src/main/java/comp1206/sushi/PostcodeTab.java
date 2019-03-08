package comp1206.sushi;

import comp1206.sushi.common.Postcode;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PostcodeTab extends MainTab {

    public PostcodeTab(String name){
        super(name);

        HBox superBox = new HBox();

        VBox inputBox = new VBox();
        TableView<Postcode> postcodeTableView = new TableView<>();

        TableColumn<Postcode,String> postcodeColumn = new TableColumn<>("postCode");
        TableColumn<Postcode, Double> latitudeColumn = new TableColumn<>("Latitude");
        TableColumn<Postcode, Double> longitudeColumn = new TableColumn<>("Longitude");
        TableColumn<Postcode, Double> distanceColumn = new TableColumn<>("Distance");
        postcodeTableView.getColumns().add(postcodeColumn);
        postcodeTableView.getColumns().add(latitudeColumn);
        postcodeTableView.getColumns().add(longitudeColumn);
        postcodeTableView.getColumns().add(distanceColumn);

        TextField textField = new TextField();
        Button updateButton = new Button("Update");

        inputBox.getChildren().add(textField);
        inputBox.getChildren().add(updateButton);
        superBox.getChildren().add(postcodeTableView);
        superBox.getChildren().add(inputBox);
        this.setContent(superBox);

    }
}
