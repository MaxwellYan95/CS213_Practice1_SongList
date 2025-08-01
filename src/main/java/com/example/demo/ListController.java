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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ListController {
    private String fileName = "/Users/maxyanyan/Desktop/CS213_Practice_1/src/main/java/com/example/demo/AllSongs.txt";
    private Map<String, List<String>> fileInfo;
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

    public void updateDisplay() throws FileNotFoundException {
        updateFileInfo();
        List<String> songs = new ArrayList<>(fileInfo.keySet());
        Collections.sort(songs);
        ObservableList<String> songList = FXCollections.observableArrayList(songs);
        display.setItems(songList);
    }

    public void updateFileInfo() throws FileNotFoundException{
        fileInfo = new HashMap<>();
        File obj = new File(fileName);
        Scanner reader = new Scanner(obj);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            ArrayList<String> elements = new ArrayList<>();
            elements.addAll(Arrays.asList(data.split("\\|")));
            fileInfo.put(elements.get(0), elements.subList(1, elements.size()));
        }
        reader.close();
    }
    public boolean addDupFlag(String songLabel) {
        return fileInfo.containsKey(songLabel);
    }
}
