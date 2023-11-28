package com.example.projectappchat.entity;


import javax.persistence.*;

@Entity
public class GroupChatDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupChatDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Group_Chat_Id", nullable = false)
    private GroupChat groupChatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private User userId;

    public GroupChatDetail() {
    }

    public GroupChatDetail(Long groupChatDetailId, GroupChat groupChatId, User userId) {
        this.groupChatDetailId = groupChatDetailId;
        this.groupChatId = groupChatId;
        this.userId = userId;
    }

    public Long getGroupChatDetailId() {
        return groupChatDetailId;
    }

    public void setGroupChatDetailId(Long groupChatDetailId) {
        this.groupChatDetailId = groupChatDetailId;
    }

    public GroupChat getGroupChatId() {
        return groupChatId;
    }

    public void setGroupChatId(GroupChat groupChatId) {
        this.groupChatId = groupChatId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GroupChatDetail{" +
                "groupChatDetailId=" + groupChatDetailId +
                ", groupChatId=" + groupChatId +
                ", userId=" + userId +
                '}';
    }
}
