import java.text.DecimalFormat;

//Create employee object

public class Employee {
	//initialize attributes
	private String name = "";
	private long rate = 0;
	private int hours = 0;
	private String role = ""; 
	private long salary = 0;
	
	//create constructors 
	//default constructor 
	public Employee() {
	}
	
	//parameterized constructor 
	public Employee(String name, long rate, int hours, String role){
		setName(name);
		setRate(rate);
		setHours(hours);
		setRole(role);
		//calculate salary for Employee object upon instantiation, as salary is a defining attribute of an Emplyee
		calculateSalary();
	}
	
	//declare getters
	public String getName() {
		return name; 
	}
	
	public long getRate() {
		return rate; 
	}
	
	public int getHours() {
		return hours; 
	}
	
	public String getRole() {
		return role; 
	}
	
	public long getSalary() {
		return salary; 
	}
	
	//declare setters
	private void setName(String name) {
		this.name = name; 
	}
	
	private void setRate(long rate) {
		this.rate = rate; 
	}
	
	private void setHours(int hours) {
		this.hours = hours; 
	}
	
	private void setRole(String role) {
		this.role = role; 
	}
	
	//create method to calculate Employee salary property
	private void calculateSalary() {
		/* Calculation criteria: 
		 * Salary for a part time employee is Hours*Rate. Let’s not worry about OT.
		 * Salary for a full time employee is Hours*Rate but capped at $50,000.
		 * Salary for Contract employee is $10,000 + (Hours*Rate). There is no cap. 
		 */
		
		//salary base calculation always involves hours*rate. Multiply by 52 to compensate for weeks in year 
		salary = (this.hours * this.rate) * 52; 
		
		//check conditions for part time
		if(role.equalsIgnoreCase("part time")) {
			//if part-time, cap at 50000
			if(salary >= 50000) {
				salary = 50000;
			}  
		}
		//check condition for contract
		else if(role.equalsIgnoreCase("contract")) {
			//if contract, add 10000
			salary += 10000; 
		}		
	}
	
	//toString override for output
	@Override
	public String toString() {
		//create money formatter for currency values
		DecimalFormat moneyFormat = new DecimalFormat("$#,###.00");
		
		return name + " | " + moneyFormat.format(rate) + " | " + hours + " | " + role + " | " + moneyFormat.format(salary); 
	}
	

}
