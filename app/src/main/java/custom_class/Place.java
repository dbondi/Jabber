package custom_class;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Place implements Parcelable {
    private LatLng location;
    private String name;
    private int population;
    private String IPEDSID;
    private String cityID;
    private String type;

    public Place(LatLng location, String name, int population, String IPEDSID,String type) {
        this.location = location;
        this.name = name;
        this.population = population;
        this.IPEDSID = IPEDSID;
        this.type = type;
    }


    protected Place(Parcel in) {
        location = in.readParcelable(LatLng.class.getClassLoader());
        name = in.readString();
        population = in.readInt();
        IPEDSID = in.readString();
        cityID = in.readString();
        type = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getIPEDSID() {
        return IPEDSID;
    }

    public void setIPEDSID(String IPEDSID) {
        this.IPEDSID = IPEDSID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(location, flags);
        dest.writeString(name);
        dest.writeInt(population);
        dest.writeString(IPEDSID);
        dest.writeString(cityID);
        dest.writeString(type);
    }
}
