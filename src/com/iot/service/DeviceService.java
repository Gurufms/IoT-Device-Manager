package com.iot.service;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.iot.domain.Device;
import java.util.List;

@RemoteServiceRelativePath("deviceService")
public interface DeviceService extends RemoteService {
    void addDevice(Device device) throws IllegalArgumentException;

    List<Device>fetchDevicesForCurrentUser(Long userId) throws IllegalArgumentException;
    void updateDevice(Device device);

   
}
