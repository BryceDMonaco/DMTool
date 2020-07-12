package com.Monaco;

import com.Monaco.Entities.Monster;
import com.Monaco.Entities.Tools.MonsterCellView;
import com.Monaco.Entities.Tools.MonsterParser;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

public class FXMLController implements Initializable {
    @FXML
    private MenuItem openButton;

    @FXML
    private MenuItem saveButton;

    @FXML
    private MenuItem saveAsButton;

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
    private Button deselectAllButton;

    @FXML
    private Button killSelectedButton;

    @FXML
    private Button resetSelectedButton;

    @FXML
    private Button removeSelectedButton;

    @FXML
    private Button conditionSelectedButton;

    @FXML
    private Button duplicateSelectedButton;

    @FXML
    private Button damageSelectedButton;

    @FXML
    private Label selectedLabel;

    private ObservableList<Monster> activeMonsters;
    ObservableList<Monster> monsterList;

    private String savedFilename;

    public FXMLController () {
        activeMonsters = FXCollections.observableArrayList();

        monsterList = FXCollections.observableArrayList(MonsterParser.GetMonstersFromCSV("/data/MonsterCSV.csv"));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        monsterPickerBox.setItems(monsterList);
        monsterListView.setItems(activeMonsters);
        monsterListView.setCellFactory(monsterCellViewer -> new MonsterCellView());

        monsterPickerBox.setOnAction(event -> {
            if (monsterPickerBox.getSelectionModel().getSelectedItem() != null) {
                addButton.setDisable(false);
            } else {
                addButton.setDisable(true);
            }
        });

        addButton.setOnAction(event -> {
            System.out.println("Pressed!");
            if (monsterPickerBox.getSelectionModel().getSelectedItem() != null) {
                // Add a new monster cell to the monster list and populate it with items from the selected item
                Monster newMonster = new Monster(monsterPickerBox.getSelectionModel().getSelectedItem());
                Monster[] currentMonsters = activeMonsters.toArray(new Monster[1]);
                activeMonsters.clear();
                if (currentMonsters.length > 0) {
                    activeMonsters.addAll(currentMonsters);
                }
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
        });

        openButton.setOnAction(event -> {
            if (activeMonsters.size() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Delete all existing monsters and open a new file?", new ButtonType("Append"), new ButtonType("Overwrite"), ButtonType.CANCEL);
                alert.getDialogPane().getScene().getStylesheets().add("styles/modena_dark.css");
                alert.showAndWait();

                if (alert.getResult() != ButtonType.CANCEL) {
                    FileChooser fileChooser = new FileChooser();

                    //Set extension filter for text files
                    FileChooser.ExtensionFilter dmExtFilter = new FileChooser.ExtensionFilter("Diem files (*.dm)", "*.dm", "*.DM");
                    FileChooser.ExtensionFilter csvExtFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv", "*.CSV");
                    fileChooser.getExtensionFilters().addAll(dmExtFilter, csvExtFilter);

                    File targetFile = fileChooser.showOpenDialog(((Node) monsterListView).getScene().getWindow());

                    if (alert.getResult().getText().equals("Overwrite")) {
                        activeMonsters.clear();
                    }

                    activeMonsters.addAll(MonsterParser.ReadSavedFileMonsters(targetFile));

                    savedFilename = targetFile.getPath();
                }
            } else {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter for text files
                FileChooser.ExtensionFilter dmExtFilter = new FileChooser.ExtensionFilter("Diem files (*.dm)", "*.dm", "*.DM");
                FileChooser.ExtensionFilter csvExtFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv", "*.CSV");
                fileChooser.getExtensionFilters().addAll(dmExtFilter, csvExtFilter);

                File targetFile = fileChooser.showOpenDialog(((Node) monsterListView).getScene().getWindow());

                activeMonsters.addAll(MonsterParser.ReadSavedFileMonsters(targetFile));

                savedFilename = targetFile.getPath();
            }
        });

        saveButton.setOnAction(event -> {
            if (savedFilename == null) {  // No file has been saved, treat it like a save as
                saveMonstersAs();
            } else {
                File file = new File(savedFilename);

                saveListedMonstersToFile(file);
                savedFilename = file.getPath();

                System.out.println("Saved a file to: " + savedFilename);
            }
        });

        saveAsButton.setOnAction(event -> {
            saveMonstersAs();
        });

        // Allow for multiple cells to be selected at once
        monsterListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Create a listener to watch the list in the handler to update the size text whenever a monster is selected/deselected
        ChangeListener selectedChangeListener = (observable, oldValue, newValue) -> {
            int numMonstersSelected = monsterListView.getSelectionModel().getSelectedItems().size();
            // Update the selected count label TODO sometimes does not update when >1 selected and one is deselected
            selectedLabel.setText(String.valueOf(numMonstersSelected) + " Selected");

            // Disable selected function buttons if nothing is selected
            killSelectedButton.setDisable(numMonstersSelected == 0);
            resetSelectedButton.setDisable(numMonstersSelected == 0);
            removeSelectedButton.setDisable(numMonstersSelected == 0);
            conditionSelectedButton.setDisable(numMonstersSelected == 0);
            duplicateSelectedButton.setDisable(numMonstersSelected == 0);
            damageSelectedButton.setDisable(numMonstersSelected == 0);
        };

        // TODO Selecting this way basically makes the checkbox obsolete
        monsterListView.getSelectionModel().selectedItemProperty().addListener(selectedChangeListener);

        selectAllButton.setOnAction(event -> monsterListView.getSelectionModel().selectAll());

        deselectAllButton.setOnAction(event -> monsterListView.getSelectionModel().clearSelection());

        killSelectedButton.setOnAction(event -> {
            for (Monster monster : monsterListView.getSelectionModel().getSelectedItems()) {
                if (monster != null) {
                    monster.currentHP = 0;
                }
            }
            monsterListView.refresh();
        });

        System.out.println("Initialized!");

    }

    private void saveMonstersAs () {
        if (activeMonsters.size() > 0) {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter for text files
            FileChooser.ExtensionFilter dmExtFilter = new FileChooser.ExtensionFilter("Diem files (*.dm)", "*.dm", "*.DM");
            FileChooser.ExtensionFilter csvExtFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv", "*.CSV");
            fileChooser.getExtensionFilters().addAll(dmExtFilter, csvExtFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(((Node) monsterListView).getScene().getWindow());

            if (file != null) {
                saveListedMonstersToFile(file);
                savedFilename = file.getPath();
            }

            System.out.println("Saved As a file to: " + savedFilename);
        } else {
            System.out.println("No monsters to save!");
        }
    }

    private void saveListedMonstersToFile(File file) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(file);

            // Print the header line
            writer.println("Name,Entity Class,Type,Status,ALIGNMENT,Size,CR,AC,Current HP,Max HP,Spellcasting?,Attack 1 damage,Attack 2 Damage,XP,STR,STRMod,DEX,DEXMod,CON,CONMod,INT,INTMod,WIS,WISMod,CHA,CHAMod,Page,Arctic,Coast,Desert,Forest,Grassland,Hill,Mountain,Swamp,Underdark,Underwater,Urban,Book, PC or MONSTER");

            for (Monster monster : activeMonsters) {
                if (monster != null) {
                    writer.println(monster.getCSVLine());
                }
            }

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
