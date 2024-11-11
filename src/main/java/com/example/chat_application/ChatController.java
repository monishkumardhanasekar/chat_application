package com.example.chat_application;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.example.chat_application.model.Message;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage") // Listens for messages sent to /app/sendMessage
    @SendTo("/topic/group") // Broadcasts to all subscribers of /topic/group
    public Message sendMessage(Message message) {
        return message; // Automatically broadcasts to /topic/group
    }

    // Allows targeted sending to a group
    public void sendMessageToGroup(String groupName, Message message) {
        messagingTemplate.convertAndSend("/topic/" + groupName, message);
    }
}