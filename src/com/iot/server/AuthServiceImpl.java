package com.iot.server;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.iot.daoImpl.UserDaoImpl;
import com.iot.domain.User;
import com.iot.service.AuthService;


import org.apache.log4j.Logger;

public class AuthServiceImpl extends RemoteServiceServlet implements AuthService {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AuthServiceImpl.class);

    private UserDaoImpl userDao;

    // Store current user directly in the session-scoped bean
    private User currentUser;

    public AuthServiceImpl() {
        this.currentUser = null;  // Default, no user authenticated initially
    }
 // Injecting the UserDao dependency through setter method or constructor
    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Override
    public Boolean authenticateUser(String username, String password) {
        userDao = (UserDaoImpl) ApplicationContextListner.applicationContext.getBean("userDao");
        User user = userDao.authenticateUser(username, password); // Retrieve user from DB

        if (user != null && password.equals(user.getPassword())) {
            this.currentUser = user;  // Store the authenticated user in session-scoped bean
            logger.info("User authenticated successfully: " + username);
            return true;
        }

        logger.warn("Failed authentication attempt for user: " + username);
        return false;
    }

    @Override
    public void logout() {
        this.currentUser = null;  // Clear the user from the session-scoped bean
        logger.info("User logged out.");
    }

    // Return the currently authenticated user
    public User getCurrentUser() {
        return this.currentUser;
    }
}