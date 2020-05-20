package custom_class;

import android.graphics.Bitmap;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class Chat {
    private String content;
    private String imageID;
    private GeoPoint location;
    private Timestamp timestamp;
    private String userUID;
    private Bitmap profilePicBitmap;
    private String profileName;

    public Chat(String content, String imageID, GeoPoint location, Timestamp timestamp, String userUID, Bitmap profilePicBitmap, String profileName, Integer commentNumber, Integer likeNumber, Bitmap imageBitmap, ArrayList<Comment> comments) {
        this.content = content;
        this.imageID = imageID;
        this.location = location;
        this.timestamp = timestamp;
        this.userUID = userUID;
        this.profilePicBitmap = profilePicBitmap;
        this.profileName = profileName;
        this.commentNumber = commentNumber;
        this.likeNumber = likeNumber;
        this.imageBitmap = imageBitmap;
        this.comments = comments;
    }

    public Chat(String content, String imageID, GeoPoint location, Timestamp timestamp, String userUID, Integer commentNumber, Integer likeNumber) {
        this.content = content;
        this.imageID = imageID;
        this.location = location;
        this.timestamp = timestamp;
        this.userUID = userUID;
        this.comments = comments;
        this.commentNumber = commentNumber;
        this.likeNumber = likeNumber;
    }

    public Chat(String content, String imageID, GeoPoint location, Timestamp timestamp, String userUID, ArrayList<Comment> comments, Integer commentNumber, Integer likeNumber) {
        this.content = content;
        this.imageID = imageID;
        this.location = location;
        this.timestamp = timestamp;
        this.userUID = userUID;
        this.comments = comments;
        this.commentNumber = commentNumber;
        this.likeNumber = likeNumber;
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

    private Integer commentNumber;
    private Integer likeNumber;
    private Bitmap imageBitmap;
    private ArrayList<Comment> comments;

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

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
