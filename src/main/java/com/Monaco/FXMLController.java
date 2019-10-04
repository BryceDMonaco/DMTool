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
import java.util.LinkedList;
import java.util.List;
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

    private ObservableList<Monster> activeMonsters;
    ObservableList<Monster> monsterList;

    public FXMLController () {
        activeMonsters = FXCollections.observableArrayList();

        monsterList = FXCollections.observableArrayList(MonsterParser.GetMonstersFromCSV("data/MonsterCSV.csv"));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        monsterPickerBox.setItems(monsterList);
        monsterListView.setItems(activeMonsters);
        monsterListView.setCellFactory(monsterCellViewer -> new MonsterCellView());

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
                System.out.println("Pressed!");
                if (monsterPickerBox.getSelectionModel().getSelectedItem() != null) {
                    // Add a new monster cell to the monster list and populate it with items from the selected item
                    Monster newMonster = new Monster(monsterPickerBox.getSelectionModel().getSelectedItem());
                    Monster[] currentMonsters = activeMonsters.toArray(new Monster[1]);
                    activeMonsters.clear();
                    activeMonsters.addAll(currentMonsters);
                    activeMonsters.add(newMonster);
                    System.out.println("New monster added: " + newMonster);
                    System.out.println("Monster list is now: ");
                    for (Monster monster : activeMonsters) {
                        System.out.println("\t" + monster);
                    }

                    System.out.println("Monster ListView is now: ");
                    for (Monster monster : monsterListView.getItems()) {
                        System.out.println("\t" + monster);
                    }

                    System.out.println("Done.");
                }
            }
        });

        System.out.println("Initialized!");

    }
}
