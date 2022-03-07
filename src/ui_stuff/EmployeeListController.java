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

public class EmployeeListController implements Initializable {
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	public Employee ourEmployee;
	public TimeSheet ourTimeSheet;
	 
	public databaseConnection connectTryEL;
	public Connection dbConnectionEL;
	
	@FXML
	public Button timeSheetButton;
	@FXML
	public Button DashButton;
	@FXML
	public Button empList;
	@FXML
	public Button personalInfoButton;
	@FXML
	public Button addEmployeeButton; 
	@FXML
	private Label eName2;
	@FXML
	private Label eRole;
	
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Employee> employeeTable;

	@FXML
	private TableColumn<Employee, String> colID;

	@FXML
	private TableColumn<Employee, String> colName;

	@FXML
	private TableColumn<Employee, String> colRole;
	
	@FXML
	private TableColumn<Employee, String> colType;

	@FXML
	private TableColumn<Employee, String> colSalary;
	
	@FXML
	private TableColumn<Employee, String> colMod;
	
	public ObservableList<Employee> data = FXCollections.observableArrayList();
	
	public Employee otherEmployee = null;
	
	EmployeeListController el = this; 
	


	public void switchToEmployeeList(ActionEvent event, Employee emp, databaseConnection connectTry) throws IOException {
		// TODO Auto-generated method stub
		ourEmployee = emp;
		ourTimeSheet = ourEmployee.getTimeSheet(); 
	
		System.out.println(ourEmployee.getName());

		connectTryEL = connectTry; 
		dbConnectionEL = connectTry.getConnection(); 
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("employeeManage.fxml"));
		loader.setController(this);
		root = loader.load();
		// root = FXMLLoader.load(getClass().getResource("AccountScreen.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		eName2.setText(emp.getName());
		eRole.setText(emp.getRole());

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
			System.out.println("Pushed empList");
		}
		if (event.getSource() == personalInfoButton) {
			personalInfoController pi = new personalInfoController();
			pi.switchToPersonalInfoScreen(event, ourEmployee, connectTryEL);
			System.out.println("Pushed personalInfo");
		}
		if (event.getSource() == addEmployeeButton) {
			AddEmployeeController AE = new AddEmployeeController();
			AE.switchToAddEmployeeScreen(event, ourEmployee, connectTryEL, this);
			System.out.println("Pushed addEmployee");
		}
	}
	
	public void refreshTable() {
		Statement statement;
		ResultSet queryOutput = null;
		try {
			data.clear(); 
			String mainQuery = "SELECT * FROM Employees where NOT Employee_ID = "
					+ ourEmployee.getID(); 
			statement = dbConnectionEL.createStatement();
			queryOutput = statement.executeQuery(mainQuery);
			
			while (queryOutput.next()) {
				//System.out.println(data); 
				data.add(new Employee(queryOutput.getString("Employee_Name"), queryOutput.getInt("Employee_ID"), 
						queryOutput.getString("Employee_Position"), queryOutput.getString("Pay_Type"),
						queryOutput.getDouble("Pay_Amount")));
				
				employeeTable.setItems(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 filterTable();
	}
	
	public void filterTable() {
		
		//this is the search/filter functionality 
		FilteredList<Employee> searchedData = new FilteredList<>(data, b -> true);
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
				searchedData.setPredicate(employee ->  {
						if (newValue == null || newValue.isEmpty()) {
								return true; 
						}
				String lowerCaseFilter = newValue.toLowerCase(); 
				
				if (employee.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				} else if (employee.getRole().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				} else if (String.valueOf(employee.getID()).indexOf(lowerCaseFilter) != -1) {
					return true; 
				} else return false; 
			});
		}); 
		
		SortedList<Employee> sortedData = new SortedList<>(searchedData);
		
		sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());
		employeeTable.setItems(sortedData);
	}
	
	public void loadData() {
        
        refreshTable();
        
		colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		colRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
		colType.setCellValueFactory(new PropertyValueFactory<>("PayType"));
		colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
		
		employeeTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
		    if (newValue != null) {
		        System.out.println("Selected Person: "
		            + newValue.getID() + " | "
		            + newValue.getName()
		        );
		   }
		});
		
        
        //add cell of button edit 
         Callback<TableColumn<Employee, String>, TableCell<Employee, String>> cellFoctory = (TableColumn<Employee, String> param) -> {
            // make cell containing buttons
            final TableCell<Employee, String> cell = new TableCell<Employee, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                    	
                        //HBox deleteIcon = new HBox();
                        FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
                        FontAwesomeIcon editIcon = new FontAwesomeIcon();

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            
                            try {
                            	//employeeTable.getSelectionModel().setCellSelectionEnabled(true);
                                Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

                                System.out.println(selectedEmployee.getID());
                                String mainQuery = "DELETE FROM Employees WHERE employee_id  =" + selectedEmployee.getID() + 
                                		";DELETE FROM Benifits WHERE employee_id_fk  =" + selectedEmployee.getID() + 
                                		";DELETE FROM personal_information WHERE employee_id_fk  =" + selectedEmployee.getID();
                                PreparedStatement preparedStatement = dbConnectionEL.prepareStatement(mainQuery);
                                preparedStatement.execute();
                                refreshTable();
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(EmployeeListController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                           

                        });
                        
						editIcon.setOnMouseClicked((MouseEvent event) -> {
						                            
						                    Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
						                    personalInfoPortController pip = new personalInfoPortController(); 
						                    try {
												pip.switchToPersonalInfoPortScreen(null, selectedEmployee, connectTryEL, el); 
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
						                                                    
						
						                        });


                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
         colMod.setCellFactory(cellFoctory);
         employeeTable.setItems(data);
		 filterTable();
    }

    //unfinished
	public void calcGrossPay() {
		double grossPay = 0; 
		for (Employee e : employeeTable.getItems()) {
			grossPay += e.calc.getGross(); 
		    // owner column data:
		    
		    // ...
		}
		System.out.println(grossPay); 
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { 
		loadData(); 
	}

}
