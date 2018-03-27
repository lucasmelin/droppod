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
              // TODO: Null Pointer exception if not set
            	System.out.println(rs.getString("active").compareTo("1"));
            	if (rs.getString("active").compareTo("0") == 0) {
	            	if (BCrypt.checkpw(pass, rs.getString("password"))) {
	            		status = true;
	            	}
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
    
    public static int verifyAccess(String name) {        
        int userStatus = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {

        	Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");
            conn = ds.getConnection();
                         
            pst = conn
            		.prepareStatement("select * from droppod.users where username=?");
            pst.setString(1, name);
            rs = pst.executeQuery();
            if(rs.next()){
            userStatus = rs.getInt("account_type_id"); //getInt returns 0 if value is NULL no need for additional testing
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
        return userStatus;
    }
	
	public static String[] addUuidSession(String n) {
   	 Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String[] podcastIDs = null;
        
        try {
        	//String n=request.getParameter("username");  
       	 //HttpSession session = request.getSession(false);
       	 Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");          
            con = ds.getConnection();
            
           /* Get podcast_ids by using the username */
            pst = con.prepareStatement("SELECT uuid FROM droppod.podcasts WHERE id IN (SELECT podcast_id FROM droppod.subscriptions WHERE user_id IN (SELECT id FROM droppod.users WHERE username=?))");
            pst.setString(1, n);
            
            rs = pst.executeQuery();
            //rs.next();
            rs.last();
            int rowCount = rs.getRow();
            podcastIDs = new String[rowCount];
            rs.first();
            //rs.next();
            for (int i = 0; i < rowCount; i++) {
            	podcastIDs[i] = rs.getString(1);
            	rs.next();
            }
            
            for (int i = 0; i < podcastIDs.length; i++) {
            	System.out.println(podcastIDs[i]);
            }
            
        } catch (Exception e) {
        	System.out.println("Exception: " + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.close();
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
        }
        return podcastIDs;
   }

}
