package Booking;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * admin manage all system by adding or deleting ..etc
 *
 * @author zidan
 */

public class Adminstrator extends User{


    /**
     * this constructor initialize attributes
     * @param name for admin name
     * @param id for admin id
     * @param passswrd for admin pass
     * @param email for admin email
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    Adminstrator(String name,int id,String passswrd,String email) throws SQLException, ClassNotFoundException {

        super(name,id,passswrd,email);

    }


    /**
     * this class display all playground existing on system
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public void show_all_playground() throws SQLException, ClassNotFoundException {
        SQLSimulator sqlSimulator = new SQLSimulator();
        ResultSet resultSet = sqlSimulator.display_playground();

        boolean has_data = false;

        while (resultSet.next()) {

            has_data = true;
                System.out.println("playground Id : " + resultSet.getString("playground_id") +
                        " / playground name : " + resultSet.getString("playground_name") +
                        " / address : " + resultSet.getString("address") +
                        " / GPS : " + resultSet.getString("GPS") +
                        " / size : " + resultSet.getString("size") +
                        " / cancelation period : " + resultSet.getInt("cancelation_period") +
                        " / price per hour : " + resultSet.getDouble("price_per_hour") +
                        " / active or suspend : " + resultSet.getString("active_or_suspend") +
                        " / start : " + resultSet.getInt("start") +
                        " / end : " + resultSet.getInt("end"));

            }

        if(has_data == false){
            System.out.println("no result found ");
        }

    }

    /**
     * this function display all suspended playground existing on system
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public void show_suspend_playground() throws SQLException, ClassNotFoundException {
        SQLSimulator sqlSimulator = new SQLSimulator();
        ResultSet resultSet = sqlSimulator.set_query("select * from Playground where active_or_suspend = 'suspend';");

        boolean has_data = false;


        while (resultSet.next()){
            has_data = true;
            System.out.println("playground Id : "+resultSet.getString("playground_id")+
                    " / playground name : "+resultSet.getString("playground_name")+
                    " / address : "+ resultSet.getString("address")+
                    " / GPS : "+resultSet.getString("GPS")+
                    " / size : "+resultSet.getString("size")+
                    " / cancelation period : "+resultSet.getInt("cancelation_period")+
                    " / price per hour : "+resultSet.getDouble("price_per_hour")+
                    " / start : "+resultSet.getInt("start")+
                    " / end : "+resultSet.getInt("end"));
        }


        if(has_data == false){
            System.out.println("no result found ");
        }

    }


    /**
     *this function display all active playground existing on system
     * @throws SQLException
     * @throws ClassNotFoundException
     */


    public void show_active_playground() throws SQLException, ClassNotFoundException {
        SQLSimulator sqlSimulator = new SQLSimulator();
        ResultSet resultSet = sqlSimulator.set_query("select * from Playground where active_or_suspend = 'active';");

        boolean has_data = false;

        while (resultSet.next()){

            has_data = true;
            System.out.println("playground Id : "+resultSet.getString("playground_id")+
                    " / playground name : "+resultSet.getString("playground_name")+
                    " / address : "+ resultSet.getString("address")+
                    " / GPS : "+resultSet.getString("GPS")+
                    " / size : "+resultSet.getString("size")+
                    " / cancelation period : "+resultSet.getInt("cancelation_period")+
                    " / price per hour : "+resultSet.getDouble("price_per_hour")+
                    " / start : "+resultSet.getInt("start")+
                    " / end : "+resultSet.getInt("end"));
        }


        if(has_data == false){
            System.out.println("no result found ");
        }

    }


    /**
     *this function used to suspend any playground by using playground id
     * @param playground_id for playground id
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public void suspend_playground(int playground_id) throws SQLException, ClassNotFoundException {

        SQLSimulator sqlSimulator = new SQLSimulator();
        ResultSet resultSet = sqlSimulator.set_query("select playground_id from playground where playground_id = '"+playground_id+"';");

        if(resultSet.next()){

            String sql = "update playground set active_or_suspend = 'suspend' where playground_id = '"+playground_id+"';";
            sqlSimulator.update(sql);

            System.out.println("Done");
        }
        else{
            System.out.println("incorrect id !!!");
        }

    }

    /**
     * allow admin to active any suspend playground on system by using playground id
     * @param playground_id for playground id
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public void active_playground(int playground_id) throws SQLException, ClassNotFoundException {

        SQLSimulator sqlSimulator = new SQLSimulator();
        ResultSet resultSet = sqlSimulator.set_query("select playground_id from playground where playground_id = '"+playground_id+"';");

        if(resultSet.next()){

            String sql = "update playground set active_or_suspend = 'active' where playground_id = '"+playground_id+"';";

            sqlSimulator.update(sql);

            System.out.println("Done");
        }
        else{
            System.out.println("incorrect id !!!");
        }

    }

    /**
     * delete playground from system
     * @param playground_id for playground id
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    void delete_playground(int playground_id) throws SQLException, ClassNotFoundException {
        SQLSimulator sqlSimulator = new SQLSimulator();

        ResultSet resultSet = sqlSimulator.set_query("select playground_id from playground where playground_id = '"+playground_id+"';");

        if(resultSet.next()) {
            String sql = "delete from Playground where playground_id = '" + playground_id + "';";

            sqlSimulator.update(sql);
            System.out.println("Done");
        }
        else{
            System.out.println("incorrect id !!!");
        }

    }


}
