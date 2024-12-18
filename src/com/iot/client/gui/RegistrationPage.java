package com.iot.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.iot.domain.User;
import com.iot.service.AuthService;
import com.iot.service.AuthServiceAsync;
import com.iot.service.UserService;
import com.iot.service.UserServiceAsync;

public class RegistrationPage {
    private User user;
    private final AuthServiceAsync authService = GWT.create(AuthService.class);
    private final UserServiceAsync userService = GWT.create(UserService.class);

    public RegistrationPage() {
        Label heading = new Label("IoT Device Manager");
        heading.setStyleName("heading");

        VerticalPanel loginPanel = new VerticalPanel();
        loginPanel.setSpacing(10);
        loginPanel.setStyleName("login-panel");

        Label usernameLabel = new Label("UserName:");
        final TextBox usernameTextBox = new TextBox();
        usernameTextBox.setStyleName("input-field");
        usernameTextBox.getElement().setAttribute("placeholder", "Enter username");

        Label emailLabel = new Label("Email:");
        final TextBox emailTextBox = new TextBox();
        emailTextBox.setStyleName("input-field");
        emailTextBox.getElement().setAttribute("placeholder", "Enter email");

        // Password field and Eye Icon
        Label passwordLabel = new Label("Password:");
        final PasswordTextBox passwordTextBox = new PasswordTextBox();
        passwordTextBox.setStyleName("input-field password-field");
        passwordTextBox.getElement().setAttribute("placeholder", "Enter password");

        final Image eyeIcon1 = new Image("images/eye-closed-icon.png"); // Eye icon for password visibility toggle
        eyeIcon1.setStyleName("eye-icon-password");
        eyeIcon1.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                togglePasswordVisibility(passwordTextBox, eyeIcon1);
            }
        });

        // Confirm password field and Eye Icon
        Label passwordLabel2 = new Label("Confirm Password:");
        final PasswordTextBox passwordTextBox2 = new PasswordTextBox();
        passwordTextBox2.setStyleName("input-field confirm-password-field");
        passwordTextBox2.getElement().setAttribute("placeholder", "Confirm password");

        final Image eyeIcon2 = new Image("images/eye-closed-icon.png"); // Eye icon for confirm password visibility toggle
        eyeIcon2.setStyleName("eye-icon-confirm-password");
        eyeIcon2.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                togglePasswordVisibility(passwordTextBox2, eyeIcon2);
            }
        });

        Button registerButton = new Button("Register");
        registerButton.setStyleName("register-button");

        Label signin = new Label("Sign in");
        signin.setStyleName("signin-link");

        signin.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                registered();
            }
        });

        registerButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                resetErrorMessages(usernameLabel, emailLabel);

                if (usernameTextBox.getText().isEmpty() || emailTextBox.getText().isEmpty() 
                        || passwordTextBox.getText().isEmpty() || passwordTextBox2.getText().isEmpty()) {
                    showMessage("All fields must be filled!");
                    return;
                }

                String password = passwordTextBox.getText();

                if (!password.equals(passwordTextBox2.getText())) {
                    showMessage("Password and Confirm Password must be the same!");
                    return;
                }

                String name = usernameTextBox.getText();
                String email = emailTextBox.getText();

                User user = new User();
                user.setEmail(email);
                user.setUsername(name);
                user.setPassword(password);

                userService.saveUser(user, new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        showMessage("Registered Successfully.", new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                registered();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        handleRegistrationFailure(caught, usernameLabel, emailLabel);
                    }
                });
            }
        });

        // Adding all elements to the panel
        loginPanel.add(heading);
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameTextBox);
        loginPanel.add(emailLabel);
        loginPanel.add(emailTextBox);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordTextBox);
        loginPanel.add(eyeIcon1); // Eye icon for password field
        loginPanel.add(passwordLabel2);
        loginPanel.add(passwordTextBox2);
        loginPanel.add(eyeIcon2); // Eye icon for confirm password field
        loginPanel.add(registerButton);
        loginPanel.add(signin);

        RootPanel.get().add(loginPanel);
    }

    // Toggle password visibility for both password fields
    private void togglePasswordVisibility(PasswordTextBox passwordTextBox, Image eyeIcon) {
        String type = passwordTextBox.getElement().getAttribute("type");
        if (type.equals("password")) {
            passwordTextBox.getElement().setAttribute("type", "text");
            eyeIcon.setUrl("images/eye-open-icon.png"); // Change to open eye icon
        } else {
            passwordTextBox.getElement().setAttribute("type", "password");
            eyeIcon.setUrl("images/eye-closed-icon.png"); // Change to closed eye icon
        }
    }

    private void showMessage(String message) {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Message");
        dialogBox.setHTML(message);

        Button okButton = new Button("OK");
        okButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });
        dialogBox.setWidget(okButton);
        dialogBox.setGlassEnabled(true);
        dialogBox.center();
        dialogBox.show();
    }

    private void showMessage(String message, ClickHandler okHandler) {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Message");

        VerticalPanel panel = new VerticalPanel();
        panel.setSpacing(10);
        panel.add(new Label(message));

        Button okButton = new Button("OK");
        okButton.addClickHandler(okHandler);
        panel.add(okButton);

        dialogBox.setWidget(panel);
        dialogBox.setGlassEnabled(true);
        dialogBox.center();
        dialogBox.show();
    }

    private void handleRegistrationFailure(Throwable caught, Label usernameLabel, Label emailLabel) {
        if (caught instanceof IllegalArgumentException) {
            String errorMessage = caught.getMessage();
            if (errorMessage.equals("Username already exists.")) {
                showError(usernameLabel, "UserName: (Username already exists.)");
            } else if (errorMessage.equals("Email already exists.")) {
                showError(emailLabel, "Email: (Email already exists.)");
            } else {
                showMessage("Error: " + errorMessage);
            }
        } else {
            showMessage("An unexpected error occurred: " + caught.getMessage());
        }
    }

    private void showError(Label label, String message) {
        label.setText(message);
        label.setStyleName("error-label");
    }

    private void resetErrorMessages(Label usernameLabel, Label emailLabel) {
        usernameLabel.setText("UserName:");
        emailLabel.setText("Email:");
        usernameLabel.setStyleName("");
        emailLabel.setStyleName("");
    }

    public void registered() {
        RootPanel.get().clear();
        LoginPageGUI loginPage = new LoginPageGUI();
    }
}