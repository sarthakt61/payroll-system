package ui_stuff;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import animatefx.animation.FadeIn;
import ui_stuff.AccountController;
import database_stuff.*;
import employee_stuff.*;
import eu.iamgio.animated.AnimatedSwitcher;

public class LoginControllers implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	public Employee ourEmployee;
	public TimeSheet ourTimeSheet; 
	public databaseConnection connectTry = new databaseConnection(); // attempts connection
	public Connection dbConnection = connectTry.getConnection();

	ResultSet queryOutput;

	@FXML
	public Button connect;
	@FXML
	public Button irritation;
	@FXML
	public TextField uName;
	@FXML
	public TextField pass;
	@FXML
	public Label eName2;
	@FXML
	public Label fooker;
	@FXML
	public Button timeSheetButton;

	Alert errorAlert = new Alert(AlertType.ERROR);

	public void switchToSignIn(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("SigninScreen.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		new FadeIn(root).play();

	}

	public void switchToSignUp(ActionEvent event) throws IOException {
		SignUpControllers SUC = new SignUpControllers(); 
		SUC.switchToSignUp(event);
	}

	public void switchToStartUp(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		new FadeIn(root).play();
	}

	public void signIn(ActionEvent event) {

		try {
			queryOutput = connectTry.employeeLookup(dbConnection, uName.getText(), pass.getText());
			if (queryOutput.next()) {
				ourEmployee = connectTry.newEmployee(dbConnection, uName.getText(), pass.getText());
				ourTimeSheet = connectTry.newTimeSheet(dbConnection, ourEmployee.getID()); 
				System.out.println(ourEmployee.getName());
				AccountController Ac = new AccountController();
				Ac.switchToAccountScreen(event, ourEmployee, connectTry);

			} else {
				errorAlert.setHeaderText("No");
				errorAlert.setContentText("Invalid Username or Password");
				errorAlert.showAndWait();

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void setLbl(ActionEvent event) {
		fooker.setText("changed fook");
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}
