package application.modules;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import javafx.scene.layout.GridPane;

public class FriendCardViewController implements Initializable {
	@FXML private GridPane cardContent;
	@FXML private Text nameTxt, heightWeightTxt, ratingTxt, matchNoTxt;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
}
