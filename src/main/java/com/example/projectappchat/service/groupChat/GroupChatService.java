package com.example.projectappchat.service.groupChat;

import com.example.projectappchat.entity.GroupChat;

import java.util.Optional;

public interface GroupChatService {
    void save(GroupChat groupChat);
    GroupChat findById(Long groupChatId);
}
