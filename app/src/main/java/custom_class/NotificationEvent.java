package custom_class;

import com.google.firebase.Timestamp;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class NotificationEvent {

    private String content;

    private String chatID;
    private String commentID;
    private String responseID;

    private Boolean privateMessage;

    private Boolean chatLike;
    private Boolean commentLike;
    private Boolean responseLike;

    private Boolean commentMessage;
    private Boolean responseMessage;

    private Boolean friendRequest;
    private Boolean eventInvite;
    private Boolean eventReminder;
    private Boolean otherNotification;

    private String userIds;
    private StorageReference userPics;
    private String userNames;
    private Timestamp userTimes;

    private Place place;

    public NotificationEvent(String content, String chatID, String commentID, String responseID, Boolean privateMessage, Boolean chatLike, Boolean commentLike, Boolean responseLike, Boolean commentMessage, Boolean responseMessage, Boolean friendRequest, Boolean eventInvite, Boolean eventReminder, Boolean otherNotification, String userIds, StorageReference userPics, String userNames, Timestamp userTimes, Place place) {
        this.content = content;
        this.chatID = chatID;
        this.commentID = commentID;
        this.responseID = responseID;
        this.privateMessage = privateMessage;
        this.chatLike = chatLike;
        this.commentLike = commentLike;
        this.responseLike = responseLike;
        this.commentMessage = commentMessage;
        this.responseMessage = responseMessage;
        this.friendRequest = friendRequest;
        this.eventInvite = eventInvite;
        this.eventReminder = eventReminder;
        this.otherNotification = otherNotification;
        this.userIds = userIds;
        this.userPics = userPics;
        this.userNames = userNames;
        this.userTimes = userTimes;
        this.place = place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getResponseID() {
        return responseID;
    }

    public void setResponseID(String responseID) {
        this.responseID = responseID;
    }

    public Boolean getPrivateMessage() {
        return privateMessage;
    }

    public void setPrivateMessage(Boolean privateMessage) {
        this.privateMessage = privateMessage;
    }

    public Boolean getChatLike() {
        return chatLike;
    }

    public void setChatLike(Boolean chatLike) {
        this.chatLike = chatLike;
    }

    public Boolean getCommentLike() {
        return commentLike;
    }

    public void setCommentLike(Boolean commentLike) {
        this.commentLike = commentLike;
    }

    public Boolean getResponseLike() {
        return responseLike;
    }

    public void setResponseLike(Boolean responseLike) {
        this.responseLike = responseLike;
    }

    public Boolean getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(Boolean commentMessage) {
        this.commentMessage = commentMessage;
    }

    public Boolean getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(Boolean responseMessage) {
        this.responseMessage = responseMessage;
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

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public StorageReference getUserPics() {
        return userPics;
    }

    public void setUserPics(StorageReference userPics) {
        this.userPics = userPics;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public Timestamp getUserTimes() {
        return userTimes;
    }

    public void setUserTimes(Timestamp userTimes) {
        this.userTimes = userTimes;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
