/*
 * package com.iot.domain;
 * 
 * import java.io.Serializable;
 * 
 * 
 * 
 * public class Device extends PersistantObject implements Serializable {
 * 
 * @Override public String toString() { return "Device [deviceId=" + deviceId +
 * ", userId=" + userId + ", user=" + user + ", deviceCode=" + deviceCode +
 * ", serialNumbers=" + serialNumbers + ", deviceName=" + deviceName +
 * ", deviceType=" + deviceType + ", license=" + license + ", deviceStatus=" +
 * deviceStatus + "]"; }
 * 
 *//**
	* 
	*//*
		 * private static final long serialVersionUID = -7010279223330222479L; private
		 * Long deviceId; private Long userId; private User user; private String
		 * deviceCode; private String serialNumbers; private String deviceName;
		 * 
		 * private Lookup deviceType; private Lookup license; private Lookup
		 * deviceStatus; private Lookup bondedDevice;
		 * 
		 * public Lookup getBondedDevice() { return bondedDevice; }
		 * 
		 * 
		 * public void setBondedDevice(Lookup bondedDevice) { this.bondedDevice =
		 * bondedDevice; }
		 * 
		 * 
		 * public Device() {}
		 * 
		 * 
		 * public Long getDeviceId() { return deviceId; }
		 * 
		 * public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
		 * 
		 * public Long getUserId() { return userId; }
		 * 
		 * public void setUserId(Long userId) { this.userId = userId; }
		 * 
		 * public User getUser() { return user; }
		 * 
		 * public void setUser(User user) { this.user = user; }
		 * 
		 * public Lookup getLicense() { return license; }
		 * 
		 * public void setLicense(Lookup license) { this.license = license; }
		 * 
		 * public String getDeviceCode() { return deviceCode; }
		 * 
		 * public void setDeviceCode(String deviceCode) { this.deviceCode = deviceCode;
		 * }
		 * 
		 * public String getSerialNumbers() { return serialNumbers; }
		 * 
		 * public void setSerialNumbers(String serialNumbers) { this.serialNumbers =
		 * serialNumbers; }
		 * 
		 * public Lookup getDeviceStatus() {
		 * 
		 * return deviceStatus; }
		 * 
		 * public void setDeviceStatus(Lookup deviceStatus) { this.deviceStatus =
		 * deviceStatus; }
		 * 
		 * public String getDeviceName() { return deviceName; }
		 * 
		 * public void setDeviceName(String deviceName) { this.deviceName = deviceName;
		 * }
		 * 
		 * public Lookup getDeviceType() { return deviceType; }
		 * 
		 * public void setDeviceType(Lookup deviceType) { this.deviceType = deviceType;
		 * }
		 * 
		 * 
		 * 
		 * }
		 */




package com.iot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)  // Only include non-null fields
@JsonIgnoreProperties(ignoreUnknown = true)  // Ignore unknown properties
public class Device extends PersistantObject implements Serializable {

    private static final long serialVersionUID = -7010279223330222479L;

    @JsonProperty("deviceId")  // Use this to specify the field name in JSON
    private Long deviceId;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("user")  // Serialize the 'user' object
    private User user;

    @JsonProperty("deviceCode")
    private String deviceCode;

    @JsonProperty("serialNumbers")
    private String serialNumbers;

    @JsonProperty("deviceName")
    private String deviceName;

    @JsonProperty("deviceType")
    private Lookup deviceType;

    @JsonProperty("license")
    private Lookup license;

    @JsonProperty("deviceStatus")
    private Lookup deviceStatus;

    @JsonProperty("bondedDevice")  
    private Lookup bondedDevice;

    public Device() {}

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lookup getLicense() {
        return license;
    }

    public void setLicense(Lookup license) {
        this.license = license;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getSerialNumbers() {
        return serialNumbers;
    }

    public void setSerialNumbers(String serialNumbers) {
        this.serialNumbers = serialNumbers;
    }

    public Lookup getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Lookup deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Lookup getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Lookup deviceType) {
        this.deviceType = deviceType;
    }

    public Lookup getBondedDevice() {
        return bondedDevice;
    }

    public void setBondedDevice(Lookup bondedDevice) {
        this.bondedDevice = bondedDevice;
    }

    @Override
    public String toString() {
        return "Device [deviceId=" + deviceId + ", userId=" + userId + ", user=" + user + ", deviceCode=" + deviceCode
                + ", serialNumbers=" + serialNumbers + ", deviceName=" + deviceName + ", deviceType=" + deviceType
                + ", license=" + license + ", deviceStatus=" + deviceStatus + "]";
    }
}
