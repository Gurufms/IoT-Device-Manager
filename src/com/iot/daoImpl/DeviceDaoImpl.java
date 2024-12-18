
package com.iot.daoImpl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.log4j.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.iot.dao.DeviceDao;
import com.iot.domain.Device;

public class DeviceDaoImpl implements DeviceDao {
    private static final Logger logger = Logger.getLogger(DeviceDaoImpl.class);
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        logger.info("SessionFactory has been set.");
    }

    @Override
    public void addDevice(Device device) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(device);
            logger.info("Device added/updated successfully: " + device);
        } catch (Exception e) {
            logger.error("Error adding/updating device: " + device, e);
        }
    }

    @Override
    public List<Device> getDevicesByUserId(Long userId) {
        List<Device> res = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();

        try {
           
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Device> criteriaQuery = criteriaBuilder.createQuery(Device.class);
            Root<Device> root = criteriaQuery.from(Device.class);                  
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("userId"), userId));
            res = session.createQuery(criteriaQuery).getResultList();
            logger.info("Successfully fetched devices for userId: " + userId + ", Devices: " + res);
        } catch (Exception e) {
            logger.error("Error fetching devices for userId: " + userId, e);
        }

        return res;
    }
   
}
