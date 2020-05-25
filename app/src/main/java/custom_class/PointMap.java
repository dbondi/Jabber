package custom_class;

// Define Infinite (Using INT_MAX
// caused overflow problems)

import android.os.Parcel;
import android.os.Parcelable;


public class PointMap implements Parcelable
{
    double x;
    double y;

    public PointMap(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    protected PointMap(Parcel in) {
        x = in.readDouble();
        y = in.readDouble();
    }


    public static final Creator<PointMap> CREATOR = new Creator<PointMap>() {
        @Override
        public PointMap createFromParcel(Parcel in) {
            return new PointMap(in);
        }

        @Override
        public PointMap[] newArray(int size) {
            return new PointMap[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(x);
        dest.writeDouble(y);
    }
}