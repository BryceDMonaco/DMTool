package com.Monaco.Entities;

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
    private ChoiceBox<?> typeBox;

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

    private FXMLLoader mLLoader;

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

            nameBox.setText(monster.name);
            acText.setText(String.valueOf(monster.armorClass));
            currHpField.setText(String.valueOf(monster.currentHP));
            maxHpText.setText("/ " + String.valueOf(monster.maxHP));
            attackOneLabel.setText(monster.attackOneDamage);
            attackTwoLabel.setText(monster.attackTwoDamage);

            setText(null);
            setGraphic(hbox);
        }

    }
}
