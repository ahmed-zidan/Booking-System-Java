



package Booking;

import kotlin.text.UStringsKt;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 *this class manage all classes
 *
 * @author zidan
 */

public class main {


    static SQLSimulator sqlSimulator = new SQLSimulator();


    /**
     * this class used for running program
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {


        System.out.println("1-> Registre");
        System.out.println("2-> Login");
        Scanner sc = new Scanner(System.in);
        char choice = sc.next().charAt(0);
        while(choice != '1' && choice != '2'){
            System.out.println("1-> Registre");
            System.out.println("2-> Login");
         choice = sc.next().charAt(0);
        }

        if(choice == '1'){
            register();

            System.out.println("login : ");
        }



        //login to System
        String email = " ";
        String password= " ";




        try {
            System.out.println("enter your email");
            email = sc.next();
            ResultSet resultSet1 = sqlSimulator.set_query("select email from User where email = "+"'"+email+"';");


            System.out.println("enter your password");


            password = sc.next();
            ResultSet resultSet2 = sqlSimulator.set_query("select password from User where password = "+"'"+password+"';");

            //is it correct or not ?
            resultSet1.getString("email");
            resultSet2.getString("password");
        } catch (SQLException throwables) {
            System.out.println("incorrect email or password");
            throwables.printStackTrace();
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.out.println("incorrect email or password");

        System.exit(0);
        }



            //got the user and give him its information
            try {
                ResultSet resultSet = sqlSimulator.set_query("select id,name,password,email,role from User where email= " +
                        "'"+email+"' and password = "+"'"+password+"';");

                PlaygroundOwner playgroundOwner;
                Player player;
                Adminstrator adminstrator;
                if(resultSet.getString("role").equalsIgnoreCase("player")){
                    player = new Player(resultSet.getString("name"),resultSet.getInt("id"),
                            resultSet.getString("password"),resultSet.getString("email"));

                    resultSet = sqlSimulator.set_query("select location,phone from Profile where profile.id = "+"'"+player.getId()+"';");

                    player.setProfile(new Profile(resultSet.getString("phone"),resultSet.getString("location")));

                    //System.out.println("profile inf = "+player.getProfile().getMobile()+" "+player.getProfile().getAddress());

                    //what player can do ?
                    user_menue(player);
                    //System.out.println(player.getId()+" "+player.getName()+" "+player.getPassword()+" "+player.getEmail());

                }
                else if(resultSet.getString("role").equalsIgnoreCase("playground_owner")){


                    playgroundOwner = new PlaygroundOwner(resultSet.getString("name"),resultSet.getInt("id"),
                            resultSet.getString("password"),resultSet.getString("email"));

                    resultSet = sqlSimulator.set_query("select location,phone from Profile where profile.id = "+"'"+playgroundOwner.getId()+"';");

                    playgroundOwner.setProfile(new Profile(resultSet.getString("phone"),resultSet.getString("location")));

                    //what playground owner can do

                    user_menue(playgroundOwner);
                    //System.out.println(playgroundOwner.getId()+" "+playgroundOwner.getName()+" "+playgroundOwner.getPassword()+" "+playgroundOwner.getEmail());
                }

                else if(resultSet.getString("role").equalsIgnoreCase("admin")){


                    adminstrator = new Adminstrator(resultSet.getString("name"),resultSet.getInt("id"),
                            resultSet.getString("password"),resultSet.getString("email"));

                    resultSet = sqlSimulator.set_query("select location,phone from Profile where profile.id = "+"'"+adminstrator.getId()+"';");

                    adminstrator.setProfile(new Profile(resultSet.getString("phone"),resultSet.getString("location")));

                    //what playground owner can do

                    user_menue(adminstrator);
                    //System.out.println(playgroundOwner.getId()+" "+playgroundOwner.getName()+" "+playgroundOwner.getPassword()+" "+playgroundOwner.getEmail());
                }

                else {
                    System.out.println("nooooooooooo");
                }




            } catch (SQLException throwables) {
                System.out.println("connection error in DBMS");

                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }



    }


    /**
     * user interface for admin
     * @param adminstrator for manage Admin class
     */
    private static void user_menue(Adminstrator adminstrator) {


        while (true) {
            System.out.println("1->show all playgrounds");
            System.out.println("2->show active playgrounds");
            System.out.println("3->show suspend playgrounds");
            System.out.println("4->suspend playground");
            System.out.println("5->active playground");
            System.out.println("6->delete playground");
           System.out.println("7->show message");
            System.out.println("8->set message or complaint");
            System.out.println("9->logout");

            Scanner scanner = new Scanner(System.in);
            char choice = scanner.next().charAt(0);
            if (choice < '1' && choice > '9') {
                System.out.println("enter a valid choice !!!!");
                continue;
            }

            if(choice == '1'){
                try {
                    adminstrator.show_all_playground();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            else if(choice == '2'){
                try {
                    adminstrator.show_active_playground();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            else if(choice == '3'){
                try {
                    adminstrator.show_suspend_playground();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            else if(choice == '4'){
                System.out.print("playground id : ");
                int playground_id = scanner.nextInt();
                try {
                    adminstrator.suspend_playground(playground_id);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            else if(choice == '5'){
                System.out.print("playground id : ");
                int playground_id = scanner.nextInt();
                try {
                    adminstrator.active_playground(playground_id);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            else if(choice == '6'){
                System.out.print("playground id : ");
                int playground_id = scanner.nextInt();
                try {
                    adminstrator.delete_playground(playground_id);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            else if(choice == '7'){

                try {
                    adminstrator.show_my_massage();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if(choice == '8'){

                send_message(adminstrator);
            }
            else {
                System.exit(0);
            }

        }
    }


    /**
     * add new user to pooking system and payment system
     * @return true or false
     */
    public static boolean register(){
        System.out.println("register as :");
        System.out.println("1-> player");
        System.out.println("2-> playground owner");

        Scanner sc = new Scanner(System.in);
        char player_or_owner = sc.next().charAt(0);
        while(player_or_owner != '1'&&player_or_owner!='2'){
            System.out.println("1-> player");
            System.out.println("2-> playground owner");
            player_or_owner = sc.next().charAt(0);
        }


             System.out.println("your name :");
           sc.nextLine();
            String name = sc.nextLine();
            System.out.println("your password :");

            String password = sc.next();

        try {
            while (true) {
                ResultSet resultSet = sqlSimulator.set_query("select password from User where password = '" + password + "';");

                if(!new Register().password_is_strong(password)){
                    System.out.println("password should have lowercase , uppercase, sympols , numbers");
                    password = sc.next();
                continue;
                }

                else if(resultSet.next()){
                    System.out.println("password is already used");
                    System.out.println("your password :");

                    password = sc.next();
                continue;
                }

            else {
                break;
                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        sc.nextLine();
            System.out.println("confirm password :");
            String confirm = sc.next();
            while (!confirm.equalsIgnoreCase(password)){
                System.out.println("incorrect ");
                confirm = sc.next();

            }

        String email;

            while(true) {

                System.out.println("email :");
                email = sc.next();

                try {
                    ResultSet resultSet = sqlSimulator.set_query("select email from User where email = '"+email+"';");
                    if(!new Register().email_is_valid(email)){
                        System.out.println("enter a valid email");
                        continue;
                    }

                    else if(resultSet.next()){
                        System.out.println("email is already used !");
                        continue;
                    }

                    else {
                        break;
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }



            //the system in this step shoud send an invitation to email


            //is  player ?
            if(player_or_owner == '1'){

                try {

                    //get the last id in System
                    ResultSet resultSet = sqlSimulator.set_query("select id from User where id = (select max(id) from User) ;");
                    int id =resultSet.getInt("id");
                    id++;

                    System.out.println("your id is :" + id);


                    //create a new Ewallet to user
                    Socket socket = new Socket("localhost",1232);
                    PrintStream client_say = new PrintStream(socket.getOutputStream());
                    client_say.println(3);
                    client_say.println(id);
                    socket.close();



                    Player player = new Player(name,id,password,email);


                    sqlSimulator.add_user(player,"player");
                    Profile profile = new Profile(null,null);
                    sqlSimulator.add_profile(player.getId(),profile);

                    System.out.println("done");
                } catch (SQLException throwables) {
                    System.out.println("user is already used");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

            if(player_or_owner == '2'){

                try {

                    //get the last id in System
                    ResultSet resultSet = sqlSimulator.set_query("select id from User where id = (select max(id) from User) ;");
                    int id =resultSet.getInt("id");
                    id++;

                    System.out.println("your id is :" + id);

                    //create a new Ewallet to user
                    Socket socket = new Socket("localhost",1232);
                    PrintStream client_say = new PrintStream(socket.getOutputStream());
                    client_say.println(3);
                    client_say.println(id);
                    socket.close();

                    PlaygroundOwner playgroundOwner = new PlaygroundOwner(name,id,password,email);


                    sqlSimulator.add_user(playgroundOwner,"playground_owner");
                                   //create a new profile to user
                    Profile profile = new Profile(null,null);
                    sqlSimulator.add_profile(playgroundOwner.getId(),profile);

                    System.out.println("done");
                } catch (SQLException throwables) {
                    System.out.println("user is already used");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }


        return true;
    }


    /**
     * playground owner interface
     * @param playgroundOwner for PlaygroundOwner class
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void user_menue(PlaygroundOwner playgroundOwner) throws SQLException, ClassNotFoundException {

        while (true) {
            System.out.println("1-> view playground");
            System.out.println("2->add playground");
            System.out.println("3->check Ewallet ('your money') ");
            System.out.println("4->my profile");
            System.out.println("5->my account");
            System.out.println("6->show message");
            System.out.println("7->set message or complaint");
            System.out.println("8->logout");


            Scanner scanner = new Scanner(System.in);
            char choice = scanner.next().charAt(0);
            if (choice <'1'&&choice>'8'){
                System.out.println("enter a valid choice !!!!");
                continue;
            }


            if(choice == '1')
            playgroundOwner.view_booking();

            //add new playground ?
            else if(choice == '2'){
                System.out.println("playground name : ");

                scanner.nextLine();
                String name = scanner.nextLine();

                System.out.println("playground address");
                String address = scanner.nextLine();

                System.out.println("GPS : ");
                String GPS = scanner.nextLine();

                Location location = new Location(address,GPS);

                System.out.println("playground size : (large , medium , small) ");
                String size  = scanner.next();
                size = size.toLowerCase();
                while (!size.equalsIgnoreCase("large")&&!size.equalsIgnoreCase("medium")&&!size.equalsIgnoreCase("small"))
                {
                    System.out.println("playground size : (large , medium , small) ");
                    size  = scanner.next();
                    size = size.toLowerCase();
                }

                System.out.println("cancelation period : (ex : '24','48','12')");
                scanner.nextLine();
                System.out.print("in :");
                double cancelation_period = scanner.nextDouble();

                System.out.print("price per hour : ");
                double price = scanner.nextDouble();

                System.out.println("start and end of playground : (ex : from 1 to 12 or from 14 to 20)");

                System.out.println("from : ");
                int start = scanner.nextInt();

                System.out.println("to :");
                int end = scanner.nextInt();


                try {
                    ResultSet resultSet =  sqlSimulator.set_query("select playground_id from Playground where playground_id =(select max(playground_id) from Playground) ");
                    int id = 0;
                    if(resultSet.next()){
                        id = resultSet.getInt("playground_id");
                    }
                   id++;

                    Playground playground = new Playground(id,name,location,size,price,cancelation_period,start,end);

                    if(playgroundOwner.add_playground(playground)){
                        sqlSimulator.add_playground(playground,playgroundOwner.getId());
                        System.out.println("Done");
                    }
                    else{
                        System.out.println("adding failed !");
                        System.out.println("you already has this playground !!!!");
                    }


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

            else if(choice == '3'){

                check_my_ewallet(playgroundOwner);
            }

            else if(choice=='4'){
                my_profile(playgroundOwner);
            }

            else if(choice == '5'){
                playgroundOwner.print_account_info();
            }

            else if(choice == '6'){
                playgroundOwner.show_my_massage();
            }
            else if(choice == '7'){
                send_message(playgroundOwner);
            }

            else{
                System.exit(0);
            }

        }
    }


    /**
     * player interface
     * @param player for Plaayer class
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void user_menue(Player player) throws SQLException, ClassNotFoundException {

        while (true) {
            System.out.println("1->view playground");
            System.out.println("2->book playground");
            System.out.println("3->my bookings");
            System.out.println("4->check Ewallet ('your money') ");
            System.out.println("5->my profile");
            System.out.println("6->my account");
            System.out.println("7->show message");
            System.out.println("8->set message or complaint");
            System.out.println("9->logout");
            Scanner scanner = new Scanner(System.in);
            char choice = scanner.next().charAt(0);
            while (choice < '1' && choice > '9') {
                System.out.println("1->view playground");
                System.out.println("2->book playground");
                System.out.println("3->my bookings");
                System.out.println("3->my bookings");
                System.out.println("4->check Ewallet ('your money') ");
                System.out.println("5->my profile");
                System.out.println("6->my account");
                System.out.println("7->logout");
                choice = scanner.next().charAt(0);
            }

            if (choice == '1') {

                view_playground(player);
            }

            else if(choice == '2'){
                book_play_ground(player);
            }

            else if(choice == '3'){
                player.show_my_booking();
            }

            else if(choice == '4'){
                check_my_ewallet(player);
            }

            else if(choice == '5'){
                my_profile(player);
            }

            else if(choice == '6'){
                player.print_account_info();
            }

            else if(choice == '7'){
                player.show_my_massage();
            }
            else if(choice == '8'){

                send_message(player);
            }

            else {
                System.exit(0);
            }

        }



    }


    /**
     * show all operation on Ewallet
     * @param user for any User
     */
    private static void check_my_ewallet(User user){
        Scanner scanner = new Scanner(System.in);
        while (true){
            user.getEwallet().refresh(user.getId());
            System.out.println("you have : "+user.getEwallet().getMoney()+"$");

            System.out.println("going to the nearest fawry System to got your money by your id");
            System.out.println("1->refresh my ewallet");
            System.out.println("2->transimit money");
            System.out.println("3->close");
            char c = scanner.next().charAt(0);
            while (c<'1'&&c>3){
                System.out.println("1->refresh my ewallet");
                System.out.println("2->transimit money");
                System.out.println("3->close");
                c= scanner.next().charAt(0);
            }

            if(c=='1'){
                user.getEwallet().refresh(user.getId());

            }

            if(c=='2'){

                System.out.print("enter the receiver id : ");

                int id = scanner.nextInt();
                System.out.print("how much money you need to transmit : ");
                double money = scanner.nextDouble();
                if(user.getEwallet().transimision(user.getId(),id,money)){
                    System.out.println("transimition successed ");
                }
                else {
                    System.out.println("no such id in System or you have not enough money !!!");
                }

            }

            else if(c == '3'){
                break;
            }

        }

    }


    /**
     * create profile or show profile info
     * @param user for any user
     */
    private static void my_profile(User user){

        Scanner scanner = new Scanner(System.in);
        if(user.getProfile().getAddress() == null&&user.getProfile().getMobile()==null) {
            System.out.println("you have not profile yet");
            System.out.println("create a new profile");
            System.out.println("enter your address");
            String address = scanner.nextLine();

            while (true) {
                System.out.println("your mobile phone :");
                String mobile = scanner.next();

                if(mobile.length()!=11){
                    System.out.println("enter a valid phone ");
                    continue;
                }


                try {
                    ResultSet resultSet = sqlSimulator.set_query("select phone from Profile where phone = '" + mobile + "';");
                    if (resultSet.next()) {
                        System.out.println("mobile already used");
                        continue;
                    }
                    else {
                        Profile profile = new Profile(mobile,address);
                        user.setProfile(profile);
                        sqlSimulator.add_profile(user.getId(),profile);
                        System.out.println("Done");
                        break;
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        else {
            System.out.println("phone : "+user.getProfile().getMobile());
            System.out.println("address : "+user.getProfile().getAddress());
        }


    }


    /**
     * display what mode of viewing
     * @param player for Player
     */
    private static void view_playground(Player player){

        while (true) {
            try {

                System.out.println("1->show all playground ");
                System.out.println("2->filter time slot");
                System.out.println("3->filter by area");
                System.out.println("4->filter by area and time slot");
                System.out.println("5->close");
                Scanner scanner = new Scanner(System.in);
                char choice = scanner.next().charAt(0);
                while (choice < '1' && choice > '5') {

                    System.out.println("1->show all playground ");
                    System.out.println("2->filter by time slot");
                    System.out.println("3->filter by area");
                    System.out.println("4->filter by area");
                    System.out.println("5->close");
                    choice = scanner.next().charAt(0);
                }

                if (choice == '1') {
                    player.view_playgrounds();

                }
                if(choice == '2'){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    int year,month,day;

                    System.out.print("year : ");
                    year = scanner.nextInt();
                    System.out.print("month : ");
                    month = scanner.nextInt();
                    System.out.print("day : ");
                    day = scanner.nextInt();

                    String date =Integer.toString(year)+'-'+Integer.toString(month)+'-'+Integer.toString(day);

                    Date date_format = format.parse(date);

                    date = format.format(date_format);
                    System.out.println("hour : (24 hour clock)");
                    int hour = scanner.nextInt();
                    player.view_playgrounds(date,hour);

                }
                if(choice == '3'){
                    System.out.println("area : ");
                    scanner.nextLine();
                    String area = scanner.nextLine();
                    player.view_playgrounds(area);
                }

                if(choice == '4'){
                    // get area
                    System.out.println("area : ");
                    scanner.nextLine();
                    String area = scanner.nextLine();

                    //get time slot
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    int year,month,day;

                    System.out.print("year : ");
                    year = scanner.nextInt();
                    System.out.print("month : ");
                    month = scanner.nextInt();
                    System.out.print("day : ");
                    day = scanner.nextInt();

                    String date =Integer.toString(year)+'-'+Integer.toString(month)+'-'+Integer.toString(day);

                    Date date_format = format.parse(date);

                    date = format.format(date_format);
                    System.out.println("hour : (24 hour clock)");
                    int hour = scanner.nextInt();

                    player.view_playgrounds(area,date,hour);

                }

                else if(choice == '5'){
                    break;
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * when and where and how much
     * @param player for Player
     */
    private static void book_play_ground(Player player){
        Scanner scanner = new Scanner(System.in);
        System.out.print("playground id : ");
        int playground_id = scanner.nextInt();

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-mm-dd");
        int year,month,day;

        System.out.print("year : ");
        year = scanner.nextInt();
        System.out.print("month : ");
        month = scanner.nextInt();
        System.out.print("day : ");
        day = scanner.nextInt();

        String date =Integer.toString(year)+'-'+Integer.toString(month)+'-'+Integer.toString(day);
        System.out.print("hour : ");
        double hour = scanner.nextDouble();

        try {
            Date date_format = simpleDateFormat.parse(date);
            date = simpleDateFormat.format(date_format);
            player.book_playground(playground_id,date,hour);


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    /**
     * to whom ?
     * @param user
     */
    private static void send_message(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter receiver id  : ");
        int id = scanner.nextInt();
        try {
            ResultSet resultSet = sqlSimulator.set_query("select id from User where id = '"+id+"';");
            if(resultSet.next()){

                System.out.println("enter message : ");

                scanner.nextLine();
                String message = scanner.nextLine();

                LocalDate localDate = LocalDate.now();

                String date = localDate.toString();

                sqlSimulator.add_message(new Mesasge(user.getId(),id,message,date));

                System.out.println("Done");


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
