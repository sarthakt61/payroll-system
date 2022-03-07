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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import animatefx.animation.FadeIn;
import database_stuff.*;
import employee_stuff.*;
import eu.iamgio.animated.AnimatedSwitcher;

public class AccountController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	public Employee ourEmployee;
	public TimeSheet ourTimeSheet; 
	public databaseConnection connectTryAC;
	public Connection dbConnectionAC;
	
	public TextField uName;
	@FXML
	public TextField pass;
	@FXML
	public Label eName2;
	@FXML
	public Label week; 
	@FXML
	public Label empHours;
	@FXML
	public Label eRole;
	@FXML
	public Label beforeDeduct;
	@FXML
	public Label afterDeduct;
	@FXML
	public Button timeSheetButton;
	@FXML
	public Button DashButton;
	@FXML
	public Button empList; 
	@FXML
	public Button personalInfoButton;

	@FXML
	public Pane Dashboard;

	Alert errorAlert = new Alert(AlertType.ERROR);

	public void switchToAccountScreen(ActionEvent event, Employee emp, databaseConnection connectTry) throws IOException {

		ourEmployee = emp;
		ourTimeSheet = ourEmployee.getTimeSheet(); 
		System.out.println(ourEmployee.getName());
		connectTryAC = connectTry; 
		dbConnectionAC = connectTry.getConnection(); 
		
		
		System.out.println(ourEmployee.getRole());

		FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountScreen.fxml"));
		loader.setController(this);
		root = loader.load();
		// root = FXMLLoader.load(getClass().getResource("AccountScreen.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		eName2.setText(emp.getName());
		eRole.setText(emp.getRole());
		week.setText(emp.getTimeSheet().getWeekDate());
		empHours.setText(String.valueOf(ourEmployee.getEmployeeHours())); 
		
		beforeDeduct.setText(String.valueOf(ourEmployee.calc.getGross()));
		afterDeduct.setText(String.valueOf(ourEmployee.calc.getNet()));

		stage.show();
		new FadeIn(root).play();

		// System.out.println(ourEmployee.getName());

	}
	
	
	@FXML
	public void switchToStartUp(ActionEvent event) throws IOException {
		LoginControllers lc = new LoginControllers();
		lc.switchToStartUp(event);
	}
	
	
	@FXML
	public void prevPayPeriod(ActionEvent event) throws IOException {
		Statement statement;
		Statement statement2; 
		ResultSet queryOutput = null;
		ResultSet queryOutput2 = null; 
		int count = 0; 
		
		try {
			statement2 = dbConnectionAC.createStatement();
			queryOutput2 = statement2.executeQuery("SELECT COUNT(*) AS rowcount FROM timesheet");
			
			queryOutput2.next(); 
			count = queryOutput2.getInt("rowcount"); 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		for(int i = 1; i < count; i++) {
			try {
				
				/*
				 * Statement s = cd.createStatement();
					ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM timesheet");
					r.next();
					int count = r.getInt("rowcount");
				 */
	
				String mainQuery = "SELECT * FROM timesheet where ID =  " + (ourTimeSheet.getID() - i)
						+ " AND employee_id_fk = "+ ourEmployee.getID(); 
				statement = dbConnectionAC.createStatement();
				queryOutput = statement.executeQuery(mainQuery);
				
				while (queryOutput.next()) {
					//System.out.println(data); 
					
					TimeSheet TS = new TimeSheet(queryOutput.getInt("ID"), ourEmployee.getID(), queryOutput.getString("Week_Date"), 
							queryOutput.getDouble("Sunday"),queryOutput.getDouble("Monday"),
							queryOutput.getDouble("Tuesday"),queryOutput.getDouble("Wednesday"),
							queryOutput.getDouble("Thursday"),queryOutput.getDouble("Friday"),
							queryOutput.getDouble("Saturday"),queryOutput.getDouble("Vaccation_Total"),
							queryOutput.getDouble("Sick_Total"),queryOutput.getString("Verified"),
							queryOutput.getInt("Verification_ID"));
					ourEmployee.setTimeSheet(TS);
					ourEmployee.calc.insertPayment(ourEmployee.getID(), TS.getWeekDate()); 
					AccountController ac = new AccountController(); 
					ac.switchToAccountScreen(event, ourEmployee, connectTryAC);
					return; 
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void nexPayPeriod(ActionEvent event) throws IOException {
		Statement statement;
		Statement statement2; 
		ResultSet queryOutput = null;
		ResultSet queryOutput2 = null; 
		int count = 0; 
		
		try {
			statement2 = dbConnectionAC.createStatement();
			queryOutput2 = statement2.executeQuery("SELECT COUNT(*) AS rowcount FROM timesheet");
			
			queryOutput2.next(); 
			count = queryOutput2.getInt("rowcount"); 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		for(int i = 1; i < count; i++) {
			try {
		
				String mainQuery = "SELECT * FROM timesheet where ID =  " + (ourTimeSheet.getID() + i)
						+ " AND employee_id_fk = "+ ourEmployee.getID(); 
				statement = dbConnectionAC.createStatement();
				queryOutput = statement.executeQuery(mainQuery);
				
				while (queryOutput.next()) {
					//System.out.println(data); 
					
					TimeSheet TS = new TimeSheet(queryOutput.getInt("ID"), ourEmployee.getID(), queryOutput.getString("Week_Date"), 
							queryOutput.getDouble("Sunday"),queryOutput.getDouble("Monday"),
							queryOutput.getDouble("Tuesday"),queryOutput.getDouble("Wednesday"),
							queryOutput.getDouble("Thursday"),queryOutput.getDouble("Friday"),
							queryOutput.getDouble("Saturday"),queryOutput.getDouble("Vaccation_Total"),
							queryOutput.getDouble("Sick_Total"),queryOutput.getString("Verified"),
							queryOutput.getInt("Verification_ID"));
					ourEmployee.setTimeSheet(TS);
					ourEmployee.calc.insertPayment(ourEmployee.getID(), TS.getWeekDate()); 
					AccountController ac = new AccountController(); 
					ac.switchToAccountScreen(event, ourEmployee, connectTryAC);
					return;
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void handleClicks(ActionEvent event) throws IOException {
			if (event.getSource() == DashButton) {
				System.out.println("Pushed dash");
			}
			if (event.getSource() == timeSheetButton) {
				TimeSheetController ts = new TimeSheetController();
				ts.switchToTimeSheetScreen(event, ourEmployee, connectTryAC);
			System.out.println("Pushed timeSheet");
			}
		if(event.getSource() == empList) {
			EmployeeListController el = new EmployeeListController(); 
			el.switchToEmployeeList(event, ourEmployee,  connectTryAC);
			System.out.println("Pushed empList");
			}
		if (event.getSource() == personalInfoButton) {
			personalInfoController pi = new personalInfoController();
			pi.switchToPersonalInfoScreen(event, ourEmployee, connectTryAC);
			System.out.println("Pushed personalInfo");
			}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}
