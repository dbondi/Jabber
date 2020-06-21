package custom_class;

import android.graphics.Bitmap;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

public class Chat {
    private String content;
    private String imageID;
    private GeoPoint location;
    private Timestamp timestamp;
    private String userUID;
    private String userName;
    private ArrayList<String> likeList;
    private Map<String,Integer> pollVoteList;
    private Integer commentNumber;
    private Integer likeNumber;
    private String gifURL;
    private ArrayList<String> pollOptions;
    private ArrayList<Long> pollVotes;
    private Boolean boolText;
    private Boolean boolPoll;
    private Boolean boolImage;
    private Boolean boolGif;
    private StorageReference gsReference;
    private StorageReference profPicReference;
    private Integer imageWidth;
    private Integer imageHeight;
    private String messageID;
    private Place place;
    private ArrayList<String> color;
    private Integer valueVoted;
    private String chatGroupId;

    private Boolean poleVoted;

    public Chat(String content, String imageID, GeoPoint location, Timestamp timestamp, String userUID, String userName, ArrayList<String> likeList, Map<String,Integer> pollVoteList, Integer commentNumber, Integer likeNumber, String gifURL, ArrayList<String> pollOptions, ArrayList<Long> pollVotes, Boolean boolText, Boolean boolPoll, Boolean boolImage, Boolean boolGif, StorageReference gsReference, StorageReference profPicReference, Integer imageWidth, Integer imageHeight, String messageID, Place place, ArrayList<String> color, String chatGroupId) {
        this.content = content;
        this.imageID = imageID;
        this.location = location;
        this.timestamp = timestamp;
        this.userUID = userUID;
        this.userName = userName;
        this.likeList = likeList;
        this.pollVoteList = pollVoteList;
        this.commentNumber = commentNumber;
        this.likeNumber = likeNumber;
        this.gifURL = gifURL;
        this.pollOptions = pollOptions;
        this.pollVotes = pollVotes;
        this.boolText = boolText;
        this.boolPoll = boolPoll;
        this.boolImage = boolImage;
        this.boolGif = boolGif;
        this.gsReference = gsReference;
        this.profPicReference = profPicReference;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.messageID = messageID;
        this.place = place;
        this.color = color;
        poleVoted = false;
        valueVoted = 0;
        this.chatGroupId = chatGroupId;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    public Integer getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserUID() {
        return userUID;
    }

    public ArrayList<String> getLikeList() {
        return likeList;
    }

    public void setLikeList(ArrayList<String> likeList) {
        this.likeList = likeList;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public StorageReference getGsReference() {
        return gsReference;
    }

    public void setGsReference(StorageReference gsReference) {
        this.gsReference = gsReference;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public StorageReference getProfPicReference() {
        return profPicReference;
    }

    public void setProfPicReference(StorageReference profPicReference) {
        this.profPicReference = profPicReference;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public ArrayList<String> getColor() {
        return color;
    }

    public void setColor(ArrayList<String> color) {
        this.color = color;
    }

    public String getGifURL() {
        return gifURL;
    }

    public void setGifURL(String gifURL) {
        this.gifURL = gifURL;
    }

    public ArrayList<String> getPollOptions() {
        return pollOptions;
    }

    public void setPollOptions(ArrayList<String> pollOptions) {
        this.pollOptions = pollOptions;
    }

    public Boolean getBoolText() {
        return boolText;
    }

    public void setBoolText(Boolean boolText) {
        this.boolText = boolText;
    }

    public Boolean getBoolPoll() {
        return boolPoll;
    }

    public void setBoolPoll(Boolean boolPoll) {
        this.boolPoll = boolPoll;
    }

    public Boolean getBoolImage() {
        return boolImage;
    }

    public void setBoolImage(Boolean boolImage) {
        this.boolImage = boolImage;
    }

    public Boolean getBoolGif() {
        return boolGif;
    }

    public void setBoolGif(Boolean boolGif) {
        this.boolGif = boolGif;
    }

    public Map<String, Integer> getPollVoteList() {
        return pollVoteList;
    }

    public void setPollVoteList(Map<String, Integer> pollVoteList) {
        this.pollVoteList = pollVoteList;
    }

    public ArrayList<Long> getPollVotes() {
        return pollVotes;
    }

    public void setPollVotes(ArrayList<Long> pollVotes) {
        this.pollVotes = pollVotes;
    }

    public Boolean getPoleVoted() {
        return poleVoted;
    }

    public void setPoleVoted(Boolean poleVoted) {
        this.poleVoted = poleVoted;
    }

    public Integer getValueVoted() {
        return valueVoted;
    }

    public void setValueVoted(Integer valueVoted) {
        this.valueVoted = valueVoted;
    }

    public String getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(String chatGroupId) {
        this.chatGroupId = chatGroupId;
    }
}

