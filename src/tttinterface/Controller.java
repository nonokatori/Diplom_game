package tttinterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML GridPane table;
    @FXML Label going, clock;
    @FXML Button button11,button12,button13,button21,button22,button23,button31,button32,button33;
    @FXML MenuItem fstPlayer, sndPlayer, ai1, easy1, middle1, hard1, player1, ai2, easy2, middle2, hard2, player2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setClock (ActionEvent event) {

    }

}
