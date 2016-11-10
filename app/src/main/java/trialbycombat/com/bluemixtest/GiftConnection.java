package trialbycombat.com.bluemixtest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IS96266 on 31.10.2016 - 15:00.
 */
public class GiftConnection {

    private double BeaconDistance;
    public GiftConnection(String beaconID, Double beaconDistance)
    {
        this.setBeaconid(beaconID);
        this.setBeaconDistance(beaconDistance);
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
}
