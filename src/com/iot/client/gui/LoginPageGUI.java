package com.iot.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.iot.service.AuthService;
import com.iot.service.AuthServiceAsync;

public class LoginPageGUI {
    private final AuthServiceAsync service = GWT.create(AuthService.class);

    public LoginPageGUI() {
        // Creating a login panel
        VerticalPanel loginPanel = new VerticalPanel();
        loginPanel.setSpacing(10);
        loginPanel.setStyleName("login-panel");
        Label heading = new Label("IoT Device Manager");
        heading.setStyleName("login-heading");
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyleName("login-font");
        final TextBox usernameTextBox = new TextBox();
        usernameTextBox.getElement().setAttribute("placeholder", "Enter username");
        usernameTextBox.setStyleName("login-textbox");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyleName("login-font");
        final PasswordTextBox passwordTextBox = new PasswordTextBox();
        passwordTextBox.getElement().setAttribute("placeholder", "Enter password");
        passwordTextBox.setStyleName("login-textbox");

        Button loginButton = new Button("Login");
        loginButton.setStyleName("login-button");
        loginButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String username = usernameTextBox.getText();
                String password = passwordTextBox.getText();

                if (username.isEmpty() || password.isEmpty()) {
                    showMessageBox("Username and password are required.");
                    return; 
                }
                loginButton.setEnabled(false);

                service.authenticateUser(username, password, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        showMessageBox("Authentication failed: " + caught.getMessage());
                        loginButton.setEnabled(true); 
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        loginButton.setEnabled(true); 
                        if (result) {
                            loadDevicePage();
                        } else {
                            showMessageBox("Invalid user or password.");
                        }
                    }
                });
            }
        });

        Label signup = new Label("SignUp");
        signup.setStyleName("signup-link");

        signup.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                gotoSignup();
            }
        });

        loginPanel.add(heading);
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameTextBox);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordTextBox);
        loginPanel.add(loginButton);
        loginPanel.add(signup);

        RootPanel.get().add(loginPanel);
    }
    private void showMessageBox(String message) {
        DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Message");
        dialogBox.setAnimationEnabled(true);

        VerticalPanel panel = new VerticalPanel();
        panel.setSpacing(10);  // Spacing between elements inside the panel

        // Add the label and button
        Label label = new Label(message);
        Button okButton = new Button("OK");

        // Add a click handler to hide the dialog when the button is pressed
        okButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });

        panel.add(label);
        panel.add(okButton);

        dialogBox.setWidget(panel);

        panel.getElement().getStyle().setPadding(10, com.google.gwt.dom.client.Style.Unit.PX);
        panel.getElement().getStyle().setMarginTop(10, com.google.gwt.dom.client.Style.Unit.PX);
        dialogBox.setGlassEnabled(true);
       
        dialogBox.center();  
        dialogBox.show();   
    }

    public void loadDevicePage() {
        RootPanel.get().clear();
        DeviceListPageGUI device = new DeviceListPageGUI();
    }

    public void gotoSignup() {
        RootPanel.get().clear();
        RegistrationPage newRegister = new RegistrationPage();
    }
}
