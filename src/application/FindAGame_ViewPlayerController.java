package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.GridPane;

import dataAccess.HostsDA;

public class FindAGame_ViewPlayerController {
	
	@FXML private AnchorPane anchorDisplay;
	
    @FXML private ScrollPane scrollPane;

    @FXML private JFXButton closeBtn;
    
    @FXML private GridPane infoDisplayField;
    
    private int a = 0;
    private String whoseFriends = "";
    
    public void initialize() throws IOException {
    	
    	scrollPane.setFitToWidth(true);
    	
    	if (HostsDA.getFriends(FindAGameController.whosegameisclicked).size() > 5) {
    		anchorDisplay.setMaxHeight(20+(250+20)*HostsDA.getFriends(FindAGameController.whosegameisclicked).size());
    		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_ViewPlayerDetails.fxml")), 0, a++);
    	} else {
    		anchorDisplay.setMinHeight(1000);    	
    	}
    	
    	for (int i = 1; i <= HostsDA.getFriends(FindAGameController.whosegameisclicked).size(); i++) {
    		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_ViewPlayerDetails.fxml")), 0, a++);
    	}
    }
    
    @FXML void handleCloseBtn(ActionEvent event) {
    	Main.getRoot().setRight(null);
    }

}
