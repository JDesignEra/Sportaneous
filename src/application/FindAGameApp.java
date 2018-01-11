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

//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("/application/FindAGame_ViewPlayer.fxml"));
//			Parent root = loader.load();
//			FindAGame_ViewPlayerController controller = loader.getController();

			// AnchorPane root =
			// FXMLLoader.load(getClass().getResource("/application/FindAGame.fxml"));
//			Scene scene = new Scene(root, 1600, 900);
//			stage.setScene(scene);
//			stage.show();

			HostsDA.initDA();
			LocalDate ld = LocalDate.of(2018, 1, 2);
//			System.out.println(HostsDA.hostGame("170146W", "Camila Cabello", ld, "1700", 1, null));
//			ld = LocalDate.of(2018, 1, 4);
//			System.out.println(HostsDA.hostGame("170285X", "Annalise Keating", ld, "1900", 2, null));
//			ld = LocalDate.of(2018, 1, 8);
//			System.out.println(HostsDA.hostGame("170374Y", "Mark Zuckerberg", ld, "1100", 4, null));
//			ld = LocalDate.of(2018, 1, 16);
//			System.out.println(HostsDA.hostGame("170463Z", "Tim Cook", ld, "1400", 3, null));
//			ld = LocalDate.of(2018, 2, 1);
//			System.out.println(HostsDA.hostGame("170552A", "Bill Gates", ld, "1900", 5, null));
			ld = LocalDate.of(2018, 2, 1);
			System.out.println(HostsDA.hostGame("170957E", "Sheldon Cooper", ld, "1700", 0, null));
//			
			AccountsDA.initDA();
//			
			AccountsDA.addAccount("Annalise Keating", "170285X", "ak@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Mark Zuckerberg", "170374Y", "mz@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Tim Cook", "170463Z", "tc@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Bill Gates", "170552Z", "bg@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Simon Cowell", "170152Z", "sc@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Jon Snow", "170866B", "js@mymail.nyp.edu.sg", "a");
			AccountsDA.addAccount("Lim Wilson", "175150R", "175150R@mymail.nyp.edu.sg", "a");
			HostsDA.addFriends("170552A", "170866B");
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
