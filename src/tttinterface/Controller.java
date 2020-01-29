package tttinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tttclogic.Tic_Toe;
import tttclogic.TypeGame;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private GridPane table, gpType, gpPlayer, gpLevel;
    @FXML private Button btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22;
    @FXML private Button btAI, btPlayer, btEasy, btMid, btHard, btOn, btOff;
    @FXML private Text fstPlayer, sndPlayer, type, levelGame;
    @FXML private Label going, congrat;
    @FXML private MenuItem newGame, endGame;
    @FXML private ImageView image;
    @FXML private Pane paneImage, paneTable;

    Button[] buttons;
    Map<Button, TypeGame> btLevel = new HashMap<>();

    Tic_Toe ticToe = new Tic_Toe();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btLevel.put(btEasy, TypeGame.EASY);
        btLevel.put(btMid, TypeGame.MIDDLE);
        btLevel.put(btHard, TypeGame.HARD);
        buttons = new Button[]{btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22};
    }

    public void clickedBtLvl(ActionEvent actionEvent) {
        Enum type = null;
        Button button = (Button) actionEvent.getSource();
        for (Button btn : btLevel.keySet()) {
            if (btn.getId().equals(button.getId())) {
                type = btLevel.get(btn);
                break;
            }
        }
        if (ticToe.getPlayer1() != null) {
            gpLevel.setVisible(false);
            paneTable.setVisible(true);
            ticToe.setPlayer2(type);
            endGame.setDisable(false);
            newGame.setDisable(false);
        } else {
            gpPlayer.setVisible(true);
            gpLevel.setVisible(false);
            fstPlayer.setVisible(false);
            sndPlayer.setVisible(true);
            ticToe.setPlayer1(type);
        }
    }

    public void clickedNG(ActionEvent actionEvent) {
        paneTable.setVisible(false);
        for (Button bt : buttons) {
            bt.setText("");
            bt.setDisable(false);
        }
        gpType.setVisible(true);
        ticToe.setPlayer1(null);
        ticToe.setPlayer2(null);
        newGame.setDisable(true);
        endGame.setDisable(true);
        ticToe.setLetter('X');
    }

    public void clickedEG(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void clickedBtOff(ActionEvent actionEvent) {
        gpType.setVisible(false);
        gpPlayer.setVisible(true);
    }

    public void clickedBtPlayer(ActionEvent actionEvent) {
        if (ticToe.getPlayer1() != null) {
            gpPlayer.setVisible(false);
            paneTable.setVisible(true);
            ticToe.setPlayer2(TypeGame.USER);
            newGame.setDisable(false);
            endGame.setDisable(false);
        } else {
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
        paneTable.setVisible(true);
        newGame.setDisable(false);
        endGame.setDisable(false);
    }

    public void clickedButton(ActionEvent actionEvent) {

        going.setVisible(true);

        Button clickedBtn = (Button) actionEvent.getSource();

        if(!"".equals(clickedBtn.getText())) {
            going.setText("Эта клетка занята,\nиспользуйте другую");
            return;
        }

        String id = clickedBtn.getId().substring(3,5);
        ticToe.setID(id);//меняем флаг для остановки ожидания, передаем координаты
        ticToe.setWait(true);
        clickedBtn.setText(String.valueOf(ticToe.getLetter())); //устанавливаем знак
        ticToe.ticTacToe_Game();
        clickedBtn.setDisable(false);
        going.setVisible(false);
        //TODO мб лучше перенести в логику игры
    }


    public Label getGoing() {
        return going;
    }

    public Label getCongrat() {
        return congrat;
    }

    public ImageView getImage() {
        return image;
    }

    public Pane getPaneImage() {
        return paneImage;
    }

}

