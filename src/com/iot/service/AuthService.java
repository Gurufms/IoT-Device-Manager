package com.iot.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.iot.domain.User;
@RemoteServiceRelativePath("auth")


public interface AuthService extends RemoteService {
	Boolean authenticateUser(String username,String password);
	void logout();
	User getCurrentUser();
}
