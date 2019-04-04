package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.LogicService;

public class Controller {


    @FXML
    private TextField tf_name;

    @FXML
    public void onSubmitClick() throws Exception {
        LogicService service = new LogicService();
        System.out.println(tf_name.getText());
    }


}
