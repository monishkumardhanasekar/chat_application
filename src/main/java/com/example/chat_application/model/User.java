package com.example.chat_application.model;

import java.util.Objects;

public class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Override equals to compare users by username
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    // Override hashCode to generate a hash based on username
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
