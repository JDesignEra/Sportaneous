package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.GridPane;

import dataAccess.HostsDA;

public class FindAGame_ViewPlayerController {
	
	@FXML private AnchorPane anchorDisplay;

    @FXML private GridPane infoDisplayField;

    @FXML private JFXButton closeBtn;
    
    private int a = 1;
    private String whoseFriends = "";
    
    public void initialize() throws IOException {
    	
    	System.out.println(FindAGameController.whosegameisclicked);
//    	if (HostsDA.getFriends(FindAGameController.whosegameisclicked).size() > 5) {
//    		anchorDisplay.setMaxHeight(25+(250+20)*HostsDA.getFriends(FindAGameController.whosegameisclicked).size());
//    	} else {
//    		anchorDisplay.setMinHeight(1045);    	
//    	}
    	
//    	anchorDisplay.setMinHeight(25+(250+20)*10);
//    	
//    	for (int i = 1; i <= 4; i++) {
//    		
//    		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_ViewPlayerDetails.fxml")), 0, a++);
//    		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/modules/FindAGame_ViewPlayerDetails_SPACING.fxml")), 0, a++);
//    		
//    	}
    	anchorDisplay.setMinHeight(1080);
		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_ViewPlayerDetails.fxml")), 1, 1);
		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/modules/FindAGame_ViewPlayerDetails_SPACING.fxml")), 1, 2);
		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_ViewPlayerDetails.fxml")), 1, 3);
		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/modules/FindAGame_ViewPlayerDetails_SPACING.fxml")), 1, 4);
		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/FindAGame_ViewPlayerDetails.fxml")), 1, 5);
		infoDisplayField.add(FXMLLoader.load(getClass().getResource("/application/modules/FindAGame_ViewPlayerDetails_SPACING.fxml")), 1, 6);

    }
    
    @FXML void handleCloseBtn(ActionEvent event) {
    	Main.getRoot().setRight(null);
    }

}
