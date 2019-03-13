package comp1206.sushi.Tabs;


import comp1206.sushi.common.Staff;
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

public class StaffTab extends MainTab {


    private TableView<Staff> staffTableView;
    private ObservableList<Staff> staffObservableList;
    private TextField input;
    private ServerInterface server;

    public StaffTab(String name, ServerInterface server){
        super(name);
        this.server = server;

        staffObservableList = FXCollections.observableArrayList(server.getStaff());

        HBox superBox = new HBox();
        superBox.setPadding(new Insets(10,10,10,10));
        superBox.setSpacing(10);

        VBox inputBox = new VBox();
        staffTableView = new TableView<>();

        TableColumn<Staff,String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Staff, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        TableColumn<Staff, Number> fatigueColumn = new TableColumn<>("Fatigue");
        fatigueColumn.setCellValueFactory(new PropertyValueFactory<>("fatigue"));

        staffTableView.getColumns().addAll(nameColumn, statusColumn, fatigueColumn);
        staffTableView.setItems(staffObservableList);

        input = new TextField();
        input.setPromptText("Ex: SO17 4SZ");

        Button addButton = new Button("Update");
        Button removeButton = new Button ("Remove");

        addButton.setOnAction(e -> addButtonClicked());
        removeButton.setOnAction(event -> removeButtonClicked());

        inputBox.getChildren().addAll(input, addButton, removeButton);
        superBox.getChildren().addAll(staffTableView, inputBox);
        this.setContent(superBox);

    }

    public void addButtonClicked(){
//        Postcode postcode = new Postcode(input.getText());
        server.addStaff(input.getText());
        staffObservableList = FXCollections.observableArrayList(server.getStaff());
        staffTableView.setItems(staffObservableList);
        for (Staff temp : server.getStaff()){
            System.out.println(temp.getName());
        }
//        staffTableView.getItems().add(postcode);
        input.clear();
    }

    public void removeButtonClicked(){
//        ObservableList<Postcode> selectedProduct, allProduct;
//        allProduct = staffTableView.getItems();
//        selectedProduct= staffTableView.getSelectionModel().getSelectedItems();
//        selectedProduct.forEach(allProduct::remove);
        try{
            server.removeStaff(staffTableView.getSelectionModel().getSelectedItem());
            staffObservableList = FXCollections.observableArrayList(server.getStaff());
            staffTableView.setItems(staffObservableList);
        }catch(ServerInterface.UnableToDeleteException e){
            System.out.println("Was unable to remove that.");
        }
    }
}
