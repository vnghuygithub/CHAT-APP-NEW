package com.example.projectappchat.repository;

import com.example.projectappchat.entity.GroupChatDetail;
import com.example.projectappchat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupChatDetailRepository extends JpaRepository<GroupChatDetail,Long> {
    /*tim danh sach nhung group chat cua nguoi dung*/
    @Query(nativeQuery = true, value = "select * from group_chat_detail gcd where gcd.user_id = :userId")
    List<GroupChatDetail> findGroupChatDetailByUserId(@Param("userId") Long userId);


    /*tim danh sach user co trong group chat*/
    @Query(nativeQuery = true, value = "select * from group_chat_detail gcd where gcd.group_chat_id = :groupChatId")
    List<GroupChatDetail> findGroupChatDetailInGroupChatByGroupChatId(@Param("groupChatId") Long groupChatId);
}
