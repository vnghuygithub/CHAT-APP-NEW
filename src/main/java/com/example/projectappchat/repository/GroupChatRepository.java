package com.example.projectappchat.repository;


import com.example.projectappchat.entity.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {
}
