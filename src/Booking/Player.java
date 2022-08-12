package Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * define all operation that player can do
 *
 * @author zidan
 */

public class Player extends User {

    ArrayList<Player>team;
    ArrayList<Booking>bookings = new ArrayList<Booking>();


    /**
     * initialize player attributes
     * @param name for player name
     * @param id for player id
     * @param passswrd for player password
     * @param email for player email
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    Player(String name,int id,String passswrd,String email) throws SQLException, ClassNotFoundException {


        super(name,id,passswrd,email);

        read_booking();
    }


    /**
     * display all playground on system
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void view_playgrounds() throws SQLException, ClassNotFoundException {

        SQLSimulator sqlSimulator = new SQLSimulator();
        ResultSet resultSet = sqlSimulator.display_playground();

        while (resultSet.next()){

            if(resultSet.getString("active_or_suspend").equalsIgnoreCase("active")) {
                System.out.println("playground name : " + resultSet.getString("playground_name") +
                        " / playground id : " + resultSet.getInt("playground_id") +
                        "  / playground address : " + resultSet.getString("address") +
                        " / playground Gps : "+resultSet.getString("GPS")+
                        " / playground size : " + resultSet.getString("size")+
                        " / price per hour : "+resultSet.getDouble("price_per_hour"));
            }

        }

    }


    /**
     * filter playground by date available and time slot
     * @param date for available date
     * @param time for available time slot
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void view_playgrounds(String date, int time) throws SQLException, ClassNotFoundException {

        SQLSimulator sqlSimulator = new SQLSimulator();
        ResultSet resultSet1 = sqlSimulator.display_playground();
        ResultSet resultSet2 = sqlSimulator.dispaly_booked_hours();

        boolean result_found = false;
        while(resultSet1.next()){

            if(resultSet1.getString("playground_id").equalsIgnoreCase(resultSet2.getString("playground_id"))&&
            resultSet2.getString("hour_date").equalsIgnoreCase(date)&&resultSet2.getInt("hour")==time){

                continue;
            }

            else {
                if(resultSet1.getString("active_or_suspend").equalsIgnoreCase("active")&&
                resultSet1.getInt("start")<=time&&resultSet1.getInt("end")>=time) {
                    result_found = true;
                    System.out.println("playground name : " + resultSet1.getString("playground_name") +
                            " / playground id : " + resultSet1.getInt("playground_id") +
                            "  / playground address : " + resultSet1.getString("address") +
                            " / playground Gps : "+resultSet1.getString("GPS")+
                            " / playground size : " + resultSet1.getString("size")+
                            " / price per hour : "+resultSet1.getDouble("price_per_hour"));
                }
            }


        }


        if(result_found==false){
            System.out.println("no result found");
        }


    }


    /**
     * filter playground by area
     * @param area for playground area
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void view_playgrounds(String area) throws SQLException, ClassNotFoundException {

       SQLSimulator sqlSimulator = new SQLSimulator();
        ResultSet resultSet = sqlSimulator.display_playground();
        boolean result_foound = false;
        while (resultSet.next()){
            if(resultSet.getString("address").equalsIgnoreCase(area)||resultSet.getString("GPS").equalsIgnoreCase(area)){
                if(resultSet.getString("active_or_suspend").equalsIgnoreCase("active")) {
                    result_foound = true;
                    System.out.println("playground name : " + resultSet.getString("playground_name") +
                            " / playground id : " + resultSet.getInt("playground_id") +
                            "  / playground address : " + resultSet.getString("address") +
                            " / playground Gps : "+resultSet.getString("GPS")+
                            " / playground size : " + resultSet.getString("size")+
                            " / price per hour : "+resultSet.getDouble("price_per_hour"));
                }
            }
        }

        if(result_foound == false){
            System.out.println("no result found");
        }

    }


    /**
     * filter playground by area and date and time slots
     * @param area for playground area
     * @param date for playground date
     * @param time for playground time
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void view_playgrounds(String area,String date,int time) throws SQLException, ClassNotFoundException {
        SQLSimulator sqlSimulator = new SQLSimulator();
        ResultSet resultSet1 = sqlSimulator.display_playground();
        ResultSet resultSet2 = sqlSimulator.dispaly_booked_hours();

        boolean result_found = false;
        while(resultSet1.next()){

            // is it booked ?
            if(resultSet1.getString("playground_id").equalsIgnoreCase(resultSet2.getString("playground_id"))&&
                    resultSet2.getString("hour_date").equalsIgnoreCase(date)&&resultSet2.getInt("hour")==time){

                continue;
            }

            //not booked but is it active and time betwween start and end and area is true?
            else {
                if(resultSet1.getString("active_or_suspend").equalsIgnoreCase("active")&&
                        resultSet1.getInt("start")<=time&&resultSet1.getInt("end")>=time&&
                        (resultSet1.getString("address").equalsIgnoreCase(area)||resultSet1.getString("GPS").equalsIgnoreCase(area))) {
                    result_found = true;
                    System.out.println("playground name : " + resultSet1.getString("playground_name") +
                            " / playground id : " + resultSet1.getInt("playground_id") +
                            "  / playground address : " + resultSet1.getString("address") +
                            " / playground Gps : "+resultSet1.getString("GPS")+
                            " / playground size : " + resultSet1.getString("size")+
                            " / price per hour : "+resultSet1.getDouble("price_per_hour"));
                }
            }


        }


        if(result_found==false){
            System.out.println("no result found");
        }


    }


    /**
     * allow player making book
     * @param playground_id value int
     * @param date when
     * @param hour when
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void book_playground(int playground_id,String date,double hour) throws SQLException, ClassNotFoundException {

        SQLSimulator sqlSimulator = new SQLSimulator();
        ResultSet resultSet = sqlSimulator.set_query("select playground_id,start,end,price_per_hour,active_or_suspend,playground_owner_id from Playground where playground_id = '"+playground_id+"' and active_or_suspend = 'active' ;");



        //is it valid id
        if(resultSet.next()){
            ResultSet resultSet1 = sqlSimulator.set_query("select *from Playground_hours where playground_id = '"+resultSet.getInt("playground_id")+"'"+
                    "and hour_date = '"+date+"' and hour = '"+hour+"';");
            //hour is already booked ?
            if(resultSet1.next()){
                System.out.println("sorry hour is already booked !!!");
            }
            //not booked ?
            else {
                //have enough money ?
                if(hour >resultSet.getInt("start")&&hour<resultSet.getInt("end")){

                if(this.getEwallet().transimision(getId(),resultSet.getInt("playground_owner_id"),resultSet.getDouble("price_per_hour"))){
                    System.out.println("booked successfully");
                    Booking booking = new Booking(resultSet.getInt("playground_id"),date,hour,resultSet.getDouble("price_per_hour"));


                    sqlSimulator.add_playground_hours(booking,resultSet.getInt("playground_id"),getId());
                    bookings.add(booking);
                }
                //have not enough money
                else {
                    System.out.println("sorry you have not enough money charge your ewallet ");

                }

                }
                else {
                    System.out.println("playground not available in this hour");
                }
            }

        }
        //id is not here
        else {
            System.out.println("incorrect playground id");
        }


    }


    /**
     * read player booking from data base
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void read_booking() throws SQLException, ClassNotFoundException {

        SQLSimulator sqlSimulator = new SQLSimulator();
        ResultSet resultSet = sqlSimulator.set_query("select * from Playground_hours where player_id = '"+getId()+"';");
        while (resultSet.next()){

            bookings.add(new Booking(resultSet.getInt("playground_id"),resultSet.getString("hour_date"),resultSet.getDouble("hour"),resultSet.getDouble("price_per_hour")));

        }
    }

    /**
     * show all booking that player made
     */
    public void show_my_booking(){

        if (bookings.size() == 0){
            System.out.println("you have not any booking yet");
        }

        for(int i = 0;i<bookings.size();i++){
            System.out.println("playground id : "+bookings.get(i).getPlayground_id()+" / date : "+bookings.get(i).getDate()
            +" / hour : "+bookings.get(i).getHour()+" / price per hour : "+bookings.get(i).getPrice_per_hour());
        }

    }


}
