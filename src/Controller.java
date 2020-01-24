import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import tttclogic.Tic_Toe;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML GridPane table;
    @FXML Button btn11,btn12,btn13,btn21,btn22,btn23,btn31,btn32,btn33;
    @FXML RadioButton bot, easy, middle, hard, rbOnline, rbOffline;
    @FXML Text fstPlayer, sndPlayer, type, levelGame;
    @FXML Label going;
    @FXML MenuItem newGame;

    Tic_Toe ticToe = new Tic_Toe();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void clickedEasy(ActionEvent actionEvent) {
    }

    public void clickedButton33(ActionEvent actionEvent) {
    }

    public void clickedButton32(ActionEvent actionEvent) {
    }

    public void clickedButton12(ActionEvent actionEvent) {
    }

    public void clickedButton11(ActionEvent actionEvent) {
    }

    public void clickedButton13(ActionEvent actionEvent) {
    }

    public void clickedButton21(ActionEvent actionEvent) {
    }

    public void clickedButton22(ActionEvent actionEvent) {
    }

    public void clickedButton23(ActionEvent actionEvent) {
    }

    public void clickedButton31(ActionEvent actionEvent) {
    }

    public void clickedMiddle(ActionEvent actionEvent) {
    }

    public void clickedHard(ActionEvent actionEvent) {
    }

    public void clickedNG(ActionEvent actionEvent) {
    }

    public void clickedEG(ActionEvent actionEvent) {
    }
}
