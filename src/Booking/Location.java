package Booking;

/**
 * this class store address and gps for playground
 *
 * @author zidan
 */

public class Location {

    private String address;
    private String GPS;


    /**
     * default constructor
     */
    public Location(){}

    /**
     * inital location attributes
     * @param address
     * @param GPS
     */
    public Location(String address,String GPS){
        this.address = address;
        this.GPS = GPS;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGPS(String GPS) {
        this.GPS = GPS;
    }

    public String getGPS() {
        return GPS;
    }
}
