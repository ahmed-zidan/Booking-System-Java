package Booking;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * this class connect booking system with payment system by using sockets
 *
 * @author zidan
 */

public class Ewallet {


    private double money;

    /**
     * initial money from payment system
     * @param id for client id
     */
    public Ewallet(int id){

        refresh(id);
    }


    /**
     * get money from payment system
     * @return money
     */
    public double getMoney() {

        //setMoney(id);
        return money;
    }


    /**
     * refresh money to get new result on money
     * @param id for client id
     */
    public void refresh(int id){
        try {


            Socket sc = new Socket("localhost",1232);
            PrintStream client_say = new PrintStream(sc.getOutputStream());
            Scanner server_say = new Scanner(sc.getInputStream());

            client_say.println(0);
            client_say.println(id);

            money = server_say.nextDouble();

            sc.close();
        } catch (IOException e) {
            System.out.println("Connection error");
        }


    }

    /**
     * transimit money from user to user
     * @param sender_id from ?
     * @param reciever_id to ?
     * @param money_amount how much ?
     * @return true or false
     */
    public boolean transimision(int sender_id,int reciever_id,double money_amount){

        try {

            Socket sc = new Socket("localhost",1232);
            PrintStream client_say = new PrintStream(sc.getOutputStream());
            Scanner server_say = new Scanner(sc.getInputStream());

            //tell server the number of process should be done
            client_say.println(4);

            //sender account
            client_say.println(sender_id);
            //reciever account
            client_say.println(reciever_id);


            //server found ids or not
            int done_or_not = server_say.nextInt();


            if(done_or_not == 1) {
                client_say.println(money_amount);

                //money is enough or not
                done_or_not = server_say.nextInt();


                if(done_or_not == 1){

                   //money = server_say.nextDouble();

                    return true;

                }

                else {
                    return false;
                }
            }
            else{
                return false;
            }

        } catch (IOException e) {
            System.out.println("connection error");
        return false;
        }
    }





}
