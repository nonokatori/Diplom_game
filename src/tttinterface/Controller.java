package tttinterface;

import javafx.application.Platform;
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
    @FXML
    public GridPane table, gpType, gpPlayer, gpLevel;
    @FXML
    public Button btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22;
    @FXML
    public Button btAI, btPlayer, btEasy, btMid, btHard, btOn, btOff;
    @FXML
    public Text fstPlayer, sndPlayer, type, levelGame;
    @FXML
    public Label going, congrat;
    @FXML
    public MenuItem newGame, endGame;
    @FXML
    public ImageView image;
    @FXML
    public Pane paneImage, paneTable;

    public Button[] buttons;
    Map<Button, TypeGame> btLevel = new HashMap<>();

    private Tic_Toe ticToe = new Tic_Toe();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btLevel.put(btEasy, TypeGame.EASY);
        btLevel.put(btMid, TypeGame.MIDDLE);
        btLevel.put(btHard, TypeGame.HARD);
        buttons = new Button[]{btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22};
        ticToe.setBtn(buttons, going);
        ticToe.initArray();



        Thread thread = new Thread(() -> {
            while (true) {
                Enum flag = ticToe.mainLogic();
                if () break;
            }
        });
        thread.start();


    }

    void update() {

        char [] field = new char[9];
        int coun = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; i < 3; i++){
                field[coun] = ticToe.getArrField()[i][j];
                coun++;
        }
        for (coun = 0; coun< field.length; coun++) {
            if(!buttons[coun].getText().equals(field[coun]))
                buttons[coun].setText(String.valueOf(field[coun]));
            coun++;
        }
    }

    public void clickedBtLvl(ActionEvent actionEvent) {
        Enum type = null;
        Button button = (Button) actionEvent.getSource();
        for (Button btn : btLevel.keySet())
            if (btn.getId().equals(button.getId())) {
                type = btLevel.get(btn);
                break;
            }
        if (ticToe.getPlayer1() != null) {
            ticToe.setPlayer2(type);
            gpLevel.setVisible(false);
            visibleObj(true);
        } else {
            gpPlayer.setVisible(true);
            gpLevel.setVisible(false);
            ticToe.setPlayer1(type);
            visibleObj(false);
        }
    }

    public void clickedNG(ActionEvent actionEvent) {
        gpType.setVisible(true);
        ticToe.initArray(); //очистка массивов, установка первоначального символа
        newGame.setDisable(true);
        endGame.setDisable(true);
        paneTable.setVisible(false);
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
            ticToe.setPlayer2(TypeGame.USER);
            visibleObj(true);
        } else {
            ticToe.setPlayer1(TypeGame.USER);
            visibleObj(false);
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
        ticToe.setGoGame(true);
    }

    public void clickedButton(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        if (!"".equals(btn.getText())) {
            going.setText("Эта клетка занята,\nиспользуйте другую");
            return;
        }
        going.setVisible(true);
        ticToe.clicked(btn);//меняем флаг для остановки ожидания, передаем координаты
        going.setVisible(false);
    }

    public void visibleObj(boolean flag) {
        if (flag) {
            gpPlayer.setVisible(false);
            paneTable.setVisible(true);
            newGame.setDisable(false);
            endGame.setDisable(false);
        } else {
            fstPlayer.setVisible(false);
            sndPlayer.setVisible(true);
            ticToe.setGoGame(true);
        }
    }

}
