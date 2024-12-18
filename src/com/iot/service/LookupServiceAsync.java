package com.iot.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.iot.dao.LookupDao;
import com.iot.domain.Lookup;

import java.util.List;

public interface LookupServiceAsync {
    void fetchDeviceTypes(AsyncCallback<List<Lookup>> callback);
    void fetchLicenses(AsyncCallback<List<Lookup>> callback);
    void fetchDeviceStatuses(AsyncCallback<List<Lookup>> callback);
    void fetchBondedTypes(AsyncCallback<List<Lookup>> callback);
    
}
