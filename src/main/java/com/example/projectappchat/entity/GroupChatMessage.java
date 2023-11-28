package com.example.projectappchat.entity;


import javax.persistence.*;

@Entity
public class GroupChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupChatMessageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Send_Id", nullable = false)
    private User userSendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Group_Chat_Receiver_Id", nullable = false)
    private GroupChat groupChatReceiverId;

    private String groupChatMessageBody;

    public GroupChatMessage() {
    }

    public GroupChatMessage(Long groupChatMessageId, User userSendId, GroupChat groupChatReceiverId, String groupChatMessageBody) {
        this.groupChatMessageId = groupChatMessageId;
        this.userSendId = userSendId;
        this.groupChatReceiverId = groupChatReceiverId;
        this.groupChatMessageBody = groupChatMessageBody;
    }

    public Long getGroupChatMessageId() {
        return groupChatMessageId;
    }

    public void setGroupChatMessageId(Long groupChatMessageId) {
        this.groupChatMessageId = groupChatMessageId;
    }

    public User getUserSendId() {
        return userSendId;
    }

    public void setUserSendId(User userSendId) {
        this.userSendId = userSendId;
    }

    public GroupChat getGroupChatReceiverId() {
        return groupChatReceiverId;
    }

    public void setGroupChatReceiverId(GroupChat groupChatReceiverId) {
        this.groupChatReceiverId = groupChatReceiverId;
    }

    public String getGroupChatMessageBody() {
        return groupChatMessageBody;
    }

    public void setGroupChatMessageBody(String groupChatMessageBody) {
        this.groupChatMessageBody = groupChatMessageBody;
    }

    @Override
    public String toString() {
        return "GroupChatMessage{" +
                "groupChatMessageId=" + groupChatMessageId +
                ", userSendId=" + userSendId +
                ", groupChatReceiverId=" + groupChatReceiverId +
                ", groupChatMessageBody='" + groupChatMessageBody + '\'' +
                '}';
    }
}
