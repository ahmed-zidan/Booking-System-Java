


package Booking;


import java.io.IOException;
import java.sql.*;

/**
 *connect system with sqlite embedded database and define all tables used in system
 *
 * @author zidan
 */
public class SQLSimulator {

    private static Connection con;
    private static boolean has_data = false;

    /**
     * display all users in system
     * @return resultset
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ResultSet display_users() throws SQLException, IOException, ClassNotFoundException {


        if (con == null) {
            get_connection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("select name ,id,password,email,role from User");
        return res;

    }


    /**
     * display all profile in system
     * @return resultset
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ResultSet display_profiles() throws SQLException, IOException, ClassNotFoundException {


        //System.out.println("display profile");
        if (con == null) {
            get_connection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("select phone,location from Profile");
        return res;

    }


    /**
     * connect to sqlite-jdbc
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void get_connection() throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:SQLSimulator.db");

        initialise();
    }

    /**
     * define all tables
     * @throws SQLException
     */
    private void initialise() throws SQLException {

        if (!has_data) {
            has_data = true;
        }


        Building_user_table();
        Building_profile_table();

        Building_playground_table();

        Building_playground_hours();

        //Building_team();
        Building_message_table();
    }

    /**
     * add new user to database
     * @param user value User
     * @param role playr or playground owner ?
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void add_user(User user, String role) throws SQLException, ClassNotFoundException {
        if (con == null) {
            get_connection();
        }


        PreparedStatement prep = con.prepareStatement("INSERT INTO User values (?,?,?,?,?);");
        prep.setString(1, Integer.toString(user.getId()));
        prep.setString(2, user.getName());
        prep.setString(3, user.getPassword());
        prep.setString(4, user.getEmail());

        prep.setString(5, role);
        prep.execute();

    }


    /**
     * Building user table
     * @throws SQLException
     */
    private void Building_user_table() throws SQLException {
        Statement state = con.createStatement();

        ResultSet res = state.executeQuery("SELECT name from sqlite_master where type ='table' and name ='User';");


        if (!res.next()) {

            //Building user tables
            Statement state2 = con.createStatement();
            state2.execute("create table User (id int primary key,name varchar(60) not null,password varchar (60)unique" +
                    ",email varchar(60) unique,role varchar(60) not null);");

            PreparedStatement preparedStatement = con.prepareStatement("insert into USER values (?,?,?,?,?)");

            preparedStatement.setString(1, "100");
            preparedStatement.setString(2, "zidan");
            preparedStatement.setString(3, "Iloveallah@6");
            preparedStatement.setString(4, "zidanzezo@gmail.com");
            preparedStatement.setString(5, "player");

            preparedStatement.execute();

            preparedStatement = con.prepareStatement("insert into USER values (?,?,?,?,?)");
            preparedStatement.setString(1, "101");
            preparedStatement.setString(2, "ahmed");
            preparedStatement.setString(3, "Jadoreallah@1");
            preparedStatement.setString(4, "ahmed@gmail.com");
            preparedStatement.setString(5, "playground_owner");


            preparedStatement.execute();

            preparedStatement = con.prepareStatement("insert into USER values (?,?,?,?,?)");
            preparedStatement.setString(1, "102");
            preparedStatement.setString(2, "mohammed");
            preparedStatement.setString(3, "hH@1");
            preparedStatement.setString(4, "mohammed@gmail.com");
            preparedStatement.setString(5, "admin");


            preparedStatement.execute();


        }

    }


    /**
     * Building profile table
     * @throws SQLException
     */
    private void Building_profile_table() throws SQLException {
        ResultSet res;
        Statement statement = con.createStatement();
        res = statement.executeQuery("SELECT name from sqlite_master where type ='table' and name ='Profile';");
        if (!res.next()) {

            //System.out.println("building profile table");
            //Building profile tables
            Statement state2 = con.createStatement();
            state2.execute("create table Profile (id integer  not null,phone varchar(60) unique,location varchar (60) null" +
                    ",foreign key(id) references User(id));");


            PreparedStatement preparedStatement = con.prepareStatement("insert into Profile values (?,?,?);");

            preparedStatement.setString(1, "100");
            preparedStatement.setString(2, "01115930826");
            preparedStatement.setString(3, "Giza");

            preparedStatement.execute();

            preparedStatement = con.prepareStatement("insert into Profile values (?,?,?);");
            preparedStatement.setString(1, "101");
            preparedStatement.setString(2, "01123232211");
            preparedStatement.setString(3, "cairo");

            preparedStatement.execute();

            preparedStatement = con.prepareStatement("insert into Profile values (?,?,?);");
            preparedStatement.setString(1, "102");
            preparedStatement.setString(2, "01123233333");
            preparedStatement.setString(3, "alex");

            preparedStatement.execute();

        }
    }


    /**
     * used for select any data
     * @param query value String
     * @return Resultset
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public ResultSet set_query(String query) throws SQLException, ClassNotFoundException {

        if (con == null) {
            get_connection();
        }

        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        return resultSet;
    }


    /**
     * create profile
     * @param id for user id
     * @param profile value Profile
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void add_profile(int id, Profile profile) throws SQLException, ClassNotFoundException {


        if (con == null) {
            get_connection();
        }


        PreparedStatement prep = con.prepareStatement("INSERT INTO Profile values (?,?,?);");

        prep.setString(1, Integer.toString(id));
        prep.setString(2, profile.getMobile());
        prep.setString(3, profile.getAddress());
        prep.execute();

    }


    /**
     * Building playground table
     * @throws SQLException
     */
    private void Building_playground_table() throws SQLException {

        ResultSet res;
        Statement statement = con.createStatement();
        res = statement.executeQuery("SELECT name from sqlite_master where type ='table' and name ='Playground';");
        if (!res.next()) {



            //Building playground table
            Statement state2 = con.createStatement();
            state2.execute("create table Playground(playground_id int primary key ,playground_name varchar(60) not null," +
                    "address varchar(60) not null,GPS varchar(60) not null,size varchar (60)not null,cancelation_period double not null ," +
                    "price_per_hour double not null,active_or_suspend varchar(60) not null ,start int not null,end int not null,playground_owner_id int not null," +
                    "unique (playground_name,address),foreign key (playground_owner_id) references User(id));");



            PreparedStatement preparedStatement = con.prepareStatement("insert into Playground values (?,?,?,?,?,?,?,?,?,?,?);");

            preparedStatement.setString(1, "1");
            preparedStatement.setString(2, "juventus");
            preparedStatement.setString(3, "Giza");
            preparedStatement.setString(4, "saft");
            preparedStatement.setString(5, "large");
            preparedStatement.setString(6, "24");
            preparedStatement.setString(7, "60");

            preparedStatement.setString(8, "active");
            preparedStatement.setString(9, "12");
            preparedStatement.setString(10, "24");
            preparedStatement.setString(11, "101");

            preparedStatement.execute();

            preparedStatement = con.prepareStatement("insert into Playground values (?,?,?,?,?,?,?,?,?,?,?);");

            preparedStatement.setString(1, "2");
            preparedStatement.setString(2, "barcelone");
            preparedStatement.setString(3, "cairo");
            preparedStatement.setString(4, "saft labn");
            preparedStatement.setString(5, "large");
            preparedStatement.setString(6, "24");
            preparedStatement.setString(7, "60");

            preparedStatement.setString(8, "suspend");
            preparedStatement.setString(9, "12");
            preparedStatement.setString(10, "24");
            preparedStatement.setString(11, "101");

            preparedStatement.execute();


        }


    }

    /**
     * Building playground hours (booking table)
     * @throws SQLException
     */
    private void Building_playground_hours() throws SQLException {

        ResultSet res;
        Statement statement = con.createStatement();
        res = statement.executeQuery("SELECT name from sqlite_master where type ='table' and name ='Playground_hours';");
        if (!res.next()) {


            //Building playground table
            Statement state2 = con.createStatement();
            state2.execute("create table Playground_hours(hour_date date NOT NULL ,hour double not null, " +
                    "price_per_hour double not null,playground_id int not null,player_id int not null," +
                    "primary key (hour_date,hour,playground_id),foreign key (player_id) references User(id),foreign key (playground_id) references Plaground(playground_id));");


            PreparedStatement preparedStatement = con.prepareStatement("insert into Playground_hours values (?,?,?,?,?);");


            preparedStatement.setString(1, "2020-01-06");
            preparedStatement.setString(2, "16");
            preparedStatement.setString(3, "60");

            preparedStatement.setString(4, "1");
            preparedStatement.setString(5,"100");

            preparedStatement.execute();


        }



    }


    /**
     * add new playground
     * @param playground value Playground
     * @param playground_owner_id playgroundowner id
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void add_playground(Playground playground,int playground_owner_id) throws SQLException, ClassNotFoundException {

        if (con == null) {
            get_connection();
        }

        PreparedStatement preparedStatement = con.prepareStatement("insert into Playground values (?,?,?,?,?,?,?,?,?,?,?);");

        preparedStatement.setString(1, Integer.toString(playground.getId()));
        preparedStatement.setString(2, playground.getName());
        preparedStatement.setString(3, playground.getLocation().getAddress());
        preparedStatement.setString(4, playground.getLocation().getGPS());
        preparedStatement.setString(5, playground.getSize());
        preparedStatement.setString(6, Double.toString(playground.getCancelation_period()));
       preparedStatement.setString(7,Double.toString(playground.getPrice_per_hour()));
        preparedStatement.setString(8, playground.getActive_or_Suspend());
        preparedStatement.setString(9,Integer.toString(playground.getStart()));
        preparedStatement.setString(10,Integer.toString(playground.getEnd()));
        preparedStatement.setString(11, Integer.toString(playground_owner_id));

        preparedStatement.execute();



    }


    /**
     * add new booking
     * @param booking value Booking
     * @param playground_id where ?
     * @param player_id who ?
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void add_playground_hours(Booking booking,int playground_id,int player_id) throws SQLException, ClassNotFoundException {


        if (con == null) {
            get_connection();
        }

           PreparedStatement preparedStatement = con.prepareStatement("insert into Playground_hours values (?,?,?,?,?);");


            preparedStatement.setString(1,booking.getDate());
            preparedStatement.setString(2, Double.toString(booking.getHour()));

            preparedStatement.setString(3, Double.toString(booking.getPrice_per_hour()));


            preparedStatement.setString(4, Integer.toString(playground_id));

            preparedStatement.setString(5,Integer.toString(player_id));
            preparedStatement.execute();



    }


    /**
     * display all playground on system
     * @return Resultset
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ResultSet display_playground() throws SQLException, ClassNotFoundException {

        if(con == null){
            get_connection();
        }

        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Playground");
        return resultSet;
    }

    /**
     * display all booking
     * @return resultset
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ResultSet dispaly_booked_hours() throws SQLException, ClassNotFoundException {
        if(con == null){
            get_connection();
        }

        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Playground_hours");
        return resultSet;
    }


    /**
     * build team
     * @throws SQLException
     */
    private void Building_team() throws SQLException {

        ResultSet res;
        Statement statement = con.createStatement();
        res = statement.executeQuery("SELECT name from sqlite_master where type ='table' and name ='Team';");
        //not created yet
        if(!res.next()){

            statement.execute("create table Team(leader int primary key,player1_id int not null,player2_id int not null" +
                    " ,player3_id int not null,player3_id int not null,player4_id int not null);");


        }

    }


    /**
     * Building message table
     * @throws SQLException
     */
    private void Building_message_table() throws SQLException {

        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("select name from sqlite_master where type = 'table' and name = 'Message';");

        if(!resultSet.next()){
            Statement state2 = con.createStatement();
            state2.execute("create table Message(sender_id int not null,receiver_id int not null,message varchar(60) not null," +
                    " date date not null, foreign key (sender_id) references User(id), foreign key(receiver_id) references User(id));");


            PreparedStatement preparedStatement= con.prepareStatement("insert into Message values (?,?,?,?)");
            preparedStatement.setString(1,"100");
            preparedStatement.setString(2,"101");
            preparedStatement.setString(3,"hello my bro");

            preparedStatement.setString(4,"15-6-2020");
            preparedStatement.execute();
        }


    }


    /**
     * send a new massage
     * @param mesasge value Massage
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void add_message(Mesasge mesasge) throws SQLException, ClassNotFoundException {

        if(con == null){
            get_connection();
        }

        PreparedStatement preparedStatement = con.prepareStatement("insert into Message values (?,?,?,?)");

        preparedStatement.setString(1,Integer.toString(mesasge.getSender_id()));
        preparedStatement.setString(2,Integer.toString(mesasge.getReceiver_id()));
        preparedStatement.setString(3,mesasge.getMessage());
        preparedStatement.setString(4,mesasge.getDate());
        preparedStatement.execute();

    }

    /**
     * update any row in database
     * @param sql
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void update(String sql) throws SQLException, ClassNotFoundException {

        if(con ==null){
            get_connection();
        }

        PreparedStatement preparedStatement = con.prepareStatement(sql);

        //preparedStatement.setString(8,value);
        preparedStatement.execute();

    }


}
