package com.example.projectappchat.model;

import com.example.projectappchat.entity.User;

import java.sql.Date;

public class MessageModel {

    private String fromLogin;
    private Long messageSendId;
    private Long messageReceiverId;
    private String messageBody;
    private Date messageDateCreation;



    public String getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(String fromLogin) {
        this.fromLogin = fromLogin;
    }

    public Long getMessageSendId() {
        return messageSendId;
    }

    public void setMessageSendId(Long messageSendId) {
        this.messageSendId = messageSendId;
    }

    public Long getMessageReceiverId() {
        return messageReceiverId;
    }

    public void setMessageReceiverId(Long messageReceiverId) {
        this.messageReceiverId = messageReceiverId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Date getMessageDateCreation() {
        return messageDateCreation;
    }

    public void setMessageDateCreation(Date messageDateCreation) {
        this.messageDateCreation = messageDateCreation;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                ", fromLogin='" + fromLogin + '\'' +
                ", messageSendId=" + messageSendId +
                ", messageReceiverId=" + messageReceiverId +
                ", messageBody='" + messageBody + '\'' +
                ", messageDateCreation=" + messageDateCreation +
                '}';
    }
}

