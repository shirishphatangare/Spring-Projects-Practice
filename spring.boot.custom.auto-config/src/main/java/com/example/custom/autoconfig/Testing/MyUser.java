package com.example.custom.autoconfig.Testing;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MyUser {

	@Id
    private String email;
 
    public MyUser(String email) {
    	this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
}