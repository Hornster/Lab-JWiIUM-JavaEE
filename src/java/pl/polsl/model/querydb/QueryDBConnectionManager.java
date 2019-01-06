/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.model.querydb;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**Manages connection with the Queries Database.
 *
 * @author Karol KozuchGroup 4 Section 8
 * @version 1.0*/
@WebListener
public class QueryDBConnectionManager  implements ServletContextListener {
    /**
     * Stores database url
     */
    private static String url;
    /**
     * Stores database username
     */
    private static String username;
    /**
     * Stores database password
     */
    private static String password;
    /**
     * Driver used by the database.
     */
    private static String driver;
    /**
     * Connection to the database
     */
    private Connection dbConnection;
    
    /**
     * Loads connection ata from a web.xml file.
     * @return TRUE if data has been loaded correctly. FALSE otherwise.
     */
    /*private boolean loadConnectionData()
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("web" + File.separator + "WEB-INF" + File.separator + configFile);
            
            url = document.getElementsByTagName("url").item(0).getTextContent();
            username = document.getElementsByTagName("username").item(0).getTextContent();
            password = document.getElementsByTagName("password").item(0).getTextContent();
            driver = document.getElementsByTagName("driver").item(0).getTextContent();
        }
        catch(SAXException ex)    
        {
            System.out.println("Could not parse "+ configFile +" Reason: \n"+ ex.getMessage());
            return false;
        }
        catch(IOException ex)
        {
            System.out.println("Could not open web.xml. Reason: \n"+ ex.getMessage());
            return false;
        }
        catch(ParserConfigurationException ex)
        {
            System.out.println("Could not create parser instance. Reason: \n"+ ex.getMessage());
            return false;
        }
        return true;
    }*/
   
    /**
     * Initializes connection with the database.
     * @return Returns TRUE if connection was established. FALSE otherwise.
     */
    public boolean iniConnection()
    {
        try {
            // loading the JDBC driver
            Class.forName(driver);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Could not load the database driver. Reason: \n" + cnfe.getMessage());
            return false;
        }
        try
        {
            dbConnection = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException ex)
        {
            System.out.println("Could not establish connection with db. Reason: \n"
                    + ex.getMessage());
            return false;
        }
        
        return true;
    }
    /**
     * Returns connection to the database object. If iniConnection() was not called or
     * returned FALSE - this method will return NULL.
     * @return Objetc with db connection info.
     */
    public Connection getDBConnection()
    {
        return dbConnection;
    }
    /**
     * Initializes data required to establish connection with the database server. Reads from web.xml file.
     * @param sce Context through which the data is read from the web.xml file. Passed as event argument.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        
        String dbUrl = ctx.getInitParameter("dbUrl");
        String dbUsername = ctx.getInitParameter("dbUser");
        String dbPassword = ctx.getInitParameter("dbPassword");
        String dbDriver = ctx.getInitParameter("dbDriver");
        
        QueryDBConnectionManager.url = dbUrl;
    	QueryDBConnectionManager.username = dbUsername;
    	QueryDBConnectionManager.password = dbPassword;
        QueryDBConnectionManager.driver = dbDriver;
    }
    /**
     * Currently does nothing. Is here because of need of ServletContextListener interface implementation.
     * @param sce Argument passed when context is destroyed (event).
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //Nothing to do here, to be honest
    }
}
