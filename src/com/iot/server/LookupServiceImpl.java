package com.iot.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.iot.dao.LookupDao;
import com.iot.domain.Lookup;
import com.iot.service.LookupService;

import org.apache.log4j.Logger;
import com.iot.domain.Category;

import java.util.List;

public class LookupServiceImpl extends RemoteServiceServlet implements LookupService {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LookupServiceImpl.class);
    private LookupDao lookupDao;

    public LookupServiceImpl() {
        /*try {
            lookupDao = (LookupDao) ApplicationContextListner.applicationContext.getBean("lookupDao");
            logger.info("LookupDao bean initialized successfully.");
        } catch (Exception e) {
            logger.error("Failed to initialize LookupDao bean.", e);
        }*/
    }

    @Override
    public List<Lookup> fetchDeviceTypes() {
        logger.info("Fetching device types.");
        lookupDao = (LookupDao) ApplicationContextListner.applicationContext.getBean("lookupDao");
        List<Lookup> deviceTypes = null;
        try {
        	deviceTypes = lookupDao.getAllDeviceTypes();
            logger.info("Successfully fetched device types: " + deviceTypes);
        } catch (Exception e) {
            logger.error("Error fetching device types.", e);
        }
        return deviceTypes;
    }
    @Override
    public List<Lookup> fetchBondedTypes() {
        logger.info("Fetching bonded types.");
        lookupDao = (LookupDao) ApplicationContextListner.applicationContext.getBean("lookupDao");
        List<Lookup> bondedTypes = null;
        try {
        	bondedTypes = lookupDao.getAllBondedTypes();
            logger.info("Successfully fetched device types: " + bondedTypes);
        } catch (Exception e) {
            logger.error("Error fetching bonded types.", e);
        }
        return bondedTypes;
    }

    @Override
    public List<Lookup> fetchLicenses() {
        logger.info("Fetching licenses.");
        lookupDao = (LookupDao) ApplicationContextListner.applicationContext.getBean("lookupDao");
        List<Lookup> licenses = null;
        try {
            licenses = lookupDao.getAllLicenses();

            logger.info("Successfully fetched licenses: " + licenses);
        } catch (Exception e) {
            logger.error("Error fetching licenses.", e);
        }
        return licenses;
    }

    @Override
    public List<Lookup> fetchDeviceStatuses() {
        logger.info("Fetching device statuses.");
        lookupDao = (LookupDao) ApplicationContextListner.applicationContext.getBean("lookupDao");
        List<Lookup> deviceStatuses = null;
        try {
            deviceStatuses = lookupDao.getAllDeviceStatuses();
            logger.info("Successfully fetched device statuses: " + deviceStatuses);
        } catch (Exception e) {
            logger.error("Error fetching device statuses.", e);
        }
        return deviceStatuses;
    }

	public LookupDao getLookupDao() {
		return lookupDao;
	}

	public void setLookupDao(LookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}   
    
}


//package com.iot.server;
//
//import com.google.gwt.user.server.rpc.RemoteServiceServlet;
//import com.iot.client.service.LookupService;
//import com.iot.dao.LookupDao;
//import com.iot.domain.Lookup;
//import org.apache.log4j.Logger;
//import com.iot.domain.Category;
//
//import java.util.List;
//
//public class LookupServiceImpl extends RemoteServiceServlet implements LookupService {
//    private static final long serialVersionUID = 1L;
//    private static final Logger logger = Logger.getLogger(LookupServiceImpl.class);
//    private LookupDao lookupDao;
//    
//    public void setLookupDao(LookupDao lookupDao) {
//    	try{
//    		this.lookupDao = lookupDao;
//    		logger.info("LookupDao bean initialized successfully.");
//    	}catch(Exception e){
//    		logger.info("LookupDao bean not initialized ", e);
//    	}
//    }
//
//    @Override
//    public List<Lookup> fetchDeviceTypes() {
//        logger.info("Fetching device types.");
//        List<Lookup> deviceTypes = null;
//        try {
//            deviceTypes = lookupDao.getAllDeviceTypes();
//            logger.info("Successfully fetched device types: " + deviceTypes);
//        } catch (Exception e) {
//            logger.error("Error fetching device types.", e);
//        }
//        return deviceTypes;
//    }
//
//    @Override
//    public List<Lookup> fetchBondedTypes() {
//        logger.info("Fetching bonded types.");
//        List<Lookup> bondedTypes = null;
//        try {
//            bondedTypes = lookupDao.getAllBondedTypes();
//            logger.info("Successfully fetched bonded types: " + bondedTypes);
//        } catch (Exception e) {
//            logger.error("Error fetching bonded types.", e);
//        }
//        return bondedTypes;
//    }
//
//    @Override
//    public List<Lookup> fetchLicenses() {
//        logger.info("Fetching licenses.");
//        List<Lookup> licenses = null;
//        try {
//            licenses = lookupDao.getAllLicenses();
//            logger.info("Successfully fetched licenses: " + licenses);
//        } catch (Exception e) {
//            logger.error("Error fetching licenses.", e);
//        }
//        return licenses;
//    }
//
//    @Override
//    public List<Lookup> fetchDeviceStatuses() {
//        logger.info("Fetching device statuses.");
//        List<Lookup> deviceStatuses = null;
//        try {
//            deviceStatuses = lookupDao.getAllDeviceStatuses();
//            logger.info("Successfully fetched device statuses: " + deviceStatuses);
//        } catch (Exception e) {
//            logger.error("Error fetching device statuses.", e);
//        }
//        return deviceStatuses;
//    }
//
//    public void saveLookup(String lookupName, String categoryName) {
//        try {
//            Category category = new Category();
//            category.setName(categoryName);
//
//            Lookup lookup = new Lookup(lookupName);
//            lookup.setCategory(category); 
//
//            lookupDao.saveLookup(lookup);
//            logger.info("Successfully saved Lookup: " + lookup + " with Category: " + category);
//        } catch (Exception e) {
//            logger.error("Error saving Lookup.", e);
//        }
//    }
//}

