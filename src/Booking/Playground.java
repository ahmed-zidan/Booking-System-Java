

package Booking;

import java.util.ArrayList;

/**
  *assign playground information
 *
 * @author zidan
 */


public class Playground {

    private int id;
    private String name;
    private Location location;
    private String size;
private double price_per_hour;
    private int start;
    private int end;
    //public ArrayList<Booking> available_hours;
    public ArrayList<Booking> booking_hours;

    private double cancelation_period;

    String active_or_Suspend;


    /**
     * initial playground with info
     * @param id for playground id
     * @param name playground name
     * @param location playground location
     * @param size playground size
     * @param price_per_hour how much per hour ?
     * @param cancelation_period how long ?
     * @param start when it open ?
     * @param end when it end ?
     * @param booking_hours how many booking that playground had ?
     */
    public Playground(int id,String name,Location location,String size,double price_per_hour,double cancelation_period,int start,int end,ArrayList<Booking>booking_hours){

       this( id, name,location,size,price_per_hour,cancelation_period,start,end);
    //this.available_hours =available_hours;
    this.booking_hours = booking_hours;

    }


    /**
     * initial playground with info
     * @param id for playground id
     * @param name playground name
     * @param location playground location
     * @param size playground size
     * @param price_per_hour how much per hour ?
     * @param cancelation_period how long ?
     * @param start when it open ?
     * @param end when it end ?
     */
    public Playground(int id,String name,Location location,String size,double price_per_hour,double cancelation_period,int start,int end){

        this.id = id;
        this.name = name;
        this.location = location;
        this.size = size;
        this.cancelation_period = cancelation_period;

        this.price_per_hour = price_per_hour;
        this.start = start;
        this.end = end;
        active_or_Suspend = "suspend";
        booking_hours = new ArrayList<Booking>();

    }

    //setters and getters
    public String getName() {
        return name;
    }

    public void setActive_or_Suspend(String active_or_Suspend) {
        this.active_or_Suspend = active_or_Suspend;
    }


    public String getActive_or_Suspend() {
        return active_or_Suspend;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public boolean setName(String name) {

        if(name.isBlank()||name.isEmpty()){
            return false;
        }
        else {
       this.name = name;
       return true;
        }

    }

    public int getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getCancelation_period() {
        return cancelation_period;
    }


    public void setCancelation_period(double cancelation_period) {
        this.cancelation_period = cancelation_period;
    }

    public void setPrice_per_hour(double price_per_hour) {
        this.price_per_hour = price_per_hour;
    }

    public double getPrice_per_hour() {
        return price_per_hour;
    }
}
