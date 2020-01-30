package tttinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tttclogic.GameState;
import tttclogic.Tic_Toe;
import tttclogic.TypeGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML public GridPane table, gpType, gpPlayer, gpLevel;
    @FXML public Button btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22;
    @FXML public Button btAI, btPlayer, btEasy, btMid, btHard, btOn, btOff;
    @FXML public Text fstPlayer, sndPlayer, type, levelGame;
    @FXML public Label going, congrat;
    @FXML public MenuItem newGame, endGame;
    @FXML public ImageView image;
    @FXML public Pane paneImage, paneTable;

    public Button[] buttons;
    Map<Button, TypeGame> btLevel = new HashMap<>();

    private Tic_Toe ticToe = new Tic_Toe();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btLevel.put(btEasy, TypeGame.EASY);
        btLevel.put(btMid, TypeGame.MIDDLE);
        btLevel.put(btHard, TypeGame.HARD);
        buttons = new Button[]{btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22};
        ticToe.initArray(buttons);
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
        paneImage.setVisible(false);
        gpType.setVisible(true);
        ticToe.initArray(buttons); //очистка массивов, установка первоначального символа
        newGame.setDisable(true);
        endGame.setDisable(true);
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
        ticToe.clicked((Button) actionEvent.getSource());//меняем флаг для остановки ожидания, передаем координаты
        going.setVisible(false);
        if (ticToe.getWinDraw() != null) viewPage();
    }

    public void viewPage () {
        String player =  new Tic_Toe().getLetter() == 'X' ? " игрок 1" : " игрок 2";
        paneImage.setVisible(true);
        paneTable.setVisible(false);
        try {
            if (ticToe.getWinDraw().equals(GameState.WIN)) {
                new ImageView(new Image(new FileInputStream("resource/win.jpg")));
                congrat.setText("Победитель" + player );
            }
            else {
                new ImageView(new Image(new FileInputStream("resource/draw.jpg")));
                congrat.setText("Ничья");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

