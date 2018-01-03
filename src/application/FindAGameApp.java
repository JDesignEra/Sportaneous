package application;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.ConcurrentMap;

import dataAccess.HostsDA;
import entity.HostsEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class FindAGameApp extends Application {
	
	public static int index = 0;
	@Override
	public void start(Stage stage) throws Exception {
		try {
			
			AnchorPane root = FXMLLoader.load(getClass().getResource("/application/FindAGame.fxml"));
			Scene scene = new Scene(root, 1600,900);
			stage.setScene(scene);
			stage.show();
			
			
		
			HostsDA.initDA();
			LocalDate ld = LocalDate.of(2018, 1, 2);
			System.out.println(HostsDA.hostGame("170146W", "Camila Cabello", ld, "1700", 1));
			ld = LocalDate.of(2018, 1, 4);
			System.out.println(HostsDA.hostGame("170285X", "Annalise Keating", ld, "1900", 2));
			ld = LocalDate.of(2018, 1, 8);
			System.out.println(HostsDA.hostGame("170374Y", "Mark Zuckerberg", ld, "1100", 4));
			ld = LocalDate.of(2018, 1, 16);
			System.out.println(HostsDA.hostGame("170463Z", "Tim Cook", ld, "1400", 3));
			ld = LocalDate.of(2018, 2, 1);
			System.out.println(HostsDA.hostGame("170552A", "Bill Gates", ld, "1900", 5));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}	
}


