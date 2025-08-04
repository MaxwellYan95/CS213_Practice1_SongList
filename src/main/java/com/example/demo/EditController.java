package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditController {
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
}
