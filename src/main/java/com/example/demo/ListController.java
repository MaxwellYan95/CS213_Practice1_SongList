package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ListController extends SongLib implements Initializable {
    private String fileName = "/Users/maxyanyan/Desktop/CS213_Practice_1/src/main/java/com/example/demo/AllSongs.txt";
    private File file = new File(fileName);
    private Map<String, List<String>> songInfo;
    private Map<String, Integer> songYear;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ListView display;
    @FXML
    private ListView details;
    
    public void goToAddWindow(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("addOption.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void goToEditWindow(ActionEvent event) throws IOException {
        if (details.getItems().size() == 0) {

        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editOption.fxml"));
            root = loader.load();
            EditController controller = loader.getController();
            String key = (String) display.getSelectionModel().getSelectedItem();
            List<String> value = songInfo.get(key);
            controller.setEditTexts(value.get(0), value.get(1), value.get(2), value.get(3));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    public boolean dupFlag(String songLabel) throws FileNotFoundException {
        for (String label: songInfo.keySet()) {
            boolean duplicate = label.toLowerCase().equals(songLabel.toLowerCase());
            if (duplicate) {
                return true;
            }
        }
        return false;
    }
    public void firstSelect() {
        boolean empty = (0 == display.getItems().size());
        if (!empty) {
            display.getSelectionModel().select(0);
            updateDetails();
        }
    }
    public void initializeSongInfo() throws FileNotFoundException{
        songInfo = new HashMap<>();
        songYear = new HashMap<>();
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            ArrayList<String> elements = new ArrayList<>();
            elements.addAll(Arrays.asList(data.split("\\|")));
            if (isNumber(elements.get(4))) {
                songYear.put(elements.get(0), Integer.parseInt(elements.get(4)));
            }
            songInfo.put(elements.get(0), elements.subList(1, 4));
        }
        reader.close();
    }
    public void updateDisplay() throws FileNotFoundException {
        List<String> songs = new ArrayList<>(songInfo.keySet());
        Collections.sort(songs, String.CASE_INSENSITIVE_ORDER);
        ObservableList<String> songList = FXCollections.observableArrayList(songs);
        display.setItems(songList);
    }
    public void updateDetails() {
        String selectSong = (String) display.getSelectionModel().getSelectedItem();
        boolean contained = display.getItems().contains(selectSong);
        if (contained) {
            List<String> detailList = new ArrayList<>();
            detailList.addAll(songInfo.get(selectSong));
            boolean hasYear = songYear.containsKey(selectSong);
            if (hasYear) {
                detailList.add(songYear.get(selectSong) + "");
            }
            details.setItems(FXCollections.observableArrayList(detailList));
        } else {
            details.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        }
    }
    public void updateFile() throws IOException {
        FileWriter writer = new FileWriter(fileName);
        for (String key: songInfo.keySet()) {
            String line = "" + key;
            for (String info: songInfo.get(key)) {
                line += ("|" + info);
            }
            if (songYear.containsKey(key)) {
                line += ("|" + songYear.get(key));
            } else {
                line += "|";
            }
            line += "|*\n";
            writer.append(line);
        }
        writer.close();
    }

    public void addSong(String name, String artist, String album, String year) throws IOException {
        String songLabel = convertSongLabel(name, artist);
        ArrayList<String> values = new ArrayList<>();
        values.addAll(Arrays.asList(name, artist, album));
        songInfo.put(songLabel, values);
        if (isNumber(year)) {
            songYear.put(songLabel, Integer.parseInt(year));
        }
        updateFile();
        updateDisplay();
        display.getSelectionModel().select(songLabel);
    }
    public void deleteSongInfo(String songLabel) {
        songInfo.remove(songLabel);
        if (songYear.containsKey(songLabel)) {
            songYear.remove(songLabel);
        }
    }
    public void deleteSong(ActionEvent event) throws IOException {
        String selectSong = (String) display.getSelectionModel().getSelectedItem();
        deleteSongInfo(selectSong);
        updateFile();
        updateDisplay();
        firstSelect();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeSongInfo();
            updateDisplay();
            firstSelect();
            display.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    updateDetails();
                }
            });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
