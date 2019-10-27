package spring.standalone.jdbc.repo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import spring.standalone.jdbc.model.Employee;


public class EmployeeRepositoryImpl implements EmployeeRepository{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	// A) Use queryForObject when it is expected that execution of the query will return a single result.
	
	// Using Custom RowMapper
	/*@Override
	public Employee findById(int id) {
		String query = "SELECT * FROM employees WHERE ID = ?";
		Employee employee = jdbcTemplate.queryForObject(
		    query, new Object[] {id}, new EmployeeRowMapper());
		return employee;
	}
	
	// Using BeanPropertyRowMapper saves you a lot of time for the mapping
	@Override
	public Employee findById(int id) {
		String query = "SELECT * FROM employees WHERE ID = ?";
		Employee employee = (Employee) jdbcTemplate.queryForObject(
		    query, new Object[] {id}, new BeanPropertyRowMapper<Employee>(Employee.class));
		return employee;
	} */
	
	// Using Java 8 features
	@Override
	public Employee findById(int id) {
        String sql = "SELECT * FROM employees WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Employee(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
    }
	
	// B) jdbcTemplate.query for multiple rows or list
	// Using Custom RowMapper
	/*@Override
	public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";

        List<Employee> employees = jdbcTemplate.query(
                sql,
                new EmployeeRowMapper());

        return employees;
		
    }
	
	// Using BeanPropertyRowMapper saves you a lot of time for the mapping
	@Override
	public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";

        List<Employee> employees = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<Employee>(Employee.class));

        return employees;
    }
	
	// List Using Java 8 features
	@Override
	public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                      new Employee(
                             rs.getInt("id"),
                             rs.getString("name")
       ));
    }*/
	
	// C) jdbcTemplate.queryForList for list works, but not recommend, the mapping in Map may not same as the object, need casting.
	// Use when query is expected to return multiple rows. Results will be mapped to a List (one entry for each row) of Maps
	@Override
	public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        List<Employee> employees = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
        	Employee obj = new Employee();

            obj.setId((int)row.get("ID"));
            obj.setName((String)row.get("NAME"));
            employees.add(obj);
        }

        return employees;
    }
	
	// D) jdbcTemplate.queryForMap - Use it when query is expected to return a single row.
	
	@Override
	public Employee findByIdUsingMap(int id) {
        String sql = "SELECT * FROM employees WHERE ID = ?";
        Map<String,Object> singleRow = jdbcTemplate.queryForMap(sql, id);
        Employee obj = new Employee();
        obj.setId((int)singleRow.get("ID"));
        obj.setName((String)singleRow.get("Name"));
        return obj;
    }
	
	
	@Override
	public Employee findByIdAndNameUsingMap(int id, String name) {
        String sql = "SELECT * FROM employees WHERE ID = ? AND NAME = ?";
        Map<String,Object> singleRow = jdbcTemplate.queryForMap(sql, new Object[]{id, name}, new int[] {Types.INTEGER,Types.VARCHAR});
        Employee obj = new Employee();
        obj.setId((int)singleRow.get("ID"));
        obj.setName((String)singleRow.get("Name"));
        return obj;
    }
	
	//E) jdbcTemplate.queryForRowSet - The results will be mapped to an SqlRowSet which holds the data in a disconnected fashion. This wrapper will translate any SQLExceptions thrown to more generic exception like DataAccessException
	
	@Override
	public void printAllNamesUsingSqlRowSet() throws DataAccessException{
        String sql = "SELECT * FROM employees";
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sql); // SqlRowSet behaves like resultSet in JDBC API

        while (resultSet.next()) {
        	String name = resultSet.getString("Name");
        	System.out.println(name);
        }
    }
	
	// Misc
	@Override
	public String findEmployeeNameById(int id) {
        String sql = "SELECT NAME FROM employees WHERE ID = ?";

        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, String.class);
    }
	
	@Override
	public int findEmployeeCount() {
        String sql = "SELECT COUNT(*) FROM employees";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
	
	// update method issues a single SQL update operation (such as an insert, update or delete statement). It returns the number of rows affected
	public int saveEmployee(Employee e){  
	    String query="insert into employees values('"+e.getId()+"','"+e.getName()+"')";  
	    return jdbcTemplate.update(query);  
	}  
	
	public int updateEmployee(Employee e){  
	    String query="update employees set name='"+e.getName()+"' where id='"+e.getId()+"' ";  
	    return jdbcTemplate.update(query);  
	}
	
	public int deleteEmployee(Employee e){  
	    String query="delete from employees where id='"+e.getId()+"' ";  
	    return jdbcTemplate.update(query);  
	}  
	
	// execute(String sql) is generally used to execute DDL query.
	public void startEmployessApp() {
        jdbcTemplate.execute("DROP TABLE employees IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE employees (id INTEGER PRIMARY KEY, name VARCHAR(30))");
	}
	
	
	
	// execute(String sql, PreparedStatementCallback<T> action)
	// PreparedStatementCallback interface processes the input parameters and output results. In such case, you don't need to care about single and double quotes.
	public Boolean saveEmployeeByPreparedStatement(final Employee e){  
	    String query="insert into employees values(?,?)";  
	    return jdbcTemplate.execute(query,new PreparedStatementCallback<Boolean>(){  
		    @Override  
		    public Boolean doInPreparedStatement(PreparedStatement ps)  
		            throws SQLException, DataAccessException {  
		        ps.setInt(1,e.getId());  
		        ps.setString(2,e.getName());  
		        return ps.execute();  
		    }  
	    });  
	}
	
	// public T query(String sql,ResultSetExtractor<T> rse)  
	// ResultSetExtractor interface can be used to fetch records from the database. It accepts a ResultSet and returns the list.
	public List<Employee> getAllEmployeesUsingResultSetExtractor(){  
		 return jdbcTemplate.query("select * from employees",new ResultSetExtractor<List<Employee>>(){  
		    @Override  
		     public List<Employee> extractData(ResultSet rs) throws SQLException,  
		            DataAccessException {  
		        List<Employee> list = new ArrayList<Employee>();  
		        
		        while(rs.next()){  
			        Employee e = new Employee();  
			        e.setId(rs.getInt(1));  
			        e.setName(rs.getString(2));  
			        list.add(e);  
		        }  
		        return list;  
		        }  
		    });  
    }
	
	
	// public T query(String sql,RowMapper<T> rm)
	// RowMapper saves a lot of code because it internally adds the data of ResultSet into the collection.
	public List<Employee> getAllEmployeesRowMapper(){  
		 return jdbcTemplate.query("select * from employees",new RowMapper<Employee>(){  
		    @Override  
		    public Employee mapRow(ResultSet rs, int rownumber) throws SQLException {  
		        Employee e=new Employee();  
		        e.setId(rs.getInt(1));  
		        e.setName(rs.getString(2));  
		        return e;  
		    }  
		    });  
	}  
	
	
	public List<Employee> getAllEmployeesRowCallbackHandler(){  
		String sql = "select * from employees";
		EmployeeRowCallbackHandler rowHandler = new EmployeeRowCallbackHandler();
		jdbcTemplate.query(sql, rowHandler);
		return rowHandler.getEmployeeList();
	} 
	
	
	public List<Employee> getEmployeeByIdRowCallbackHandler(int id){  
		String sql = "select * from employees where id=?";
		Object[] values = new Object[] { id };
		EmployeeRowCallbackHandler rowHandler = new EmployeeRowCallbackHandler();
		jdbcTemplate.query(sql, values, rowHandler);
		return rowHandler.getEmployeeList();
	}
	
	
	// int[] JdbcTemplate.batchUpdate(String sql, BatchPreparedStatementSetter pss) throws DataAccessException
	// Returns an array of the number of rows affected by each statement
	public int[] saveEmployeesWithBatchUpdate(List<Employee> employees) {
	    return jdbcTemplate.batchUpdate("INSERT INTO EMPLOYEES VALUES (?, ?)",
	        new BatchPreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement ps, int i) throws SQLException {
	                ps.setInt(1, employees.get(i).getId());
	                ps.setString(2, employees.get(i).getName());
	            }
	            
	            @Override
	            public int getBatchSize() {
	                return 3; // Set size of batch you are passing
	            }
	        });
	}
	

	// Stored Procedures - CallableStatement
	public void executeSP() {
		SimpleJdbcCall simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("MYFUNCTION");
		Map<String, Object> map= simpleJdbcCallRefCursor.execute();
		System.out.println(map);
		

		/*List<SqlParameter> parameters = Arrays.asList(
	            new SqlParameter(Types.INTEGER), new SqlOutParameter("status_code", Types.INTEGER),new SqlOutParameter("status_message", Types.VARCHAR));

	    Map<String, Object> t = jdbcTemplate.call(new CallableStatementCreator() {
	        @Override
	        public CallableStatement createCallableStatement(Connection con) throws SQLException {
	            CallableStatement callableStatement = con.prepareCall(sql);
	            callableStatement.setInt(1, 2);
	            callableStatement.registerOutParameter(2, Types.INTEGER);
	            callableStatement.registerOutParameter(3, Types.VARCHAR);
	            return callableStatement;
	        }
	    }, parameters);*/
		
		
		//jdbcTemplate.call(CallableStatementCreator csc, List<SqlParameter> declaredParameters)
	}
	
}



