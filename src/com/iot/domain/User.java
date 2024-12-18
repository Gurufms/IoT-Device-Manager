
package com.iot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)  
@JsonIgnoreProperties(ignoreUnknown = true)  
public class User extends PersistantObject implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @JsonProperty("userId") 
    private long userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
    
   

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }


	public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	
    // Detach method to handle any cleanup if needed
    public void detach() {
        super.detach();
    }
}
