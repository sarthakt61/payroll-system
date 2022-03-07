package employee_stuff;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class Calculations {
	
	double gross = 0, federal, state, local, socialSecurity, medicare, disability, familyLeave, net;
    double health = 0, life = 0, dental = 0, retirement = 0;

	
	public databaseConnection connectTry = new databaseConnection(); // attempts connection
	public Connection dbConnection = connectTry.getConnection();
	
    public double getGross() {
		return gross;
	}

	public void setGross(double gross) {
		this.gross = gross;
	}

	public double getFederal() {
		return federal;
	}

	public void setFederal(double federal) {
		this.federal = federal;
	}

	public double getState() {
		return state;
	}

	public void setState(double state) {
		this.state = state;
	}

	public double getLocal() {
		return local;
	}

	public void setLocal(double local) {
		this.local = local;
	}

	public double getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(double socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public double getMedicare() {
		return medicare;
	}

	public void setMedicare(double medicare) {
		this.medicare = medicare;
	}

	public double getDisability() {
		return disability;
	}

	public void setDisability(double disability) {
		this.disability = disability;
	}

	public double getFamilyLeave() {
		return familyLeave;
	}

	public void setFamilyLeave(double familyLeave) {
		this.familyLeave = familyLeave;
	}

	public double getNet() {
		return net;
	}

	public void setNet(double net) {
		this.net = net;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getLife() {
		return life;
	}

	public void setLife(double life) {
		this.life = life;
	}

	public double getDental() {
		return dental;
	}

	public void setDental(double dental) {
		this.dental = dental;
	}

	public double getRetirement() {
		return retirement;
	}

	public void setRetirement(double retirement) {
		this.retirement = retirement;
	}
	
	
	public double getStateTax(int id) {
		
		//databaseConnection connectTry = new databaseConnection(); // attempts connection
		//Connection dbConnection = connectTry.getConnection();

        String q = "SELECT Pay_Amount FROM Employees WHERE Employee_ID = "+id+";";
        PreparedStatement stat;

        try {

            stat = dbConnection.prepareStatement(q);
            ResultSet set = stat.executeQuery();

            set.next();

            if (set.getInt(1) < 21401) {

                return 0.059;

            } else if (set.getInt(1) >= 21401 && set.getInt(1) < 80650) {

                return 0.0597;

            } else if (set.getInt(1) >= 80650 && set.getInt(1) < 215400) {

                return 0.0633;

            } else if (set.getInt(1) >= 215400) {

                return 0.0685;

            }

        } catch (SQLException e) {

            //TODO Auto-generated catch block
            e.printStackTrace();

        }

        return 0;

    }

	public double getFederalTax(int id) {
		
			databaseConnection connectTry = new databaseConnection(); // attempts connection
			Connection dbConnection = connectTry.getConnection();
	
	        String q = "SELECT Pay_Amount FROM Employees WHERE Employee_ID = "+id+";";
	        PreparedStatement stat;
	        
	        
	
	        try {
	
	            stat = dbConnection.prepareStatement(q);
	            ResultSet set = stat.executeQuery();
	
	            set.next();
	
	            if (set.getInt(1) < 9950) {
	
	                return 0.10;
	
	            } else if (set.getInt(1) >= 9950 && set.getInt(1) < 40525) {
	
	                return 0.12;
	
	            } else if (set.getInt(1) >= 40525 && set.getInt(1) < 86375) {
	
	                return 0.22;
	
	            } else if (set.getInt(1) >= 86375 && set.getInt(1) < 164925) {
	
	                return 0.24;
	
	            } else if (set.getInt(1) >= 164925 && set.getInt(1) < 209425) {
	
	                return 0.32;
	
	            } else if (set.getInt(1) >= 209425) {
	
	                return 0.35;
	
	            }
	
	        } catch (SQLException e) {
	
	            //TODO Auto-generated catch block
	            e.printStackTrace();
	
	        }
	
	        return 0;
	
	    }
	public double getTotalHours(int id, String date) { //String date
	
	        String q = "SELECT Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, "
	        		+ "Saturday, Vaccation_Total, Sick_Total FROM Timesheet WHERE Employee_ID_FK = "+id+" AND Week_Date = '"+date+"'";
	        PreparedStatement stat;
	
	        double totalHours = 0;
	
	        try {
	
	            stat = dbConnection.prepareStatement(q);
	            ResultSet set = stat.executeQuery();
	
	            set.next();
	
	            for (int i = 1; i <= 9; i++) {
	
	                totalHours += set.getDouble(i);
	
	            }
	
	        } catch (SQLException e) {
	
	            //TODO Auto-generated catch block
	            e.printStackTrace();
	
	        }
	
	        return totalHours;
	
	    }
	
	public String getDate(int id) throws SQLException {
		String query = "SELECT Week_Date from Timesheet where employee_id_fk = " + id; 
		String result = ""; 
        PreparedStatement stat = dbConnection.prepareStatement(query);
        ResultSet set = stat.executeQuery();
        if (set.next()) {
        	result =  set.getString(1);
        }
        return result;
	}
	
	
	
	public boolean insertPayment (int id, String date) {
	
	        String insert = "INSERT INTO Payment VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	        String payType = "SELECT Pay_Type, Pay_Amount FROM Employees WHERE Employee_ID = "+id+";";
	        String benifits = "SELECT Health, Life, Dental, Retirement FROM Benifits WHERE Employee_ID_FK = "+id+";";
	
	        try {
	
	            PreparedStatement stat = dbConnection.prepareStatement(payType);
	            ResultSet set = stat.executeQuery();
	
	            set.next();
	
	            if (set.getString(1).equals("Salary")) {
	
	                gross = Math.round((double)set.getInt(2)/52*100.0)/100.0;
	
	            } else {
	            															//, date
	                gross =  Math.round((double)(set.getInt(2) * getTotalHours(id, date))*100.0)/100.0;
	
	            }
	
	        } catch (SQLException e) {
	
	            //TODO Auto-generated catch block
	            e.printStackTrace();
	
	        }
	
	        try {
	
	            PreparedStatement stat = dbConnection.prepareStatement(benifits);
	            ResultSet set = stat.executeQuery();
	
	            set.next();
	
	            health = set.getDouble(1);
	            life = set.getDouble(2);
	            dental = set.getDouble(3);
	            retirement = set.getDouble(4);
	
	        } catch (SQLException e) {
	
	            //TODO Auto-generated catch block
	            e.printStackTrace();
	
	        }
		federal = Math.round(gross*getFederalTax(id)*100.0)/100.0;
		        state = Math.round(gross*getStateTax(id)*100.0)/100.0;
		        local = 0;
		        socialSecurity = Math.round(gross*0.062*100.0)/100.0;
		        medicare = Math.round(gross*0.0145*100.0)/100.0;
		        disability = Math.round(gross*0.0004*100.0)/100.0;
		        familyLeave = Math.round(gross*0.0012*100.0)/100.0;
		        net = Math.round((gross - federal - state - local - socialSecurity - medicare - disability - familyLeave - health - life - dental - retirement)*100.0)/100.0;
		
		        try {
		
		            PreparedStatement stat = dbConnection.prepareStatement(insert);
		
		            stat.setInt(1, id);
		            stat.setInt(2, id);
		            stat.setString(3, this.getDate(id));
		            stat.setDouble(4, gross);
		            stat.setDouble(5, federal);
		            stat.setDouble(6, state);
		            stat.setDouble(7, local);
		            stat.setDouble(8, socialSecurity);
		            stat.setDouble(9, medicare);
		            stat.setDouble(10, disability);
		            stat.setDouble(11, familyLeave);
		            stat.setDouble(12, net);
		
		            if (stat.execute()) {
		                return true;
		            } else {
		                return false;
		            }
		
		        } catch (SQLException e) {
		
		            //TODO Auto-generated catch block
		            e.printStackTrace();
		
		        }
		
		        return false;
		
		    }
	public ResultSet getPayroll(int id) {
	
	        String q = "SELECT p.Employee_ID_FK, p.Payment_Date, p.Gross_Pay, b.Health, b.Life, b.Dental, b.Retirement, p.Social_Security, p.Medicare, "
	        		+ "p.Disability, p.Family_Leave, p.Net_Pay FROM Payment as p, Benifits as b WHERE p.Employee_ID_FK = "+id+" AND b.Employee_ID_FK = "+id+";";
	        PreparedStatement stat;
	        ResultSet set = null;
	
	        try {
	
	            stat = dbConnection.prepareStatement(q);
	            set = stat.executeQuery();
	
	        } catch (SQLException e) {
	
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	
	        }
	
	        return set;
	
	    }

}