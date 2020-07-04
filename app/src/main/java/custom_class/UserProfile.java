package custom_class;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UserProfile implements Parcelable {
    private String UID;
    private String name;
    private ArrayList<String> storageReferences;
    private ArrayList<Boolean> boolPictures;
    private String about;
    private Integer age;
    private String gender;
    private ArrayList<Event> pastEvents;
    private ArrayList<Event> currentEvents;
    private String phoneNumber;
    private String profileName;

    public UserProfile(String UID, ArrayList<String> storageReferences, ArrayList<Boolean> boolPictures, String about, Integer age, String gender, ArrayList<Event> pastEvents, ArrayList<Event> currentEvents, String phoneNumber, String profileName) {
        this.UID = UID;
        this.storageReferences = storageReferences;
        this.boolPictures = boolPictures;
        this.about = about;
        this.age = age;
        this.gender = gender;
        this.pastEvents = pastEvents;
        this.currentEvents = currentEvents;
        this.phoneNumber = phoneNumber;
        this.profileName = profileName;
    }

    public UserProfile(String UID, String profileName) {
        this.UID = UID;
        this.profileName = profileName;
        age = 0;
    }

    public UserProfile() {
        age = 0;
    }

    public static Creator<UserProfile> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected UserProfile(Parcel in) {
        UID = in.readString();
        storageReferences = in.readArrayList(String.class.getClassLoader());
        boolPictures = in.readArrayList(Boolean.class.getClassLoader());
        about = in.readString();
        age = in.readInt();
        gender = in.readString();
        pastEvents = in.readArrayList(ClassLoader.getSystemClassLoader());
        currentEvents = in.readArrayList(ClassLoader.getSystemClassLoader());
        phoneNumber = in.readString();
        profileName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UID);
        dest.writeList(storageReferences);
        dest.writeList(boolPictures);
        dest.writeString(about);
        dest.writeInt(age);
        dest.writeString(gender);
        dest.writeList(pastEvents);
        dest.writeList(currentEvents);
        dest.writeString(phoneNumber);
        dest.writeString(profileName);
    }
    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public ArrayList<String> getStorageReferences() {
        return storageReferences;
    }

    public void setStorageReferences(ArrayList<String> storageReferences) {
        this.storageReferences = storageReferences;
    }

    public ArrayList<Boolean> getBoolPictures() {
        return boolPictures;
    }

    public void setBoolPictures(ArrayList<Boolean> boolPictures) {
        this.boolPictures = boolPictures;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ArrayList<Event> getPastEvents() {
        return pastEvents;
    }

    public void setPastEvents(ArrayList<Event> pastEvents) {
        this.pastEvents = pastEvents;
    }

    public ArrayList<Event> getCurrentEvents() {
        return currentEvents;
    }

    public void setCurrentEvents(ArrayList<Event> currentEvents) {
        this.currentEvents = currentEvents;
    }

    public String getPhone_number() {
        return phoneNumber;
    }

    public void setPhone_number(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
