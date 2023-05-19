package com.example.cloudcomputingproject.chat;

public class ChatMessage {
    private String senderId;
    private String recipientId;
    private String messageText;
    private long timestamp;

    public ChatMessage(){
    }

    public ChatMessage(String senderId, String recipientId, String messageText, long timestamp) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}