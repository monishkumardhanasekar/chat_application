package com.example.chat_application.service;

import com.example.chat_application.model.Group;
import com.example.chat_application.model.User;
import com.example.chat_application.model.Message;
import com.example.chat_application.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GroupManagementService {

    @Autowired
    private GroupRepository groupRepository;

    public String createGroup(String groupName) {
        if (groupRepository.existsById(groupName)) {
            return "Group already exists!";
        }
        Group newGroup = new Group(groupName);
        groupRepository.save(newGroup);
        return "Group " + groupName + " created successfully!";
    }

    public String joinGroup(String groupName, String username) {
        Group group = groupRepository.findById(groupName).orElse(null);
        if (group == null) {
            return "Group not found!";
        }
        User user = new User(username);
        group.addUser(user);
        groupRepository.save(group);
        return username + " joined group " + groupName;
    }

    public String leaveGroup(String groupName, String username) {
        Group group = groupRepository.findById(groupName).orElse(null);
        if (group == null) {
            return "Group not found!";
        }
        User user = new User(username);
        if (group.getUsers().contains(user)) {
            group.removeUser(user);
            groupRepository.save(group);
            return username + " left group " + groupName;
        } else {
            return "User not found in group!";
        }
    }

    public String sendMessageToGroup(String groupName, String sender, String content) {
        Group group = groupRepository.findById(groupName).orElse(null);
        if (group == null) {
            return "Group not found!";
        }

        User senderUser = new User(sender);
        if (!group.getUsers().contains(senderUser)) {
            return "User is not a member of the group!";
        }

        Message message = new Message(sender, content);
        group.addMessage(message);
        groupRepository.save(group);

        return "Message from " + sender + " broadcasted to group " + groupName + ": " + content;
    }

    public List<Message> getGroupMessages(String groupName) {
        Group group = groupRepository.findById(groupName).orElse(null);
        return group != null ? group.getMessages() : null;
    }

    public Set<User> getActiveUsers(String groupName) {
        Group group = groupRepository.findById(groupName).orElse(null);
        return group != null ? group.getUsers() : null;
    }

    public boolean isUserInGroup(String groupName, String username) {
        // Check if the user is in the group
        Set<User> usersInGroup = getActiveUsers(groupName); // this method returns all active users in the
                                                            // group
        for (User user : usersInGroup) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

}
