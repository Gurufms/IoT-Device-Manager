package com.iot.server;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.iot.daoImpl.DeviceDaoImpl;
import com.iot.domain.Device;
import com.iot.domain.User;
import com.iot.service.DeviceService;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;


public class DeviceServiceImpl extends RemoteServiceServlet implements DeviceService {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(DeviceServiceImpl.class);
    private DeviceDaoImpl deviceDao;
    private SessionRepository<Session> sessionRepository;
    
    public DeviceServiceImpl() {
        logger.info("DeviceServiceImpl initialized with deviceDao: " + deviceDao);
    }
    private AuthServiceImpl authService; // Inject the session-scoped AuthService

    public DeviceServiceImpl(AuthServiceImpl authService, DeviceDaoImpl deviceDao) {
        this.authService = authService;
        this.deviceDao = deviceDao;
    }


    @Override
    public void addDevice(Device device) {
    	  if (device == null) {
    	        logger.error("Attempted to add a null device.");
    	        throw new IllegalArgumentException("Device cannot be null");
    	    }
        try {
            deviceDao.addDevice(device);    
            
            logger.info("Device added/updated successfully: " + device);
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("Error adding/updating device: " + device, e);
        }
    }
    
    @Override
    public List<Device> fetchDevicesForCurrentUser(Long userId) throws IllegalArgumentException {
        logger.info("Fetching devices for user ID: " + userId);
        List<Device> devices = deviceDao.getDevicesByUserId(userId);
        logger.info("Devices fetched: " + devices);
        
        return devices;
    }
    
    public User getCurrentUser() {
        return authService.getCurrentUser();  
    }
    
    @Override
	public void updateDevice(Device device) {
		deviceDao.addDevice(device);
	}


	public DeviceDaoImpl getDeviceDao() {
		return deviceDao;
	}


	public void setDeviceDao(DeviceDaoImpl deviceDao) {
		this.deviceDao = deviceDao;
	}
    
}
