package database_stuff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import employee_stuff.*;

public class databaseConnection {

	public Connection databaseLink;
	public int ein;

	public Connection getConnection() {

		String uName = "root";
		String pass = "23*Taipei"; //enter your password here
		String URL = "jdbc:mysql://localhost:3306/NOMENPAYROLL?allowMultiQueries=true";

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			databaseLink = DriverManager.getConnection(URL, uName, pass);

		} catch(Exception e) {
				e.printStackTrace();
			}

	return databaseLink;
	}
	
	public ResultSet employeeLookup(Connection db, String uName, String pass) {
		String mainQuery = "SELECT E.Employee_Name, E.Employee_Password FROM Employees "
				+ "E WHERE E.Employee_Name = " + "'" + uName + "'"
				+ " AND E.Employee_Password = " + "'" + pass + "'";
		Statement statement;
		ResultSet queryOutput = null;
		try {
			statement = db.createStatement();
			queryOutput = statement.executeQuery(mainQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queryOutput; 
	}
	
	
	public boolean CompanyExists(String comp) {//Checks if the company already exists within our DB
		int com = Integer.parseInt(comp);
		String q = "SELECT EIN FROM Company WHERE EIN = "+comp+";";
		PreparedStatement stat;
		try {
			stat = databaseLink.prepareStatement(q);
			ResultSet set = stat.executeQuery();
			if(!set.next()) {//if it is empty
				ein = com;
				return false;
			}else {
			do {//if it returns results if they are equal
			if(set.getInt("EIN")==com)
				return true;
			}while(set.next());
			}
			ein = com;//after checking the resultset if nothing matches its true
			return false;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean InsertCompD( String si,String li,String compName,String numEmp, String ownerName,String ownerEmail) {
		//validators would go here 
		
		String q = "INSERT INTO Company (EIN,State_ID,Local_ID,Company_Name,Num_Employees,Owner_Name,Owner_Email) values(?,?,?,?,?,?,?)";
		try {
			PreparedStatement stat = databaseLink.prepareStatement(q);
			stat.setInt(1,ein);
			stat.setString(2,si);
			stat.setString(3,li);
			stat.setString(4,compName);
			stat.setString(5,numEmp);
			stat.setString(6,ownerName);
			stat.setString(7,ownerEmail);
			if(stat.execute()) {
				return true;
			}else 
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean InsertEmp(int eID, String ePass,String eName,String ssn,String ePos,String pTy, String pAm) {
		//validators here
		int am = Integer.parseInt(pAm);
		String q = "INSERT INTO Employees (Employee_ID, Employee_Password, Employee_Name, SSN, Company_FK, Employee_Position, Pay_Type, Pay_Amount) VALUES(?, ?, ?, ?, ?, ?, ?,?);";
		try {
			PreparedStatement stat = databaseLink.prepareStatement(q);
			stat.setInt(1,eID);
			stat.setString(2,ePass);
			stat.setString(3,eName);
			stat.setString(4,ssn);
			stat.setInt(5,ein);
			stat.setString(6,ePos);
			stat.setString(7,pTy);
			stat.setInt(8,am);
			if(stat.execute()) {
				return true;
			}else 
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean InsertPinfo(int id,String name,String address,String pNumber, String email, String dis) {
		String q = "Insert Into personal_information values (?,?,?,?,?,?);";
		PreparedStatement stat;
		try {
			stat = databaseLink.prepareStatement(q);
			stat.setInt(1,id);
			stat.setString(2,name);
			stat.setString(3,address);
			stat.setString(4,pNumber);
			stat.setString(5,email);
			stat.setString(6,dis);
			if(stat.execute()) {
				return true;
			}else 
				return false;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Employee newEmployee(Connection db, String uName, String pass) {
		
		String mainQuery = "SELECT * FROM Employees "
				+ "E WHERE E.Employee_Name = " + "'" + uName + "'"
				+ " AND E.Employee_Password = " + "'" + pass + "'";
		
		Statement statement;
		ResultSet queryOutput = null;
		Employee E = null; 
		try {
			statement = db.createStatement();
			queryOutput = statement.executeQuery(mainQuery);
			
			if (queryOutput.next()) {
				E = new Employee(queryOutput.getString("Employee_Name"), queryOutput.getInt("Employee_ID"), 
						queryOutput.getString("Employee_Position"), queryOutput.getString("Pay_Type"),
						queryOutput.getDouble("Pay_Amount"));
				E.setTimeSheet(newTimeSheet(db, E.getID()));
				E.setPassword(pass);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return E;
	}
	
	/*CREATE TABLE Timesheet (
	Employee_ID_FK INT REFERENCES Employees(Employee_ID),
    Week_Date VARCHAR(10) NOT NULL,
    Sunday DOUBLE DEFAULT '0.00',
	Monday DOUBLE DEFAULT '0.00',
    Tuesday DOUBLE DEFAULT '0.00',
    Wednesday DOUBLE DEFAULT '0.00',
    Thursday DOUBLE DEFAULT '0.00',
    Friday DOUBLE DEFAULT '0.00',
    Saturday DOUBLE DEFAULT '0.00',
    Vaccation_Total DOUBLE DEFAULT '0.00',
    Sick_Total DOUBLE DEFAULT '0.00',
    Verified VARCHAR(3) DEFAULT "NO",
    Verification_ID INT DEFAULT 0
	);
    */
	
	public TimeSheet newTimeSheet(Connection db, int eID) {
		
		/*select *
			from timesheet where employee_ID_FK = 1
			order by ID desc
			limit 1;
		 * 
		 * */
		
		String mainQuery = "SELECT * FROM Timesheet "
				+ "T WHERE T.Employee_ID_FK = " + "'" + eID + "'" +
				"ORDER BY ID DESC LIMIT 1";
		
		Statement statement;
		ResultSet queryOutput = null;
		TimeSheet TS = null; 
		try {
			statement = db.createStatement();
			queryOutput = statement.executeQuery(mainQuery);
			
			if (queryOutput.next()) {
				TS = new TimeSheet(queryOutput.getInt("ID"),eID, queryOutput.getString("Week_Date"), 
						queryOutput.getDouble("Sunday"),queryOutput.getDouble("Monday"),
						queryOutput.getDouble("Tuesday"),queryOutput.getDouble("Wednesday"),
						queryOutput.getDouble("Thursday"),queryOutput.getDouble("Friday"),
						queryOutput.getDouble("Saturday"),queryOutput.getDouble("Vaccation_Total"),
						queryOutput.getDouble("Sick_Total"),queryOutput.getString("Verified"),
						queryOutput.getInt("Verification_ID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return TS;
	}
}
