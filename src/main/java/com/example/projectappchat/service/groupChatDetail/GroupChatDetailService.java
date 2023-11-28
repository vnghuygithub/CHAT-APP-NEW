package com.example.projectappchat.service.groupChatDetail;

import com.example.projectappchat.entity.GroupChatDetail;
import com.example.projectappchat.entity.User;

import java.util.List;

public interface GroupChatDetailService {
    void save(GroupChatDetail groupChatDetail);
    List<GroupChatDetail> findGroupChatDetailByUserId(Long userId);
    List<GroupChatDetail> findGroupChatDetailInGroupChatByGroupChatId(Long groupChatId);
}
