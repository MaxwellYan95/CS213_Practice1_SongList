package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class EditController extends SongLib {
    @FXML
    private TextField name;
    @FXML
    private TextField artist;
    @FXML
    private TextField album;
    @FXML
    private TextField year;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void setEditTexts(String name, String artist, String album, String year) {
        this.name.setText(name);
        this.artist.setText(artist);
        this.album.setText(album);
        this.year.setText(year);
    }
    public void edit(ActionEvent event) throws IOException {
        String songLabel = convertSongLabel(name.getText(), artist.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("songList.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        root = loader.load();
        ListController controller = loader.getController();
        // deletes the old songLabel, this
        // allows you to edit the current
        // songLabel without triggering a
        // duplicate error
        controller.deleteSongInfo(songLabel);
        boolean duplicate = controller.dupFlag(songLabel);
        if (duplicate) {

        } else {
            controller.addSong(name.getText(), artist.getText(), album.getText(), year.getText());
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
