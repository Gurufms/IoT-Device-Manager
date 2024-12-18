package com.iot.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.iot.domain.Device;
import com.iot.service.DeviceService;

public class DeviceServiceAsyncImpl extends RemoteServiceServlet implements DeviceService{

	private static final long serialVersionUID = 1L;

	@Override
	public void addDevice(Device device) throws IllegalArgumentException {
		((DeviceService)(ApplicationContextListner.applicationContext.getBean("deviceService"))).addDevice(device);
	}

	@Override
	public List<Device> fetchDevicesForCurrentUser(Long userId) throws IllegalArgumentException {
		return ((DeviceService)(ApplicationContextListner.applicationContext.getBean("deviceService"))).fetchDevicesForCurrentUser(userId);
	}

	@Override
	public void updateDevice(Device device) {
		((DeviceService)(ApplicationContextListner.applicationContext.getBean("deviceService"))).updateDevice(device);

	}

}
