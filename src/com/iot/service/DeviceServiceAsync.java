package com.iot.service;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.iot.domain.Device;
import java.util.List;

public interface DeviceServiceAsync {
    void addDevice(Device device, AsyncCallback<Void> callback);

    void fetchDevicesForCurrentUser(Long userId, AsyncCallback<List<Device>> callback);
    
    void updateDevice(Device device, AsyncCallback<Void> callback);

}

