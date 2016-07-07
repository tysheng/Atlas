package tysheng.atlas.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shengtianyang on 16/5/1.
 */
public class GankFuli implements Parcelable {
    public String _id;
    public String _ns;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;

    protected GankFuli(Parcel in) {
        _id = in.readString();
        _ns = in.readString();
        createdAt = in.readString();
        desc = in.readString();
        publishedAt = in.readString();
        source = in.readString();
        type = in.readString();
        url = in.readString();
        used = in.readByte() != 0;
        who = in.readString();
    }

    public static final Creator<GankFuli> CREATOR = new Creator<GankFuli>() {
        @Override
        public GankFuli createFromParcel(Parcel in) {
            return new GankFuli(in);
        }

        @Override
        public GankFuli[] newArray(int size) {
            return new GankFuli[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(_ns);
        dest.writeString(createdAt);
        dest.writeString(desc);
        dest.writeString(publishedAt);
        dest.writeString(source);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeByte((byte) (used ? 1 : 0));
        dest.writeString(who);
    }
}
