package com.iot.client.gui; 
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.iot.domain.User;
import com.iot.service.AuthService;
import com.iot.service.AuthServiceAsync;
import com.iot.service.DeviceService;
import com.iot.service.DeviceServiceAsync;
import com.iot.service.LookupService;
import com.iot.service.LookupServiceAsync;
import com.iot.service.UserService;
import com.iot.service.UserServiceAsync;
import com.iot.domain.Device;
import com.iot.domain.Lookup;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
public class DeviceListPageGUI {
    private final AuthServiceAsync authService = GWT.create(AuthService.class);
    private final DeviceServiceAsync deviceService = GWT.create(DeviceService.class);
    final UserServiceAsync userService = GWT.create(UserService.class);
    //private  final UserServiceAsync userService = GWT.create(UserService.class);
    private List<Device> devicesToShow = new ArrayList<>();
    private VerticalPanel globalPanel = new VerticalPanel();
    private TabPanel tabpanel = new TabPanel();
    private Label username;
    private FlexTable deviceTable;
    private FlexTable bondedDeviceTable;

    private Device currentDevice; 
    private Button saveButton; 
    private Button addButton; 
    private TextBox devicename;
    private TextBox deviceCodeInput;
    private TextBox serialNo;
    private ListBox licenseListBox;
    private List<Lookup> licenseTypes;
    private ListBox deviceStatusListBox;
    private List<Lookup> deviceStatusTypes;
    private ListBox deviceTypeListBox;
    private List<Lookup> deviceTypes;
    //new feature
    private ListBox bondedTypeListBox;
    private List<Lookup> bondedTypes;

    public DeviceListPageGUI() {
    	RootPanel.get().add(globalPanel);
        deviceTable = new FlexTable();
        saveButton = new Button("Save Changes"); 
        saveButton.setStyleName("save-bt");
        saveButton.getElement().getStyle().setBackgroundColor("#0ba6ff");
        saveButton.setVisible(false); // Initially hidden
        
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                GWT.log("Save button clicked");
                updateDevice();
            }
        });
       
        addDevice();
        createDeviceTable();
        RootPanel.get().add(globalPanel);
    }

    public void addDevice() {
    	authService.getCurrentUser(new AsyncCallback<User>() {
            @Override
            public void onFailure(Throwable caught) {
                
                showMessage("Failed to get current user.");
                
            }

            @Override
            public void onSuccess(User result) {
                username = new Label("USER : " + result.getUsername());
                username.setStyleName("display-username");
                globalPanel.add(username);
                
                // Fetch devices for the logged-in user
                fetchDevicesForUser(result.getUserId());
            }
        });

        
        // Creating a Device Vertical Panel
        VerticalPanel devicePanel = new VerticalPanel();
        devicePanel.setStyleName("device-panel");
        devicePanel.setSpacing(10);
        
        // Device name input
        Label deviceNameLabel = new Label("Device Name ");
        deviceNameLabel.setStyleName("add-device");
        devicename = new TextBox();
        devicename.setStyleName("device-input");

        // Select device type
        Label nameLabel = new Label("Device Type ");
        nameLabel.setStyleName("add-device");
        deviceTypeListBox = new ListBox();
        deviceTypeListBox.setStyleName("device-list");
        initializeDeviceTypes();
        
        
        
     // if selected any thing other than masterGate way ---> enable
        Label bondLabel = new Label("Bonded Gateway");
        bondLabel.setStyleName("add-device");
        bondedTypeListBox = new ListBox();
        bondedTypeListBox.setStyleName("device-list");
        initializeBondedTypes();
        
        // License input
        Label licenseLabel = new Label("License ");
        licenseLabel.setStyleName("add-device");
        licenseListBox = new ListBox();
        licenseListBox.setStyleName("device-list");
        initializeLicense();
        
        // Device code input
        Label deviceCodeLabel = new Label("Device Code ");
        deviceCodeLabel.setStyleName("add-device");
        deviceCodeInput = new TextBox();
        deviceCodeInput.setStyleName("device-input");

        // Serial number input
        Label serialNumberLabel = new Label("Serial Number ");
        serialNumberLabel.setStyleName("add-device");
        serialNo = new TextBox();
        serialNo.setStyleName("device-input");

        // Device status
        Label deviceStatusLabel = new Label("Device Status ");
        deviceStatusLabel.setStyleName("add-device");
        deviceStatusListBox = new ListBox();
        deviceStatusListBox.setStyleName("device-list");
        initializeStatus();
        
        
        deviceTypeListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                int selectedIndex = deviceTypeListBox.getSelectedIndex();
                String selectedDeviceType = deviceTypeListBox.getItemText(selectedIndex);

                if ("Master Gateway".equals(selectedDeviceType)) {
                    bondedTypeListBox.setEnabled(false);
                } else {
                   bondedTypeListBox.setEnabled(true); 
                
                }
            }
        });
        // Add button
        addButton = new Button("Add"); 
        addButton.setStyleName("add-bt");

        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	
            	final String deviceName = devicename.getText();
            	final int selectedDeviceIndex = deviceTypeListBox.getSelectedIndex();
            	final Lookup deviceType = deviceTypes.get(selectedDeviceIndex); 
            	final int selectedBondedDevice = bondedTypeListBox.getSelectedIndex();
            	final Lookup bondedDevice = bondedTypes.get(selectedBondedDevice);
            	final int selectedLicenseIndex = licenseListBox.getSelectedIndex();
            	final Lookup licenseNum = licenseTypes.get(selectedLicenseIndex); 
            	final String deviceCodeNum = deviceCodeInput.getText();
            	final String serialNum = serialNo.getText();
            	final int selectedStatusIndex = deviceStatusListBox.getSelectedIndex();
            	final Lookup deviceStatus = deviceStatusTypes.get(selectedStatusIndex);

                if (deviceName.isEmpty() || deviceType.equals("Not Selected") || serialNum.isEmpty()) {
                   
                    showMessage("Please fill out all fields correctly.");
                    return;
                }
              
                authService.getCurrentUser(new AsyncCallback<User>() {
                	 
                    @Override
                    public void onFailure(Throwable caught) {
                    	showMessage("Failed to retrieve current user ID: " + caught.getMessage());
                        
                   
                    }

                    @Override
                    public void onSuccess(User currentUser) {
                        // Creating a device object
                    	
                        final Device device = new Device();   
                     
                        device.setUser(currentUser);
                        device.setUserId(currentUser.getUserId()); 
                        device.setDeviceName(deviceName);
                        device.setDeviceType(deviceType);
                        device.setBondedDevice(bondedDevice);
                        device.setLicense(licenseNum);
                        device.setDeviceCode(deviceCodeNum);
                        device.setSerialNumbers(serialNum);
                        device.setDeviceStatus(deviceStatus);
                     
                        deviceService.addDevice(device, new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable caught) {
                            	showMessage("Failed to add the device.");
                            }

                            @Override
                            public void onSuccess(Void result) {
                            	showMessage("Device added successfully");

                                // Adding the new device to the list and refreshing the table
                                devicesToShow.add(device);
                                refreshDevices();
                                
                                // Clear the fields after adding
                                devicename.setText("");
                                deviceTypeListBox.setSelectedIndex(0); 
                                bondedTypeListBox.setSelectedIndex(0);
                                licenseListBox.setSelectedIndex(0); 
                                deviceCodeInput.setText("");
                                serialNo.setText("");
                                deviceStatusListBox.setSelectedIndex(0);
                            }
                        });
                    }
                });
            }
        });
     // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyleName("logout-bt");
        logoutButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                logout();
                
            }
        });
        globalPanel.add(logoutButton);

        // Adding components to the panel
        devicePanel.add(deviceNameLabel);
        devicePanel.add(devicename);
        devicePanel.add(nameLabel);
        devicePanel.add(deviceTypeListBox);
        
        devicePanel.add(bondLabel);
        devicePanel.add(bondedTypeListBox);
        
        devicePanel.add(licenseLabel);
        devicePanel.add(licenseListBox);
        devicePanel.add(deviceCodeLabel);
        devicePanel.add(deviceCodeInput);
        devicePanel.add(serialNumberLabel);
        devicePanel.add(serialNo);
        devicePanel.add(deviceStatusLabel);
        devicePanel.add(deviceStatusListBox);
        devicePanel.add(addButton);
        devicePanel.add(saveButton);

        globalPanel.add(devicePanel);
    }
    private void initializeBondedTypes(){
    	bondedTypeListBox = new ListBox(); 
    	bondedTypes = new ArrayList<>();

         LookupServiceAsync lookupService = GWT.create(LookupService.class);
         lookupService.fetchBondedTypes(new AsyncCallback<List<Lookup>>() {
             @Override
             public void onFailure(Throwable caught) {
            	 showMessage("Failed to fetch Bonded types.");
             }

             @Override
             public void onSuccess(List<Lookup> result) {
            	 bondedTypes.addAll(result);
                 for (Lookup lookup : bondedTypes) {
                	 bondedTypeListBox.addItem(lookup.getName());
                 }
             }
         });
    }
    private void initializeDeviceTypes() {
        deviceTypeListBox = new ListBox(); 
        deviceTypes = new ArrayList<>();

        LookupServiceAsync lookupService = GWT.create(LookupService.class);
        lookupService.fetchDeviceTypes(new AsyncCallback<List<Lookup>>() {
            @Override
            public void onFailure(Throwable caught) {
            	showMessage("Failed to fetch device types.");
            }

            @Override
            public void onSuccess(List<Lookup> result) {
                deviceTypes.addAll(result);
                for (Lookup lookup : deviceTypes) {
                    deviceTypeListBox.addItem(lookup.getName());
                }
            }
        });
    }

    private void initializeLicense() {
        licenseListBox = new ListBox(); 
        licenseTypes = new ArrayList<>();

        LookupServiceAsync lookupService = GWT.create(LookupService.class);
        lookupService.fetchLicenses(new AsyncCallback<List<Lookup>>() {
            @Override
            public void onFailure(Throwable caught) {
            	showMessage("Failed to fetch licenses.");
            }

            @Override
            public void onSuccess(List<Lookup> result) {
                licenseTypes.addAll(result);
                for (Lookup lookup : licenseTypes) {
                    licenseListBox.addItem(lookup.getName());
                }
            }
        });
    }

    private void initializeStatus() {
        deviceStatusListBox = new ListBox(); 
        deviceStatusTypes = new ArrayList<>();
        
        LookupServiceAsync lookupService = GWT.create(LookupService.class);
        lookupService.fetchDeviceStatuses(new AsyncCallback<List<Lookup>>() {
            @Override
            public void onFailure(Throwable caught) {
            	showMessage("Failed to fetch device statuses.");
            }

            @Override
            public void onSuccess(List<Lookup> result) {
                deviceStatusTypes.addAll(result);
                for (Lookup lookup : deviceStatusTypes) {
                    deviceStatusListBox.addItem(lookup.getName());
                }
            }
        });
    }
   

    private void createDeviceTable() {
        GWT.log("Creating device table");

        // Initialize TabPanel for devices and bonded devices
        TabPanel tabPanel = new TabPanel();
        tabPanel.setStyleName("device-tab-panel");

        // Create Device Table
        deviceTable.setStyleName("deviceTable");

        // Set headers for Device Table
        deviceTable.setText(0, 0, "Device Name");
        deviceTable.setText(0, 1, "Serial Number");
        deviceTable.setText(0, 2, "Device Code");
        deviceTable.setText(0, 3, "Device Type");
        deviceTable.setText(0, 4, "Device Status");

        // Style header row
        deviceTable.getRowFormatter().setStyleName(0, "tableHeader");

        // Add click handler for device table rows
        deviceTable.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                int rowIndex = getClickedRow(event);
                if (rowIndex >= 1) { // Skip header row
                    currentDevice = devicesToShow.get(rowIndex - 1);
                    populateEditFields(currentDevice);
                }
            }
        });

        // Create ScrollPanel for device table
        ScrollPanel scrollPanel = new ScrollPanel(deviceTable);
        scrollPanel.setStyleName("scrollableTablePanel");

        // Create VerticalPanel for Devices Tab
        VerticalPanel devicesTabPanel = new VerticalPanel();
        devicesTabPanel.add(scrollPanel);

        // Devices Tab Label
        HTML devicesLabel = new HTML("<span style='font-size: x-large;'>Devices</span>");
        tabPanel.add(devicesTabPanel, devicesLabel);
        tabPanel.selectTab(0);
        
        
        globalPanel.add(tabPanel);
    }

    private int getClickedRow(ClickEvent event) {
        int rowIndex = deviceTable.getCellForEvent(event).getRowIndex();
        GWT.log("Clicked row index: " + rowIndex);
        return rowIndex;
    }

    private void populateEditFields(Device device) {
        GWT.log("Populating edit fields for device: " + device.getDeviceName());
        devicename.setText(device.getDeviceName());
        deviceTypeListBox.setSelectedIndex(getIndexOf(device.getDeviceType(), deviceTypeListBox));
        licenseListBox.setSelectedIndex(getIndexOf(device.getLicense(), licenseListBox));
        deviceCodeInput.setText(device.getDeviceCode());
        serialNo.setText(device.getSerialNumbers());
        deviceStatusListBox.setSelectedIndex(getIndexOf(device.getDeviceStatus(), deviceStatusListBox));

        
        saveButton.setVisible(true);
        addButton.setVisible(false); 
    }

    private int getIndexOf(Lookup item, ListBox listBox) {
        for (int i = 0; i < listBox.getItemCount(); i++) {
            if (listBox.getItemText(i).equals(item.getName())) {
                return i;
            }
        }
        return -1;
    }
    
    private Lookup getSelectedLookup(ListBox listBox, List<Lookup> list){
    	return list.get(listBox.getSelectedIndex());
    }

    private void updateDevice() {
        GWT.log("Updating device: " + currentDevice.getDeviceName());
        if (currentDevice != null) {
            currentDevice.setDeviceName(devicename.getText());
            currentDevice.setDeviceType(getSelectedLookup(deviceTypeListBox, deviceTypes));
            currentDevice.setLicense(getSelectedLookup(licenseListBox, licenseTypes));
            currentDevice.setDeviceCode(deviceCodeInput.getText());
            currentDevice.setSerialNumbers(serialNo.getText());
            currentDevice.setDeviceStatus(getSelectedLookup(deviceStatusListBox, deviceStatusTypes));

            deviceService.updateDevice(currentDevice, new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    GWT.log("Failed to update the device: " + caught.getMessage());
                    showMessage("Failed to update the device.");
                    saveButton.setVisible(false); 
                }

                @Override
                public void onSuccess(Void result) {
                    GWT.log("Device updated successfully: " + currentDevice.getDeviceName());
                    showMessage("Device updated successfully");
                    refreshDevices();
                    saveButton.setVisible(false); 
                    clearEditFields(); 
                }
            });
        }
    }

    private void clearEditFields() {
        devicename.setText("");
        deviceTypeListBox.setSelectedIndex(0);
        licenseListBox.setSelectedIndex(0);
        deviceCodeInput.setText("");
        serialNo.setText("");
        deviceStatusListBox.setSelectedIndex(0);
        currentDevice = null;
        saveButton.setVisible(false); 
        addButton.setVisible(true); 
    }

    private void fetchDevicesForUser(Long userId) {
        GWT.log("Fetching devices for user ID: " + userId);
        deviceService.fetchDevicesForCurrentUser(userId, new AsyncCallback<List<Device>>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Failed to fetch devices: " + caught.getMessage());
                showMessage("Failed to fetch devices.");
            }

            @Override
            public void onSuccess(List<Device> result) {
                devicesToShow.clear();
                devicesToShow.addAll(result);
                refreshDevices();
            }
        });
    }

    private void refreshDevices() {
        GWT.log("Refreshing device list");
        int row = 0;

        deviceTable.clear(); 

        if (row == 0) {
            deviceTable.setText(row, 0, "Device Name");
            deviceTable.setText(row, 1, "Serial Number");
            deviceTable.setText(row, 2, "Device Code");
            deviceTable.setText(row, 3, "Device Type");
            deviceTable.setText(row, 4, "Device Status");
            row++; 
        }

        for (Device d : devicesToShow) {
            deviceTable.setText(row, 0, d.getDeviceName());   
            deviceTable.setText(row, 1, d.getSerialNumbers()); 
            deviceTable.setText(row, 2, d.getDeviceCode());     
            deviceTable.setText(row, 3, d.getDeviceType().getName());     
            deviceTable.setText(row, 4, d.getDeviceStatus().getName());

            row++;
        }
    }

    public void logout() {
        GWT.log("Logging out");
        RootPanel.get().clear();
        final AuthServiceAsync authService = GWT.create(AuthService.class);
        authService.logout(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
            	showMessage("Logout failed: " + caught.getMessage());
            }

            @Override
            public void onSuccess(Void result) {
            	LoginPageGUI loginPage = new LoginPageGUI();
            }
        });
    }
    private void showMessage(String message) {
        DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Message");
        VerticalPanel dialogContents = new VerticalPanel();
        dialogBox.setWidget(dialogContents);

        dialogContents.add(new Label(message));

        Button closeButton = new Button("Close");
        closeButton.addClickHandler(event -> dialogBox.hide());
        dialogContents.add(closeButton);

        dialogContents.getElement().getStyle().setPadding(10, com.google.gwt.dom.client.Style.Unit.PX);
        dialogContents.getElement().getStyle().setMarginTop(10, com.google.gwt.dom.client.Style.Unit.PX);
        dialogBox.setGlassEnabled(true);  
        dialogBox.center();  
        dialogBox.show();  
    }


}


