package custom_class;

import android.graphics.Bitmap;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Chat {
    private String content;
    private String imageID;
    private GeoPoint location;
    private Timestamp timestamp;
    private String userUID;
    private String userName;
    private ArrayList<String> likeList;
    private ArrayList<Comment> commentList;
    private Bitmap profilePicBitmap;
    private String profileName;
    private Integer commentNumber;
    private Integer likeNumber;
    private Bitmap imageBitmap;
    private StorageReference gsReference;
    private StorageReference profPicReference;
    private Integer imageWidth;
    private Integer imageHeight;
    private String messageID;


    public Chat(String content, String imageID, GeoPoint location, Timestamp timestamp, String userUID, String profileName, Integer commentNumber, Integer likeNumber, ArrayList<String> likeList, StorageReference gsReference, StorageReference profPicReference) {

        this.content = content;
        this.imageID = imageID;
        this.location = location;
        this.timestamp = timestamp;
        this.userUID = userUID;
        this.profileName = profileName;
        this.commentNumber = commentNumber;
        this.likeNumber = likeNumber;
        this.likeList = likeList;
        this.gsReference = gsReference;
        this.profPicReference = profPicReference;
    }

    public Chat(String content, String imageID, GeoPoint location, Timestamp timestamp, String userUID, String profileName, Integer commentNumber, Integer likeNumber, ArrayList<String> likeList, StorageReference gsReference, StorageReference profPicReference,Integer imageWidth, Integer imageHeight, String messageID) {

        this.content = content;
        this.imageID = imageID;
        this.location = location;
        this.timestamp = timestamp;
        this.userUID = userUID;
        this.profileName = profileName;
        this.commentNumber = commentNumber;
        this.likeNumber = likeNumber;
        this.likeList = likeList;
        this.gsReference = gsReference;
        this.profPicReference = profPicReference;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.messageID = messageID;
    }


    public Bitmap getProfilePicBitmap() {
        return profilePicBitmap;
    }

    public void setProfilePicBitmap(Bitmap profilePicBitmap) {
        this.profilePicBitmap = profilePicBitmap;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }


    public Chat(){

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

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
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

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public ArrayList<Comment> getComments() {
        return commentList;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.commentList = comments;
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
}
