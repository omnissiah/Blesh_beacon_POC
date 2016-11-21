package trialbycombat.com.bluemixtest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by IS96266 on 31.10.2016 - 15:00.
 */
public class GiftConnection implements Parcelable{

    private double BeaconDistance;


    public Long getLastContactedTime() {
        return LastContactedTime;
    }

    public void setLastContactedTime(Long lastContactedTime) {
        LastContactedTime = lastContactedTime;
    }

    private Long LastContactedTime;
    public GiftConnection(String beaconID, Double beaconDistance,Long lastContactedTime)
    {
        this.setBeaconid(beaconID);
        this.setBeaconDistance(beaconDistance);
        this.setLastContactedTime(lastContactedTime);
    }
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_rev")
    @Expose
    private String rev;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("tckn")
    @Expose
    private String tckn;
    @SerializedName("beaconid")
    @Expose
    private String beaconid;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("eventtype")
    @Expose
    private String eventtype;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The rev
     */
    public String getRev() {
        return rev;
    }

    /**
     *
     * @param rev
     * The _rev
     */
    public void setRev(String rev) {
        this.rev = rev;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname
     * The surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return
     * The tckn
     */
    public String getTckn() {
        return tckn;
    }

    /**
     *
     * @param tckn
     * The tckn
     */
    public void setTckn(String tckn) {
        this.tckn = tckn;
    }

    /**
     *
     * @return
     * The beaconid
     */
    public String getBeaconid() {
        return beaconid;
    }

    /**
     *
     * @param beaconid
     * The beaconid
     */
    public void setBeaconid(String beaconid) {
        this.beaconid = beaconid;
    }

    /**
     *
     * @return
     * The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo
     * The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     *
     * @return
     * The eventtype
     */
    public String getEventtype() {
        return eventtype;
    }

    /**
     *
     * @param eventtype
     * The eventtype
     */
    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public double getBeaconDistance() {
        return BeaconDistance;
    }

    public void setBeaconDistance(double beaconDistance) {
        BeaconDistance = beaconDistance;
    }
    protected GiftConnection(Parcel in) {
        BeaconDistance = in.readDouble();
        id = in.readString();
        rev = in.readString();
        name = in.readString();
        surname = in.readString();
        tckn = in.readString();
        beaconid = in.readString();
        photo = in.readString();
        eventtype = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(BeaconDistance);
        dest.writeString(id);
        dest.writeString(rev);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(tckn);
        dest.writeString(beaconid);
        dest.writeString(photo);
        dest.writeString(eventtype);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GiftConnection> CREATOR = new Parcelable.Creator<GiftConnection>() {
        @Override
        public GiftConnection createFromParcel(Parcel in) {
            return new GiftConnection(in);
        }

        @Override
        public GiftConnection[] newArray(int size) {
            return new GiftConnection[size];
        }
    };
}
