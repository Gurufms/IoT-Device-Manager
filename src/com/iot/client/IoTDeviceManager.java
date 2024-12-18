package com.iot.client;

import com.iot.client.gui.LoginPageGUI;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.iot.client.gui.DeviceListPageGUI;
import com.iot.client.gui.RegistrationPage;
import com.iot.domain.User;
import com.iot.service.AuthService;
import com.iot.service.AuthServiceAsync;
import com.iot.service.UserService;
import com.iot.service.UserServiceAsync;




/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IoTDeviceManager implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	    final UserServiceAsync userService = GWT.create(UserService.class);
	    final AuthServiceAsync authService = GWT.create(AuthService.class);
	    
	    // Call to check if there's a current user logged in
	    authService.getCurrentUser(new AsyncCallback<User>() {
	        @Override
	        public void onFailure(Throwable caught) {
	            Window.alert("ERROR: Could not check user session.");
	        }

	        @Override
	        public void onSuccess(User result) {
	            if (result == null) {
	                // No user is logged in, show the login page
	                LoginPageGUI loginPage = new LoginPageGUI();
	               
	            } 
	            else {
	               
	            	DeviceListPageGUI devicePage = new DeviceListPageGUI();
	              
	            }
	        }
	    });
	}

}
