package access;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Melody on 2017/7/29.
 */
public class DBAccess {
    private static Connection conn=null;

    public static Connection getConnection(){
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/practice" ;
        String username = "root" ;
        String password = "123456" ;
        try{
                Class.forName(driver);
                conn= DriverManager.getConnection(url , username , password ) ;

        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection con){
        if(conn!=null)
        {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
