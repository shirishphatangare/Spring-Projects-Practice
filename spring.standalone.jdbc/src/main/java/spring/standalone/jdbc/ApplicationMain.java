package spring.standalone.jdbc;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.standalone.jdbc.config.AppConfigM2;
import spring.standalone.jdbc.model.Employee;
import spring.standalone.jdbc.service.EmployeeService;
import spring.standalone.jdbc.service.EmployeeServiceNamedJdbc;

/* We can create spring beans with java configuration using annotations in 2 ways -
1)M1- Annotation configuration with component scanning - @Component,@Repository,@Service,@Controller and then use component scan on pkgs having classes(beans) with component annotations.
2)M2- Create beans using @Bean and @Configuration annotations
*/

public class ApplicationMain {

	public static void main(String[] args) {
		
		/* Standalone application context, accepting component classes as input — in particular @Configuration-annotated classes, but also plain @Component types and JSR-330 compliant classes using javax.inject annotations.
		Allows for registering classes one by one using register(Class...) as well as for classpath scanning using scan(String...).*/
		
		//Method 1
        //ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfigM2.class);
         
        //Method 2
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfigM2.class); // Register one or more component classes to be processed.
        //ctx.scan("spring.standalone.jdbc.service");  // Alternative to register method. Perform a scan within the specified base packages (the packages to scan for component classes)
        ctx.refresh();
        
        
        EmployeeService empManager = ctx.getBean(EmployeeService.class);
        empManager.startEmployessApp(); // Create DB table - I know we have scripts for it but this is demo of another way of doing it using execute(String sql)
        executeAllJdbcOperations(empManager);
        
        EmployeeServiceNamedJdbc empServiceNamedManager = ctx.getBean(EmployeeServiceNamedJdbc.class);
        executeAllNamedParameterJdbcOperations(empServiceNamedManager);
        
        
        ctx.close();
	}
	
	private static void executeAllJdbcOperations(EmployeeService empManager) {
        System.out.println("EXECUTING JdbcTemplate Operations ....");
        
        Employee emp1 = new Employee(1,"Mkyong");
        System.out.println("Creating a new Employee " + emp1);
        empManager.saveEmployee(emp1);
        
        emp1 = new Employee(2,"Alex");
        System.out.println("Creating a new Employee " + emp1);
        empManager.saveEmployee(emp1);
        
        emp1 = new Employee(3,"Joel");
        System.out.println("Creating a new Employee " + emp1);
        empManager.saveEmployee(emp1);
        
        int id = 2;
        Employee employee = empManager.findById(id);
        System.out.println("Employee Details for id " + id);
        System.out.println(employee);

        List<Employee> employees = empManager.findAll();
        System.out.println("Employee Details for all employees");
        employees.forEach(emp -> System.out.println(emp));
        
        id = 3;
        employee = empManager.findByIdUsingMap(id);
        System.out.println("Employee Details for id using queryForMap - " + id);
        System.out.println(employee);

        id = 3;
        String name="Joel";
        employee = empManager.findByIdAndNameUsingMap(id, name);
        System.out.println("Employee Details for id and name using queryForMap - " + id + " " + name);
        System.out.println(employee);
        
        System.out.println("Employee Name for " + id + " is " +  empManager.findEmployeeNameById(id));
        
        emp1 = new Employee(4,"Nitin");
        System.out.println("Creating a new Employee " + emp1);
        empManager.saveEmployee(emp1);
        
        emp1.setName("Shirish");
        empManager.updateEmployee(emp1);
        System.out.println("Updating employee changing name to Shirish " + emp1);
        
        empManager.deleteEmployee(emp1);
        
        System.out.println("Total Employee count is " +  empManager.findEmployeeCount());
        
        emp1 = new Employee(4,"Baba");
        System.out.println("Creating a new Employee using Prepared statement callback " + emp1);
        empManager.saveEmployeeByPreparedStatement(emp1);
        
        employees = empManager.getAllEmployeesUsingResultSetExtractor();
        System.out.println("Employee Details for all employees using ResultSetExtractor");
        employees.forEach(emp -> System.out.println(emp));
        
        employees = empManager.getAllEmployeesRowMapper();
        System.out.println("Employee Details for all employees using RowMapper");
        employees.forEach(emp -> System.out.println(emp));

        employees = empManager.getAllEmployeesRowCallbackHandler();
        System.out.println("Employee Details for all employees using RowCallbackHandler");
        employees.forEach(emp -> System.out.println(emp));
        
        id = 1;
        employees = empManager.getEmployeeByIdRowCallbackHandler(id);
        System.out.println("Employee Details for one employee with id " + id + " using RowCallbackHandler");
        employees.forEach(emp -> System.out.println(emp));
        
        System.out.println("Creating 3 Employees in a batch");
        employees = Arrays.asList(
		        new Employee(8,"Dana"),
		        new Employee(9,"Robin"),
		        new Employee(10,"Surabhi")
        		);
        
        empManager.saveEmployeesWithBatchUpdate(employees);
        
        System.out.println("Print all employee names using SqlRowSet - ");
        empManager.printAllNamesUsingSqlRowSet();
        
        System.out.println("Execute SP - ");
        empManager.executeSP();

	}
	
	
	private static void executeAllNamedParameterJdbcOperations(EmployeeServiceNamedJdbc empServiceNamedManager) {
		System.out.println("EXECUTING NamedParameterJdbcTemplate Operations ....");
		
		Employee emp1 = new Employee(11,"Surabhi");
        System.out.println("Creating a new Employee " + emp1);
        empServiceNamedManager.saveEmployee(emp1);
        
        emp1 = new Employee(22,"Minal");
        System.out.println("Creating a new Employee " + emp1);
        empServiceNamedManager.saveEmployee(emp1);
        
        emp1 = new Employee(33,"Payal");
        System.out.println("Creating a new Employee " + emp1);
        empServiceNamedManager.saveEmployee(emp1);
		
        System.out.println("Creating 3 Employees in a batch");
        List<Employee> employees = Arrays.asList(
		        new Employee(44,"Dana"),
		        new Employee(55,"Robin"),
		        new Employee(66,"Surabhi")
		);
        
        empServiceNamedManager.saveEmployeesUsingBatchUpdate(employees);
        
        System.out.println("Get All Employees");
        employees = empServiceNamedManager.findAll();
        employees.forEach(emp -> System.out.println(emp));
        
        String name="Surabhi";
        System.out.println("Find All Employees with name " + name);
        employees = empServiceNamedManager.findAllByName(name);
        employees.forEach(emp -> System.out.println(emp));
        
        System.out.println("Find All Employee ids with name Surabhi");
        List<Integer> ids = empServiceNamedManager.findAllByNameWithqueryForList();
        ids.forEach(employeeId -> System.out.println(employeeId));
        
        System.out.println("Total number of Employees with name "  + name  + " " + empServiceNamedManager.getTotalNumberOfEmployees());
        
        System.out.println("Updating Employee changing name to Bhavesh for id 22");
        empServiceNamedManager.updateEmployee();
        
        System.out.println("Deleting Employee with id 11 ");
        empServiceNamedManager.deleteEmployee();
        
        System.out.println("Get All Employees");
        employees = empServiceNamedManager.findAll();
        employees.forEach(emp -> System.out.println(emp));
        
       
	}

}
