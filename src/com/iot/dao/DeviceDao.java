package com.iot.dao;

import com.iot.domain.Device;

import java.util.List;

public interface DeviceDao { 
    void addDevice(Device device);
    List<Device> getDevicesByUserId(Long userId); 
}
