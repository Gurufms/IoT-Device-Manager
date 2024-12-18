package com.iot.service;



import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.iot.domain.User;

public interface AuthServiceAsync {
	void authenticateUser(String username,String password,AsyncCallback<Boolean> callback);
	void logout(AsyncCallback<Void> asyncCallback);
	void getCurrentUser(AsyncCallback<User> asyncCallback);
	
}
