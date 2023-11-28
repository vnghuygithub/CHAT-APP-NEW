package com.example.projectappchat.service.groupChatDetail;

import com.example.projectappchat.entity.GroupChatDetail;
import com.example.projectappchat.entity.User;
import com.example.projectappchat.repository.GroupChatDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupChatDetailServiceImpl implements GroupChatDetailService {

    @Autowired
    GroupChatDetailRepository groupChatDetailRepository;

    @Override
    public void save(GroupChatDetail groupChatDetail) {
        groupChatDetailRepository.save(groupChatDetail);
    }

    @Override
    public List<GroupChatDetail> findGroupChatDetailByUserId(Long userId) {
        return groupChatDetailRepository.findGroupChatDetailByUserId(userId);
    }

    @Override
    public List<GroupChatDetail> findGroupChatDetailInGroupChatByGroupChatId(Long groupChatId) {
        return groupChatDetailRepository.findGroupChatDetailInGroupChatByGroupChatId(groupChatId);
    }
}
