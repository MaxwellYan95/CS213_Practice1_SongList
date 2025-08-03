package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ListController implements Initializable {
    private String fileName = "/Users/maxyanyan/Desktop/CS213_Practice_1/src/main/java/com/example/demo/AllSongs.txt";
    private Map<String, List<String>> fileInfo;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ListView display;
    @FXML
    private ListView details;
    
    public void goToAddWindow(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("addOption.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public boolean dupFlag(String songLabel) throws FileNotFoundException {
        updateDisplay();
        for (String label: fileInfo.keySet()) {
            boolean duplicate = label.toLowerCase().equals(songLabel.toLowerCase());
            if (duplicate) {
                return true;
            }
        }
        return false;
    }
    public void selectListView(int index) throws FileNotFoundException {
        updateDisplay();
        boolean outOfBounds = (index >= display.getItems().size());
        if (!outOfBounds) {
            display.getSelectionModel().select(index);
            updateDetails();
        }
    }
    public void selectListView(String element) throws FileNotFoundException {
        updateDisplay();
        boolean contained = display.getItems().contains(element);
        if (contained) {
            display.getSelectionModel().select(element);
            updateDetails();
        }
    }
    public void updateDisplay() throws FileNotFoundException {
        updateFileInfo();
        List<String> songs = new ArrayList<>(fileInfo.keySet());
        Collections.sort(songs, String.CASE_INSENSITIVE_ORDER);
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
    public void updateDetails() {
        String selectSong = (String) display.getSelectionModel().getSelectedItem();
        System.out.println(selectSong);
        List<String> detailList = fileInfo.get(selectSong);
        details.setItems(FXCollections.observableArrayList(detailList));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateDisplay();
            display.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    updateDetails();
                }
            });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
