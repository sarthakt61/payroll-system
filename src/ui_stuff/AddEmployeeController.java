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

import javax.management.relation.Role;

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
import javafx.scene.control.Alert;
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
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class AddEmployeeController implements Initializable {
	
	
		private Stage stage;
		private Scene scene;
		private Parent root;
	
		public Employee ourEmployee;
		public TimeSheet ourTimeSheet; 
		public databaseConnection connectTryAE;
		public Connection dbConnectionAE;
		
		  @FXML
		    private Button Reset;

		    @FXML
		    private TextField empAddress;

		    @FXML
		    private TextField empDI;

		    @FXML
		    private TextField empDis;

		    @FXML
		    private TextField empEmail;

		    @FXML
		    private TextField empHI;

		    @FXML
		    private TextField empID;

		    @FXML
		    private TextField empLI;

		    @FXML
		    private TextField empName;

		    @FXML
		    private TextField empPass;

		    @FXML
		    private TextField empPayAmount;

		    @FXML
		    private TextField empPhone;

		    @FXML
		    private TextField empPos;

		    @FXML
		    private TextField empR;

		    @FXML
		    private TextField empSSN;

		    @FXML
		    private TextField empST;

		    @FXML
		    private TextField empType;

		    @FXML
		    private TextField empVT;

		    @FXML
		    private Button save;

	    
	   public String query = null;
	   public Connection connection = null;
	   public ResultSet resultSet = null;
	   public PreparedStatement preparedStatement;
	   public Employee employee = null;
	    public EmployeeListController el = new EmployeeListController(); 
	    private boolean update;
	    int studentId;

	    /**
	     * Initializes the controller class.
	     */
	    @Override
	    public void initialize(URL url, ResourceBundle rb) {
	        // TODO
	    }
	    
		public void switchToAddEmployeeScreen(ActionEvent event, Employee emp, databaseConnection connectTry, EmployeeListController EL) throws IOException {

			ourEmployee = emp;
			ourTimeSheet = ourEmployee.getTimeSheet();
			System.out.println(ourEmployee.getName());
			connectTryAE = connectTry; 
			dbConnectionAE = connectTry.getConnection(); 
			el = EL; 

			FXMLLoader loader = new FXMLLoader(getClass().getResource("addEmployee.fxml"));
			loader.setController(this);
			root = loader.load();
			Parent parent = loader.getRoot();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(parent));

	        stage.show();
			new FadeIn(root).play();


		}

	    public void save(ActionEvent event) {
	    	
	    	//employee table
	    	int eID = zeroOrNotInt(empID.getText()); 
	    	String pass = empPass.getText(); 
	        String name = empName.getText();
	        int payAmount = zeroOrNotInt(empPayAmount.getText()); 
	        String ssn = empSSN.getText();
	        String type = empType.getText();
	        String pos = empPos.getText();
	        
	        //Personal Info table
	        String phone = empPhone.getText(); 
	        String address = empAddress.getText(); 
	        String phoneNumber = empPhone.getText(); 
	        String email = empEmail.getText(); 
	        String dis = empDis.getText();
	        
	        //beni(e)fits table
	        double hi = zeroOrNot(empHI.getText());
	        double li = zeroOrNot(empLI.getText());
	        double di = zeroOrNot(empDI.getText());
	        double re = zeroOrNot(empR.getText());
	        double vt = zeroOrNot(empVT.getText());
	        double st = zeroOrNot(empST.getText());
	        
	        
	        //JMetro jMetro = new JMetro(Style.LIGHT);
	        //scene.getStylesheets().add(Alert.class.getResource("default-skin.css").toExternalForm());
	        //Have to include more error cases here; 
            Alert alert = new Alert(Alert.AlertType.ERROR);
	        if (name.isEmpty() || eID == 0|| ssn.isEmpty()
	        		|| type.isEmpty() || pos.isEmpty()) {
	            alert.setHeaderText(null);
	            alert.setContentText("Please Fill All DATA");
	            alert.showAndWait();
	            //return;

	        } //else if (Role != "")
	        
	        else {
		    	query = "INSERT INTO Employees (Employee_ID, Employee_Password, Employee_Name, SSN, Company_FK, Employee_Position, Pay_Type, Pay_Amount) VALUES(?, ?, ?, ?, ?, ?, ?,?);"+
		    			"INSERT INTO Personal_Information (Employee_ID_FK, Employee_Name_FK, Address, Phone_Number, Email, Disability) VALUES(?, ?, ?, ?, ?, ?);"+
		    			"INSERT INTO Benifits (Employee_ID_FK, Health, Life, Dental, Retirement, Vaccation_Time, Sick_Time) VALUES(?, ?, ?, ?, ?, ?, ?);" +
		    			"INSERT INTO Timesheet (Employee_ID_FK, Week_Date) VALUES (?, ?);";
		    	
				try {
					PreparedStatement stat = dbConnectionAE.prepareStatement(query);
					stat.setInt(1, eID);
					stat.setString(2,pass);
					stat.setString(3,name);
					stat.setString(4,ssn);
					stat.setInt(5,2); //placeholder -- my bad!
					stat.setString(6,pos);
					stat.setString(7,type);
					stat.setInt(8,payAmount);
					
					//9-14
					
					stat.setInt(9, eID);
					stat.setString(10, name);
					stat.setString(11, address);
					stat.setString(12, phoneNumber);
					stat.setString(13, email);
					stat.setString(14, dis);
					
					//15-21
					stat.setInt(15, eID);
					stat.setDouble(16, hi);
					stat.setDouble(17, li);
					stat.setDouble(18, di);
					stat.setDouble(19, re);
					stat.setDouble(20, vt);
					stat.setDouble(21, st);
					
					//generic timesheet creator, to avoid errors
					stat.setInt(22, eID);
					stat.setString(23,  "11-10-2021");
					
					if(stat.execute()) {
						System.out.println("worked");
					}else 
						System.out.println("ntet"); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	((Node)(event.getSource())).getScene().getWindow().hide();
	            el.refreshTable();

		    	}
	        }

	    
	    //accounting for empty strings
	    private double zeroOrNot(String test) {
	    	if (test.isEmpty()) {
	    		return 0;
	    	} else return Double.valueOf(test);
	    }
	    private int zeroOrNotInt(String test) {
	    	if (test.isEmpty()) {
	    		return 0;
	    	} else return Integer.valueOf(test);
	    }

	    @FXML
	    private void clean() {
	        empID.setText(null);
	        empName.setText(null);
	        empPass.setText(null);
	        empPos.setText(null);
	        empSSN.setText(null);
	        empType.setText(null);
	        
	    }

    	/*
    	 *	 	@FXML
			    private TextField empID;
		
			    @FXML
			    private TextField empName;
		
			    @FXML
			    private TextField empPass;
		
			    @FXML
			    private TextField empPayAmount;
		
			    @FXML
			    private TextField empPos;
		
			    @FXML
			    private TextField empSSN;
		
			    @FXML
			    private TextField empType;
		
			    @FXML
			    private Button save; 
    	 * */

}
