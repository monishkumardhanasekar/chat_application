package com.example.chat_application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

@Document(collection = "groups") // MongoDB Collection
public class Group {
    @Id
    private String groupName;
    private Set<User> users = new HashSet<>();
    private List<Message> messages = new ArrayList<>();

    public Group(String groupName) {
        this.groupName = groupName;
    }

    // Getters and setters
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }
}
