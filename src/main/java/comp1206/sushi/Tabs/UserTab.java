package comp1206.sushi.Tabs;

import comp1206.sushi.common.Postcode;
import comp1206.sushi.common.User;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class UserTab extends MainTab{

    public UserTab(String name, ServerInterface server){
        super(name);
        ObservableList<User> userObservableList = FXCollections.observableArrayList(server.getUsers());

        TableView<User> userTableView = new TableView<>();
        userTableView.setPadding(new Insets(10,10,10,10));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));


        TableColumn<User, Postcode> postcodeTableColumn = new TableColumn<>("Postcode");
        postcodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("postcode"));

        userTableView.getColumns().addAll(usernameColumn, postcodeTableColumn);
        userTableView.setItems(userObservableList);

        this.setContent(userTableView);

    }


}
