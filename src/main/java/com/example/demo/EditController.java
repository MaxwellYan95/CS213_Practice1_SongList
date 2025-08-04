package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    public void setEditTexts(String name, String artist, String album, String year) {
        this.name.setText(name);
        this.artist.setText(artist);
        this.album.setText(album);
        this.year.setText(year);
    }
    public void edit(ActionEvent event) throws IOException {
        String songLabel = convertSongLabel(name.getText(), artist.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("songList"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent parent = loader.load();
        ListController controller = loader.getController();
        boolean duplicate = controller.dupFlag(songLabel);
        if (duplicate) {

        } else {

        }
    }
}
