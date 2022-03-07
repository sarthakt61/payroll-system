package ui_stuff;



//these names get longer and longer, sorry

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import animatefx.animation.FadeIn;
import database_stuff.*;
import employee_stuff.*;
import eu.iamgio.animated.AnimatedSwitcher;

public class IndividualCheckController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	public Employee ourEmployee;
	public TimeSheet ourTimeSheet; 
	public databaseConnection connectTryAC;
	public Connection dbConnectionAC;
	
    public EmployeeListController el = new EmployeeListController(); 
	
    @FXML
    private Label dentalInsurance;

    @FXML
    private Label eName21;

    @FXML
    private Label familyLeave;

    @FXML
    private Label federalTax;

    @FXML
    private Label grossEarnings;

    @FXML
    private Label grossEarningsDeduct;

    @FXML
    private Label healthInsurance;

    @FXML
    private Label lifeInsurance;

    @FXML
    private Label localTax;

    @FXML
    private Label mediCare;

    @FXML
    private Label disability;

    @FXML
    private Label retirement;

    @FXML
    private Label socialSecurity;

    @FXML
    private Label stateTax;




	public void switchToIndividualCheck(ActionEvent event, Employee emp, databaseConnection connectTry) throws IOException {

		ourEmployee = emp;
		ourTimeSheet = ourEmployee.getTimeSheet();
		System.out.println(ourEmployee.getName());
		connectTryAC = connectTry; 
		dbConnectionAC = connectTry.getConnection(); 

		FXMLLoader loader = new FXMLLoader(getClass().getResource("individualCheck.fxml"));
		loader.setController(this);
		root = loader.load();
		Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));

		setTextFields(); 
        stage.show();
		new FadeIn(root).play();


		// System.out.println(ourEmployee.getName());

	}
	
	
	private void setTextFields() {
		// TODO Auto-generated method stub
		//sheesh
		eName21.setText(ourEmployee.getName());
		grossEarnings.setText(String.valueOf(ourEmployee.calc.getGross()));		
		//null pointers are a result of gaps in database info
		//wait what, maybe not? 
		socialSecurity.setText(String.valueOf(ourEmployee.calc.getSocialSecurity()));
		mediCare.setText(String.valueOf(ourEmployee.calc.getMedicare()));	
		disability.setText(String.valueOf(ourEmployee.calc.getDisability()));
		familyLeave.setText(String.valueOf(ourEmployee.calc.getFamilyLeave()));
		federalTax.setText(String.valueOf(ourEmployee.calc.getFederal()));
		stateTax.setText(String.valueOf(ourEmployee.calc.getState()));
		localTax.setText(String.valueOf(ourEmployee.calc.getLocal()));
		healthInsurance.setText(String.valueOf(ourEmployee.calc.getHealth()));
		lifeInsurance.setText(String.valueOf(ourEmployee.calc.getLife()));
		dentalInsurance.setText(String.valueOf(ourEmployee.calc.getDental()));
		retirement.setText(String.valueOf(ourEmployee.calc.getRetirement()));
		grossEarningsDeduct.setText(String.valueOf(ourEmployee.calc.getNet()));
		
	}
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
