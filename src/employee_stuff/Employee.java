package employee_stuff;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.IIOException;

import database_stuff.databaseConnection;

/*
 * Employee_ID INT PRIMARY KEY,
	Employee_Password VARCHAR(100) NOT NULL,
    Employee_Name VARCHAR (100) NOT NULL,
    SSN VARCHAR(11) UNIQUE NOT NULL,
    Company_FK INT REFERENCES Company(EID),
    Employee_Position VARCHAR(25) NOT NULL,
    Pay_Type VARCHAR(10) NOT NULL,
    Pay_Amount INT NOT NULL*/

public class Employee {
	private int emplID; 
	private String name;
	private String password; 
	private String role;
	private String payType; 
	private double salary;
	private TimeSheet ts; 
	private personalInfo pi; 
	private Benefits b; 
	public Calculations calc; 
	
	private double totalHours; 

	public Employee(String n, int ID, String r, String pt, double s){
		name = n; 
		emplID = ID; 
		role = r;
		payType = pt; 
		salary = s; 
		calc = new Calculations(); 
		
		
		databaseConnection connectTry = new databaseConnection(); // attempts connection
		Connection dbConnection = connectTry.getConnection();
		
		String tsQuery = "SELECT * FROM Timesheet T, Personal_Information P, Benifits B "
				+ "WHERE T.Employee_ID_FK = " + "'" + ID + "'" +
				"AND P.Employee_ID_FK = " + "'" + ID + "'" +
				"AND B.Employee_ID_FK = " + "'" + ID + "'";
		
		Statement statement;
		ResultSet queryOutput = null;

		//creates timeSheet (probably not the most ideal way)
		try {
			statement = dbConnection.createStatement();
			queryOutput = statement.executeQuery(tsQuery);
			
			if (queryOutput.next()) {
				this.ts = new TimeSheet(queryOutput.getInt(ID),emplID, queryOutput.getString("Week_Date"), 
						queryOutput.getDouble("Sunday"),queryOutput.getDouble("Monday"),
						queryOutput.getDouble("Tuesday"),queryOutput.getDouble("Wednesday"),
						queryOutput.getDouble("Thursday"),queryOutput.getDouble("Friday"),
						queryOutput.getDouble("Saturday"),queryOutput.getDouble("Vaccation_Total"),
						queryOutput.getDouble("Sick_Total"),queryOutput.getString("Verified"),
						queryOutput.getInt("Verification_ID"));
				this.pi = new personalInfo(ID, queryOutput.getString("address"), 
						queryOutput.getString("Phone_Number"), queryOutput.getString("Email"),
						queryOutput.getString("Disability")); 
				this.b = new Benefits(ID, queryOutput.getInt("ID"), 
						queryOutput.getDouble("Health"), queryOutput.getDouble("Life"),
						queryOutput.getDouble("Dental"), queryOutput.getDouble("Retirement"),
						queryOutput.getDouble("Vaccation_Time"), queryOutput.getDouble("Sick_Time")); 
				
				this.calc.insertPayment(emplID,ts.getWeekDate()); 
			}
			queryOutput.close(); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

	public double getEmployeeHours() {
		totalHours = this.calc.getTotalHours(emplID, ts.getWeekDate()); 
		return totalHours; 
	}

	public int getID() {
		return emplID;
	}


	public void setID(int emplID) {
		this.emplID = emplID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getPayType() {
		return payType;
	}


	public void setPayType(String payType) {
		this.payType = payType;
	}


	public double getSalary() {
		return salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public TimeSheet getTimeSheet() {
		return ts;
	}


	public void setTimeSheet(TimeSheet ts) {
		this.ts = ts;
	}

	public Benefits getBenefits() {
		return b; 
	}

	public personalInfo getPersonalInfo() {
		return pi;
	}


	public void setPersonalInfo(personalInfo pi) {
		this.pi = pi;
	}
}