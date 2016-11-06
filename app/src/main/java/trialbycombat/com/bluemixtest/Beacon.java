package trialbycombat.com.bluemixtest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IS96266 on 31.10.2016 - 15:00.
 */
public class Beacon {

    /**
     *
     * (Required)
     *
     */
    @SerializedName("_id")
    @Expose
    private String id;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("_rev")
    @Expose
    private String rev;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("name")
    @Expose
    private String name;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("surname")
    @Expose
    private String surname;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("tckn")
    @Expose
    private String tckn;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("beaconid")
    @Expose
    private String beaconid;

    /**
     *
     * (Required)
     *
     */
    @SerializedName("photo")
    @Expose
    private byte[] photo;

    /**
     *
     * (Required)
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * (Required)
     *
     * @param id
     * The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * (Required)
     *
     * @return
     * The rev
     */
    public String getRev() {
        return rev;
    }

    /**
     *
     * (Required)
     *
     * @param rev
     * The _rev
     */
    public void setRev(String rev) {
        this.rev = rev;
    }

    /**
     *
     * (Required)
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * (Required)
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * (Required)
     *
     * @return
     * The surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * (Required)
     *
     * @param surname
     * The surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * (Required)
     *
     * @return
     * The tckn
     */
    public String getTckn() {
        return tckn;
    }

    /**
     *
     * (Required)
     *
     * @param tckn
     * The tckn
     */
    public void setTckn(String tckn) {
        this.tckn = tckn;
    }

    /**
     *
     * (Required)
     *
     * @return
     * The beaconid
     */
    public String getBeaconid() {
        return beaconid;
    }

    /**
     *
     * (Required)
     *
     * @param beaconid
     * The beaconid
     */
    public void setBeaconid(String beaconid) {
        this.beaconid = beaconid;
    }

    /**
     *
     * (Required)
     *
     * @return
     * The photo
     */
    public byte[] getPhoto() {
        return photo;
    }

    /**
     *
     * (Required)
     *
     * @param photo
     * The photo
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
