package com.example.projectappchat.service.groupChatMessage;


import com.example.projectappchat.entity.GroupChatMessage;
import com.example.projectappchat.repository.GroupChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupChatMessageServiceImpl implements GroupChatMessageService{
    @Autowired
    GroupChatMessageRepository groupChatMessageRepository;
    @Override
    public List<GroupChatMessage> findGroupChatMessageByGroupChatId(Long groupChatId) {
        return groupChatMessageRepository.findGroupChatMessageByGroupChatId(groupChatId);
    }

    @Override
    public void save(GroupChatMessage groupChatMessage) {
        groupChatMessageRepository.save(groupChatMessage);
    }
}
