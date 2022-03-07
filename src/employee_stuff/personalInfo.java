package employee_stuff;

public class personalInfo {
	
	private int emplID; 
	private String address; 
	private String phoneNumber; 
	private String email; 
	private String disability; 
	
	
	public personalInfo(int ID, String ad, String pn, String em, String da) {
		emplID = ID;
		address = ad; 
		phoneNumber = pn; 
		email = em; 
		disability = da; 
	}
	
	public int getEmplID() {
		return emplID;
	}


	public void setEmplID(int emplID) {
		this.emplID = emplID;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getDisability() {
		return disability;
	}


	public void setDisability(String disability) {
		this.disability = disability;
	}

	
}
