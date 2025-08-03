package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
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
    private String fileName = "/Users/maxyanyan/Desktop/CS213_Practice_1/src/main/java/com/example/demo/AllSongs.txt";
    public void adding(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("songList.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        ListController controller = loader.getController();
        String songLabel = (trimSpace(song.getText()) + " by " + trimSpace(artist.getText()));
        boolean duplicate = controller.dupFlag(songLabel);
        if (duplicate) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Duplicate Song");
            error.setContentText("Either the song name or artist has to be different");
            error.setContentText("Try Again");
            error.showAndWait();
        } else {
            addToFile(song.getText(), artist.getText(), album.getText(), year.getText());
            controller.updateFileInfo();
            controller.updateDisplay();
            controller.selectListView(songLabel);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void addToFile(String name, String artist, String album, String year) throws IOException {
        String songLabel = (trimSpace(name) + " by " + trimSpace(artist));
        String newLine = songLabel + "|"
                + trimSpace(name) + "|"
                + trimSpace(artist) + "|"
                + trimSpace(album) + "|"
                + trimSpace(year);
        FileWriter writer = new FileWriter(fileName, true);
        writer.append(newLine + "\n");
        writer.close();
    }

    public String trimSpace(String word) {
        int begin = 0;
        int end = word.length()-1;
        boolean isBegin = true;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != ' ') {
                if (isBegin) {
                    begin = i;
                    isBegin = false;
                } else {
                    end = i;
                }
            }
        }
        end = end + 1;
        return word.substring(begin, end);
    }
}