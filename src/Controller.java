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
import tttclogic.TypeGame;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML GridPane table, gpType, gpPlayer, gpLevel;
    @FXML Button btn00,btn01,btn02,btn10,btn11,btn12,btn20,btn21,btn22;
    @FXML Button btAI, btPlayer, btEasy, btMid, btHard, btOn, btOff;
    @FXML Text fstPlayer, sndPlayer, type, levelGame;
    @FXML Label going;
    @FXML MenuItem newGame, endGame;

    Button [] buttons = {btn00,btn01,btn02,btn10,btn11,btn12,btn20,btn21,btn22};
    Map<TypeGame, Button> btLevel = new HashMap<>();
    Button [] btNet = {btOn, btOff};
    Button [] btType = {btAI, btPlayer};

    Tic_Toe ticToe = new Tic_Toe();
    private Controller() {
        btLevel.put(TypeGame.EASY,btEasy);
        btLevel.put(TypeGame.MIDDLE,btMid);
        btLevel.put(TypeGame.HARD,btHard);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    
    public void clickedBtLvl(ActionEvent actionEvent) {

        for (Button btn: btLevel.values()) {
            if(btn.getId().equals(actionEvent.))
        }
        if(ticToe.getPlayer1()!=null) {
            gpPlayer.setVisible(false);
            table.setVisible(true);
            ticToe.setPlayer2(TypeGame.USER);
        }
        else {
            fstPlayer.setVisible(false);
            sndPlayer.setVisible(true);
            ticToe.setPlayer1(TypeGame.USER);
        }
    }

    public void clickedNG(ActionEvent actionEvent) {
        table.setVisible(false);
        for (Button bt: buttons) {
            bt.setText("");
        }
        gpType.setVisible(true);
        ticToe.setPlayer1(null);
        ticToe.setPlayer2(null);
    }

    public void clickedEG(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void clickedBtOff(ActionEvent actionEvent) {
        gpType.setVisible(false);
        gpPlayer.setVisible(true);
    }

    public void clickedBtPlayer(ActionEvent actionEvent) {
        if(ticToe.getPlayer1()!=null) {
            gpPlayer.setVisible(false);
            table.setVisible(true);
            ticToe.setPlayer2(TypeGame.USER);
        }
        else {
            fstPlayer.setVisible(false);
            sndPlayer.setVisible(true);
            ticToe.setPlayer1(TypeGame.USER);
        }
    }

    public void clickedBtAi(ActionEvent actionEvent) {
        gpPlayer.setVisible(false);
        gpLevel.setVisible(true);
    }

    public void clickedBtOn(ActionEvent actionEvent) {
        gpType.setVisible(false);
        table.setVisible(true);
        newGame.setDisable(true);
        endGame.setDisable(true);
    }

    public void clickedButton(ActionEvent actionEvent) {
    }
}
