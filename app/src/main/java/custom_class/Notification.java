package custom_class;

import com.google.firebase.Timestamp;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Notification {

    //chat content
    private int likeNumber;
    private String content;

    private String privateMessageContent;
    private Boolean privateMessage;

    private Boolean commentLike;
    private Boolean commentMessage;

    private Boolean chatLike;
    private Boolean chatMessage;

    private Boolean friendRequest;
    private Boolean eventInvite;
    private Boolean eventReminder;
    private Boolean otherNotification;

    private ArrayList<StorageReference> notificationPics;
    private ArrayList<String> notificationNames;
    private ArrayList<Timestamp> notificationTimes;

    private Timestamp lastNotification;

    public Notification(int likeNumber, String content, String privateMessageContent, Boolean privateMessage, Boolean commentLike, Boolean commentMessage, Boolean chatLike, Boolean chatMessage, Boolean friendRequest, Boolean eventInvite, Boolean eventReminder, Boolean otherNotification, ArrayList<StorageReference> notificationPics, ArrayList<String> notificationNames, ArrayList<Timestamp> notificationTimes, Timestamp lastNotification) {
        this.likeNumber = likeNumber;
        this.content = content;
        this.privateMessageContent = privateMessageContent;
        this.privateMessage = privateMessage;
        this.commentLike = commentLike;
        this.commentMessage = commentMessage;
        this.chatLike = chatLike;
        this.chatMessage = chatMessage;
        this.friendRequest = friendRequest;
        this.eventInvite = eventInvite;
        this.eventReminder = eventReminder;
        this.otherNotification = otherNotification;
        this.notificationPics = notificationPics;
        this.notificationNames = notificationNames;
        this.notificationTimes = notificationTimes;
        this.lastNotification = lastNotification;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrivateMessageContent() {
        return privateMessageContent;
    }

    public void setPrivateMessageContent(String privateMessageContent) {
        this.privateMessageContent = privateMessageContent;
    }

    public Boolean getPrivateMessage() {
        return privateMessage;
    }

    public void setPrivateMessage(Boolean privateMessage) {
        this.privateMessage = privateMessage;
    }

    public Boolean getCommentLike() {
        return commentLike;
    }

    public void setCommentLike(Boolean commentLike) {
        this.commentLike = commentLike;
    }

    public Boolean getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(Boolean commentMessage) {
        this.commentMessage = commentMessage;
    }

    public Boolean getChatLike() {
        return chatLike;
    }

    public void setChatLike(Boolean chatLike) {
        this.chatLike = chatLike;
    }

    public Boolean getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(Boolean chatMessage) {
        this.chatMessage = chatMessage;
    }

    public Boolean getFriendRequest() {
        return friendRequest;
    }

    public void setFriendRequest(Boolean friendRequest) {
        this.friendRequest = friendRequest;
    }

    public Boolean getEventInvite() {
        return eventInvite;
    }

    public void setEventInvite(Boolean eventInvite) {
        this.eventInvite = eventInvite;
    }

    public Boolean getEventReminder() {
        return eventReminder;
    }

    public void setEventReminder(Boolean eventReminder) {
        this.eventReminder = eventReminder;
    }

    public Boolean getOtherNotification() {
        return otherNotification;
    }

    public void setOtherNotification(Boolean otherNotification) {
        this.otherNotification = otherNotification;
    }

    public ArrayList<StorageReference> getNotificationPics() {
        return notificationPics;
    }

    public void setNotificationPics(ArrayList<StorageReference> notificationPics) {
        this.notificationPics = notificationPics;
    }

    public ArrayList<String> getNotificationNames() {
        return notificationNames;
    }

    public void setNotificationNames(ArrayList<String> notificationNames) {
        this.notificationNames = notificationNames;
    }

    public ArrayList<Timestamp> getNotificationTimes() {
        return notificationTimes;
    }

    public void setNotificationTimes(ArrayList<Timestamp> notificationTimes) {
        this.notificationTimes = notificationTimes;
    }

    public Timestamp getLastNotification() {
        return lastNotification;
    }

    public void setLastNotification(Timestamp lastNotification) {
        this.lastNotification = lastNotification;
    }

}
