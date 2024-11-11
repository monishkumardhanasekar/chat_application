package com.example.chat_application.controller;

import com.example.chat_application.model.Message;
import com.example.chat_application.model.User;
import com.example.chat_application.service.GroupManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupManagementService groupManagementService;

    private final SimpMessagingTemplate messagingTemplate;

    public GroupController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createGroup(@RequestParam String groupName) {
        String result = groupManagementService.createGroup(groupName);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinGroup(@RequestParam String groupName, @RequestParam String username) {
        String result = groupManagementService.joinGroup(groupName, username);

        // Notify other members of the group about the new user
        Message joinMessage = new Message(username, username + " has joined the group " + groupName);
        messagingTemplate.convertAndSend("/topic/" + groupName, joinMessage);

        // Send the updated user list to all clients in the group
        Set<User> activeUsers = groupManagementService.getActiveUsers(groupName);
        messagingTemplate.convertAndSend("/topic/" + groupName + "/users", activeUsers);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/leave")
    public ResponseEntity<String> leaveGroup(@RequestParam String groupName, @RequestParam String username) {
        String result = groupManagementService.leaveGroup(groupName, username);

        // Notify other members of the group about the user leaving
        Message leaveMessage = new Message(username, username + " has left the group " + groupName);
        messagingTemplate.convertAndSend("/topic/" + groupName, leaveMessage);

        // Send the updated user list to all clients in the group
        Set<User> activeUsers = groupManagementService.getActiveUsers(groupName);
        messagingTemplate.convertAndSend("/topic/" + groupName + "/users", activeUsers);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String groupName, @RequestParam String sender,
            @RequestParam String content) {
        String result = groupManagementService.sendMessageToGroup(groupName, sender, content);

        // Broadcast the message to the group using WebSocket
        Message message = new Message(sender, content);
        messagingTemplate.convertAndSend("/topic/" + groupName, message);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(@RequestParam String groupName) {
        List<Message> messages = groupManagementService.getGroupMessages(groupName);
        if (messages == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/users")
    public ResponseEntity<Set<User>> getActiveUsers(@RequestParam String groupName) {
        Set<User> activeUsers = groupManagementService.getActiveUsers(groupName);
        if (activeUsers == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activeUsers);
    }
}
