package com.Monaco;

import com.Monaco.Entities.Entity;
import com.Monaco.Entities.Monster;
import com.Monaco.Entities.Tools.MonsterCellView;
import com.Monaco.Entities.Tools.MonsterParser;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
    private Button renameSelectedButton;

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

        // TODO Selecting this way basically makes the checkbox obsolete
        monsterListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Monster>) c -> {
            int numMonstersSelected = monsterListView.getSelectionModel().getSelectedItems().size();
            // Update the selected count label
            selectedLabel.setText(String.valueOf(numMonstersSelected) + " Selected");

            // Disable selected function buttons if nothing is selected
            killSelectedButton.setDisable(numMonstersSelected == 0);
            resetSelectedButton.setDisable(numMonstersSelected == 0);
            removeSelectedButton.setDisable(numMonstersSelected == 0);
            conditionSelectedButton.setDisable(numMonstersSelected == 0);
            duplicateSelectedButton.setDisable(numMonstersSelected == 0);
            damageSelectedButton.setDisable(numMonstersSelected == 0);
            renameSelectedButton.setDisable(numMonstersSelected == 0);
        });

        selectAllButton.setOnAction(event -> {
            monsterListView.getSelectionModel().clearSelection();
            monsterListView.getSelectionModel().selectAll();
        });

        deselectAllButton.setOnAction(event -> monsterListView.getSelectionModel().clearSelection());

        killSelectedButton.setOnAction(event -> {
            for (Monster monster : monsterListView.getSelectionModel().getSelectedItems()) {
                if (monster != null) {
                    monster.currentHP = 0;
                }
            }
            monsterListView.refresh();
        });

        resetSelectedButton.setOnAction(event -> {
            for (Monster monster : monsterListView.getSelectionModel().getSelectedItems()) {
                if (monster != null) {
                    monster.reset();
                }
            }
            monsterListView.refresh();
        });

        removeSelectedButton.setOnAction(event -> {
            // Saved to an array because removing them shrinks the selected items list
            final Monster[] selectedMonsters = monsterListView.getSelectionModel().getSelectedItems().toArray(new Monster[]{});

            for (Monster monster : selectedMonsters) {
                if (monster != null) {
                    monsterListView.getItems().remove(monster);
                }
            }
            monsterListView.refresh();
        });

        conditionSelectedButton.setOnAction(event -> {
            AtomicInteger conditionValue = new AtomicInteger(-1);
            ComboBox conditionBox;
            Button massApplyButton;
            Button massCancelButton;

            ObservableList condList = FXCollections.observableArrayList(
                    "Normal", "Blinded", "Charmed", "Deafened", "Frightened", "Grappled", "Incapacitated",
                    "Invisible", "Paralyzed", "Poisoned", "Prone", "Restrained", "Stunned", "Unconscious");


            Stage conditionWindow = new Stage();
            conditionWindow.initModality(Modality.WINDOW_MODAL);
            conditionWindow.initOwner(((Node) monsterListView).getScene().getWindow());
            conditionWindow.setTitle("Apply Condition");
            try {
                conditionWindow.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MassComboBoxWindow.fxml"))));
                conditionBox = (ComboBox) conditionWindow.getScene().lookup("#massComboBox");
                conditionBox.setItems(condList);
                massApplyButton = (Button) conditionWindow.getScene().lookup("#massApplyButton");
                massApplyButton.setOnAction(event3 -> {
                    if (conditionBox.getSelectionModel().getSelectedItem() != null) {
                        conditionValue.set(conditionBox.getSelectionModel().getSelectedIndex());
                    } else {
                        conditionValue.set(-1);  // An enum value can never be -1
                    }

                    conditionWindow.close();

                    if (conditionValue.get() != -1) {
                        Entity.Status newStatus = Entity.Status.values()[conditionValue.get()];
                        for (Monster monster : monsterListView.getSelectionModel().getSelectedItems()) {
                            if (monster != null) {
                                monster.status = newStatus;
                            }
                        }
                    }

                    monsterListView.refresh();
                });

                massCancelButton = (Button) conditionWindow.getScene().lookup("#massCancelButton");
                massCancelButton.setOnAction(event3 -> {
                    conditionWindow.close();
                });

                conditionWindow.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("There was an error opening the mass condition window");
                return;
            }            Entity.Status newStatus = Entity.Status.BLINDED;

        });

        duplicateSelectedButton.setOnAction(event -> {
            for (Monster monster : monsterListView.getSelectionModel().getSelectedItems()) {
                if (monster != null) {
                    monsterListView.getItems().add(new Monster(monster, monster.currentHP, monster.status, monster.name));
                }
            }
            monsterListView.refresh();
        });

        damageSelectedButton.setOnAction(event -> {
            AtomicInteger damageAmount = new AtomicInteger();
            TextField damageField;
            Button massApplyButton;
            Button massCancelButton;
            Stage damageWindow = new Stage();
            damageWindow.initModality(Modality.WINDOW_MODAL);
            damageWindow.initOwner(((Node) monsterListView).getScene().getWindow());
            damageWindow.setTitle("Apply Damage");
            try {
                damageWindow.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MassTextFieldWindow.fxml"))));
                // TODO enforce numbers only input
                damageField = (TextField) damageWindow.getScene().lookup("#massTextField");

                massApplyButton = (Button) damageWindow.getScene().lookup("#massApplyButton");
                massApplyButton.setOnAction(event3 -> {
                    try {
                        damageAmount.set(Integer.parseInt(damageField.getText()));
                    } catch (NumberFormatException e) {
                        damageAmount.set(0);
                    }

                    damageWindow.close();

                    for (Monster monster : monsterListView.getSelectionModel().getSelectedItems()) {
                        if (monster != null) {
                            monster.currentHP += -(damageAmount.get());

                            if (monster.currentHP < 0) {
                                monster.currentHP = 0;
                            }
                        }
                    }
                    monsterListView.refresh();
                });

                massCancelButton = (Button) damageWindow.getScene().lookup("#massCancelButton");
                massCancelButton.setOnAction(event3 -> {
                    damageWindow.close();
                });

                damageWindow.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("There was an error opening the mass damage window");
                return;
            }
        });

        renameSelectedButton.setOnAction(event -> {
            AtomicReference<String> newName = new AtomicReference<>();
            TextField nameField;
            Button massApplyButton;
            Button massCancelButton;
            Stage nameWindow = new Stage();
            nameWindow.initModality(Modality.WINDOW_MODAL);
            nameWindow.initOwner(((Node) monsterListView).getScene().getWindow());
            nameWindow.setTitle("Apply Name");
            try {
                nameWindow.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MassTextFieldWindow.fxml"))));
                nameField = (TextField) nameWindow.getScene().lookup("#massTextField");

                massApplyButton = (Button) nameWindow.getScene().lookup("#massApplyButton");
                massApplyButton.setOnAction(event3 -> {
                    try {
                        newName.set(nameField.getText());
                    } catch (NumberFormatException e) {
                        newName.set(null);
                    }

                    nameWindow.close();

                    for (Monster monster : monsterListView.getSelectionModel().getSelectedItems()) {
                        if (monster != null) {
                            monster.name = newName.get();
                        }
                    }
                    monsterListView.refresh();
                });

                massCancelButton = (Button) nameWindow.getScene().lookup("#massCancelButton");
                massCancelButton.setOnAction(event3 -> {
                    nameWindow.close();
                });

                nameWindow.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("There was an error opening the mass damage window");
                return;
            }
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
