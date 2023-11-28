package com.example.projectappchat.repository;

import com.example.projectappchat.entity.GroupChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupChatMessageRepository extends JpaRepository<GroupChatMessage, Long> {
    @Query(nativeQuery = true, value = "select * from group_chat_message gcm where gcm.group_chat_receiver_id = :groupChatId")
    List<GroupChatMessage> findGroupChatMessageByGroupChatId(@Param("groupChatId") Long groupChatId);
}
