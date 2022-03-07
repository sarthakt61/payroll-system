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

public class personalInfoPortController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	public Employee ourEmployee;
	public TimeSheet ourTimeSheet; 
	public databaseConnection connectTryAC;
	public Connection dbConnectionAC;
	
    public EmployeeListController el = new EmployeeListController(); 
	
		@FXML
	    private TextField empAd;

	    @FXML
	    private TextField empDi;

	    @FXML
	    private TextField empDis;

	    @FXML
	    private TextField empEm;

	    @FXML
	    private TextField empHi;

	    @FXML
	    private TextField empLi;

	    @FXML
	    private TextField empName;

	    @FXML
	    private TextField empPn;

	    @FXML
	    private TextField empRe;

	    @FXML
	    private TextField empRole;

	    @FXML
	    private TextField empSt;

	    @FXML
	    private TextField empVt;

	    @FXML
	    private Button setEmpAd;

	    @FXML
	    private Button setEmpDi;

	    @FXML
	    private Button setEmpDis;

	    @FXML
	    private Button setEmpEm;

	    @FXML
	    private Button setEmpHi;

	    @FXML
	    private Button setEmpLi;

	    @FXML
	    private Button setEmpName;

	    @FXML
	    private Button setEmpPn;

	    @FXML
	    private Button setEmpRe;

	    @FXML
	    private Button setEmpRole;

	    @FXML
	    private Button setEmpSt;

	    @FXML
	    private Button setEmpVt;


	Alert errorAlert = new Alert(AlertType.ERROR);

	public void switchToPersonalInfoPortScreen(ActionEvent event, Employee emp, databaseConnection connectTry, EmployeeListController EL) throws IOException {

		ourEmployee = emp;
		ourTimeSheet = ourEmployee.getTimeSheet();
		System.out.println(ourEmployee.getName());
		connectTryAC = connectTry; 
		dbConnectionAC = connectTry.getConnection(); 
		el = EL; 

		FXMLLoader loader = new FXMLLoader(getClass().getResource("personalInfoPort.fxml"));
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
		empName.setPromptText(ourEmployee.getName());
		empRole.setPromptText(ourEmployee.getRole());
		//null pointers are a result of gaps in database info
		//wait what, maybe not? 
		empAd.setPromptText(ourEmployee.getPersonalInfo().getAddress());
		empPn.setPromptText(ourEmployee.getPersonalInfo().getPhoneNumber());
		empEm.setPromptText(ourEmployee.getPersonalInfo().getEmail());
		empDis.setPromptText(ourEmployee.getPersonalInfo().getDisability());
		empHi.setPromptText(String.valueOf(ourEmployee.getBenefits().getHealth()));
		empLi.setPromptText(String.valueOf(ourEmployee.getBenefits().getLife()));
		empDi.setPromptText(String.valueOf(ourEmployee.getBenefits().getDental()));
		empRe.setPromptText(String.valueOf(ourEmployee.getBenefits().getRetire()));
		empVt.setPromptText(String.valueOf(ourEmployee.getBenefits().getVacTime()));
		empSt.setPromptText(String.valueOf(ourEmployee.getBenefits().getSicTime()));
		
	}
	
	@FXML
	private void generateCheck(ActionEvent event) {
		IndividualCheckController ic = new IndividualCheckController(); 
		try {
			ic.switchToIndividualCheck(event, ourEmployee, connectTryAC);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ah christ how many of these do I have to...
	@FXML
	private void setName() throws SQLException {
		String newName = empName.getText(); 
		/*UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;*/
		String query = "UPDATE Employees SET Employee_Name =" + "'" 
		+ newName + "'" + "WHERE Employee_ID = " + ourEmployee.getID();
		PreparedStatement stat = dbConnectionAC.prepareStatement(query);
		if(stat.execute()) {
			empName.setPromptText(newName);
		}
		el.refreshTable(); 
	}
	@FXML
	private void setRole() throws SQLException {
		String newRole = empRole.getText(); 
		/*UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;*/
		String query = "UPDATE Employees SET Employee_Position =" + "'" 
		+ newRole+ "'" + "WHERE Employee_ID = " + ourEmployee.getID();
		PreparedStatement stat = dbConnectionAC.prepareStatement(query);
		if(stat.execute()) {
			empName.setPromptText(newRole);
		}
		el.refreshTable(); 
	}
	
	@FXML
	private void setAddress() throws SQLException {
		String newAd = empAd.getText(); 
		/*UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;*/
		String query = "UPDATE Personal_Information SET Address =" + "'" 
		+ newAd + "'" + "WHERE Employee_ID_FK = " + ourEmployee.getID();
		PreparedStatement stat = dbConnectionAC.prepareStatement(query);
		if(stat.execute()) {
			empName.setPromptText(newAd);
		}
		el.refreshTable(); 
	}
	
	@FXML
	private void setPhoneNumber() throws SQLException {
		String newPn = empPn.getText(); 
		/*UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;*/
		String query = "UPDATE Personal_Information SET Phone_Number =" + "'" 
		+ newPn + "'" + "WHERE Employee_ID_FK = " + ourEmployee.getID();
		PreparedStatement stat = dbConnectionAC.prepareStatement(query);
		if(stat.execute()) {
			empName.setPromptText(newPn);
		}
		el.refreshTable(); 
	}
	
	@FXML
	private void setEmail() throws SQLException {
		String newEm = empEm.getText(); 
		/*UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;*/
		String query = "UPDATE Personal_Information SET Email =" + "'" 
		+ newEm + "'" + "WHERE Employee_ID_FK = " + ourEmployee.getID();
		PreparedStatement stat = dbConnectionAC.prepareStatement(query);
		if(stat.execute()) {
			empName.setPromptText(newEm);
		}
		el.refreshTable(); 
	}


	@FXML
	private void setDisability() throws SQLException {
		String newDis = empDis.getText(); 
		/*UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;*/
		String query = "UPDATE Personal_Information SET Disability =" + "'" 
		+ newDis + "'" + "WHERE Employee_ID_FK = " + ourEmployee.getID();		
		PreparedStatement stat = dbConnectionAC.prepareStatement(query);
		if(stat.execute()) {
			empName.setPromptText(newDis);
		}
		el.refreshTable(); 
	}

	@FXML
	private void setHealthInsurance() throws SQLException {
		String newHi = empHi.getText(); 
		/*UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;*/
		String query = "UPDATE Benifits SET Health =" + "'" 
		+ newHi + "'" + "WHERE Employee_ID_FK = " + ourEmployee.getID();
		PreparedStatement stat = dbConnectionAC.prepareStatement(query);
		if(stat.execute()) {
			empName.setPromptText(newHi);
		}
		el.refreshTable(); 
	}
	
	@FXML
	private void setLifeInsurance() throws SQLException {
		String newLi = empLi.getText(); 
		/*UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;*/
		String query = "UPDATE Benifits SET Life =" + "'" 
		+ newLi + "'" + "WHERE Employee_ID_FK = " + ourEmployee.getID();
		PreparedStatement stat = dbConnectionAC.prepareStatement(query);
		if(stat.execute()) {
			empName.setPromptText(newLi);
		}
		el.refreshTable(); 
	}
	
	@FXML
	private void setDentalInsurance() throws SQLException {
		String newDi = empDi.getText(); 
		/*UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;*/
		String query = "UPDATE Benifits SET Dental =" + "'" 
		+ newDi + "'" + "WHERE Employee_ID_FK = " + ourEmployee.getID();
		PreparedStatement stat = dbConnectionAC.prepareStatement(query);
		if(stat.execute()) {
			empName.setPromptText(newDi);
		}
		el.refreshTable(); 
	}
	
	@FXML
	private void setRetirement() throws SQLException {
		String newRe = empRe.getText(); 
		/*UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;*/
		String query = "UPDATE Benifits SET Retirement =" + "'" 
		+ newRe + "'" + "WHERE Employee_ID_FK = " + ourEmployee.getID();
		PreparedStatement stat = dbConnectionAC.prepareStatement(query);
		if(stat.execute()) {
			empName.setPromptText(newRe);
		}
		el.refreshTable(); 
	}

	@FXML
	private void setVacationTime() throws SQLException {
		String newVt = empVt.getText(); 
		/*UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;*/
		String query = "UPDATE Benifits SET Vaccation_Time =" + "'" 
		+ newVt + "'" + "WHERE Employee_ID_FK = " + ourEmployee.getID();
		PreparedStatement stat = dbConnectionAC.prepareStatement(query);
		if(stat.execute()) {
			empName.setPromptText(newVt);
		}
		el.refreshTable(); 
	}
	
	@FXML
	private void setSickTime() throws SQLException {
		String newSt = empSt.getText(); 
		/*UPDATE table_name
		SET column1 = value1, column2 = value2, ...
		WHERE condition;*/
		String query = "UPDATE Benifits SET Sick_Time =" + "'" 
		+ newSt + "'" + "WHERE Employee_ID_FK = " + ourEmployee.getID();
		PreparedStatement stat = dbConnectionAC.prepareStatement(query);
		if(stat.execute()) {
			empName.setPromptText(newSt);
		}
		el.refreshTable(); 
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
