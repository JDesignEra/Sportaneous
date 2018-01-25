package application.modules;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import entity.RatingsEntity;

import dataAccess.AccountsDA;
import dataAccess.RatingsDA;

import modules.Misc;

import application.Main;
import application.RatingsViewController;

public class RatingsCardViewController {
	@FXML private GridPane profileGridPane;
	@FXML private Text nameTxt;
	@FXML private Text dayTxt;
	@FXML private Text dateTxt;
	@FXML private Text timeTxt;
	@FXML private Text sportTxt;
	@FXML private Text playerCountsTxt;

	private List<RatingsEntity> ratings = RatingsDA.getRatings();
	private int ratingIndex = RatingsViewController.getRatingIndex();

	private String name = AccountsDA.getAccData(ratings.get(ratingIndex).getHostAdminNo()).getName();
	private String sport = RatingsDA.getRatings().get(ratingIndex).getSport();
	private LocalDateTime dateTime = RatingsDA.getRatings().get(ratingIndex).getDateTime();
	private int playerCount = RatingsDA.getRatings().get(ratingIndex).getAdminNums().length + 1;

	private final URL ratingsPlayerCardURL = getClass().getResource("/application/modules/RatingsPlayerCardView.fxml");

	@FXML
	private void initialize() {
		profileGridPane.add(new Misc().cropCirclePhoto(ratings.get(ratingIndex).getHostAdminNo(), 75), 0, 0);
		nameTxt.setText(name);
		dayTxt.setText(dateTime.format(DateTimeFormatter.ofPattern("EEEE")));
		dateTxt.setText(dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		timeTxt.setText(dateTime.format(DateTimeFormatter.ofPattern("h:m a")));
		sportTxt.setText(sport);
		playerCountsTxt.setText(Integer.toString(playerCount));
	}

	// Event Listener on JFXButton[#ratePlayersBtn].onAction
	@FXML
	private void ratePlayersBtnOnAction(ActionEvent event) {
		// Dialog
		StackPane rootStackPane = (StackPane) Main.getRoot().lookup("#ratingRoot");
		JFXDialogLayout content = new JFXDialogLayout();

		JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);
		dialog.getStyleClass().add("ratingsDialog");

		VBox playerRatingContent = new VBox();
		ScrollPane playerRatingScrollPane = new ScrollPane(playerRatingContent);

		for (int i = 0; i < ratings.get(ratingIndex).getAdminNums().length; i++) {
			RatingsPlayerCardViewController.setRatingIndex(ratingIndex);
			RatingsPlayerCardViewController.setPlayerIndex(i);
			
			try {
				playerRatingContent.getChildren().add(FXMLLoader.load(ratingsPlayerCardURL));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Submit Dialog Buttons
		JFXButton submitBtn = new JFXButton("SUBMIT");
		submitBtn.getStyleClass().add("success");

		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO update ratings
			}
		});

		// Cancel Dialog Buttons
		JFXButton cancelBtn = new JFXButton("CANCEL");
		cancelBtn.getStyleClass().add("danger");

		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});

		content.setHeading(new Text("RATE PLAYERS"));
		content.setBody(playerRatingScrollPane);
		content.getActions().addAll(submitBtn, cancelBtn);

		dialog.show();
	}
}
