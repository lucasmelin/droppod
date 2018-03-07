package droppod.login;

import droppod.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginDroppod {
	final static Logger logger = LoggerFactory.getLogger(LoginDroppod.class);
    public static boolean validate(String name, String pass) {        
        boolean status = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int passwordColumn = 3;
        
        try {       	
        	Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");
            conn = ds.getConnection();
                         
            pst = conn
            		.prepareStatement("select * from droppod.users where username=?");
            pst.setString(1, name);
            rs = pst.executeQuery();
            if (rs.next()) { //True if username exists in database otherwise false.
            	if (BCrypt.checkpw(pass, rs.getString(passwordColumn))) {
            		status = true;
            	}
            }
            
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }
}
