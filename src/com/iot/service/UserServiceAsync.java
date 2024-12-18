package com.iot.service;
import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.iot.domain.User;
public interface UserServiceAsync {
	
	void saveUser(User user,AsyncCallback<Void> callback);
	
	void isUsernameExists(String username, AsyncCallback<Boolean> callback);
	void isEmailExists(String email, AsyncCallback<Boolean> callback);	
}
