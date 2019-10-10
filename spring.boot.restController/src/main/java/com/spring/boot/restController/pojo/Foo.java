package com.spring.boot.restController.pojo;

import javax.validation.constraints.Size;

import org.springframework.hateoas.ResourceSupport;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

// Adding Hypermedia Support to a Resource
// The Foo resource extends from the ResourceSupport class to inherit the add() method. 
// So once we create a link, we can easily set that value to the resource representation without adding any new fields to it
@ApiModel(description="All details about the Foo.") // Swagger description for Foo 
public class Foo extends ResourceSupport{
    private long fooId;
    
    @Size(min=2, message="Name should have atleast 2 characters") // -- Size validation for @Valid annotation
    @ApiModelProperty(notes="Name should have atleast 2 characters") // Swagger description for field name 
    private String name;
    
    // Similarly there are other validations in javax.validation API like @Past for date, @NotNull, @NotEmpty, @Future, @Email, @Positive, @Negative etc.

    public Foo() {
        super();
    }

    public Foo(final String name) {
        super();

        this.name = name;
    }

    public Foo(final long fooId, final String name) {
        super();

        this.fooId = fooId;
        this.name = name;
    }

    // API

    public long getFooId() {
		return fooId;
	}

	public void setFooId(long fooId) {
		this.fooId = fooId;
	}

    
    public String getName() {
        return name;
    }

	public void setName(final String name) {
        this.name = name;
    }

	
// Apart from @ApiModel and @ApiModelProperty swagger provides other annotations - APIOpeartion, APIResponse, Authorization, Contact, Example, Extension, ResponseHeader, Tag etc.	(can be checked in swagger-annotations jar file)
}