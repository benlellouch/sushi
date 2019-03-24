package comp1206.sushi.Tabs;

import comp1206.sushi.common.Postcode;
import comp1206.sushi.common.Supplier;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.DuplicateFormatFlagsException;
import java.util.Map;

public class PostcodeTab extends MainTab {


    private TableView<Postcode> postcodeTableView;
    private ObservableList<Postcode> postcodeObservableList;
    private TextField input;
    private ServerInterface server;
    private SupplierTab supplierTab;

    public PostcodeTab(String name, ServerInterface server, SupplierTab supplierTab){
        super(name);
            this.server = server;
            this.supplierTab = supplierTab;


            postcodeObservableList = FXCollections.observableArrayList(server.getPostcodes());

            HBox superBox = new HBox();
            superBox.setPadding(new Insets(10, 10, 10, 10));
            superBox.setSpacing(10);

            VBox inputBox = new VBox();
            postcodeTableView = new TableView<>();

            TableColumn<Postcode, String> postcodeColumn = new TableColumn<>("Postcode");
            postcodeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Postcode, Map> latitudeColumn = new TableColumn<>("LatLong");
            latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latLong"));

            TableColumn<Postcode, Number> distanceColumn = new TableColumn<>("Distance");
            distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

            postcodeTableView.getColumns().addAll(postcodeColumn, latitudeColumn, distanceColumn);
            postcodeTableView.setItems(postcodeObservableList);

            input = new TextField();
            input.setPromptText("Ex: SO17 4SZ");

            Button addButton = new Button("Update");
            Button removeButton = new Button("Remove");

            addButton.setOnAction(e -> addButtonClicked());
            removeButton.setOnAction(event -> removeButtonClicked());

            inputBox.getChildren().addAll(input, addButton, removeButton);
            superBox.getChildren().addAll(postcodeTableView, inputBox);
            this.setContent(superBox);

    }

    public void addButtonClicked(){
        try {
            String postcode = input.getText().toUpperCase();
            Postcode test = new Postcode(postcode);
            for (Postcode cursor : server.getPostcodes()
            ) {
                if (cursor.getName().equals(test.getName())) {

                    throw new DuplicateFormatFlagsException("Trying to create a duplicate object");
                }
            }
            if (postcode.matches("^([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([AZa-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z])))) [0-9][A-Za-z]{2})$")) {

                server.addPostcode(postcode);
                postcodeObservableList = FXCollections.observableArrayList(server.getPostcodes());
                postcodeTableView.setItems(postcodeObservableList);
                supplierTab.setPostcodeBox(postcodeObservableList);
                for (Postcode temp : server.getPostcodes()) {
                    System.out.println(temp.getName() + " " + temp.getDistance());
                }
//        postcodeTableView.getItems().add(postcode);
                input.clear();
            } else {
                unableToParse(this);
                input.clear();
            }
        }catch (DuplicateFormatFlagsException e){
            duplicateAlert();
        } catch (IOException e){
            System.out.println("fuck off let me compile");
        }
    }

    public void removeButtonClicked(){
//        ObservableList<Postcode> selectedProduct, allProduct;
//        allProduct = postcodeTableView.getItems();
//        selectedProduct= postcodeTableView.getSelectionModel().getSelectedItems();
//        selectedProduct.forEach(allProduct::remove);
        try{
            Postcode postcode = postcodeTableView.getSelectionModel().getSelectedItem();
            for (Supplier temp : server.getSuppliers()) {

                if(postcode == temp.getPostcode()){
                    throw new ServerInterface.UnableToDeleteException("Bruv you're trying to delete a postcode that is being in use");
                }

            }
//            server.removePostcode(postcodeTableView.getSelectionModel().getSelectedItem());
            server.removePostcode(postcode);
            postcodeObservableList = FXCollections.observableArrayList(server.getPostcodes());
            postcodeTableView.setItems(postcodeObservableList);
            supplierTab.setPostcodeBox(postcodeObservableList);
        }catch(ServerInterface.UnableToDeleteException e){
            unableToDeleteAlert(this);
        }
    }
}
