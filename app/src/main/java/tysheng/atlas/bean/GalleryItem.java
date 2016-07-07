package tysheng.atlas.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shengtianyang on 16/4/30.
 */
public class GalleryItem implements Parcelable {
    public String url;
    public String description;

    public GalleryItem(String url, String description) {
        this.url = url;
        this.description = description;
    }

    protected GalleryItem(Parcel in) {
        url = in.readString();
        description = in.readString();
    }

    public static final Creator<GalleryItem> CREATOR = new Creator<GalleryItem>() {
        @Override
        public GalleryItem createFromParcel(Parcel in) {
            return new GalleryItem(in);
        }

        @Override
        public GalleryItem[] newArray(int size) {
            return new GalleryItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(description);
    }
}
