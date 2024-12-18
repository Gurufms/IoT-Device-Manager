package com.iot.dao;

import com.iot.domain.User;
import com.iot.shared.EmailExistsException;
import com.iot.shared.UsernameExistsException;

public interface UserDao {
	void saveUser(User user) throws UsernameExistsException, EmailExistsException;
	User authenticateUser(String email,String password);
	
}
