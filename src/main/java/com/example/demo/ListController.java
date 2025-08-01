package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListController {
    ArrayList<String> songLib = new ArrayList<>();
    Map<String, List<String>> details = new HashMap<>();
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ListView display;
    public void goToAddWindow(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("addOption.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void addToList(String name, String artist, String album, String year) {
        String song = (name + " by " + artist);
        for (int i = 0; i < songLib.size(); i++) {
            if (songLib.get(i).equals(song)) {
                return;
            }
        }
        songLib.add(song);
        List<String> info = new ArrayList<>();
        info.add(name);
        info.add(artist);
        info.add(album);
        info.add(year);
        details.put(song, info);
        ObservableList<String> songList = FXCollections.observableArrayList(songLib);
        display.setItems(songList);

    }
}
