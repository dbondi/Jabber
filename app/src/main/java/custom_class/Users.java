package custom_class;

import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable {
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String uid;

    public Users(NoPhoneUser newUser, String phone_number,String uid) {
        first_name = newUser.getFirstName();
        last_name = newUser.getLastName();
        this.email = newUser.getEmail();
        this.uid = uid;
        this.phone_number = phone_number;
    }

    protected Users(Parcel in) {
        first_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        phone_number = in.readString();
        uid = in.readString();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getUid() {
        return uid;
    }

    public String getPassword() {
        return phone_number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(email);
        dest.writeString(phone_number);
        dest.writeString(uid);
    }
}
