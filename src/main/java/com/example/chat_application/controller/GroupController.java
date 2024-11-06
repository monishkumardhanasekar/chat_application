package com.example.chat_application.controller;

import com.example.chat_application.model.Message;
import com.example.chat_application.model.User;
import com.example.chat_application.service.GroupManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupManagementService groupManagementService;

    // Endpoint to create a new group
    @PostMapping("/create")
    public ResponseEntity<String> createGroup(@RequestParam String groupName) {
        String result = groupManagementService.createGroup(groupName);
        return ResponseEntity.ok(result);
    }

    // Endpoint for a user to join a group
    @PostMapping("/join")
    public ResponseEntity<String> joinGroup(@RequestParam String groupName, @RequestParam String username) {
        String result = groupManagementService.joinGroup(groupName, username);
        return ResponseEntity.ok(result);
    }

    // Endpoint for a user to leave a group
    @PostMapping("/leave")
    public ResponseEntity<String> leaveGroup(@RequestParam String groupName, @RequestParam String username) {
        String result = groupManagementService.leaveGroup(groupName, username);
        return ResponseEntity.ok(result);
    }

    // Endpoint to send a message to a group
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String groupName, @RequestParam String sender,
            @RequestParam String content) {
        String result = groupManagementService.sendMessageToGroup(groupName, sender, content);
        return ResponseEntity.ok(result);
    }

    // Endpoint to get messages from a group
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(@RequestParam String groupName) {
        List<Message> messages = groupManagementService.getGroupMessages(groupName);
        if (messages == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(messages);
    }

    // Endpoint to get active users in a group
    @GetMapping("/users")
    public ResponseEntity<Set<User>> getActiveUsers(@RequestParam String groupName) {
        Set<User> activeUsers = groupManagementService.getActiveUsers(groupName);
        if (activeUsers == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activeUsers);
    }
}