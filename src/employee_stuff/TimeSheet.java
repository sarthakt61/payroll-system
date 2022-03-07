package employee_stuff;

import javafx.beans.property.DoubleProperty;

/*
 * CREATE TABLE Timesheet 
 * ( Employee_ID_FK INT REFERENCES Employees(Employee_ID), 
 * Week_Date VARCHAR(10) NOT NULL, 
 * Sunday DOUBLE DEFAULT '0.00', 
 * Monday DOUBLE DEFAULT '0.00', 
 * Tuesday DOUBLE DEFAULT '0.00',
 * Wednesday DOUBLE DEFAULT '0.00',
 *  Thursday DOUBLE DEFAULT '0.00', 
 *  Friday DOUBLE DEFAULT '0.00', 
 *  Saturday DOUBLE DEFAULT '0.00', 
 *  Vaccation_Total DOUBLE DEFAULT '0.00',
 *   Sick_Total DOUBLE DEFAULT '0.00', 
 *   Verified VARCHAR(3) DEFAULT "NO",
 *    Verification_ID INT DEFAULT 0 );
 * 
 */

public class TimeSheet {

	int ID; 
	int emplID; 
	String weekDate; 
	Double sunday, monday, tuesday, wednesday, thursday, friday, saturday; 
	Double vacationTotal; 
	Double sickTotal; 
	String isVerified; 
	int verifiedID; 
	
	
	public TimeSheet(int ID, int emplID, String weekDate, Double sunday, Double monday, Double tuesday, Double wednesday,
			Double thursday, Double friday, Double saturday, Double vacationTotal, Double sickTotal, String isVerified,
			int verifiedID) {
		super();
		this.ID = ID; 
		this.emplID = emplID;
		this.weekDate = weekDate;
		this.sunday = sunday;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.vacationTotal = vacationTotal;
		this.sickTotal = sickTotal;
		this.isVerified = isVerified;
		this.verifiedID = verifiedID;
	}
	
	public int getID() {
		return ID; 
	}
	
	public int getEmplID() {
		return emplID;
	}


	public void setEmplID(int emplID) {
		this.emplID = emplID;
	}


	public String getWeekDate() {
		return weekDate;
	}


	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}


	public String getSunday() {
		return String.valueOf(sunday);
	}


	public void setSunday(Double sunday) {
		this.sunday = sunday;
	}


	public String getMonday() {
		return String.valueOf(monday);
	}


	public void setMonday(Double monday) {
		this.monday = monday;
	}


	public String getTuesday() {
		return String.valueOf(tuesday);
	}


	public void setTuesday(Double tuesday) {
		this.tuesday = tuesday;
	}


	public String getWednesday() {
		return String.valueOf(wednesday);
	}


	public void setWednesday(Double wednesday) {
		this.wednesday = wednesday;
	}


	public String getThursday() {
		return String.valueOf(thursday);
	}


	public void setThursday(Double thursday) {
		this.thursday = thursday;
	}


	public String getFriday() {
		return String.valueOf(friday);
	}


	public void setFriday(Double friday) {
		this.friday = friday;
	}
	


	public String getSaturday() {
		return String.valueOf(friday);
	}


	public void setSaturday(Double saturday) {
		this.saturday = saturday;
	}


	public Double getVacationTotal() {
		return vacationTotal;
	}


	public void setVacationTotal(Double vacationTotal) {
		this.vacationTotal = vacationTotal;
	}


	public Double getSickTotal() {
		return sickTotal;
	}


	public void setSickTotal(Double sickTotal) {
		this.sickTotal = sickTotal;
	}


	public String getIsVerified() {
		return isVerified;
	}


	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}


	public int getVerifiedID() {
		return verifiedID;
	}


	public void setVerifiedID(int verifiedID) {
		this.verifiedID = verifiedID;
	}
	


}

