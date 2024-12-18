//
//package com.iot.daoImpl;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.apache.log4j.Logger;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import com.iot.dao.LookupDao;
//import com.iot.domain.Lookup;
//import com.iot.domain.Category;
//import java.util.List;
//
//public class LookupDaoImpl implements LookupDao {
//    private static final Logger logger = Logger.getLogger(LookupDaoImpl.class);
//    private SessionFactory sessionFactory;
//
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//        logger.info("SessionFactory has been set for LookupDaoImpl.");
//    }
//    @Override
//    public List<Lookup> getAllDeviceTypes() {
//        logger.info("Fetching all device types.");
//        return getLookupsByCategory(1);
//    }
//    @Override
//    public List<Lookup> getAllLicenses() {
//        logger.info("Fetching all licenses.");
//        return getLookupsByCategory(2);
//    }
//    @Override
//    public List<Lookup> getAllDeviceStatuses() {
//        logger.info("Fetching all device statuses.");
//        return getLookupsByCategory(3);
//    }
//    @Override
//    public List<Lookup> getAllBondedTypes() {
//        logger.info("Fetching all bonded types.");
//        return getLookupsByCategory(4);
//    }
//    private List<Lookup> getLookupsByCategory(int categoryId) {
//        Session session = sessionFactory.getCurrentSession();
//        List<Lookup> results = null;
//        try {
//            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//            CriteriaQuery<Lookup> criteriaQuery = criteriaBuilder.createQuery(Lookup.class);
//            Root<Lookup> root = criteriaQuery.from(Lookup.class);
//            // Fetching lookups based on the category's categoryId field
//            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId));
//            results = session.createQuery(criteriaQuery).getResultList();
//            logger.info("Successfully fetched lookups for category: " + categoryId + ", Results: " + results);
//        } catch (Exception e) {
//            logger.error("Error fetching lookups for category: " + categoryId, e);
//        }
//       return results;
//    }
//	@Override
//	public void saveCategory(Category category) {
//		// TODO Auto-generated method stub
//	}
//	@Override
//	public void saveLookup(Lookup lookup) {
//		// TODO Auto-generated method stub
//	}
//	 public void saveLookupWithCategory(Lookup lookup) {
//	        Session session = sessionFactory.getCurrentSession();
//	        Category mergedCategory = (Category) session.merge(lookup.getCategory());
//	        lookup.setCategory(mergedCategory);
//	        session.save(lookup);
//	    }
//}
package com.iot.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.log4j.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iot.dao.LookupDao;
import com.iot.domain.Lookup;
import com.iot.domain.Category;
import java.util.List;

public class LookupDaoImpl implements LookupDao {
    private static final Logger logger = Logger.getLogger(LookupDaoImpl.class);
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        logger.info("SessionFactory has been set for LookupDaoImpl.");
    }

    @Override
    public List<Lookup> getAllDeviceTypes() {
        logger.info("Fetching all device types.");
        return getLookupsByCategory(1);
    }

    @Override
    public List<Lookup> getAllLicenses() {
        logger.info("Fetching all licenses.");
        return getLookupsByCategory(2);
    }

    @Override
    public List<Lookup> getAllDeviceStatuses() {
        logger.info("Fetching all device statuses.");
        return getLookupsByCategory(3);
    }

    @Override
    public List<Lookup> getAllBondedTypes() {
        logger.info("Fetching all bonded types.");
        return getLookupsByCategory(4);
    }

    private List<Lookup> getLookupsByCategory(int categoryId) {
        Session session = sessionFactory.getCurrentSession();
        List<Lookup> results = null;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Lookup> criteriaQuery = criteriaBuilder.createQuery(Lookup.class);
            Root<Lookup> root = criteriaQuery.from(Lookup.class);
            // Fetching lookups based on the category's categoryId field
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId));
            results = session.createQuery(criteriaQuery).getResultList();
            logger.info("Successfully fetched lookups for category: " + categoryId + ", Results: " + results);
        } catch (Exception e) {
            logger.error("Error fetching lookups for category: " + categoryId, e);
        }
        return results;
    }
    public void saveLookupWithCategory(Lookup lookup) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Category category = lookup.getCategory();

            // Check if the category exists
            if (category != null) {
                if (category.getCategoryId() == null) {
                    // If categoryId is null, it's a new category that needs to be saved
                    session.save(category); // Save the new category first
                } else {
                    // If categoryId is not null, fetch the existing category
                    Category existingCategory = session.get(Category.class, category.getCategoryId());
                    if (existingCategory != null) {
                        // If the existing category is found, associate it
                        lookup.setCategory(existingCategory);
                    } else {
                        // If not found, save the new category
                        session.save(category);
                    }
                }
            }
            // Now save the lookup
            session.save(lookup); 
            tx.commit();
            logger.info("Successfully saved Lookup: " + lookup.getName() + " with Category: " + (category != null ? category.getName() : "null"));
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            logger.error("Error saving Lookup: " + lookup.getName(), e);
        }
    }

    

}
