// Maxwell Yan
package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class AddController extends SongLib {
    @FXML
    private TextField song;
    @FXML
    private TextField artist;
    @FXML
    private TextField album;
    @FXML
    private TextField year;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void adding(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("songList.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        ListController controller = loader.getController();
        String songLabel = convertSongLabel(song.getText(), artist.getText());
        boolean duplicate = controller.dupFlag(songLabel);
        boolean empty = (song.getText().trim().equals("")) || (artist.getText().trim().equals(""));
        if (duplicate) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Duplicate Song");
            error.setContentText("Either the song name or artist has to be different. Try again.");
            error.showAndWait();
        } else if (empty) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setContentText("You need a song name and artist. Try again.");
            error.showAndWait();
        } else {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setContentText("Are you sure?");
            if (confirm.showAndWait().get() == ButtonType.OK) {
                controller.addSong(song.getText().trim(),
                        artist.getText().trim(),
                        album.getText().trim(),
                        year.getText().trim());
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

}