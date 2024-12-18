package com.iot.dao;

import com.iot.domain.Lookup;
import java.util.List;
import com.iot.domain.Category;
public interface LookupDao {
    List<Lookup> getAllDeviceTypes();
    List<Lookup> getAllLicenses();
    List<Lookup> getAllDeviceStatuses();
    List<Lookup> getAllBondedTypes();
    void saveLookupWithCategory(Lookup lookup);
}
