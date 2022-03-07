package ui_stuff;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.pixelduke.control.skin.FXSkins;

import animatefx.animation.FadeIn;

public class testRunner extends Application {
	// JMetro jMetro = new JMetro(Style.DARK);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
		try {
			Parent root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
			Scene scene = new Scene(root);

			scene.getStylesheets().add(FXSkins.getStylesheetURL());
			// scene.getStylesheets().add(TextFieldSample.class.getResource("default-skin.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();

			new FadeIn(root).play();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
