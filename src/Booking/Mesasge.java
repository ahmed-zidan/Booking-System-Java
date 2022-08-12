

package Booking;

/**
 * every user can send or receive message from another user
 *
 * @author zidan
 */

public class Mesasge {

    private int sender_id;
    private int receiver_id;
    private String message;

    String date;


    public Mesasge(){}

    /**
     * to whom ? from whome ? what message ? when ?
     * @param sender_id value int
     * @param receiver_id value int
     * @param message value String
     * @param date value String
     */
    public Mesasge(int sender_id,int receiver_id,String message,String date){
        this.message = message;
        this.sender_id =sender_id;
        this.receiver_id = receiver_id;
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
