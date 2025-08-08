package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SongLib extends Application {
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
    public String convertSongLabel(String name, String artist) {
        return (trimSpace(name) + " by " + trimSpace(artist));
    }
    public boolean isNumber(String year) {
        if (year.length() == 0) {
            return false;
        }
        for (char c: year.toCharArray()) {
            boolean digit = (c == '0') || (c == '1') || (c == '2') || (c == '3') || (c == '4') || (c == '5') ||
                    (c == '6') || (c == '7') || (c == '8') || (c == '9');
            if (!digit) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("songList.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}