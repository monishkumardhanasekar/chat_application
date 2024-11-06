package com.example.chat_application.service;

import com.example.chat_application.model.Group;
import com.example.chat_application.model.User;
import com.example.chat_application.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GroupManagementService {

    private final ConcurrentHashMap<String, Group> groupMap = new ConcurrentHashMap<>();

    // Create a new group
    public String createGroup(String groupName) {
        if (groupMap.containsKey(groupName)) {
            return "Group already exists!";
        }
        Group newGroup = new Group(groupName);
        groupMap.put(groupName, newGroup);
        return "Group " + groupName + " created successfully!";
    }

    // User joins a group
    public String joinGroup(String groupName, String username) {
        Group group = groupMap.get(groupName);
        if (group == null) {
            return "Group not found!";
        }
        User user = new User(username);
        group.addUser(user);
        return username + " joined group " + groupName;
    }

    // User leaves a group
    public String leaveGroup(String groupName, String username) {
        Group group = groupMap.get(groupName);
        if (group == null) {
            return "Group not found!";
        }
        User user = new User(username);
        if (group.getUsers().contains(user)) {
            group.removeUser(user);
            return username + " left group " + groupName;
        } else {
            return "User not found in group!";
        }
    }

    // Broadcast a message to a group
    public String sendMessageToGroup(String groupName, String sender, String content) {
        Group group = groupMap.get(groupName);
        if (group == null) {
            return "Group not found!";
        }

        Message message = new Message(sender, content);
        group.addMessage(message);

        // Simulating message broadcast to users (could be replaced with WebSocket or
        // other mechanism)
        return "Message from " + sender + " broadcasted to group " + groupName + ": " + content;
    }

    // Get messages for a group
    public List<Message> getGroupMessages(String groupName) {
        Group group = groupMap.get(groupName);
        if (group == null) {
            return null;
        }
        return group.getMessages();
    }

    // Get active users in a group
    public Set<User> getActiveUsers(String groupName) {
        Group group = groupMap.get(groupName);
        if (group == null) {
            return null;
        }
        return group.getUsers();
    }
}
