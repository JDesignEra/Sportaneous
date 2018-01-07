package modules;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TransitionAnimation {
	public void fadeIn(double duration, Node node, double fromValue) {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), node);
		fadeTransition.setFromValue(fromValue);
		fadeTransition.setToValue(1.0);
		
		fadeTransition.play();
	}
	
	public void fadeOut(double duration, Node node, double fromValue) {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), node);
		fadeTransition.setFromValue(fromValue);
		fadeTransition.setToValue(0);
		
		fadeTransition.play();
	}
	
	public void fromLeftFadeIn(double duration, Node node, double translateFromX) {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration));
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1.0);

		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration));
		translateTransition.setFromX(translateFromX);
		translateTransition.setToX(0);

		ParallelTransition parallelTransition = new ParallelTransition(node, fadeTransition, translateTransition);
		parallelTransition.play();
	}

	public void fromRightFadeIn(double duration, Node node, double translateFromY) {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration));
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1.0);

		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration));
		translateTransition.setFromX(-(translateFromY));
		translateTransition.setToX(0);

		ParallelTransition parallelTransition = new ParallelTransition(node, fadeTransition, translateTransition);
		parallelTransition.play();
	}
}
