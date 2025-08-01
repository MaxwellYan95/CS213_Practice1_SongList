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

public class ListController {
    ArrayList<String> songs = new ArrayList<>();
    ArrayList<String> artists = new ArrayList<>();
    ArrayList<String> album = new ArrayList<>();
    ArrayList<String> year = new ArrayList<>();
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ListView songView;
    @FXML
    private ListView artistView;
    @FXML
    private ListView albumView;
    @FXML
    private ListView yearView;
    public void goToAddWindow(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("addOption.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void addToList(String s, String a, String alb, String y) {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(s) && artists.get(i).equals(a)) {
                return;
            }
        }
        songs.add(s);
        artists.add(a);
        album.add(alb);
        year.add(y);
        ObservableList<String> songData = FXCollections.observableArrayList(songs);
        songView.setItems(songData);
        ObservableList<String> artistData = FXCollections.observableArrayList(artists);
        artistView.setItems(artistData);
        ObservableList<String> albumData = FXCollections.observableArrayList(album);
        albumView.setItems(albumData);
        ObservableList<String> yearData = FXCollections.observableArrayList(year);
        yearView.setItems(yearData);
    }
}
