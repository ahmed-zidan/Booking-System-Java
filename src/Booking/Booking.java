package Booking;
import java.util.ArrayList;

/**
 *handle every booking between playground and player
 * @author zidan
 */

public class Booking {


    private String date;
    private double hour;
    private double price_per_hour;
    private int playground_id;

    /**
     * default constructor
     */
    Booking(){}

    /**
     * this constructor for playground
     * @param date for booking date
     * @param hour for booking hour
     * @param price_per_hour for price per hour in a playground
     */
    Booking(String date,double hour,double price_per_hour){

        this.date = date;
        this.hour = hour;
        this.price_per_hour = price_per_hour;
    }


    /**
     * constructor for player
     * @param playground_id for playground id
     * @param date for booking date
     * @param hour for booking hour
     * @param price_per_hour for price per hour in a playground
     */
    Booking(int playground_id,String date,double hour,double price_per_hour){
        this(date,hour,price_per_hour);
        this.playground_id = playground_id;

    }


    /**
     * set date
     * @param date for booking date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * get date
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * set hour
     * @param hour for booking hour
     */
    public void setHour(double hour) {
        this.hour = hour;
    }

    /**
     * get hour
     * @return
     */
    public double getHour() {
        return hour;
    }

    /**
     * set price per hour
     * @param price_per_hour
     */
    public void setPrice_per_hour(double price_per_hour) {
        this.price_per_hour = price_per_hour;
    }

    /**
     * get price per hour
     * @return
     */
    public double getPrice_per_hour() {
        return price_per_hour;
    }

    /**
     * get id 
     * @return
     */
    public int getPlayground_id() {
        return playground_id;
    }
}
