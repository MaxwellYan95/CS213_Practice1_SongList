package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddController {
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
        boolean duplicate = controller.add(song.getText(), artist.getText(), album.getText(), year.getText());
        if (duplicate) {
            Dialog<String> error = new Dialog<>();
            error.setTitle("Duplicate Song");
            error.setContentText("Either the song name or artist has to be different");
            error.setContentText("Try Again");
            error.showAndWait();
            error.setOnCloseRequest((e) -> {
                error.close();
            });
        } else {
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}