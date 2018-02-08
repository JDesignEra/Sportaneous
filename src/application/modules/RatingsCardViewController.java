package application.modules;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXToggleButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import entity.RatingsEntity;

import dataAccess.AccountsDA;
import dataAccess.RatingsDA;

import modules.Utils;

import application.Main;

public class RatingsCardViewController {
	@FXML private GridPane profileGridPane;
	@FXML private Text nameTxt;
	@FXML private Text dayTxt;
	@FXML private Text dateTxt;
	@FXML private Text timeTxt;
	@FXML private Text sportTxt;
	@FXML private Text playerCountsTxt;

	private List<RatingsEntity> ratings;
	private int ratingIndex;

	private String name;
	private String sport;
	private LocalDateTime dateTime;
	private int playerCount;

	private final URL ratingsPlayerViewURL = getClass().getResource("/application/RatingsView.fxml");
	private final URL ratingsPlayerCardURL = getClass().getResource("/application/modules/RatingsPlayerCardView.fxml");

	@FXML
	private void initialize() {
		if (ratings != null && !ratings.isEmpty()) {
			profileGridPane.add(Utils.cropCirclePhoto(ratings.get(ratingIndex).getHostAdminNo(), 75), 0, 0);
			nameTxt.setText(name);
			dayTxt.setText(dateTime.format(DateTimeFormatter.ofPattern("EEEE")));
			dateTxt.setText(dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			timeTxt.setText(dateTime.format(DateTimeFormatter.ofPattern("h:m a")));
			sportTxt.setText(sport);
			playerCountsTxt.setText(Integer.toString(playerCount));
		}
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
			FXMLLoader loader = new FXMLLoader(ratingsPlayerCardURL);

			try {
				playerRatingContent.getChildren().add(loader.load());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			RatingsPlayerCardViewController ratingsPlayerCardViewController = loader.getController();
			ratingsPlayerCardViewController.setPlayerRatings(ratings.get(ratingIndex).getAdminNums()[i]);
		}

		// Submit Dialog Buttons
		JFXButton submitBtn = new JFXButton("SUBMIT");
		submitBtn.getStyleClass().add("success");

		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Set<Node> playerCards = Main.getRoot().getCenter().lookupAll("#playerRatingCard");
				String[] playerComments = new String[playerCards.size()];
				int[] playerRatings = new int[playerCards.size()];
				boolean[] playerAttendances = new boolean[playerCards.size()];
				int i = 0;

				for (Node node : playerCards) {
					if (node.lookup("#comments") instanceof TextArea) {
						playerComments[i] = ((TextArea) node.lookup("#comments")).getText();
					}

					if (node.lookup("#ratingStarsGrp") instanceof HBox) {
						HBox ratingStarsGrp = (HBox) node.lookup("#ratingStarsGrp");
						int ratingStar = 0;

						for (Node ratingStarBtn : ratingStarsGrp.getChildren()) {
							if (ratingStarBtn instanceof ToggleButton && ((ToggleButton) ratingStarBtn).isSelected()) {
								ratingStar++;
							}
						}

						playerRatings[i] = ratingStar;
					}

					if (node.lookup("#attendance") instanceof JFXToggleButton) {
						playerAttendances[i] = ((JFXToggleButton) node.lookup("#attendance")).isSelected();
					}
					
					i++;
				}
				
				RatingsDA.updateRatings(ratings.get(ratingIndex).getMatchID(), playerComments, playerRatings, playerAttendances);
				
				try {
					Main.getRoot().setCenter(FXMLLoader.load(ratingsPlayerViewURL));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
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
	
	public void setRatings(List<RatingsEntity> ratings, int index) {
		this.ratings = ratings;
		ratingIndex = index;
		
		name = AccountsDA.getAccData(ratings.get(index).getHostAdminNo()).getName();
		sport = RatingsDA.getRatings().get(index).getSport();
		dateTime = RatingsDA.getRatings().get(index).getDateTime();
		playerCount = RatingsDA.getRatings().get(index).getAdminNums().length + 1;
		
		initialize();
	}
}
