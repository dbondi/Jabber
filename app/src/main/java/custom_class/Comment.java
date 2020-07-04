package custom_class;

import android.graphics.Bitmap;

import com.giphy.sdk.core.models.Media;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class Comment {

    //All comment data
    private ArrayList<String> likeList;
    private String userUID;
    private String userName;
    private GeoPoint location;
    private Timestamp timestamp;
    private Integer likeNumber;

    //Spot in commentSection
    private Integer columnNumber;

    //Gif type
    //private Media media;
    private String gifUrl;

    //boolean types
    private Boolean gifBoolean;
    private Boolean imageBool;
    private Boolean stringBoolean;
    //private Boolean videoBool;

    //image type data
    private Integer imageHeight;
    private Integer imageWidth;
    private String imageID;

    //text type data
    private String content;

    //database storage
    private StorageReference gsReference;
    private StorageReference profPicReference;

    private ArrayList<String> color;
    private Integer height;
    private boolean last;
    private String commentID;

    public Comment(String content, String imageID, String gifUrl, GeoPoint location, Timestamp timestamp, String userUID, String userName, Integer likeNumber, ArrayList<String> likeList, StorageReference gsReference, StorageReference profPicReference, Integer imageWidth, Integer imageHeight, Boolean imageBool, Boolean gifBoolean, Boolean stringBoolean, Integer columnNumber, ArrayList<String> color, String commentID) {
        this.content = content;
        this.imageID = imageID;
        this.gifUrl = gifUrl;
        this.location = location;
        this.timestamp = timestamp;
        this.userUID = userUID;
        this.userName = userName;
        this.likeList = likeList;
        this.likeNumber = likeNumber;
        this.gsReference = gsReference;
        this.profPicReference = profPicReference;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imageBool = imageBool;
        this.gifBoolean = gifBoolean;
        this.stringBoolean = stringBoolean;
        this.columnNumber = columnNumber;
        this.color = color;
        this.commentID = commentID;
        height = 0;
        last = false;
    }
    public Comment(int height){
        this.height = height;
        last = true;
    }

    public Comment() {

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<String> getLikeList() {
        return likeList;
    }

    public void setLikeList(ArrayList<String> likeList) {
        this.likeList = likeList;
    }

    public Integer getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }

    public StorageReference getGsReference() {
        return gsReference;
    }

    public void setGsReference(StorageReference gsReference) {
        this.gsReference = gsReference;
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

    public Boolean getBoolImage() {
        return imageBool;
    }

    public void setBoolImage(Boolean imageBool) {
        this.imageBool = imageBool;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public Boolean getGifBoolean() {
        return gifBoolean;
    }

    public void setGifBoolean(Boolean gifBoolean) {
        this.gifBoolean = gifBoolean;
    }

    public Boolean getImageBool() {
        return imageBool;
    }

    public void setImageBool(Boolean imageBool) {
        this.imageBool = imageBool;
    }

    public Boolean getStringBoolean() {
        return stringBoolean;
    }

    public void setStringBoolean(Boolean stringBoolean) {
        this.stringBoolean = stringBoolean;
    }

    public ArrayList<String> getColor() {
        return color;
    }

    public void setColor(ArrayList<String> color) {
        this.color = color;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }
}
