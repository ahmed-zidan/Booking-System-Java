package Booking;

import java.util.ArrayList;

/**
 * all operation needed for complete register process
 */

public class Register {

    /**
     * check if pass is strong or week
     * @param password for user password
     * @return true or false
     */
    public boolean password_is_strong(String password){

        //password should have lowercase , uppercase, sympols , numbers

        boolean has_number = false;
        boolean has_sympol = false;
        boolean has_uppercase_letter = false;
        boolean has_lowercase_letter = false;

        for(int i =0;i<password.length();i++){
            //is it sympol ?
            if(password.charAt(i)>=33&&password.charAt(i)<=47||password.charAt(i)==64){
                //System.out.println("1111");
                has_sympol = true;
            }
            //is it number ?
            else if(password.charAt(i)>=48&&password.charAt(i)<=57){
                //System.out.println("2222");
                has_number = true;
            }
            //is it uppercase letter ?
            else if(password.charAt(i)>=65&&password.charAt(i)<=90){
                //System.out.println("3333");
                has_uppercase_letter = true;
            }
            //is it lowercase letter
            else if(password.charAt(i) >= 97 && password.charAt(i)<=122){
                //System.out.println("4444");
                has_lowercase_letter = true;
            }
        else {
                //System.out.println("5555");
            return false;
            }
        }


        if(has_lowercase_letter == true && has_number == true &&has_sympol == true&&has_uppercase_letter==true){
            return true;
        }

        else {

            return false;
        }
    }


    /**
     * check email is valid or not
     * @param email for user email
     * @return true or false
     */
    public boolean email_is_valid(String email){

        if(email.contains("@")&&email.contains(".com")){
            return true;
        }
        else return false;
    }


}
