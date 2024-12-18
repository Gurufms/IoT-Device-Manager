package com.iot.service;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.iot.domain.User;
@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService{
	void saveUser(User user) throws IllegalArgumentException;
	
	boolean isUsernameExists(String username);
    boolean isEmailExists(String email);
}
