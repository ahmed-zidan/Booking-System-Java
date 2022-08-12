package Booking;


/**
 * store phone and address of every user
 *
 * @author zidan
 */
public class Profile {


    private String mobile;
    private String address;

    /**
     * default constructor
     */
    public Profile(){
        mobile = null;
        address = null;
    }

    /**
     * initial value
     * @param mobile for user mobile
     * @param address for user address
     */
    public Profile(String mobile,String address){
        this.mobile = mobile;
        this.address = address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public boolean setMobile(String mobile) {
        if(mobile.length() <9 ){
            return false;
        }
        else {
            this.mobile = mobile;
        return true;
        }
    }

    public String getMobile() {
        return mobile;
    }

}
