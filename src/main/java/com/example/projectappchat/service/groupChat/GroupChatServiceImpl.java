package com.example.projectappchat.service.groupChat;

import com.example.projectappchat.entity.GroupChat;
import com.example.projectappchat.repository.GroupChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupChatServiceImpl implements GroupChatService{

    @Autowired
    GroupChatRepository groupChatRepository;

    @Override
    public void save(GroupChat groupChat) {
        groupChatRepository.save(groupChat);
    }

    @Override
    public GroupChat findById(Long groupChatId) {
        return groupChatRepository.getById(groupChatId);
    }
}
