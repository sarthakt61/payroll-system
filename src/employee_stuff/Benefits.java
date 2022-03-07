package employee_stuff;



/*CREATE TABLE Benifits (
	ID INT PRIMARY KEY,
	Employee_ID_FK INT REFERENCES Employees(Employee_ID),
    Health DOUBLE DEFAULT '0.00',
    Life DOUBLE DEFAULT '0.00',
    Dental DOUBLE DEFAULT '0.00',
    Retirement DOUBLE DEFAULT '0.00',
    Vaccation_Time DOUBLE DEFAULT '0.00',
    Sick_Time DOUBLE DEFAULT '0.00'
	);
 * 
 * */
public class Benefits {
	
	int emplID; 
	int intID; 
	double health; 
	double life; 
	double dental;
	double retire; 
	double vacTime; 
	double sicTime; 
	
	
	public Benefits(int eID, int iID, double h, double l, double r, double d, double vt, double st) {
		
		emplID = eID;
		intID = iID;
		health = h;
		life = l;
		dental = d;
		retire = r; 
		vacTime = vt; 
		sicTime = st; 
		
	}
	public int getEmplID() {
		return emplID;
	}
	public void setEmplID(int emplID) {
		this.emplID = emplID;
	}
	public int getIntID() {
		return intID;
	}
	public void setInternalID(int internalID) {
		this.intID = internalID;
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
	public double getRetire() {
		return retire;
	}
	public void setRetire(double retire) {
		this.retire = retire;
	}
	public double getVacTime() {
		return vacTime;
	}
	public void setVacTime(double vacTime) {
		this.vacTime = vacTime;
	}
	public double getSicTime() {
		return sicTime;
	}
	public void setSicTime(double sicTime) {
		this.sicTime = sicTime;
	}
}
