/*
 * Arlando Boykin
 * CPSI Code Challenge
 * Start date: 5/28/2021
 * Completion date: 5/30/21
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

//begin program
public class CPSICodeChallenge2021 {
	//declare static formatter for currency values 
	private static DecimalFormat moneyFormat = new DecimalFormat("$#,###.00");
	
	//begin main
	public static void main(String[] args) {
		//initialize variables
		Scanner s = new Scanner(System.in);
		String userIn = "";
		List<Employee> employeeList = new ArrayList<Employee>();
		
		//begin loop for user input
		while(true) {
			//request and parse input for file path
			System.out.println("Please enter a file path (or \"0\" to exit)");
			userIn = s.next();
			
			//exit program at user command
			if(userIn.equals("0")) {
				System.out.println("Exiting program.");
				break;
			}
			//otherwise, continue execution
			else {
				//upon input, generate Employee list directly from file	
				employeeList = generateEmployeeList(userIn);
				
				//if list returns empty, an error has occurred
				if(employeeList.isEmpty()) {
					System.out.println("An error has occurred. Please try again.");
					continue;
				}
				//otherwise, continue execution
				else {
					//initialize variable for user choice
					int choice = 0;
					System.out.println("File successfully read.");	
					//begin loop for user menu input
					while (choice != 4) {
						//display menu options and parse user input
						System.out.println("What would you like to do?");
						System.out.println("--------\nPlease enter one of the following options: "
								+ "\n1: Display employees (with total salaries)"
								+ "\n2: Display total of employee salaries"
								+ "\n3: Display total salaries by individual role"
								+ "\n4: Close\n-------------");
						
						//make sure user is entering a numeric value 
						try {
							choice = Integer.parseInt(s.next());
						}
						catch (NumberFormatException e) {
							System.out.println("Please enter a valid number.\n");
							continue; 
						}
						
						//begin switch-case
						switch	(choice) {
							//option for displaying list
							case 1:
								System.out.println(displayEmployees(employeeList));
								break;
							//option for displaying employee salary total
							case 2: 
								System.out.println("Total salary for all employees: " + moneyFormat.format(getSalaryTotal(employeeList)) + "\n");
								break;
							//option to display sums grouped by roles 
							case 3: 
								//store groupings in HashMap consisting of <role, summed_value> as respective key/value pairs
								Map<Object, Long> m = getSalariesByRole(employeeList);
								//print each map element
								m.entrySet().forEach(entry -> {
								    System.out.println("Total salaries for " + entry.getKey() + " employees: " + moneyFormat.format(entry.getValue()));
								});
								break;
							//option to close program
							case 4: 
								System.out.println("Exiting program."); 
								break;
							//invalid option default
							default: 
								System.out.println("Invalid choice, please enter a valid number");
						}
					}
				}
				//break from outer loop if user does not wish to continue
				System.out.println("Would you like to input another file? Please input Y if so, or anything else to exit.");
				userIn = s.next();
				if(userIn.equalsIgnoreCase("y"))
					continue; 
				else {
					System.out.println("Exiting program.");
					break;
				}
			}
		}
		//close scanner
		s.close();
}

	//begin method to read CSV file
	private static List<Employee> generateEmployeeList(String path) {
		List<Employee> employees = new ArrayList<Employee>(); 
	
		//get file data from BufferedReader
		try (BufferedReader reader = new BufferedReader(new FileReader(path));){
	
			String line = "";
			while((line = reader.readLine()) != null) {
				//parse rows of csv as new Employee objects
				String [] data = line.split(","); 
				employees.add(new Employee(data[0], Long.parseLong(data[1]), Integer.parseInt(data[2]), data[3]));
			}		
		}
		//exception handling 
		//alert user if file path is invalid
		catch(FileNotFoundException e) {
			System.out.println("Invalid file path.");
			e.printStackTrace();
		}
		//alert user if an IO exception has occurred
		catch(IOException e) {
			System.out.println("There was an error while reading the file.");
			e.printStackTrace();
		}
		//alert user of a mismatched field in CSV
		catch(NumberFormatException e) {
			System.out.println("Please check that each file entry is in the desired output (Name,Rate,Hours,Role).");
			e.printStackTrace();
		}
		//alert user of a missing file entry
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Please check that there are no missing file entries.");
			e.printStackTrace();
		}
		
		return employees;
	}
	
	//begin display method
	private static String displayEmployees(List<Employee> employeeList) {
		//initialize return value
		String retVal = "";
		
		//Add column headers to return value
		retVal += "NAME | RATE | HOURS | ROLE | TOTAL SALARY\n" 
				+ "-------------------------------------\n";
		//append Employee object data 
		for(Employee x : employeeList)
		{
			retVal += x.toString() + "\n"; 
		}
		return retVal; 
	}
	
	//begin method to display salary totals
	private static long getSalaryTotal(List<Employee> employees) {
		long sum = 0;
		//sum totals and return 
		for(Employee e : employees) {
			sum += e.getSalary();
		}
		return sum;
	}
	
	//create method for returning salaries grouped by role
	private static Map<Object, Long> getSalariesByRole(List<Employee> employees) {
		//call Collectors.groupingBy to collect Employee entries by role
		return employees.stream()
				.collect(Collectors.groupingBy(
						e -> Arrays.asList(e.getRole()),
						Collectors.summingLong(Employee::getSalary)
						));
	}	
}