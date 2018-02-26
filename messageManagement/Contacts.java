package messageManagement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;


/**
 * @file Contacts.java
 * @author Sion Rees
 * @date 11 Dec 2016
 *
 * Class that handles all of the database requests to do with a contact list.
 */
public class Contacts {
    public static class ContactInfo{

        /**
         *  Collects and array of all the profiles that a user is freinds with
         */
        public static ArrayList<Profile> getContactList(Integer proId){
            ArrayList<Profile> contactList= new ArrayList<>();
            try{
                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet rslt = selectStmt.executeQuery("select profileEdge from contacts where profileVertex="+proId+";");
                while(rslt.next()){
                    Profile pro = DBMngr.ManageDB.getProfile(rslt.getInt("profileEdge"));
                    contactList.add(pro);
                }
                selectStmt.close();
            }catch (Exception exc){
                exc.printStackTrace();
            }
            return contactList;
        }

        /**
         * Orders an array of profiles by their last name.
         * @param proArray
         * @return
         */
        private static ArrayList<Profile> orderMess(ArrayList<Profile> proArray) {
            Collections.sort(proArray, (Profile p1, Profile p2) -> p1.getM_lastName().compareTo(p2.getM_lastName()));
            return proArray;
        }

        /**
         * add new contacts to contact list
         * @param user1
         * @param user2
         */
        public static void addContact(Integer user1, Integer user2){

            try{
                String pstmt = "insert into contacts (profileVertex, profileEdge) values (?,?)";
                PreparedStatement statement = ConnectionSetup.Conn.getConection().prepareStatement(pstmt);
                statement.setInt(1,user1);
                statement.setInt(2,user2);
                statement.executeUpdate();
                statement.close();
            }catch (Exception exc){
                exc.printStackTrace();
            }
        }

        /**
         * CHECKS FOR THE PRESENCE OF RELEVENT CONTACT REQUESTS
         * @param userId
         * @return
         */
        public static boolean isContactR(Integer userId){
            boolean pendingR = false;
            try{
                ;
                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet rslt = selectStmt.executeQuery("select * from pendingContactRequest where reqTo="+userId+";");
                if(rslt.next()){
                    pendingR = true;
                }
                selectStmt.close();
            }catch(Exception exc){
                exc.printStackTrace();
            }
            return pendingR;
        }

        /**
         * RETURNS AN ARRAY OF PROFILES WHAT WANT TO ADD THE CURRENT USER
         * @param userId
         * @return
         */
        public static ArrayList<Profile> getContReq(Integer userId){
            ArrayList<Profile> newContacts = new ArrayList<>();
            try{

                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet rslt = selectStmt.executeQuery("select * from pendingContactRequest where reqTo="+userId+";");
                while(rslt.next()){
                    newContacts.add(DBMngr.ManageDB.getProfile(rslt.getInt("reqFrom")));
                }
                selectStmt.close();
            }catch(Exception exc){
                exc.printStackTrace();
            }
            return newContacts;
        }

        /**
         * Used to delete contact requests after they have either been accepted or declined
         * @param sender
         * @param reciver
         */
        public static void deleteContactReq(Integer sender, Integer reciver){
            try{
                String query = "delete from pendingContactRequest where reqFrom = ? AND reqTo= ?";
                PreparedStatement preparedStmt = ConnectionSetup.Conn.getConection().prepareStatement(query);
                preparedStmt.setInt(1, sender);
                preparedStmt.setInt(2, reciver);
                preparedStmt.executeUpdate();
                preparedStmt.close();
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }

        /**
         * Used to add a contact request to the database
         * @param sender
         * @param reciver
         */
        public static void addContactReq(Integer sender, Integer reciver){
            try{
                String pstmt = "insert into pendingContactRequest (reqTo,reqFrom) values (?,?)";
                PreparedStatement statment = ConnectionSetup.Conn.getConection().prepareStatement(pstmt);
                statment.setInt(1, reciver);
                statment.setInt(2, sender);
                statment.executeUpdate();
                statment.close();
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
//End of ContactInfo
    }
}

