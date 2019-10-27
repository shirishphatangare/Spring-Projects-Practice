package spring.standalone.jdbc.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


// Convert the row into an object via row mapper.
public class EmployeeRowMapper implements RowMapper<Employee> {
	@Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
 
        employee.setId(rs.getInt("ID"));
        employee.setName(rs.getString("NAME"));
        //employee.setFirstName(rs.getString("FIRST_NAME"));
        //employee.setLastName(rs.getString("LAST_NAME"));
        //employee.setAddress(rs.getString("ADDRESS"));
 
        return employee;
    }
}
