package com.packtpub.reactive;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	private Integer userid;
	private String username;

	
/*	The @JsonProperty annotation is used to indicate the property name in JSON.
	Use @JsonProperty to serialize/deserialize the property name when we're dealing with non-standard getters and setters 
	or when we want JSON property names to be different from Pojo properties.
*/	
	public User(@JsonProperty("userid") Integer userid,  @JsonProperty("username") String username){
		this.userid = userid;
		this.username = username;
	}

	//@JsonProperty("userID") // propertyname in response changes
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	//@JsonProperty("userNAME")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}