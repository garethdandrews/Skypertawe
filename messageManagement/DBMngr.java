package messageManagement;

import collaberativeDrawingEnvironment.DrawingElement;
import collaberativeDrawingEnvironment.ParticleTrace;
import collaberativeDrawingEnvironment.StraightLine;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.*;
import java.util.Collections;
import java.util.Comparator;

/**
 * @file DBMngr.java
 * @author Sion Rees
 * @date 10 Dec 2016
 *
 * Class that handles all of the database requests to do with a profile.
 */
public class DBMngr {

    public static class ManageDB {

        /**
         * SELECTS PROFILE DATA, CONSTRUCTS PROFILE OBJECT AND RETURNS IT
         * @param id
         * @return
         */
        public static Profile getProfile(Integer id) {
            Profile pro = null;
            try {
                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet rslt = selectStmt.executeQuery("select * from profile where profileId =" + id + ";");
                while (rslt.next()) {
                    pro = new Profile(rslt.getInt("profileId"), rslt.getString("userName")
                            , rslt.getString("firstName"), rslt.getString("lastName")
                            , rslt.getString("phoneNumber"), rslt.getString("dob"), rslt.getString("city")
                            , rslt.getString("lastOnline"));
                }
                selectStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return pro;
        }

        /**
         * Return/search profile by username
         * @param username
         * @return
         */
        public static Profile checkProfile(String username) {
            Profile pro = null;
            try {
                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet rslt = selectStmt.executeQuery("select * from profile where userName ='" + username + "';");
                while (rslt.next()) {
                    pro = new Profile(rslt.getInt("profileId"), rslt.getString("userName")
                            , rslt.getString("firstName"), rslt.getString("lastName")
                            , rslt.getString("phoneNumber"), rslt.getString("dob"), rslt.getString("city")
                            , rslt.getString("lastOnline"));
                }
                selectStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return pro;
        }

        /**
         * INSERTS A PROFILE IN DB
         * @param userName
         * @param firstName
         * @param lastName
         * @param phoneNumber
         * @param dob
         * @param city
         * @param lastOnline
         * @return
         */
        public static Profile insertProfile(String userName, String firstName, String lastName
                , String phoneNumber, String dob, String city, String lastOnline){
            Integer maxProId = maxProfileId() + 1;
            try{
                String pstmt = "insert into profile (profileId, userName, firstName, lastName, phoneNumber," +
                        " city, lastOnline, dob) values (?,?,?,?,?,?,?,?)";
                PreparedStatement statement = ConnectionSetup.Conn.getConection().prepareStatement(pstmt);
                statement.setInt(1,maxProId);
                statement.setString(2,userName);
                statement.setString(3,firstName);
                statement.setString(4,lastName);
                statement.setString(5,phoneNumber);
                statement.setString(6,city);
                statement.setString(7,lastOnline);
                statement.setString(8,dob);
                statement.executeUpdate();
                statement.close();
            }catch(Exception exc){
                exc.printStackTrace();
            }
            return getProfile(maxProId);
        }

        /**
         * Returns the max profile id
         * @return
         */
        public static Integer maxProfileId() {
            int max = 0;
            try {
                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet results = selectStmt.executeQuery("select max(profileId) from profile;");
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
         * Inserts image to DB
         * @param image
         * @param profileId
         */
        public static void insertProfileImage(BufferedImage image, Integer profileId){
            try{
                String pstmt = "insert into profileImage (proId, image, imageName) values (?,?,?)";
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", os);
                InputStream is = new ByteArrayInputStream(os.toByteArray());

                PreparedStatement statement = ConnectionSetup.Conn.getConection().prepareStatement(pstmt);
                statement.setInt(1, profileId);
                statement.setBinaryStream(2, is);
                statement.setString(3, getProfile(profileId).getM_userName() + "'s profile image");
                statement.executeUpdate();
                statement.close();
            }catch(Exception exc) {
                exc.printStackTrace();
            }
        }

        /**
         * Gets the image out of the DB
         * @param profileId
         * @return
         */
        public static BufferedImage getProfileImage(Integer profileId){
            BufferedImage imBuff = null;
            try{
                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet results = selectStmt.executeQuery("select image from profileImage where proId="+profileId+";");
                if(results.next()){
                    Blob imageBlob = results.getBlob(1);
                    InputStream binaryStream = imageBlob.getBinaryStream(1, imageBlob.length());
                    imBuff = ImageIO.read(binaryStream);
                    return imBuff;
                }
                selectStmt.close();
            }catch(Exception exc){
                exc.printStackTrace();
            }
            return imBuff;
        }

        public static void drawLineToDB(StraightLine drawn){
            //COMPLETE ME
            Integer senderId = drawn.getSender();
            Integer reciverId = drawn.getReceiver();
            Integer startX = drawn.getStartX();
            Integer startY = drawn.getStartY();
            Integer endX = drawn.getEndX();
            Integer endY = drawn.getEndY();
            String type = drawn.getTYPE();
            try{
                String pstmt = "insert into draw (senderId, reciverId, startPointX, startPointY," +
                        "endPointX, endPointY, drawType) values (?,?,?,?,?,?,?)";
                PreparedStatement statment = ConnectionSetup.Conn.getConection().prepareStatement(pstmt);
                statment.setInt(1, senderId);
                statment.setInt(2, reciverId);
                statment.setInt(3, startX);
                statment.setInt(4, startY);
                statment.setInt(5, endX);
                statment.setInt(6, endY);
                statment.setString(7, type);
                statment.executeUpdate();
                statment.close();

            }catch(Exception exc){
                exc.printStackTrace();
            }
        }

        public static void drawPartiToDB(ParticleTrace drawn){
            //COMPLETE ME
            Integer senderId = drawn.getSender();
            Integer reciverId = drawn.getReceiver();
            Integer startX = drawn.getStartX();
            Integer startY = drawn.getStartY();
            Integer endX = drawn.getEndX();
            Integer endY = drawn.getEndY();
            Integer stroke = drawn.getM_strokeSize();
            String type = drawn.getTYPE();
            try{
                String pstmt = "insert into draw (senderId, reciverId, startPointX, startPointY," +
                        "endPointX, endPointY, stroke, drawType) values (?,?,?,?,?,?,?,?)";
                PreparedStatement statment = ConnectionSetup.Conn.getConection().prepareStatement(pstmt);
                statment.setInt(1, senderId);
                statment.setInt(2, reciverId);
                statment.setInt(3, startX);
                statment.setInt(4, startY);
                statment.setInt(5, endX);
                statment.setInt(6, endY);
                statment.setInt(7, stroke);
                statment.setString(8, type);
                statment.executeUpdate();
                statment.close();

            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
        public static ArrayList<DrawingElement> getDrawings(Integer sender, Integer reciver){
            ArrayList<DrawingElement> drawArray = new ArrayList<>();
            try{
                Statement selectStmt = ConnectionSetup.Conn.getConection().createStatement();
                ResultSet rslt = selectStmt.executeQuery("select * from draw where senderId="+sender+" AND reciverId="+reciver+";");
                while(rslt.next()){
                    if(rslt.getString("drawType") == "pt"){
                        Point startPoint = new Point(rslt.getInt("startPointX"), rslt.getInt("startPointY"));
                        Point endPoint = new Point(rslt.getInt("endPointX"), rslt.getInt("endPointY"));
                        ParticleTrace pt = new ParticleTrace(startPoint, endPoint, rslt.getInt("senderId"), rslt.getInt("reciverId"));
                        drawArray.add(pt);
                    }else if(rslt.getString("drawType")=="sl"){
                        Point startPoint = new Point(rslt.getInt("startPointX"), rslt.getInt("startPointY"));
                        Point endPoint = new Point(rslt.getInt("endPointX"), rslt.getInt("endPointY"));
                        StraightLine sl = new StraightLine(startPoint, endPoint, rslt.getInt("senderId"), rslt.getInt("reciverId"));
                        drawArray.add(sl);
                    }
                }
                rslt.close();
            }catch (Exception exc){
                exc.printStackTrace();
            }
            return drawArray;
        }

        /**
         * Sets the date and time that the user was last logged in
         * @param dateTime
         * @param user
         */
        public static void setLastOnline(String dateTime, Integer user){
            try{
                String pstmt = "UPDATE profile SET lastOnline=? WHERE profileId=?;";
                PreparedStatement statement = ConnectionSetup.Conn.getConection().prepareStatement(pstmt);
                statement.setString(1, dateTime);
                statement.setInt(2, user);
                statement.executeUpdate();
                statement.close();
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
        //end of ManageDB
    }
}
