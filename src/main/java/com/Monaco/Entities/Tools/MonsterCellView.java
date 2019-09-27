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

            acText.setText("AC: " + monster.armorClass + " XP: " + monster.xp);
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
                }
            });

            statusBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    monster.status = statusBox.getSelectionModel().getSelectedIndex();
                }
            });

            setText(null);
            setGraphic(hbox);
        }

    }
}
