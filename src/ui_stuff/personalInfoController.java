package ui_stuff;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import animatefx.animation.FadeIn;
import database_stuff.databaseConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import employee_stuff.Employee;
import employee_stuff.TimeSheet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class personalInfoController implements Initializable {
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	public Employee ourEmployee;
	public TimeSheet ourTimeSheet;
	 
	public databaseConnection connectTryEL;
	public Connection dbConnectionEL;
	
    @FXML
    private Button DashButton;

    @FXML
    private Label address;

    @FXML
    private Label dental;

    @FXML
    private Label disability;

    @FXML
    private Label eName2;

    @FXML
    private Label eRole;
    
    @FXML
    private Label eName21;

    @FXML
    private Label eRole1;

    @FXML
    private Label email;

    @FXML
    private Button empList;

    @FXML
    private Label health;

    @FXML
    private Label life;

    @FXML
    private Button personalInfoButton;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label retire;

    @FXML
    private Label sick;

    @FXML
    private Button timeSheetButton;

    @FXML
    private Label vacation;
	


	public void switchToPersonalInfoScreen(ActionEvent event, Employee emp,
			databaseConnection connectTry) throws IOException {
		// TODO Auto-generated method stub

		ourEmployee = emp;
		ourTimeSheet = ourEmployee.getTimeSheet(); 
		/*if(ourEmployee.getRole().equals("Employee") || ourEmployee.getRole().equals("Manager") ) {
		empList.setVisible(false);
		System.out.println("I'm a dumbass");
	}*/
		System.out.println(ourEmployee.getName());

		connectTryEL = connectTry; 
		dbConnectionEL = connectTry.getConnection(); 
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("personalInfo.fxml"));
		loader.setController(this);
		root = loader.load();
		// root = FXMLLoader.load(getClass().getResource("AccountScreen.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		eName2.setText(emp.getName());
		eRole.setText(emp.getRole());
		eName21.setText(emp.getName());
		eRole1.setText(emp.getRole());
		address.setText(emp.getPersonalInfo().getAddress());
		phoneNumber.setText(emp.getPersonalInfo().getPhoneNumber());
		email.setText(emp.getPersonalInfo().getEmail());
		disability.setText(emp.getPersonalInfo().getDisability());
		
		health.setText(String.valueOf(emp.getBenefits().getHealth()));
		life.setText(String.valueOf(emp.getBenefits().getHealth()));
		dental.setText(String.valueOf(emp.getBenefits().getHealth()));
		retire.setText(String.valueOf(emp.getBenefits().getHealth()));
		vacation.setText(String.valueOf(emp.getBenefits().getHealth()));
		sick.setText(String.valueOf(emp.getBenefits().getHealth()));

		stage.show();
		new FadeIn(root).play();
		
	}
	
	public void handleClicks(ActionEvent event) throws IOException {
		if (event.getSource() == DashButton) {
			AccountController Ac = new AccountController();
			Ac.switchToAccountScreen(event, ourEmployee, connectTryEL);
			System.out.println("Pushed dash");
		}
		if (event.getSource() == timeSheetButton) {
				TimeSheetController ts = new TimeSheetController();
				ts.switchToTimeSheetScreen(event, ourEmployee, connectTryEL);
			System.out.println("Pushed timeSheet");
		}
		if(event.getSource() == empList) {
			EmployeeListController el = new EmployeeListController(); 
			el.switchToEmployeeList(event, ourEmployee, connectTryEL);
			System.out.println("Pushed empList");
		}
		if (event.getSource() == personalInfoButton) {
			System.out.println("Pushed personalInfo");
		}
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { 
	
	}

}
