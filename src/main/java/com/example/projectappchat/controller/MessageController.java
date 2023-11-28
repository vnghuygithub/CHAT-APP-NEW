package com.example.projectappchat.controller;

import com.example.projectappchat.entity.GroupChat;
import com.example.projectappchat.entity.GroupChatMessage;
import com.example.projectappchat.entity.Message;
import com.example.projectappchat.entity.User;
import com.example.projectappchat.model.MessageModel;
import com.example.projectappchat.service.groupChat.GroupChatService;
import com.example.projectappchat.service.groupChatMessage.GroupChatMessageService;
import com.example.projectappchat.service.message.MessageService;
import com.example.projectappchat.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private GroupChatMessageService groupChatMessageService;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel messageModel) {
/*        System.out.println("handling send message: " + messageModel + " to " + to);*/

        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, messageModel);
        Message message = new Message();
        User userSend = userService.findUserById(messageModel.getMessageSendId());
        User userReceiver = userService.findUserById(messageModel.getMessageReceiverId());

        message.setMessageSendId(userSend);
        message.setMessageReceiverId(userReceiver);
        message.setMessageBody(messageModel.getMessageBody());

        messageService.save(message);

    }

    @MessageMapping("/group/chat/{to}")
    public void sendMessageToGroupChat(@DestinationVariable String to, MessageModel messageModel) {
        /*        System.out.println("handling send message: " + messageModel + " to " + to);*/

        simpMessagingTemplate.convertAndSend("/topic/group-chat/messages/" + to, messageModel);
        GroupChatMessage groupChatMessage = new GroupChatMessage();
        User userSend = userService.findUserById(messageModel.getMessageSendId());
        GroupChat groupChat = groupChatService.findById(messageModel.getMessageReceiverId());

        groupChatMessage.setUserSendId(userSend);
        groupChatMessage.setGroupChatReceiverId(groupChat);
        groupChatMessage.setGroupChatMessageBody(messageModel.getMessageBody());
        groupChatMessageService.save(groupChatMessage);

    }
}