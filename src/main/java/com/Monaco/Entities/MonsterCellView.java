package com.Monaco.Entities;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MonsterCellView extends ListCell<Monster> {
    @FXML
    private HBox hbox;

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

    private FXMLLoader mLLoader;

    private String customName = null;

    @Override
    protected void updateItem(Monster monster, boolean empty) {
        super.updateItem(monster, empty);

        if(empty || monster == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/fxml/MonsterCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (customName == null) {
                nameBox.setText(monster.name);
            } else {
                nameBox.setText(customName);
            }

            acText.setText("AC: " + String.valueOf(monster.armorClass));
            currHpField.setText(String.valueOf(monster.currentHP));
            maxHpText.setText("/ " + String.valueOf(monster.maxHP));
            attackOneLabel.setText(monster.attackOneDamage);
            attackTwoLabel.setText(monster.attackTwoDamage);

            strText.setText(String.valueOf(monster.str) + " (" + ((monster.strMod >= 0) ? "+" : "") + String.valueOf(monster.strMod) + ")");
            dexText.setText(String.valueOf(monster.dex) + " (" + ((monster.dexMod >= 0) ? "+" : "") + String.valueOf(monster.dexMod) + ")");
            conText.setText(String.valueOf(monster.con) + " (" + ((monster.conMod >= 0) ? "+" : "") + String.valueOf(monster.conMod) + ")");
            intText.setText(String.valueOf(monster.intl) + " (" + ((monster.intlMod >= 0) ? "+" : "") + String.valueOf(monster.intlMod) + ")");
            wisText.setText(String.valueOf(monster.wis) + " (" + ((monster.wisMod >= 0) ? "+" : "") + String.valueOf(monster.wisMod) + ")");
            chaText.setText(String.valueOf(monster.cha) + " (" + ((monster.chaMod >= 0) ? "+" : "") + String.valueOf(monster.chaMod) + ")");

            currHpField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    int temp = monster.currentHP;

                    try {
                        monster.currentHP = Integer.parseInt(currHpField.getText());
                    } catch (NumberFormatException e) {
                        System.out.println("Could not convert \"" + currHpField.getText() + "\" to an int.");
                        monster.currentHP = temp;
                    }
                }
            });

            nameBox.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    monster.name = nameBox.getText();
                    System.out.println(" Text Changed to  " + newValue + ")");
                }
            });

            setText(null);
            setGraphic(hbox);
        }

    }
}
