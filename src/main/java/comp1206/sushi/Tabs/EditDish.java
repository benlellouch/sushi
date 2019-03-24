package comp1206.sushi.Tabs;

import comp1206.sushi.common.Dish;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.text.NumberFormat;
import java.text.ParseException;

public class EditDish extends MainTab{

    private DishTab dishTab;
    private ObservableList<Dish> ingredientObservableList;
    private TextField restockAmount;
    private TextField restockThreshold;
    private ServerInterface server;

    public EditDish (String name, DishTab dishTab, ServerInterface server){
        super(name);
        this.server= server;
        this.dishTab = dishTab;
        VBox superbox = new VBox();
        superbox.setSpacing(10);
        restockAmount = new TextField();
        restockAmount.setPromptText("Restock Amount");
        restockThreshold = new TextField();
        restockThreshold.setPromptText("Restock Threshold");

        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(event -> modifyButtonClicked());

        superbox.getChildren().addAll(restockAmount,restockThreshold,modifyButton);
        this.setContent(superbox);


    }

    public void modifyButtonClicked(){
        try {
            Dish dish = dishTab.getDishTableView().getSelectionModel().getSelectedItem();
            Number newRestockAmount = NumberFormat.getInstance().parse(restockAmount.getText());
            Number newRestockThreshold = NumberFormat.getInstance().parse(restockThreshold.getText());
            server.setRestockLevels(dish,newRestockThreshold,newRestockAmount);
            dishTab.buildUI();


        }
        catch(ParseException e){
            unableToParse(this);
        }


    }
}
