package com.Monaco.Entities.Tools;

import com.Monaco.Entities.Monster;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;

public class MonsterCellView extends ListCell<Monster> {
    @FXML
    private HBox hbox;

    @FXML
    private VBox selectVbox;

    @FXML
    private VBox attackBox;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField nameBox;

    @FXML
    private Label acText;

    @FXML
    private TextField currHpField;

    @FXML
    private Label maxHpText;

    @FXML
    private Label attackOneLabel;

    @FXML
    private Label attackTwoLabel;

    @FXML
    private ComboBox<?> statusBox;

    @FXML
    private Label speciesTypeText;

    @FXML
    private Label strText;

    @FXML
    private Label dexText;

    @FXML
    private Label conText;

    @FXML
    private Label intText;

    @FXML
    private Label wisText;

    @FXML
    private Label chaText;

    @FXML
    private Button sourceButton;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Monster monster, boolean empty) {
        System.out.println(this + "is updating with monster " + monster);

        super.updateItem(monster, empty);

        if(empty || monster == null) {

            setText(null);
            setGraphic(null);

        } else {
            mLLoader = new FXMLLoader(getClass().getResource("/fxml/MonsterCell.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            nameBox.setText(monster.name);

            acText.setText("AC: " + monster.armorClass + " XP: " + ((monster.xp == -999) ? "?" : monster.xp));
            currHpField.setText(String.valueOf(monster.currentHP));
            maxHpText.setText("/ " + monster.maxHP);
            attackOneLabel.setText(monster.attackOneDamage);
            attackTwoLabel.setText(monster.attackTwoDamage);

            speciesTypeText.setText(monster.entityClass + " (" + monster.type + ")");

            if (monster.str == -999) {
                strText.setText("?");
                dexText.setText("?");
                conText.setText("?");
                intText.setText("?");
                wisText.setText("?");
                chaText.setText("?");
            } else {
                strText.setText(monster.str + " (" + ((monster.strMod >= 0) ? "+" : "") + monster.strMod + ")");
                dexText.setText(monster.dex + " (" + ((monster.dexMod >= 0) ? "+" : "") + monster.dexMod + ")");
                conText.setText(monster.con + " (" + ((monster.conMod >= 0) ? "+" : "") + monster.conMod + ")");
                intText.setText(monster.intl + " (" + ((monster.intlMod >= 0) ? "+" : "") + monster.intlMod + ")");
                wisText.setText(monster.wis + " (" + ((monster.wisMod >= 0) ? "+" : "") + monster.wisMod + ")");
                chaText.setText(monster.cha + " (" + ((monster.chaMod >= 0) ? "+" : "") + monster.chaMod + ")");
            }

            ObservableList condList = FXCollections.observableArrayList(
                    "Normal", "Blinded", "Charmed", "Deafened", "Frightened", "Grappled", "Incapacitated",
                    "Invisible", "Paralyzed", "Poisoned", "Prone", "Restrained", "Stunned", "Unconscious");

            statusBox.setItems(condList);

            statusBox.getSelectionModel().select(monster.status);

            currHpField.textProperty().addListener((observable, oldValue, newValue) -> {
                int temp = monster.currentHP;

                try {
                    monster.currentHP = Integer.parseInt(currHpField.getText());
                } catch (NumberFormatException e) {
                    System.out.println("Could not convert \"" + currHpField.getText() + "\" to an int.");
                    monster.currentHP = temp;
                }
            });

            nameBox.textProperty().addListener((observable, oldValue, newValue) -> monster.name = nameBox.getText());

            statusBox.setOnAction(event -> monster.status = statusBox.getSelectionModel().getSelectedIndex());

            // TODO BUG Tooltip appears mulitple times and doesn't disappear
            Tooltip attackTip = new Tooltip("This is a test tooltip.");

            attackBox.setOnMouseEntered(event -> {
                Node  node =(Node)event.getSource();
                //attackTip.show(node, attackBox.getScene().getWindow().getX()+t.getSceneX(), attackBox.getScene().getWindow().getY()+t.getSceneY());
            });

            String monsterUrlString = "https://jsigvard.com/dnd/monster.php?m=" + monster.entityClass.replaceAll(" ", "-").replaceAll(",", "-").replaceAll(" ", "");

            sourceButton.setOnAction(event -> {
                try {
                    Desktop desktop = java.awt.Desktop.getDesktop();
                    URI oURL = new URI(monsterUrlString);
                    desktop.browse(oURL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            /*
            // Check if the monster website is reachable or not, if not, disable the source button
            if (!pingHost(monsterUrlString, 80, 1)) {
                sourceButton.setDisable(true);
            }
            */

            setText(null);
            setGraphic(hbox);
        }

    }

    public static boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }
}
