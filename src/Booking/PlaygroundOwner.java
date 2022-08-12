package Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * implement all operation playground can do
 *
 * @author zidan
 */
public class PlaygroundOwner extends User {

    public PlaygroundOwner(){}

    /**
     * initial attributes
     * @param name for playground name
     * @param id for playground id
     * @param passswrd for playground pass
     * @param email for playground email
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public PlaygroundOwner(String name,int id,String passswrd,String email) throws SQLException, ClassNotFoundException {
        super(name,id,passswrd,email);

        read_my_playground();
    }


    public ArrayList<Playground> playgrounds = new ArrayList<Playground>();

    /**
     * read all playground that playground has from data base
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void read_my_playground() throws SQLException, ClassNotFoundException {
        SQLSimulator sqlSimulator = new SQLSimulator();


        ResultSet resultSet = sqlSimulator.set_query("select *from playground where playground_owner_id = '"+getId()+"';");

        while (resultSet.next()){

            ResultSet resultSet1 = sqlSimulator.set_query("select * from Playground_hours where playground_id = '"+resultSet.getInt("playground_id")+"';");

            ArrayList<Booking>booked_hours = new ArrayList<Booking>();
            while(resultSet1.next()){

                booked_hours.add(new Booking(resultSet1.getString("hour_date"),resultSet1.getDouble("hour"),resultSet1.getDouble("price_per_hour")));



            }



            Playground playground = new Playground(resultSet.getInt("playground_id"),resultSet.getString("playground_name"),new Location(resultSet.getString("address"),resultSet.getString("GPS"))
            ,resultSet.getString("size"),resultSet.getDouble("price_per_hour"),resultSet.getDouble("cancelation_period"),resultSet.getInt("start"),resultSet.getInt("end"),booked_hours);

            playground.setActive_or_Suspend(resultSet.getString("active_or_suspend"));


            playgrounds.add(playground);
        }

    }

    /**
     * add new playground
     * @param playground value Playground
     * @return true or false
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean add_playground(Playground playground) throws SQLException, ClassNotFoundException {

        boolean accept = true;
        for(int i =0;i<playgrounds.size();i++){
            if(playgrounds.get(i).getName().equalsIgnoreCase(playground.getName())&&playgrounds.get(i).getLocation().getAddress().equalsIgnoreCase(playground.getLocation().getAddress())){

            accept = false;
            return false;
            }
        }

        if(accept == true){

            playgrounds.add(playground);

           return true;
        }

        else {
            return false;
        }

    }

    /**
     * display all booking in his playgrounds
     */
    public void view_booking(){

        if(playgrounds.size()==0){
            System.out.println("you have not any playground yet");
        }
        for(int i = 0;i<playgrounds.size();i++){

            System.out.println("playground name : "+playgrounds.get(i).getName());


            System.out.println("statue : "+playgrounds.get(i).getActive_or_Suspend());

            if(playgrounds.get(i).booking_hours.size() == 0||playgrounds.get(i).booking_hours==null){
                System.out.println("no Booking yet");
            }
            else
            System.out.println("booking hours is :");
            for(int j = 0;j<playgrounds.get(i).booking_hours.size();j++){


                System.out.println("Date : "+playgrounds.get(i).booking_hours.get(j).getDate()
                +" hour : "+playgrounds.get(i).booking_hours.get(j).getHour());

            }


        }

    }


}
