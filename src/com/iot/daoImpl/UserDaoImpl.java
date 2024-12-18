package com.iot.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.apache.log4j.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.iot.dao.UserDao;
import com.iot.domain.User;
import com.iot.shared.EmailExistsException;
import com.iot.shared.UsernameExistsException;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        logger.info("SessionFactory has been set for UserDaoImpl.");
    }

    public void saveUser(User user) throws UsernameExistsException, EmailExistsException {
        // Check if username already exists
        if (isUsernameExists(user.getUsername())) {
            throw new UsernameExistsException("Username already exists.");
        }

        // Check if email already exists
        if (isEmailExists(user.getEmail())) {
            throw new EmailExistsException("Email already exists.");
        }
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(user);
            logger.info("User saved/updated successfully: " + user);
        } catch (Exception e) {
            logger.error("Error saving/updating user: " + user, e);
        }
    }


    public boolean isUsernameExists(String username) {
        Session session = sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<User> root = criteriaQuery.from(User.class);

            // Criteria to check if the username exists
            criteriaQuery.select(criteriaBuilder.count(root))
                         .where(criteriaBuilder.equal(root.get("username"), username));

            Long count = session.createQuery(criteriaQuery).uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            logger.error("Error checking if username exists: " + username, e);
            return false;
        }
        
    }

    public boolean isEmailExists(String email) {
        Session session = sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<User> root = criteriaQuery.from(User.class);

            // Criteria to check if the email exists
            criteriaQuery.select(criteriaBuilder.count(root))
                         .where(criteriaBuilder.equal(root.get("email"), email));

            Long count = session.createQuery(criteriaQuery).uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            logger.error("Error checking if email exists: " + email, e);
            return false;
        }
    }

    @Override
    public User authenticateUser(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        User authenticatedUser = null;

        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);

            // Criteria to match username
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("username"), username));

            Query<User> query = session.createQuery(criteriaQuery);
            authenticatedUser = query.uniqueResult();

            if (authenticatedUser != null) {
                logger.info("User retrieved for authentication: " + username);
            } else {
                logger.warn("Authentication failed for username: " + username);
            }
        } catch (Exception e) {
            logger.error("Error retrieving user for authentication: " + username, e);
        }

        return authenticatedUser;
    }

	
}
