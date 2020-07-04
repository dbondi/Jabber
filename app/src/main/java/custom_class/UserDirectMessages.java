package custom_class;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class UserDirectMessages implements Parcelable{
    private String sharedPath;
    private String userUID;
    private String name;
    private String typeMessage;
    private Timestamp lastMessage;

    public UserDirectMessages(String sharedPath, String userUID, String name, String typeMessage, Timestamp lastMessage) {
        this.sharedPath = sharedPath;
        this.userUID = userUID;
        this.name = name;
        this.typeMessage = typeMessage;
        this.lastMessage = lastMessage;
    }

    protected UserDirectMessages(Parcel in) {
        sharedPath = in.readString();
        userUID = in.readString();
        name = in.readString();
        typeMessage = in.readString();
        lastMessage = in.readParcelable(Timestamp.class.getClassLoader());
    }

    public static final Creator<UserDirectMessages> CREATOR = new Creator<UserDirectMessages>() {
        @Override
        public UserDirectMessages createFromParcel(Parcel in) {
            return new UserDirectMessages(in);
        }

        @Override
        public UserDirectMessages[] newArray(int size) {
            return new UserDirectMessages[size];
        }
    };

    public String getSharedPath() {
        return sharedPath;
    }

    public void setSharedPath(String sharedPath) {
        this.sharedPath = sharedPath;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    public Timestamp getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Timestamp lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sharedPath);
        dest.writeString(userUID);
        dest.writeString(name);
        dest.writeString(typeMessage);
        dest.writeParcelable(lastMessage, flags);
    }
}
