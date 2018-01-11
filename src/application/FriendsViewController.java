package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

import dataAccess.FriendsDA;

public class FriendsViewController implements Initializable {
	@FXML JFXTextField nameAdminNo, emailTxtField;
	@FXML JFXToggleButton srcTypeToggleBtn;
	@FXML GridPane friendsGridPane;
	
	private static int friendIndex;
	private static Object[][] friends;

	private final URL FriendCardURL = getClass().getResource("/application/modules/FriendCardView.fxml");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FriendsDA.initDA();

		if (FriendsDA.getFriends().length > 0) {
			friends = FriendsDA.getFriends();
			int colCount = 0;
			int rowCount = 0;

			friendsGridPane.getChildren().remove(0);
			friendsGridPane.alignmentProperty().set(Pos.TOP_CENTER);
			friendsGridPane.setManaged(true);

			for (friendIndex = 0; friendIndex < friends.length; friendIndex++) {
				try {
					if (colCount < 4) {
						friendsGridPane.add(FXMLLoader.load(FriendCardURL), colCount++, rowCount);
					}
					else {
						colCount = 0;
						friendsGridPane.add(FXMLLoader.load(FriendCardURL), colCount++, ++rowCount);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Event Listener on JFXToggleButton[#srcTypeToggleBtn].onAction
	@FXML
	public void srcToggleBtnOnAction(ActionEvent event) {
		// TODO
	}

	// Event Listener on JFXButton[#srcBtn].onAction
	@FXML
	public void srcBtnOnAction(ActionEvent event) {
		// TODO
	}
	
	public static int getFriendIndex() {
		return friendIndex;
	}
	
	public static Object[][] getFriends() {
		return friends;
	}
}
