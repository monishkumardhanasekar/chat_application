package com.example.chat_application.model;

import java.util.Objects;

public class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    // Getters, setters, equals, and hashCode
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        return Objects.equals(username, ((User) o).username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
