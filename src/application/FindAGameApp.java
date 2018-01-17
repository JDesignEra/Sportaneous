package application;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import dataAccess.AccountsDA;
import dataAccess.HostsDA;

public class FindAGameApp extends Application {

	public static int index = 0;

	@Override
	public void start(Stage stage) throws Exception {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/HostAGame_FriendsView.fxml"));
			Parent root = loader.load();
			HostAGame_FriendsViewController controller = loader.getController();

			Scene scene = new Scene(root, 475, 1000);
			stage.setScene(scene);
			stage.show();

			HostsDA.initDA();

			AccountsDA.initDA();			
			AccountsDA.addAccount("Annalise Keating", "170285X", "ak@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Mark Zuckerberg", "170374Y", "mz@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Tim Cook", "170463Z", "tc@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Bill Gates", "170552Z", "bg@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Simon Cowell", "170152Z", "sc@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Jon Snow", "170866B", "js@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Lim Wilson", "175150R", "175150R@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Admin", "a", "a@mymail.nyp.edu.sg", "a");
			

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
