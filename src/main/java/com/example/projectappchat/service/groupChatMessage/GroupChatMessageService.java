package com.example.projectappchat.service.groupChatMessage;

import com.example.projectappchat.entity.GroupChatMessage;

import java.util.List;

public interface GroupChatMessageService {
    List<GroupChatMessage> findGroupChatMessageByGroupChatId(Long groupChatId);
    void save(GroupChatMessage groupChatMessage);
}
