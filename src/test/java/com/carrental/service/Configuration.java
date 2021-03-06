package com.carrental.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private static final String PATH_TO_PROPERTIES = "src/test/resources/database.conf";
    private String username;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setProperties() {
        Properties prop = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            prop.load(fileInputStream);
            this.username = prop.getProperty("username");
            this.password = prop.getProperty("password");
        } catch (IOException e) {
            System.err.println("Error reading username and password");
            throw new RuntimeException(e);
        }

    }

}