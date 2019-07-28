package com.method.security.global.config;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import com.method.security.dao.EmployeeRepository;
import com.method.security.entity.Employee;

public class CustomPermissionEvaluator implements PermissionEvaluator {
	
	
	private final EmployeeRepository employeeRepository;

    public CustomPermissionEvaluator(EmployeeRepository employeeRepository) { 
        this.employeeRepository = employeeRepository; 
    } 

	
/*    @Override
    public boolean hasPermission(
      Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)){
            return false;
        }
        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
         
        return hasPrivilege(auth, targetType, permission.toString().toUpperCase());
    }
 
    @Override
    public boolean hasPermission(
      Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetType.toUpperCase(), 
          permission.toString().toUpperCase());
    }
    
    
    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
            if (grantedAuth.getAuthority().startsWith(targetType)) {
                if (grantedAuth.getAuthority().contains(permission)) {
                    return true;
                }
            }
        }
        return false;
    }
*/
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) { //@PostAuthorize("hasPermission(Employee, 'read')")
		if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)){
            return false;
        }
        return hasPermission(authentication, (Employee) targetDomainObject, permission); 
    } 
	
	
	@Override
    public boolean hasPermission(
      Authentication authentication, Serializable targetId, String targetType, Object permission) { // @PreAuthorize("hasPermission(#employeeId, 'Employee', 'read')")
        if ((authentication == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        
        Optional<Employee> emp = employeeRepository.findById((Integer)targetId);
        return hasPermission(authentication, emp.orElse(null), permission);   
    }
	
	private boolean hasPermission(Authentication authentication, Employee emp, Object permission) { 
        if(emp == null) { 
            return true; 
        } 
        
        String employeeEmail = emp.getEmail();
        if("delete".equals(permission)) { // zakas simulation !! - User with ADMIN role will be able to delete record only if email of record starts with letter 'd'
        	// That means User with ADMIN role will have delete permission to record for which email starts with letter 'd'. otherwise delete will be not be authorized
            return employeeEmail.startsWith("d"); 
        } 
        
        throw new IllegalArgumentException("permission "+permission+" is not supported."); 
    } 

}


// Example of using hasPermission in @PreAuthorize and @PostAuthorize
/*@Controller
public class MainController {
     
    @PostAuthorize("hasPermission(returnObject, 'read')")
    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
    @ResponseBody
    public Foo findById(@PathVariable long id) {
        return new Foo("Sample");
    }
 
    @PreAuthorize("hasPermission(#foo, 'write')")
    @RequestMapping(method = RequestMethod.POST, value = "/foos")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Foo create(@RequestBody Foo foo) {
        return foo;
    }
}*/
