package com.iot.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.iot.dao.LookupDao;
import com.iot.domain.Lookup;
import java.util.List;

@RemoteServiceRelativePath("lookupService")
public interface LookupService extends RemoteService {
    List<Lookup> fetchDeviceTypes();
    List<Lookup> fetchLicenses();
    List<Lookup> fetchDeviceStatuses();
    List<Lookup> fetchBondedTypes();
   
}
