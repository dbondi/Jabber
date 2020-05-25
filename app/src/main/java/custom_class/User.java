package custom_class;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String UID;
    private Bitmap profileImageBitmap;
    private String profileName;
    private String phoneNumber;
    //fix name

    public User(String UID, Bitmap profileImageBitmap, String profileName, String phoneNumber) {
        this.UID = UID;
        this.profileImageBitmap = profileImageBitmap;
        this.profileName = profileName;
        this.phoneNumber = phoneNumber;
    }

    public String getPhone_number() {
        return phoneNumber;
    }

    public void setPhone_number(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }


    public User(String UID, Bitmap profileImageBitmap, String profileName) {
        this.UID = UID;
        this.profileImageBitmap = profileImageBitmap;
        this.profileName = profileName;
    }

    protected User(Parcel in) {
        UID = in.readString();
        profileImageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        profileName = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User() {

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public Bitmap getProfileImageBitmap() {
        return profileImageBitmap;
    }

    public void setProfileImageBitmap(Bitmap profileImageBitmap) {
        this.profileImageBitmap = profileImageBitmap;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UID);
        dest.writeParcelable(profileImageBitmap, flags);
        dest.writeString(profileName);
        dest.writeString(phoneNumber);
    }
}
