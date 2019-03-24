package comp1206.sushi.Tabs;

import javafx.scene.control.Alert;
import javafx.scene.control.Tab;

public class MainTab extends Tab {

    public MainTab(String name){
        super(name);
        this.setClosable(false);
//        HBox superBox = new HBox();
//        VBox inputBox = new VBox();
//        TableView<Postcode> postcodeTableView = new TableView<>();
//        TextField textField = new TextField();
//        Button updateButton = new Button();
//        inputBox.getChildren().add(textField);
//        inputBox.getChildren().add(updateButton);
//        superBox.getChildren().add(postcodeTableView);
//        superBox.getChildren().add(inputBox);
//        this.setContent(superBox);
    }

    public void unableToDeleteAlert(MainTab tab){
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Unable To Delete");

        if (tab instanceof AddIngredientTab){
            a1.setContentText("Unable to delete ingredient because it is being used by a dish.");
        } else if(tab instanceof PostcodeTab){
            a1.setContentText("Unable to delete postcode because it is being used by a supplier.");
        } else if (tab instanceof SupplierTab){
            a1.setContentText("Unable to delete supplier because it is being used by an ingredient.");
        }
        a1.setHeaderText("UnableToDeleteException");
        a1.showAndWait();
    }

    public void unableToParse(MainTab tab){
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Unable to parse");
        a1.setHeaderText("ParseException");
        if (tab instanceof PostcodeTab){
            a1.setContentText("Please enter a valid UK Postcode");
        } else {
            a1.setContentText("Please fill the text field with the correct information");
        }
        a1.showAndWait();
    }

    public void comboBoxAlert(MainTab tab){
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setTitle("ComboBox not selected");
        a1.setHeaderText("No ComboBox selected");
        if(tab instanceof SupplierTab){
            a1.setContentText("Please select a postcode before adding a supplier");
        } else if (tab instanceof AddIngredientTab){
            a1.setContentText("Please select a supplier before adding an ingredient");
        } else if (tab instanceof EditRecipeTab){
            a1.setContentText("Please select an ingredient to add to the recipe");
        }
        a1.showAndWait();
    }

    public void duplicateAlert(){
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Trying to create a duplicate item");
        a1.setHeaderText("DuplicationException");
        a1.setContentText("You are trying to add an element that is already present in the database");
        a1.showAndWait();
    }

}
