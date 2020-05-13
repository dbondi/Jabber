package custom_class;

import android.os.Parcel;
import android.os.Parcelable;

public class NoPhoneUser implements Parcelable {
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    public NoPhoneUser(String first, String last, String email, String password) {
        first_name = first;
        last_name = last;
        this.email = email;
        this.password = password;
    }

    protected NoPhoneUser(Parcel in) {
        first_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<NoPhoneUser> CREATOR = new Creator<NoPhoneUser>() {
        @Override
        public NoPhoneUser createFromParcel(Parcel in) {
            return new NoPhoneUser(in);
        }

        @Override
        public NoPhoneUser[] newArray(int size) {
            return new NoPhoneUser[size];
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

    public String getPassword() {
        return password;
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
        dest.writeString(password);
    }
}
