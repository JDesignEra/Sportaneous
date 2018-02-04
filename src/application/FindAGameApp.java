package application;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import entity.FriendsEntity;

import dataAccess.AccountsDA;
import dataAccess.FriendsDA;
import dataAccess.HostsDA;

public class FindAGameApp extends Application {

	public static int index = 0;

	@Override
	public void start(Stage stage) throws Exception {

		try {

//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("/application/HostAGame_AddedFriendsView.fxml"));
//			Parent root = loader.load();
//			HostAGame_AddedFriendsViewController controller = loader.getController();
//
//			Scene scene = new Scene(root, 1000, 1000);
//			stage.setScene(scene);
//			stage.show();

//			HostsDA.initDA();
//
			AccountsDA.initDA();			
			System.out.println(AccountsDA.addAccount("Annalise Keating", "170285X", "ak@mymail.nyp.edu.sg", "a"));
			System.out.println(AccountsDA.addAccount("Mark Zuckerberg", "170374Y", "mz@mymail.nyp.edu.sg", "a"));
			System.out.println(AccountsDA.addAccount("Tim Cook", "170463Z", "tc@mymail.nyp.edu.sg", "a"));
			System.out.println(AccountsDA.addAccount("Bill Gates", "170552Z", "bg@mymail.nyp.edu.sg", "a"));
			System.out.println(AccountsDA.addAccount("Simon Cowell", "170152Z", "sc@mymail.nyp.edu.sg", "a"));
			System.out.println(AccountsDA.addAccount("Jon Snow", "170866B", "js@mymail.nyp.edu.sg", "a"));
			System.out.println(AccountsDA.addAccount("Lim Wilson", "175150R", "175150R@mymail.nyp.edu.sg", "a"));
			System.out.println(AccountsDA.addAccount("Admin", "a", "a@mymail.nyp.edu.sg", "a"));
			System.out.println(AccountsDA.addAccount("Camila Cabello", "170146W", "cc@mymail.nyp.edu.sg", "a"));
			System.out.println(AccountsDA.addAccount("Goh Kai Hong", "170196W", "170196W@mymail.nyp.edu.sg", "a"));

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
