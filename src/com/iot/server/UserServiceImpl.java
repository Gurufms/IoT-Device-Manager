package com.iot.server;

import org.apache.log4j.Logger;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.iot.daoImpl.UserDaoImpl;
import com.iot.domain.User;
import com.iot.service.UserService;
import com.iot.shared.EmailExistsException;
import com.iot.shared.UsernameExistsException;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {
	public UserServiceImpl() {
		super();
		// Initialize manually if needed
	}

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AuthServiceImpl.class);
	private UserDaoImpl userDao;
	// Session-scoped bean, injected by Spring
	private AuthServiceImpl authService;

	public UserServiceImpl(AuthServiceImpl authService) {
		this.authService = authService;
	}
	public void saveUser(User user) {
		try {
			
			userDao = (UserDaoImpl) ApplicationContextListner.applicationContext.getBean("userDao");
			userDao.saveUser(user); // Hibernate will handle encryption via custom type

			logger.info("User saved: " + user.getUsername());

		} catch (UsernameExistsException e) {
			logger.error("Username already exists", e);
			throw new IllegalArgumentException("Username already exists.");
		} catch (EmailExistsException e) {
			logger.error("Email already exists", e);
			throw new IllegalArgumentException("Email already exists.");
		} catch (Exception e) {
			logger.error("Error during user saving", e);
		}
	}
	public User getCurrentUser() {
		User currentUser = authService.getCurrentUser();
		logger.info("Current user retrieved: " + (currentUser != null ? currentUser.getUsername() : "null"));
		return currentUser;
	}

	@Override
	public boolean isUsernameExists(String username) {
		// TODO Auto-generated method stub
		return userDao.isUsernameExists(username);
	}

	@Override
	public boolean isEmailExists(String email) {
		// TODO Auto-generated method stub
		return userDao.isEmailExists(email);
	}

}
