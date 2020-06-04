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
    private Media media;

    //boolean types
    private Boolean gifBool;
    private Boolean imageBool;
    private Boolean textBool;
    private Boolean videoBool;

    //image type data
    private Integer imageHeight;
    private Integer imageWidth;
    private String imageID;

    //text type data
    private String content;

    //database storage
    private StorageReference gsReference;
    private StorageReference profPicReference;

    public Comment(String content, String imageID, GeoPoint location, Timestamp timestamp, String userUID, String userName, Integer likeNumber, ArrayList<String> likeList, StorageReference gsReference, StorageReference profPicReference, Integer imageWidth, Integer imageHeight, Boolean imageBool, Integer columnNumber) {
        this.content = content;
        this.imageID = imageID;
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
        this.columnNumber = columnNumber;
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

}
