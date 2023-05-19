package com.example.cloudcomputingproject.message;

public class MessageList {
    private String name, mobile, lastMessage, profilePic;
    private int unseenMessages;

    public MessageList(String name, String mobile, String lastMessage, int unseenMessages, String profilePic) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.unseenMessages = unseenMessages;
        this.profilePic = profilePic;
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

    public String getProfilePic() {
        return profilePic;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }
}
