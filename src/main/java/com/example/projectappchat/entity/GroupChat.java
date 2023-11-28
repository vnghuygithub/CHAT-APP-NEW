package com.example.projectappchat.entity;


import javax.persistence.*;

@Entity
public class GroupChat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupChatId;
    @Lob
    @Column(name = "Group_Chat_Logo", length = Integer.MAX_VALUE)
    private byte[] groupChatLogo;
    private String groupChatName;
    private Long groupChatQuantityMember;

    public GroupChat() {
    }

    public GroupChat(Long groupChatId, byte[] groupChatLogo, String groupChatName, Long groupChatQuantityMember) {
        this.groupChatId = groupChatId;
        this.groupChatLogo = groupChatLogo;
        this.groupChatName = groupChatName;
        this.groupChatQuantityMember = groupChatQuantityMember;
    }



    public Long getGroupChatId() {
        return groupChatId;
    }

    public void setGroupChatId(Long groupChatId) {
        this.groupChatId = groupChatId;
    }

    public String getGroupChatName() {
        return groupChatName;
    }

    public void setGroupChatName(String groupChatName) {
        this.groupChatName = groupChatName;
    }

    public Long getGroupChatQuantityMember() {
        return groupChatQuantityMember;
    }

    public void setGroupChatQuantityMember(Long groupChatQuantityMember) {
        this.groupChatQuantityMember = groupChatQuantityMember;
    }

    public byte[] getGroupChatLogo() {
        return groupChatLogo;
    }

    public void setGroupChatLogo(byte[] groupChatLogo) {
        this.groupChatLogo = groupChatLogo;
    }

    @Override
    public String toString() {
        return "GroupChat{" +
                "groupChatId=" + groupChatId +
                ", groupChatName='" + groupChatName + '\'' +
                ", groupChatQuantityMember='" + groupChatQuantityMember + '\'' +
                '}';
    }
}
