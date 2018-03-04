package droppod.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;

public class UserDao {
	public static boolean add(String name, String pass, String email) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rs = null;
		int success = 0;
		String uuid = null;
		try {
			Context envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
			conn = ds.getConnection();

			pst = conn.prepareStatement(
					"insert into droppod.users" + "(username,password,email,validated) values" + "(?,?,?,?)");
			pst.setString(1, name);
			pst.setString(2, pass);
			pst.setString(3, email);
			pst.setInt(4, 0);
			success = pst.executeUpdate();

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
			if (pst2 != null) {
				try {
					pst2.close();
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
		if (success != 0) {
			return true;
		} else {
			return false;
		}
	}
	
    public static String getUuid(String name) {        
        boolean status = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String uuid = null;
        try {

        	Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");
            conn = ds.getConnection();
                         
            pst = conn
            		.prepareStatement("select * from droppod.users where username=?");
            pst.setString(1, name);
            rs = pst.executeQuery();
            status = rs.next();
            uuid = rs.getString("uuid");

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
        return uuid;
    }
    public static boolean verifyUser(String uuid) {
        boolean status = false;
        int updateStatus = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        PreparedStatement pst2 = null;
        ResultSet rs = null;
        try {

        	Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");
            conn = ds.getConnection();
                         
            pst = conn
            		.prepareStatement("select * from droppod.users where uuid=?");
            pst.setString(1, uuid);
            rs = pst.executeQuery();
            status = rs.next();
            int id = rs.getInt("id");
            if(status) {
            	pst2 = conn.prepareStatement("UPDATE droppod.users SET validated = 1 where id = ?");
            	pst2.setInt(1, id);
            	updateStatus = pst2.executeUpdate();
            	
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
            if (pst2 != null) {
                try {
                    pst2.close();
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
        if(updateStatus != 0) {
        	return true;
        }else {
        	return false;
        }
       }
}