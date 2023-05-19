package com.example.cloudcomputingproject.message;

public class MessageList {
    private String name, mobile, lastMessage;
    private int unseenMessages;

    public MessageList(String name, String mobile, String lastMessage, int unseenMessages) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.unseenMessages = unseenMessages;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }
}
