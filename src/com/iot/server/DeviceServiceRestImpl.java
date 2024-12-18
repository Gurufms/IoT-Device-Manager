package com.iot.server;

import com.iot.domain.Device;
import com.iot.service.DeviceServiceRest;
import com.iot.dao.DeviceDao;

import javax.ws.rs.Path;
import java.util.List;

@Path("/device")
public class DeviceServiceRestImpl implements DeviceServiceRest {
    private DeviceDao deviceDao;

    public DeviceDao getDeviceDao() {
        return deviceDao;
    }

    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    @Override
    public Device addDevice(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Device cannot be null");
        }
        deviceDao.addDevice(device);
        return device;
    }

    @Override
    public List<Device> getDevicesByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return deviceDao.getDevicesByUserId(userId);
    }
}
