package custom_class;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

public class Comment {
    private String content;
    private Timestamp timestamp;
    private String userUID;

    public Comment(String content, Timestamp timestamp, String userUID) {
        this.content = content;
        this.timestamp = timestamp;
        this.userUID = userUID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
