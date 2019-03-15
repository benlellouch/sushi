package comp1206.sushi.Tabs;

import comp1206.sushi.common.Ingredient;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.text.NumberFormat;
import java.text.ParseException;

public class EditIngredient extends MainTab{

    private IngredientTab ingredientTab;
    private ObservableList<Ingredient> ingredientObservableList;
    private TextField restockAmount;
    private TextField restockThreshold;
    private ServerInterface server;

    public EditIngredient (String name, IngredientTab ingredientTab, ServerInterface server){
        super(name);
        this.server= server;
        this.ingredientTab = ingredientTab;
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
            Ingredient ingredient = ingredientTab.getIngredientTableView().getSelectionModel().getSelectedItem();
            Number newRestockAmount = NumberFormat.getInstance().parse(restockAmount.getText());
            Number newRestockThreshold = NumberFormat.getInstance().parse(restockThreshold.getText());
            server.setRestockLevels(ingredient,newRestockThreshold,newRestockAmount);
            ingredientObservableList = FXCollections.observableArrayList(server.getIngredients());
            ingredientTab.setItems(ingredientObservableList);

        }
       catch(ParseException e){
           System.out.println(" Wasn't able to parse that.");
       }


    }
}
