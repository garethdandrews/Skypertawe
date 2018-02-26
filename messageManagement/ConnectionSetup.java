package messageManagement;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by sion_rees on 11/12/2016.
 */
public class ConnectionSetup {
    public static class Conn{
        //SETS UP CONECTION TO DB
        public static Connection getConection(){
            Connection myConn= null;
            try{
                myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/skypatawe", "root", "");
            }catch (Exception exc){
                exc.printStackTrace();
            }
            return myConn;
        }
    }
}
