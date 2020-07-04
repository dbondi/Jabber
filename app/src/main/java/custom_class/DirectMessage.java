package custom_class;

import com.google.firebase.Timestamp;
import com.google.firebase.storage.StorageReference;

public class DirectMessage {
    String content;
    String photo;
    String gifURL;
    Boolean boolContent;
    Boolean boolPhoto;
    Boolean boolGifURL;
    String sender;
    Timestamp timestamp;

    public DirectMessage(String content, String photo, String gifURL, Boolean boolContent, Boolean boolPhoto, Boolean boolGifURL, String sender, Timestamp timestamp) {
        this.content = content;
        this.photo = photo;
        this.gifURL = gifURL;
        this.boolContent = boolContent;
        this.boolPhoto = boolPhoto;
        this.boolGifURL = boolGifURL;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGifURL() {
        return gifURL;
    }

    public void setGifURL(String gifURL) {
        this.gifURL = gifURL;
    }

    public Boolean getBoolContent() {
        return boolContent;
    }

    public void setBoolContent(Boolean boolContent) {
        this.boolContent = boolContent;
    }

    public Boolean getBoolPhoto() {
        return boolPhoto;
    }

    public void setBoolPhoto(Boolean boolPhoto) {
        this.boolPhoto = boolPhoto;
    }

    public Boolean getBoolGifURL() {
        return boolGifURL;
    }

    public void setBoolGifURL(Boolean boolGifURL) {
        this.boolGifURL = boolGifURL;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
