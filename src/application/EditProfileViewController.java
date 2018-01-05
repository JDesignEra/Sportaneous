package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.StringConverter;

import dataAccess.AccountsDA;
import dataAccess.EquipmentsDA;

import modules.Snackbar;

public class EditProfileViewController implements Initializable {
	@FXML JFXTextField nameTxtField, heightTxtField, weightTxtField, emailTxtField;
	@FXML JFXPasswordField cPassTxtField, cfmPassTxtField;
	@FXML TextArea introTxtArea;
	@FXML Text introCharCountTxt;
	@FXML ComboBox<String> favSportComboBox, intSportComboBox;
	@FXML JFXButton saveBtn, cancelBtn, intSportAddBtn;
	@FXML JFXToggleButton heightVisibilityToggleBtn, weightVisibilityToggleBtn;
	@FXML Circle dpCircle;
	@FXML FlowPane intSportFlowPane;
	@FXML GridPane profileGridPane, dpOverlayGridPane, rootGridPane;
	@FXML StackPane rootStackPane;

	private String adminNo = AccountsDA.getAdminNo();
	private String name = AccountsDA.getName();
	private String intro = AccountsDA.getIntro();
	private String email = AccountsDA.getEmail();
	private String pass = AccountsDA.getPassword();
	private String favSport = AccountsDA.getFavSport();
	private String intSport = AccountsDA.getInterestedSports();
	private double height = AccountsDA.getHeight();
	private double weight = AccountsDA.getWeight();
	private boolean heightVisibility = AccountsDA.getHeightVisibility();
	private boolean weightVisibility = AccountsDA.getWeightVisibility();

	private Image img = new Image("/application/assets/uploads/default.png");
	private ImageView imgView = new ImageView(img);
	private Circle clip = new Circle(100, 100, 100);

	private final URL profileViewURL = getClass().getResource("/application/ProfileView.fxml");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		EquipmentsDA.initDA();

		// Force Double only for heightTxtField & weightTxtField
		Pattern pattern = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

		UnaryOperator<TextFormatter.Change> filter = uo -> {
			String txt = uo.getControlNewText();

			if (pattern.matcher(txt).matches()) {
				return uo;
			}
			else {
				return null;
			}
		};
		
		StringConverter<Double> converter = new StringConverter<Double>() {
			@Override
			public Double fromString(String s) {
				if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
					return 0.0;
				}
				else {
					return Double.valueOf(s);
				}
			}

			@Override
			public String toString(Double d) {
				return d.toString();
			}
		};

		heightTxtField.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
		weightTxtField.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));

		// Force introTxtArea to 120 char limit.
		introTxtArea.setTextFormatter(new TextFormatter<String>(vc -> vc.getControlNewText().length() <= 120 ? vc : null));

		// Set Fields
		nameTxtField.setText(name);
		emailTxtField.setText(email);
		cPassTxtField.setText(pass);
		cfmPassTxtField.setText(pass);
		heightTxtField.setText(Double.toString(height));
		weightTxtField.setText(Double.toString(weight));
		heightVisibilityToggleBtn.setSelected(heightVisibility);
		weightVisibilityToggleBtn.setSelected(weightVisibility);
		introTxtArea.setText(intro);

		introCharCountTxt.setText(introTxtArea.getText().length() + " / 120");

		// Add intSportComboBox
		if (!intSport.isEmpty()) {
			String[] intSports = intSport.split(",");

			for (int i = 0; i < intSports.length; i++) {
				if (i > 0) {
					ComboBox<String> newComboBox = new ComboBox<>();
					newComboBox.getStyleClass().add("sportChip");
					newComboBox.setPromptText("Intrerested Sport");
					newComboBox.setCursor(Cursor.HAND);

					intSportFlowPane.getChildren().add(newComboBox);
					FlowPane.setMargin(newComboBox, new Insets(2.5));
				}
			}
		}

		// Populate favSportComboBox & intSportComboBox items
		if (EquipmentsDA.getAllData() != null) {
			String[] intSports = new String[EquipmentsDA.getAllData().length];
			int i = 0;

			for (int j = 0; j < EquipmentsDA.getAllData().length; j++) {
				intSports[j] = EquipmentsDA.getAllData()[j][0].toString();
			}

			favSportComboBox.getItems().add("None");
			favSportComboBox.getItems().addAll(intSports);

			for (Node n : intSportFlowPane.lookupAll(".sportChip")) {
				if (n instanceof ComboBox<?>) {
					@SuppressWarnings("unchecked") ComboBox<String> node = (ComboBox<String>) n;

					node.getItems().add("None");
					node.getItems().addAll(intSports);

					if (!intSport.isEmpty()) {
						node.getSelectionModel().select(intSport.split(",")[i]);
						i++;
					}
				}
			}
		}

		if (!favSport.isEmpty()) {
			favSportComboBox.getSelectionModel().select(favSport);
		}

		// Profile Photo
		if (getClass().getResource("/application/assets/uploads/" + adminNo + ".png") != null) {
			img = new Image(getClass().getResource("/application/assets/uploads/" + adminNo + ".png").toExternalForm());
			imgView = new ImageView(img);

			// Crop
			if (img.getHeight() >= 200 || img.getWidth() >= 200) {
				int w = (int) img.getWidth();
				int h = (int) img.getHeight();

				if (img.getHeight() > img.getWidth()) {
					PixelReader pr = img.getPixelReader();
					WritableImage newImage = new WritableImage(pr, 0, (h - w) / 2, w, w);

					imgView.setImage(newImage);
				}
				else {
					PixelReader pr = img.getPixelReader();
					WritableImage newImage = new WritableImage(pr, (w - h) / 2, 0, h, h);

					imgView.setImage(newImage);
				}
			}
		}

		imgView.setFitWidth(200);
		imgView.setFitHeight(200);
		imgView.setClip(clip);
		imgView.setCursor(Cursor.HAND);

		profileGridPane.add(imgView, 0, 0);

		if (heightVisibilityToggleBtn.isSelected()) {
			heightVisibilityToggleBtn.setText("\uf06e");
		}

		if (weightVisibilityToggleBtn.isSelected()) {
			weightVisibilityToggleBtn.setText("\uf06e");
		}
	}

	// Event Listener on TextArea[#introTxtArea].onKeyTyped.
	@FXML
	public void introTxtAreaOnKeyType(KeyEvent event) {
		introCharCountTxt.setText(introTxtArea.getText().length() + " / 120");
	}

	// Event Listener on JFXButton[#saveBtn].onAction.
	@SuppressWarnings("unchecked")
	@FXML
	public void saveBtnOnAction(ActionEvent event) {
		// Remove CSS style class
		emailTxtField.getStyleClass().remove("danger");
		cPassTxtField.getStyleClass().remove("danger");
		cfmPassTxtField.getStyleClass().remove("danger");

		// Change Password & Confirm Change Password check
		if (!cPassTxtField.getText().equals(cfmPassTxtField.getText())) {
			cPassTxtField.getStyleClass().add("danger");
			cfmPassTxtField.getStyleClass().add("danger");

			new Snackbar().danger(rootStackPane, "Change Password and Confirm Change Password field does not match");
		}
		else {
			email = emailTxtField.getText();
			pass = cPassTxtField.getText();
			name = nameTxtField.getText();
			intro = introTxtArea.getText();
			height = !heightTxtField.getText().isEmpty() ? Double.parseDouble(heightTxtField.getText()) : 0.0;
			weight = !weightTxtField.getText().isEmpty() ? Double.parseDouble(weightTxtField.getText()) : 0.0;
			heightVisibility = heightVisibilityToggleBtn.isSelected();
			weightVisibility = weightVisibilityToggleBtn.isSelected();
			StringBuilder sports = new StringBuilder();

			// Build intSport
			for (Node node : intSportFlowPane.lookupAll(".sportChip")) {
				if (node instanceof ComboBox<?>) {
					String selectedItem = ((ComboBox<String>) node).getSelectionModel().getSelectedItem();

					if (selectedItem != null && !selectedItem.isEmpty() && !selectedItem.equals("None")) {
						sports.append(((ComboBox<String>) node).getSelectionModel().getSelectedItem() + ",");
					}
				}
			}

			if (!sports.toString().isEmpty()) {
				sports.substring(0, sports.length() - 1);
			}

			// String favSport assignment
			if (!favSportComboBox.getSelectionModel().getSelectedItem().isEmpty() && !favSportComboBox.getSelectionModel().getSelectedItem().equals("None")) {
				favSport = favSportComboBox.getSelectionModel().getSelectedItem();
			}

			// Dialog
			JFXDialogLayout content = new JFXDialogLayout();
			content.setHeading(new Text("Save Profile Changes"));
			content.setBody(new Text("Are you sure you want to save your current profile settings?\n" + "You will be redirected to your Profile page if you select SAVE."));
			JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

			// Dialog Save Button
			JFXButton dialogSaveBtn = new JFXButton("Save");
			dialogSaveBtn.getStyleClass().add("success");
			dialogSaveBtn.setCursor(Cursor.HAND);

			dialogSaveBtn.setOnAction(saveEV -> {
				dialog.close();

				switch (AccountsDA.editAccount(email, pass, name, favSport, sports.toString(), intro, height, weight, heightVisibility, weightVisibility)) {
					case 0: // Success
						new Snackbar().successSpinner(rootGridPane, "Your profile settings has been saved successfully. Please wait while you are being redirected...", 4000);

						// Delay on screen with timeline instead of using Thread.sleep()
						Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), ev -> {
							try {
								Main.getRoot().setCenter(FXMLLoader.load(profileViewURL));
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}));
						timeline.play();
						break;

					case 1: // Fail
						new Snackbar().danger(rootStackPane, "Whoops! Something went wrong, please contact an administrator.");
						break;

					case 2: // Email validation
						emailTxtField.getStyleClass().add("danger");
						new Snackbar().danger(rootStackPane, "Only NYP Email adress are allowed.");
						break;

					default:
						break;
				}
			});
			content.setActions(dialogSaveBtn);

			// Dialog Cancel Button
			JFXButton dialogCancelBtn = new JFXButton("Cancel");
			dialogCancelBtn.getStyleClass().addAll("danger");
			dialogCancelBtn.setCursor(Cursor.HAND);

			dialogCancelBtn.setOnAction(cancelEV -> dialog.close());
			content.getActions().add(dialogCancelBtn);
			dialog.show();
		}
	}

	// Event Listener on JFXButton[#cancelBtn].onAction.
	@FXML
	public void cancelBtnOnAction(ActionEvent event) {
		// Dialog
		JFXDialogLayout content = new JFXDialogLayout();
		content.setHeading(new Text("Cancel Profile Changes"));
		content.setBody(
				new Text("Are you sure you want to cancel your current profile settings without saving?\n" + "You will be redirected to your Profile page if you select YES."));
		JFXDialog dialog = new JFXDialog(rootStackPane, content, JFXDialog.DialogTransition.CENTER);

		// Dialog Save Button
		JFXButton dialogYesBtn = new JFXButton("Yes");
		dialogYesBtn.getStyleClass().add("success");
		dialogYesBtn.setCursor(Cursor.HAND);

		dialogYesBtn.setOnAction(yesEV -> {
			dialog.close();

			try {
				Main.getRoot().setCenter(FXMLLoader.load(profileViewURL));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
		content.setActions(dialogYesBtn);

		// Dialog Cancel Button
		JFXButton dialogNoBtn = new JFXButton("No");
		dialogNoBtn.getStyleClass().addAll("danger");
		dialogNoBtn.setCursor(Cursor.HAND);

		dialogNoBtn.setOnAction(noEV -> dialog.close());
		content.getActions().add(dialogNoBtn);
		dialog.show();
	}

	// Event Listener on JFXButton[#intSportAddBtn].onAction.
	@FXML
	public void intSportAddBtnOnAction(ActionEvent event) {
		String[] intSports = new String[EquipmentsDA.getAllData().length];
		int length = intSportFlowPane.lookupAll(".sportChip").size();

		for (int j = 0; j < EquipmentsDA.getAllData().length; j++) {
			intSports[j] = EquipmentsDA.getAllData()[j][0].toString();
		}

		if (length < EquipmentsDA.getAllData().length) {
			ComboBox<String> newComboBox = new ComboBox<>();
			newComboBox.getStyleClass().add("sportChip");
			newComboBox.setPromptText("Intrerested Sport");
			newComboBox.setCursor(Cursor.HAND);

			newComboBox.getItems().add("None");
			newComboBox.getItems().addAll(intSports);

			intSportFlowPane.getChildren().add(newComboBox);
			FlowPane.setMargin(newComboBox, new Insets(2.5));
			intSportFlowPane.setManaged(true);
		}
	}

	// Event Listener on JFXToggleButton[#heightVisibilityToggleBtn].onAction.
	@FXML
	public void heightVisibilityToggleBtnOnAction(ActionEvent event) {
		if (heightVisibilityToggleBtn.isSelected()) {
			heightVisibilityToggleBtn.setText("\uf06e");
		}
		else {
			heightVisibilityToggleBtn.setText("\uf070");
		}
	}

	// Event Listener on JFXToggleButton[#weightVisibilityToggleBtn].onAction.
	@FXML
	public void weightVisibilityToggleBtnOnAction(ActionEvent event) {
		if (weightVisibilityToggleBtn.isSelected()) {
			weightVisibilityToggleBtn.setText("\uf06e");
		}
		else {
			weightVisibilityToggleBtn.setText("\uf070");
		}
	}

	// Event Listener for dpOverlay.onMouseEnter
	@FXML
	public void dpOverlayOnMouseEnter(MouseEvent event) {
		if (dpOverlayGridPane.getOpacity() != 1.0) {
			dpOverlayGridPane.toFront();

			FadeTransition fadeTransition = new FadeTransition(Duration.millis(250), dpOverlayGridPane);
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1.0);
			fadeTransition.play();
		}
	}

	// Event Listener for dpOverlay.OnMouseExit
	@FXML
	public void dpOverlayOnMouseExit(MouseEvent event) {
		if (dpOverlayGridPane.getOpacity() != 0) {
			dpOverlayGridPane.toFront();

			FadeTransition fadeTransition = new FadeTransition(Duration.millis(250), dpOverlayGridPane);
			fadeTransition.setFromValue(1.0);
			fadeTransition.setToValue(0);
			fadeTransition.play();
		}
	}
}
