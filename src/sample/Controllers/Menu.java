package sample.Controllers;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu {


  //  @FXML
 //  Button shedule;

  //  @FXML
  //  Button teacher;

  // @FXML
  //  Button audio;

    @FXML
    void OpenShedule(ActionEvent event)
    {
     //   Parent root;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/sample.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Расписание группы");
            stage.setScene(new Scene(root, 680, 700));
            stage.show();
            // Hide this current window (if this is what you want)
          //  ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void OpenTeach(ActionEvent event)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/teach.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Расписание преподавателя");
            stage.setScene(new Scene(root, 680, 700));
            stage.show();
            // Hide this current window (if this is what you want)
            //  ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void OpenAudior(ActionEvent event)
    {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/audio.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Расписание аудитории");
            stage.setScene(new Scene(root, 680, 700));
            stage.show();
            // Hide this current window (if this is what you want)
            //  ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
