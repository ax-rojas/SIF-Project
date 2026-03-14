/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import gov.noaa.ngs.ldap.LdapAuthenticator;
import gov.noaa.ngs.ldap.LdapException;
import gov.noaa.ngs.db.DBUtil;
import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.util.Properties;


/**
 *
 * @author Ying.Jin
 */
public class DBconnection {

    //final String appName = "NSRSDB";
    final String appName = "NSRSDB_PG";
    public DBconnection() {
    }

    public Connection getOracleConn(LdapAuthenticator ldap) throws SQLException, LdapException, Exception {
        Connection conn = null;
        DBUtil db = null;

        //access connection property file
       InputStream inStream = DBconnection.class.getResourceAsStream("/DB/DBConnectionOracle.properties");
   
        if (inStream == null) {

            throw new Exception("DBUtil.setConnection: Unable to access conParms property file.");
        }
        Properties conProp = new Properties();

        try {
            conProp.load(inStream);
            // instantiate DBUtil for the given db environment
            db = new DBUtil(conProp);

            db.setAppContext(ldap, appName);

            conn = db.getConnection();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            inStream.close();
        }
        return conn;
    }
    
    public Connection getPGConn(LdapAuthenticator ldap) throws SQLException, LdapException, Exception {
        Connection conn = null;
        DBUtil db = null;

        //access connection property file
       InputStream inStream = DBconnection.class.getResourceAsStream("/DB/DBConnectionPostgres.properties");
        if (inStream == null) {

            throw new Exception("DBUtil.setConnection: Unable to access conParms property file.");
        }
        Properties conProp = new Properties();

        try {
            conProp.load(inStream);
            // instantiate DBUtil for the given db environment
            db = new DBUtil(conProp);

            db.setAppContext(ldap, appName);

            conn = db.getConnection();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            inStream.close();
        }
        return conn;
    }

    public void CloseConnection(Connection conn) {
        try {

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(" Can not close the connection");
        } finally {
            conn = null;
        }
    }
}
