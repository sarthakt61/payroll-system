package ui_stuff;

import java.io.IOException;
import java.sql.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import database_stuff.databaseConnection;
import employee_stuff.Employee;

public class SignUpControllers {

	
	public databaseConnection db = new databaseConnection();
	public Connection dbConnection = db.getConnection();

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public Employee ourEmployee; 
	public int ein; 
	
	public void switchToStartUp(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("StartUpScreen.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToSignUp(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpScreen.fxml"));
		loader.setController(this);
		root = loader.load();
		// root = FXMLLoader.load(getClass().getResource("AccountScreen.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToCompSignUp(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CompSignUp.fxml"));
		loader.setController(this);
		root = loader.load();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToHrSignUp(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HRSignUp.fxml"));
		loader.setController(this);
		root = loader.load();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToPInfoSignUp(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("pInfoSignUp.fxml"));
		loader.setController(this);
		root = loader.load();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	@FXML TextField EIN;
	public void signUp(ActionEvent event) throws IOException {
		Alert errorAlert = new Alert(AlertType.ERROR);
		System.out.println(EIN.getText());
		if(!db.CompanyExists(EIN.getText())) {
			//ein = Integer.valueOf(EIN.getText()); 
			//System.out.print("Company doesnt exist");
			//Stage stage = (Stage) ((Node) e.getTarget()).getScene().getWindow();
			//stage.setScene(compSignup());
			switchToCompSignUp(event);
		}else {	//error pop up
				errorAlert.setHeaderText("Error");
				errorAlert.setContentText("Invalid Company already exists.");
				errorAlert.showAndWait();
		}
	}
	
	@FXML TextField stateID, localID, compName, empNum, oName, oEmail;
	public void compSignUp(ActionEvent event) throws IOException{
		Alert errorAlert = new Alert(AlertType.ERROR);
		if(!db.InsertCompD(stateID.getText(), localID.getText(), compName.getText(), empNum.getText(), 
				oName.getText(), oEmail.getText())) {
			//System.out.print("Company doesnt exist");
			//Stage stage = (Stage) ((Node) e.getTarget()).getScene().getWindow();
			//stage.setScene(hrSignup());
			/*root = FXMLLoader.load(getClass().getResource("HrSignUp.fxml"));
			stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();*/
			switchToHrSignUp(event); 
		}else {	//error pop up
				errorAlert.setHeaderText("Error");
				errorAlert.setContentText("Invalid input please retype and try again.");
				errorAlert.showAndWait();
		}
	}
	
	@FXML TextField eID, name, ssn, password, confirm, pTy, pAm;
	@FXML
	public void HrSignUp(ActionEvent event) throws IOException {
		Alert errorAlert = new Alert(AlertType.ERROR);
		int ei = Integer.parseInt(eID.getText()); 
		if(password.getText().equals(confirm.getText())) {//password check
			
			if(!db.InsertEmp(ei, password.getText(), name.getText(), ssn.getText(), "HR", pTy.getText(), pAm.getText())) {
				
				//System.out.print("Company doesnt exist");
				//ourEmployee = db.newEmployee(dbConnection, name.getText(), password.getText());
				//Stage stage = (Stage) ((Node) e.getTarget()).getScene().getWindow();
				//stage.setScene(pinfoSignup());
				
				LoginControllers lc = new LoginControllers();
				lc.switchToSignIn(event);
				
			}else {	//error pop up
				errorAlert.setHeaderText("Error");
				errorAlert.setContentText("Invalid input please retype and try again.");
				errorAlert.showAndWait();
			}
		}else{
			errorAlert.setHeaderText("Error");
			errorAlert.setContentText("Passwords do not match");
			errorAlert.showAndWait();
		}
	}
	
	@FXML TextField address, pNumber, email, dis;
	public void pInfoSignUp(ActionEvent event) throws IOException {
		Alert errorAlert = new Alert(AlertType.ERROR);
		if(!db.InsertPinfo(ourEmployee.getID(), ourEmployee.getName(), address.getText(), pNumber.getText(),
				email.getText(),dis.getText())) {
			//Stage stage = (Stage) ((Node) e.getTarget()).getScene().getWindow();
			//stage.setScene(finalSignup());
			
			LoginControllers lc = new LoginControllers();
			lc.switchToSignIn(event);
			
		}else {	//error pop up
			errorAlert.setHeaderText("Error");
			errorAlert.setContentText("Invalid input please retype and try again.");
			errorAlert.showAndWait();
		}
	}
	
	
	

	
	
	
}
