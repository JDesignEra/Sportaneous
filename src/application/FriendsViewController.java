package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import entity.AccountsEntity;

import dataAccess.AccountsDA;
import dataAccess.FriendsDA;

public class FriendsViewController {
	@FXML JFXTextField nameAdminNoTxtField, emailTxtField;
	@FXML JFXToggleButton srcTypeToggleBtn;
	@FXML GridPane emptyFriendsContent;
	@FXML FlowPane friendsFlowPane;

	private static int accIndex;
	private static List<AccountsEntity> accounts;

	private final URL friendCardURL = getClass().getResource("/application/modules/FriendCardView.fxml");

	@FXML
	private void initialize() {
		if (!FriendsDA.getFriends().isEmpty()) {
			friendsFlowPane.getChildren().clear();
			friendsFlowPane.alignmentProperty().set(Pos.TOP_CENTER);
			showFriends();
		}
	}

	// Event Listener on JFXToggleButton[#srcTypeToggleBtn].onAction
	@FXML
	private void srcToggleBtnOnAction(ActionEvent event) {
		if (srcTypeToggleBtn.isSelected()) {
			srcTypeToggleBtn.setText("ALL PLAYERS");
			showAll();
		}
		else {
			srcTypeToggleBtn.setText("FRIENDS");
			showFriends();
		}
	}

	// Event Listener on JFXButton[#srcBtn].onAction
	@FXML
	private void srcBtnOnAction(ActionEvent event) {
		int i = 0;

		for (Node node : friendsFlowPane.getChildren()) {
			if ((accounts.get(i).getAdminNo().toLowerCase().contains(nameAdminNoTxtField.getText().toLowerCase())
					|| accounts.get(i).getName().toLowerCase().contains(nameAdminNoTxtField.getText().toLowerCase()))
					&& accounts.get(i).getEmail().toLowerCase().contains(emailTxtField.getText().toLowerCase())) {
				node.setVisible(true);
				node.setManaged(true);
			}
			else {
				node.setVisible(false);
				node.setManaged(false);
			}
			i++;
		}
	}

	private void showFriends() {
		accounts = AccountsDA.getFriendsAcc();

		friendsFlowPane.getChildren().clear();
		nameAdminNoTxtField.clear();
		emailTxtField.clear();

		if (accounts.isEmpty()) {
			friendsFlowPane.alignmentProperty().set(Pos.CENTER);
			friendsFlowPane.getChildren().add(emptyFriendsContent);
		}
		else {
			for (accIndex = 0; accIndex < accounts.size(); accIndex++) {
				if (!accounts.get(accIndex).getAdminNo().equals(AccountsDA.getAdminNo())) {
					try {
						friendsFlowPane.getChildren().add(FXMLLoader.load(friendCardURL));
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void showAll() {
		accounts = AccountsDA.getAllData();

		friendsFlowPane.getChildren().clear();
		nameAdminNoTxtField.clear();
		emailTxtField.clear();

		for (accIndex = 0; accIndex < accounts.size(); accIndex++) {
			if (!accounts.get(accIndex).getAdminNo().equals(AccountsDA.getAdminNo())) {
				try {
					friendsFlowPane.getChildren().add(FXMLLoader.load(friendCardURL));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static int getAccIndex() {
		return accIndex;
	}

	public static List<AccountsEntity> getAccounts() {
		return accounts;
	}
	
	public static void setAccounts(List<AccountsEntity> accList) {
		accounts = accList;
	}
}
