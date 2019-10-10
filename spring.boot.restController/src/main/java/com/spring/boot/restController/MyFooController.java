package com.spring.boot.restController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.boot.restController.exception.ResourceNotFoundException;
import com.spring.boot.restController.pojo.Foo;

// This Controller is generic controller serving REST requests. However, more formal approach will be to use @RestController annotation
// With @RestController there is no need of explicit @ResponseBody annotation

@Controller
@RequestMapping(value = "/foos")
public class MyFooController {

    @Autowired
	MyFooService myFooService;
	
    // API - read

    @RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    @ResponseBody
    public Collection<Foo> findAll() { // All the bindings from Object (Foo or Collection<Foo>) to JSON and JSON to Object are happening through jackson binding API?
    	return myFooService.findAllFoos(); // XML support to jackson can be added using dependecy jackson-dataformat-xml
    }

/*  Media Type: application/hal+json (JSON Hypertext Application Language)
    */ 
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = { "application/hal+json","application/xml" })
    @ResponseBody
    public Resource<Foo> findById(@PathVariable final long id) {
        final Foo foo = myFooService.findFooById(id);
        
        if (foo == null) {
            throw new ResourceNotFoundException("Resourse not found!");
        }
      
      // Adding self HATEOS Link  
        
        //1) the linkTo() method inspects the controller class and obtains its root mapping
        //2) the slash() method adds the customerId value as the path variable of the link
        //3) finally, the withSelfMethod() qualifies the relation as a self-link
        
        Link selfLink = linkTo(this.getClass()).slash(foo.getFooId()).withSelfRel(); // Other parameters like media,title,type,hreflang can be set for selfLink 
        foo.add(selfLink); // Foo must extend ResourceSupport to get add method
        
      // Adding HATEOAS link to all other resource
        //"all-foos", SERVER_PATH + "/foos"
  		ControllerLinkBuilder linkTo = 
  				linkTo(methodOn(this.getClass()).findAll()); // Do not hard code link "/foos" instead get it from method
  		foo.add(linkTo.withRel("all-foos")); // Name for HATEOAS link - "all-foos"
      	
  		Resource<Foo> resource = new Resource<Foo>(foo);
        return resource; // return HATEOAS resource instead of POJO
    }

    // API - write

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = { "application/json", "application/xml" }, produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Foo updateFoo(@PathVariable("id") final long id, @RequestBody final Foo foo) {
    	myFooService.updateFoo(id, foo);
        return foo;
    }

	/*
	 * When a client needs to replace an existing Resource entirely, they can use
	 * PUT. When they're doing a partial update, they can use HTTP PATCH.
	 * PUT is idempotent; PATCH can be, but isn't required to.
	 * For an operation (or service call) to be idempotent, clients can make that same call repeatedly while producing the same result.
	 */    
    
    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void patchFoo(@PathVariable("id") final long id, @RequestBody final Foo foo) {
    	myFooService.patchFoo(id, foo);
    }
    

    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json", "application/xml" }, produces = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Foo createFoo(@Valid @RequestBody final Foo foo, HttpServletResponse response) {
    	myFooService.createFoo(foo);
        response.setHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/" + foo.getFooId())
            .toUriString());
        return foo;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable final long id) {
    	myFooService.deleteById(id);
    }
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/form")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String submitFoo(@RequestParam("id") String id, @RequestParam("name") String name) {
        long idl = Long.parseLong(id);
        myFooService.createFoo(new Foo(idl, name));
    	return id;
    }
    
    

}
