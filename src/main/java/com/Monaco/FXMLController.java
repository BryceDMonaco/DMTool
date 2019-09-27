package com.Monaco;

import com.Monaco.Entities.Monster;
import com.Monaco.Entities.Tools.MonsterCellView;
import com.Monaco.Entities.Tools.MonsterParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class FXMLController implements Initializable {
    @FXML
    private ListView<Monster> monsterListView;

    @FXML
    private ListView<?> playerListView;

    @FXML
    private ComboBox<Monster> monsterPickerBox;

    @FXML
    private Button addButton;

    @FXML
    private Button selectAllButton;

    @FXML
    private Label selectedLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedLabel.setText("Owo");

        ObservableList<Monster> monsterList = FXCollections.observableArrayList(MonsterParser.GetMonstersFromCSV("data/MonsterCSV.csv"));

        monsterPickerBox.setItems(monsterList);
        monsterListView.setCellFactory(monsterCellView -> new MonsterCellView());

        monsterPickerBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (monsterPickerBox.getSelectionModel().getSelectedItem() != null) {
                    addButton.setDisable(false);
                } else {
                    addButton.setDisable(true);
                }
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (monsterPickerBox.getSelectionModel().getSelectedItem() != null) {
                    // Add a new monster cell to the monster list and populate it with items from the selected item
                    monsterListView.getItems().add(new Monster(monsterPickerBox.getSelectionModel().getSelectedItem()));
                }
            }
        });

    }
}
