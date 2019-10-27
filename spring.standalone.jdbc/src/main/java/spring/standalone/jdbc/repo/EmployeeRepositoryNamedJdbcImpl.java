package spring.standalone.jdbc.repo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import spring.standalone.jdbc.model.Employee;
import spring.standalone.jdbc.model.EmployeeRowMapper;

public class EmployeeRepositoryNamedJdbcImpl implements EmployeeRepositoryNamedJdbc{
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public EmployeeRepositoryNamedJdbcImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Override
	public void saveEmployee(Employee e){  
		String query="insert into employees values (:id,:name)";  
		  
		Map<String,Object> map=new HashMap<String,Object>();  
		map.put("id",e.getId());  
		map.put("name",e.getName());  
		  
		namedParameterJdbcTemplate.execute(query,map,new PreparedStatementCallback<Object>() {  
		    @Override  
		    public Object doInPreparedStatement(PreparedStatement ps)  
		            throws SQLException, DataAccessException {  
		        return ps.executeUpdate();  
		    }  
		});  
	} 
	
	
	//public int[] batchUpdate(String sql, SqlParameterSource[] batchArgs)
	//Executes a batch using the supplied SQL statement with the batch of supplied arguments.
	//Returns an array containing the numbers of rows affected by each update in the batch
	@Override
	public void saveEmployeesUsingBatchUpdate(List<Employee> employees) {
		String sql = "insert into employees values (:id,:name)";
		SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(employees.toArray());
		int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(sql, batchArgs);
		System.out.println("Rows afftected in each update of a batch - " );
		
		for (int i=0; i<updateCounts.length; i++)  {
	      System.out.println(updateCounts[i]);
	    }
	}
	
	
	@Override
	public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";

        List<Employee> employees = namedParameterJdbcTemplate.query(
                sql,
                new EmployeeRowMapper()
                );

        return employees;
    }
	
	
	// public <T> T query(String sql, SqlParameterSource paramSource,  ResultSetExtractor<T> rse) throws DataAccessException
	// MapSqlParameterSource
	public List<Employee> findAllByName(String name) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("name", "Surabhi");
		
		return namedParameterJdbcTemplate.query(
		    "SELECT * FROM EMPLOYEES WHERE NAME = :name", namedParameters, new ResultSetExtractor<List<Employee>>(){  
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
	
	// BeanPropertySqlParameterSource
	public int getTotalNumberOfEmployees() {
		Employee employee = new Employee();
		employee.setName("Surabhi");
		String SELECT_BY_ID = "SELECT COUNT(*) FROM EMPLOYEES WHERE NAME = :name";
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(employee);
		return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, Integer.class);
	}
	
	
	public int updateEmployee(){ 
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("name", "Bhavesh");
		parameters.put("id", 22);
		
	    String query="update employees set name= :name where id= :id ";  
	    return namedParameterJdbcTemplate.update(query,parameters);  
	}
	
	public int deleteEmployee(){  
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("id", 11);

		String query="delete from employees where id= :id";
	    return namedParameterJdbcTemplate.update(query,parameters);  
	}  
	
	// public <T> List<T> queryForList(String sql, Map<String,?> paramMap, Class<T> elementType) throws DataAccessException
	// The results will be mapped to a List (one entry for each row) of result objects, each of them matching the specified element type.
	public List<Integer> findAllByNameWithqueryForList() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("name","Surabhi");
		
		return namedParameterJdbcTemplate.queryForList("SELECT ID FROM EMPLOYEES WHERE NAME = :name",parameters,Integer.class); // Last parameter is to return only specific type of column (ID here) from a row
	}
	
}
