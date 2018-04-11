package droppod.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDao {
	public static boolean add(String name, String pass, String email, String city, String country) {
		Connection conn = null;
		Connection conn3 = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;

		ResultSet rs = null;
		ResultSet rs3 = null;

		int success = 0;
		String uuid = null;
		try {
			Context envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
			conn3 = ds.getConnection();
			
			pst3 = conn3.prepareStatement("SELECT * from droppod.cities where cities.name=?");
			pst3.setString(1, city);
			rs3 = pst3.executeQuery();
			if (!rs3.next() ) {
				pst3 = conn3.prepareStatement("INSERT INTO droppod.cities(name)" + 
						"VALUES (?);");
				pst3.setString(1, city);
				pst3.executeUpdate();
			}
			
		}catch(Exception e){
			System.out.println(e);

		}
		
		try {
			Context envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
			conn = ds.getConnection();
		 
		

			//the updated insert gets respective city and country ids from what the user entered on sign up. We then
			//add those ids to the insert statement
			pst = conn.prepareStatement("INSERT INTO droppod.users (username,password,email,city_id,country_id,validated,active) " + 
					"SELECT ?, ?, ?, c.id, co.id, ?, ? " + 
					"from droppod.cities as c, droppod.countries as co " + 
					"WHERE c.name=? " + 
					"and co.name=?");
			pst.setString(1, name);
			pst.setString(2, pass);
			pst.setString(3, email);
			pst.setString(6, city);
			pst.setString(7, country);
			pst.setInt(4, 0); // Validated
			pst.setInt(5, 0); // Active
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
    
    
    public static boolean changePassword(String uuid, String pass) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rs = null;
		int success = 0;
		try {
			Context envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
			conn = ds.getConnection();

			pst = conn.prepareStatement(
					"update into droppod.users" + "(password) values" + "(?)");
			pst.setString(1, uuid);
			pst.setString(2, pass);
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
}