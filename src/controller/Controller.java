package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.LogicService;

public class Controller {


    @FXML
    private TextField tf_name;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    public void onSubmitClick() throws Exception {
        LogicService service = new LogicService(choiceBox.getValue(),String.valueOf(tf_name));
        //

    }


}
