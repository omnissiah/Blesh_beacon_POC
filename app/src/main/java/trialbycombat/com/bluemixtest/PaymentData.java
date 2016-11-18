package trialbycombat.com.bluemixtest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IS96266 on 18.11.2016 - 16:42.
 */
public class PaymentData {

    public PaymentData(String beaconID,String name,String surname, String customerNumber, String amount)
    {
        this.setBeaconid(beaconID);
        this.setAmount(amount);
        this.setCustomerNumber(customerNumber);
        this.setName(name);
        this.setSurname(surname);
    }

    @SerializedName("beaconid")
    @Expose
    private String beaconid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("customernumber")
    @Expose
    private String customerNumber;
    @SerializedName("amount")
    @Expose
    private String amount;

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
     * The customerNumber
     */
    public String getCustomerNumber() {
        return customerNumber;
    }

    /**
     *
     * @param customerNumber
     * The customerNumber
     */
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber= customerNumber;
    }

    /**
     *
     * @return
     * The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

}