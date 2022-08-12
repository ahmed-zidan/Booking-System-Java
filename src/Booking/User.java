

package Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * assign the information about every user in the program
 *
 * @author zidan
 */


abstract class User {

    private String name;
    private int id;
    private String password;
    private String email;
    private Profile profile;

    private Ewallet ewallet;

    ArrayList<Mesasge>mesasges = new ArrayList<Mesasge>();

    /**
     * default constructor
     */
    public User(){}

    /**
     * initial user info
     * @param name user name
     * @param id useer id
     * @param passswrd user pass
     * @param email user email
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public User(String name,int id,String passswrd,String email) throws SQLException, ClassNotFoundException {
        this.name = name;
        this.id = id;
        this.email = email;
        this.password = passswrd;

        this.ewallet = new Ewallet(id);

        read_meassage();

   }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Ewallet getEwallet() {
        return ewallet;
    }

    public void setEwallet(Ewallet ewallet) {
        this.ewallet = ewallet;
    }

    public int getId() {
        return id;
    }

    /**
     * print name,id,email
     */
    public void print_account_info(){
        System.out.println("name : "+name);
        System.out.println("id : "+id);
        System.out.println("email : "+email);
    }


    /**
     * user has message ?
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void read_meassage() throws SQLException, ClassNotFoundException {

        SQLSimulator sqlSimulator = new SQLSimulator();
        ResultSet resultSet = sqlSimulator.set_query("select * from Message where receiver_id = '"+id+"';");



        while (resultSet.next()){

            mesasges.add(new Mesasge(resultSet.getInt("sender_id"),resultSet.getInt("receiver_id"),resultSet.getString("message"),resultSet.getString("date")));
        }



    }

    /**
     * display all message
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void show_my_massage() throws SQLException, ClassNotFoundException {
        if (mesasges.size() == 0) {
            System.out.println("you have no massage");
        } else {

            SQLSimulator sqlSimulator = new SQLSimulator();


            for (int i = 0; i < mesasges.size(); i++) {

                ResultSet resultSet = sqlSimulator.set_query("select id,name from User where id = '"+mesasges.get(i).getSender_id()+"';");
                System.out.println("sender id : "+mesasges.get(i).getSender_id()+" / sender name : "+ resultSet.getString("name") +" / contents : "+mesasges.get(i).getMessage()
                +" / Date : "+mesasges.get(i).getDate());

            }
        }
    }





}
