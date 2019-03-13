package comp1206.sushi.Tabs;

import comp1206.sushi.common.Order;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderTab extends MainTab {
    TableView<Order> orderTableView;
    ObservableList<Order> orderObservableList;
    ServerInterface server;
    public OrderTab (String name, ServerInterface server){
        super(name);
        this.server = server;
        orderObservableList = FXCollections.observableArrayList(server.getOrders());

        orderTableView = new TableView<>();
        orderTableView.setPadding(new Insets(10,10,10,10));

        TableColumn<Order, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Order, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Order, Number> distanceColumn = new TableColumn<>("Distance");
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

        orderTableView.getColumns().addAll(nameColumn, statusColumn, distanceColumn);
        orderTableView.setItems(orderObservableList);

        this.setContent(orderTableView);

    }
}
