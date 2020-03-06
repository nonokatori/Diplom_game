package tttinterface;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tttlogic.EnumGame;
import tttlogic.Tic_Toe;
import tttnet.MessageArr;
import tttnet.NetClient;
import tttnet.NetServer;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public GridPane table, gpType, gpPlayer, gpLevel, gpOnline;
    @FXML
    public Button btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22;
    @FXML
    public Button btAI, btPlayer, btEasy, btMid, btHard, btOn, btOff, btServ, btClient;
    @FXML
    public Text fstPlayer, sndPlayer, type, levelGame;
    @FXML
    public Label going;
    @FXML
    public MenuItem newGame, endGame;
    @FXML
    public Pane paneTable;

    public Button[] buttons;
    Map<Button, EnumGame.Type> btLevel = new HashMap<Button, EnumGame.Type>();

    private Tic_Toe ticToe = new Tic_Toe();
    private Thread logicThread;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btLevel.put(btEasy, EnumGame.Type.EASY);
        btLevel.put(btMid, EnumGame.Type.MIDDLE);
        btLevel.put(btHard, EnumGame.Type.HARD);
        buttons = new Button[]{btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22};
        ticToe.setGoing(going);
        ticToe.setButtons(buttons);
    }

    public void clickedClient() {
        paneTable.setVisible(true);
        gpOnline.setVisible(false);
        newGame.setDisable(false);
        endGame.setDisable(false);
        Thread clientThread = new Thread(new ClientThread(ticToe));
        clientThread.setDaemon(true);
        clientThread.start();
    }

    public void clickedServ() {
        paneTable.setVisible(true);
        gpOnline.setVisible(false);
        newGame.setDisable(false);
        endGame.setDisable(false);
        Thread serverThread = new Thread(new ServerThread(ticToe));
        serverThread.setDaemon(true);
        serverThread.start();
    }
    
    private void initState() {
        for (Button bt : buttons) {
            bt.setText("");
            bt.setDisable(false);
        }
        going.setText("");
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
        initState();
        newGame.setDisable(true);
        endGame.setDisable(true);
        paneTable.setVisible(false);
        fstPlayer.setVisible(true);
        sndPlayer.setVisible(false);
        going.setText("");
    }

    public void clickedEG(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void clickedBtOff(ActionEvent actionEvent) {
        gpType.setVisible(false);
        gpPlayer.setVisible(true);
        methodThread();
    }

    public void clickedBtPlayer(ActionEvent actionEvent) {
        if (ticToe.getPlayer1() != null) {
            ticToe.setPlayer2(EnumGame.Type.USER);
            visibleObj(true);
        } else {
            ticToe.setPlayer1(EnumGame.Type.USER);
            ticToe.setMyChar('X');
            visibleObj(false);
        }
    }

    public void clickedBtAi(ActionEvent actionEvent) {
        gpPlayer.setVisible(false);
        gpLevel.setVisible(true);
    }

    public void clickedBtOn(ActionEvent actionEvent) {
        gpType.setVisible(false);
        gpOnline.setVisible(true);
    }

    public void clickedButton(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        btn.setDisable(true);
        ticToe.clicked(btn);
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
        }
    }

    public void methodThread() {
        logicThread = new Thread(new LogicThread(ticToe));
        logicThread.setDaemon(true);
        logicThread.start();
    }

    public class ServerThread implements Runnable {

        Tic_Toe tic_toe;

        public ServerThread(Tic_Toe ticToe) {
            this.tic_toe = ticToe;
        }

        @Override
        public void run() {
            Enum flag;
            MessageArr messageArr = null;
            ticToe.initState();
            System.out.println("run");
            NetServer server = NetServer.create(tic_toe.randomLetter());

            Platform.runLater(()-> going.setText("Вы играете за " + tic_toe.getMyChar() + " :)"));
            while (true) {
                System.out.println("я жив");
                if (tic_toe.getLetter() == tic_toe.getMyChar()) {
                    messageArr = tic_toe.getMessageArr();
                    System.out.println(messageArr.toString());

                    try {
                        server.send(messageArr);
                    } catch (IOException e) {
                        e.printStackTrace();
                        server.close();
                    }
                } else if (tic_toe.getLetter() == tic_toe.getEnemyChar()) {
                    try {
                        messageArr = server.read();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    tic_toe.setMessageArr(messageArr);
                }

                flag = tic_toe.resultLogic();
                if (!EnumGame.State.NOTHING.equals(flag)) {
                    break;
                }
            }
            server.close();
        }

    }

    public class ClientThread implements Runnable {

        Tic_Toe tic_toe;
        public ClientThread(Tic_Toe ticToe) {
            this.tic_toe = ticToe;
        }

        @Override
        public void run() {
            Enum flag;
            MessageArr messageArr = null;
            ticToe.initState();
            NetClient client = NetClient.create();
            try {
                tic_toe.setMyChar(client.setLetter());
                Platform.runLater(()-> going.setText("Вы играете за " + tic_toe.getMyChar() + " :)" ));
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                System.out.println("я жив");
                if (tic_toe.getLetter() == tic_toe.getMyChar()) {
                    messageArr = tic_toe.getMessageArr();
                    System.out.println(messageArr.toString());
                    try {
                        client.send(messageArr);
                    } catch (IOException e) {
                        e.printStackTrace();
                        client.close();
                    }
                } else if (tic_toe.getLetter() == tic_toe.getEnemyChar()) {
                    try {
                        messageArr = client.read();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    tic_toe.setMessageArr(messageArr);
                }

                flag = tic_toe.resultLogic();
                if (!EnumGame.State.NOTHING.equals(flag)) {
                    break;
                }
            }
            client.close();
        }

    }

    public class LogicThread implements Runnable {

        Tic_Toe ticToe;
        public LogicThread(Tic_Toe ticToe) {
            this.ticToe = ticToe;
        }

        @Override
        public void run() {
            ticToe.initState();
            while (true) {
                while (ticToe.getPlayer2() == null)
                    try { Thread.sleep(500);}
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                if (ticToe.mainLogic() == true) break;
            }
        }
    }

}
