package messageManagement;

/**
 * Created by sion_rees on 29/11/2016.
 */

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @file MailBox.java
 * @author Sion Rees
 * @date 09 Dec 2016
 *
 * Class that handles all of the database requests to do with a message.
 */
public class MailBox{

    public static class MailMan{

        /**
         * SELECTS MESSAGES FROM USER A TO B AND B TO A
         * @param sender
         * @param reciver
         * @return
         */
        public static ArrayList<Message> getMessages(Integer sender, Integer reciver) {
            ArrayList<Message> prevMess = new ArrayList<>();
            try {
                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet rslt = selectStmt.executeQuery("select * from messages where senderId =" +sender+ " AND reciverId=" +reciver+ ";");
                while (rslt.next()) {
                    //IF TEXT MESSAGE
                    if(rslt.getString("messageType").equals("<text>")){
                        Message loadMess = new Message(rslt.getString("messageBlock")
                                , rslt.getString("messageType"), rslt.getInt("senderId"), rslt.getInt("reciverId")
                                , rslt.getString("timeOfMessage"), rslt.getString("senderName"), rslt.getInt("messageId"));
                        prevMess.add(loadMess);
                        //IF URL message
                    }else if (rslt.getString("messageType").equals("<url>")){
                        URLMessage loadMess = new URLMessage(rslt.getString("messageBlock")
                                , rslt.getString("messageType"), rslt.getInt("senderId"), rslt.getInt("reciverId")
                                , rslt.getString("timeOfMessage"), rslt.getString("senderName"), rslt.getInt("messageId"), rslt.getString("url"));
                        prevMess.add(loadMess);
                        //if file message
                    }else if(rslt.getString("messageType").equals("<file>")){
                        FileMessage loadMess = new FileMessage(rslt.getString("messageBlock")
                                , rslt.getString("messageType"), rslt.getInt("senderId"), rslt.getInt("reciverId")
                                , rslt.getString("timeOfMessage"), rslt.getString("senderName"), rslt.getInt("messageId"), rslt.getInt("textFileId"));
                        getFile(rslt.getInt("textFileId"));
                        prevMess.add(loadMess);
                    }
                }

                selectStmt.close();

                Statement selectStmt2 = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet rslt2 = selectStmt2.executeQuery("select * from messages where senderId =" +reciver+ " AND reciverId=" +sender+ ";");
                while (rslt2.next()) {
                    //IF TEXT MESSAGE
                    if(rslt2.getString("messageType").equals("<text>")){
                        Message loadMess = new Message(rslt2.getString("messageBlock")
                                , rslt2.getString("messageType"), rslt2.getInt("senderId"), rslt2.getInt("reciverId")
                                , rslt2.getString("timeOfMessage"), rslt2.getString("senderName"), rslt2.getInt("messageId"));
                        prevMess.add(loadMess);
                        //IF URL message
                    }else if (rslt2.getString("messageType").equals("<url>")){
                        URLMessage loadMess = new URLMessage(rslt2.getString("messageBlock")
                                , rslt2.getString("messageType"), rslt2.getInt("senderId"), rslt2.getInt("reciverId")
                                , rslt2.getString("timeOfMessage"), rslt2.getString("senderName"), rslt2.getInt("messageId"), rslt2.getString("url"));
                        prevMess.add(loadMess);
                        //if file message
                    }else if(rslt2.getString("messageType").equals("<file>")){
                        FileMessage loadMess = new FileMessage(rslt2.getString("messageBlock")
                                , rslt2.getString("messageType"), rslt2.getInt("senderId"), rslt2.getInt("reciverId")
                                , rslt2.getString("timeOfMessage"), rslt2.getString("senderName"), rslt2.getInt("messageId"), rslt2.getInt("textFileId"));
                        getFile(rslt2.getInt("textFileId"));
                        prevMess.add(loadMess);
                    }
                }
                selectStmt2.close();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            return orderMess(prevMess);
        }

        /**
         * ORDERS AN ARRAY OF MESSAGES BY DATE AND TIME
         * @param messArray
         * @return
         */
        private static ArrayList<Message> orderMess(ArrayList<Message> messArray) {
            Collections.sort(messArray, (Message m1, Message m2) -> m1.getDateOfMessage().compareTo(m2.getDateOfMessage()));
            return messArray;
        }

        /**
         * Returns an ordered by time, array list of relevant multi user messages
         * @param multiKey
         * @return
         */
        public static ArrayList<Message> getMultiMessage(String multiKey){
            ArrayList<Message> multiMess = new ArrayList<>();
            try{
                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet rslt = selectStmt.executeQuery("Select * from messages where messageType = <multi> AND multiKey="+multiKey+";");
                while(rslt.next()){
                    MultiMessage mess = new MultiMessage(rslt.getString("messageBlock"), rslt.getInt("senderId"), rslt.getInt("reciverId"), rslt.getString("timeOfMessage")
                            , rslt.getString("senderName"), rslt.getInt("messageId"), rslt.getString("multiKey"));
                    multiMess.add(mess);
                }
                selectStmt.close();
            }catch (Exception exc){
                exc.printStackTrace();
            }
            return orderMess(multiMess);
        }

        /**
         * Inserts multi messages
         * @param message
         */
        public static void sendToSave(MultiMessage message){
            Integer senderId = message.getSender();
            int messageId = message.getMessageId();
            String messageBlock = message.getMessageBlock();
            String messageType = message.getMessageType();
            Integer reciver = message.getRecipient();
            String dateTime = message.getDateOfMessage();
            String username = message.getSenderName();
            String multiKey = message.getM_multiKey();
            try{
                String pstmt = "insert into messages (messageId, messageBlock, messageType, senderId, timeOfMessage, senderName, multiKey) values (?,?,?,?,?,?,?)";
                PreparedStatement statement = ConnectionSetup.Conn.getConection().prepareStatement(pstmt);
                statement.setInt(1,messageId);
                statement.setString(2,messageBlock);
                statement.setString(3, messageType);
                statement.setInt(4, senderId);
                statement.setString(5, dateTime);
                statement.setString(6, username);
                statement.setString(7, multiKey);
                statement.close();
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }

        /**
         * Inserts messages into the database
         * @param message
         */
        public static void sendToSave(Message message){
            //Chop out data from message
            Integer senderId = message.getSender();
            int messageId = message.getMessageId();
            String messageBlock = message.getMessageBlock();
            String messageType = message.getMessageType();
            Integer reciver = message.getRecipient();
            String dateTime = message.getDateOfMessage();
            String username = message.getSenderName();
            try{
                String pstmt = "insert into messages (messageId, messageBlock, messageType, senderId, reciverId, timeOfMessage, senderName) values (?,?,?,?,?,?,?)";

                //Adds chopped out data into the insert statment, then executes
                PreparedStatement statement = ConnectionSetup.Conn.getConection().prepareStatement(pstmt);
                statement.setInt(1,messageId);
                statement.setString(2,messageBlock);
                statement.setString(3, messageType);
                statement.setInt(4, senderId);
                statement.setInt(5, reciver);
                statement.setString(6, dateTime);
                statement.setString(7, username);
                statement.executeUpdate();
                statement.close();
            }
            catch (Exception exc){
                exc.printStackTrace();
            }
        }

        /**
         * Inserts URL messages into the database
         * @param message
         */
        public static void sendToSave(URLMessage message){
            Integer senderId = message.getSender();
            int messageId = message.getMessageId();
            String messageBlock = message.getMessageBlock();
            String messageType = message.getMessageType();
            Integer reciver = message.getRecipient();
            String dateTime = message.getDateOfMessage();
            String username = message.getSenderName();
            String url = message.getURL();
            try{
                String pstmt = "insert into messages (messageId, messageBlock, messageType, senderId, reciverId, timeOfMessage, senderName, url) values (?,?,?,?,?,?,?,?);";
                //Adds chopped out data into the insert statment, then executes
                PreparedStatement statement = ConnectionSetup.Conn.getConection().prepareStatement(pstmt);
                statement.setInt(1,messageId);
                statement.setString(2,messageBlock);
                statement.setString(3, messageType);
                statement.setInt(4, senderId);
                statement.setInt(5, reciver);
                statement.setString(6, dateTime);
                statement.setString(7, username);
                statement.setString(8, url);
                statement.executeUpdate();
                statement.close();
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }

        /**
         * Inserts File messages into the database
         * @param message
         */
        public static void sendToSave(FileMessage message){
            Integer senderId = message.getSender();
            int messageId = message.getMessageId();
            String messageBlock = message.getMessageBlock();
            String messageType = message.getMessageType();
            Integer reciver = message.getRecipient();
            String dateTime = message.getDateOfMessage();
            String username = message.getSenderName();
            Integer textFileId = message.getM_textFileId();
            try{
                String pstmt = "insert into messages (messageId, messageBlock, messageType, senderId, reciverId, timeOfMessage, senderName, textFileId) values (?,?,?,?,?,?,?,?);";
                //Adds chopped out data into the insert statment, then executes
                PreparedStatement statement = ConnectionSetup.Conn.getConection().prepareStatement(pstmt);
                statement.setInt(1,messageId);
                statement.setString(2,messageBlock);
                statement.setString(3, messageType);
                statement.setInt(4, senderId);
                statement.setInt(5, reciver);
                statement.setString(6, dateTime);
                statement.setString(7, username);
                statement.setInt(8, textFileId);
                statement.executeUpdate();
                statement.close();
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }

        /**
         * inserts text file into db
         * @param tf
         */
        public static void insertTextFile(TextFile tf){
            Integer textFileId = tf.getM_textFileId();
            String fileName = tf.getM_title();
            String text = tf.getM_text();

            try{
                String pstmt = "insert into textFile (textFileId, fileName, text) values (?,?,?);";
                PreparedStatement statement = ConnectionSetup.Conn.getConection().prepareStatement(pstmt);
                statement.setInt(1, textFileId);
                statement.setString(2, fileName);
                statement.setString(3, text);
                statement.executeUpdate();
                statement.close();
            }catch (Exception exc){
                exc.printStackTrace();
            }
        }

        /**
         * gets text file from DB
         * @param textFileId
         */
        public static void getFile(Integer textFileId){
            String prefix = "C:\\Users\\rees_\\Desktop\\";
            String suffix = ".txt";
            try{
                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet rslt = selectStmt.executeQuery("select * from textFiles where textFileId="+textFileId+";");
                if(rslt.next()){
                    PrintWriter out = new PrintWriter(prefix+rslt.getString("fileName")+suffix);
                    out.print(rslt.getString("text"));
                    out.close();
                }
                selectStmt.close();
            }catch (Exception exc){
                exc.printStackTrace();
            }
        }

        /**
         * USED TO SET NEW MESSAGE ID
         * @return
         */
        public static Integer maxMessageId() {
            int max = 0;

            try {
                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet results = selectStmt.executeQuery("select max(messageId) from messages;");
                if (results.next()) {
                    max = results.getInt(1);
                }
                selectStmt.close();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            return max;
        }

        /**
         * Used to set new Text File ID
         * @return
         */
        public static Integer maxTextFileId() {
            int max = 0;

            try {
                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet results = selectStmt.executeQuery("select max(textFileId) from textFiles;");
                if (results.next()) {
                    max = results.getInt(1);
                }
                selectStmt.close();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            return max;
        }


        //End of mailMan
    }

}

