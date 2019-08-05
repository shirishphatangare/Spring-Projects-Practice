package com.method.security.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.method.security.entity.Employee;
import com.method.security.service.EmployeeManager;

/*There are two important points to remind regarding method security: 
1) By default, Spring AOP proxying is used to apply method security – if a secured method A is called by another method  
within the same class, security in A is ignored altogether. This means method A will execute without any security checking. The same applies to private methods 
2) Spring SecurityContext is thread-bound – by default, the security context isn’t propagated to child-threads*/

/*Both @PreAuthorize and @PostAuthorize annotations provide expression-based access control. 
  Hence, predicates can be written using SpEL (Spring Expression Language).
*/

/*The @PreAuthorize annotation checks the given expression before entering the method, 
whereas, the @PostAuthorize annotation verifies 
it after the execution of the method and could alter the result.*/

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeManager employeeManager;
	
	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultPage(ModelMap map) {
		return "redirect:/list";
	}
	
	
	@PreAuthorize("hasRole('ROLE_VIEWER') or hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
	//@PreAuthorize("hasAnyRole('ROLE_VIEWER','ROLE_EDITOR','ROLE_ADMIN')")
	//@Secured({"ROLE_VIEWER","ROLE_EDITOR","ROLE_ADMIN"})  // The @Secured annotation doesn’t support Spring Expression Language (SpEL).
	//@RolesAllowed({"ROLE_VIEWER","ROLE_EDITOR","ROLE_ADMIN"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listEmployees(ModelMap map) {
		map.addAttribute("employee", new Employee());
		map.addAttribute("employeeList",employeeManager.getAllEmployees());
		return "employeeList";
	}
	
	
	@PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
	//@Secured({"ROLE_EDITOR","ROLE_ADMIN"})
	//@RolesAllowed({"ROLE_EDITOR","ROLE_ADMIN"})
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addEmployee(
			@ModelAttribute(value = "employee") Employee employee,
			BindingResult result) {
		employeeManager.addEmployee(employee);
		return "redirect:/list";
	}

	@PreAuthorize(value="hasRole('ROLE_ADMIN') and hasPermission(#employeeId, 'Employee', 'delete')") // CustomPermissionEvaluator in action
	//@Secured("ROLE_ADMIN")
	//@RolesAllowed("ROLE_ADMIN")
	@RequestMapping("/delete/{employeeId}")
	public String deleteEmplyee(@PathVariable("employeeId") Integer employeeId) {
		employeeManager.deleteEmployee(employeeId);
		return "redirect:/list";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "login";
	}

	@RequestMapping(value = "/authorizationDenied", method = RequestMethod.GET)
	public String loginerror1(ModelMap model) {
		model.addAttribute("error", "true");
		return "authorizationDenied";
	}
	
	@RequestMapping(value = "/authorizationDenied", method = RequestMethod.POST)
	public String loginerror2(ModelMap model) {
		model.addAttribute("error", "true");
		return "authorizationDenied";
	}
	
	
	// For custom logout filter to work , below method is necessary
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public String logOut() {
		return "login";
	}
}

 /*Some common expressions (SPEL) which are available in both web and method security.
 
 hasRole([role])	Returns true if the current principal has the specified role.
 hasAnyRole([role1,role2])	Returns true if the current principal has any of the supplied roles (given as a comma-separated list of strings)
 principal	Allows direct access to the principal object representing the current user
 authentication	Allows direct access to the current Authentication object obtained from the SecurityContext
 permitAll	Always evaluates to true
 denyAll	Always evaluates to false
 isAnonymous()	Returns true if the current principal is an anonymous user
 isRememberMe()	Returns true if the current principal is a remember-me user
 isAuthenticated()	Returns true if the user is not anonymous
 isFullyAuthenticated()	Returns true if the user is not an anonymous or a remember-me user
*/


//@PreAuthorize and  @PostAuthorize Examples

/*public interface IBookService {
	@PostAuthorize ("returnObject.owner == authentication.name")
	public Book getBook();

	@PreAuthorize ("#book.owner == authentication.name")
	public void deleteBook(Book book);
}*/

// Here, a user can invoke the getMyRoles method only if the value of the argument username is the same as current principal’s username.
/*@PreAuthorize("#username == authentication.principal.username")
public String getMyRoles(String username) {
    //...
}*/

/*Let’s rewrite getMyRoles with @PostAuthorize

@PostAuthorize("#username == authentication.principal.username")
public String getMyRoles2(String username) {
    //...
}

With @PostAuthorize however, the authorization would get delayed after the execution of the target method.
Additionally, the @PostAuthorize annotation provides the ability to access the method result:

@PostAuthorize("returnObject.username == authentication.principal.nickName")
public CustomUser loadUserDetail(String username) {
    return userRoleRepository.loadUserByUserName(username);
}

In this example, the loadUserDetail method would only execute successfully if the username of the returned 
CustomUser is equal to the current authentication principal’s nickname.*/


/*Using @PreFilter and @PostFilter Annotations
Spring Security provides the @PreFilter annotation to filter a collection argument before executing the method:

@PreFilter("filterObject != authentication.principal.username")
public String joinUsernames(List<String> usernames) {
    return usernames.stream().collect(Collectors.joining(";"));
}

In this example, we’re joining all usernames except for the one who is authenticated.
Here, our expression uses the name filterObject to represent the current object in the collection.

However, if the method has more than one argument which is a collection type, we need to use the filterTarget property to specify which argument we want to filter:

@PreFilter(value = "filterObject != authentication.principal.username",filterTarget = "usernames")
public String joinUsernamesAndRoles(
  List<String> usernames, List<String> roles) {
    return usernames.stream().collect(Collectors.joining(";")) 
      + ":" + roles.stream().collect(Collectors.joining(";"));
}

Additionally, we can also filter the returned collection of a method by using @PostFilter annotation:

@PostFilter("filterObject != authentication.principal.username")
public List<String> getAllUsernamesExceptCurrent() {
    return userRoleRepository.getAllUsernames();
}

In this case, the name filterObject refers to the current object in the returned collection.
With that configuration, Spring Security will iterate through the returned list and remove any value which matches with the principal’s username.*/

/*If we find ourselves using the same security annotation for every method within one class, we can consider putting that annotation at class level:

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class SystemService {
 
    public String getSystemYear(){
        //...
    }
  
    public String getSystemDate(){
        //...
    }
}*/


