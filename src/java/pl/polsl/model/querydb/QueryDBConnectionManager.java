/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.model.querydb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**Manages connection with the Queries Database.
 *
 * @author Karol KozuchGroup 4 Section 8
 * @version 1.0*/
public class QueryDBConnectionManager {
    /**
     * Stores database url
     */
    private String url;
    /**
     * Stores database username
     */
    private String username;
    /**
     * Stores database password
     */
    private String password;
    /**
     * Driver used by the database.
     */
    private String driver;
    /**
     * Connection to the database
     */
    private Connection dbConnection;
    
    /**
     * Loads connection ata from a web.xml file.
     * @return TRUE if data has been loaded correctly. FALSE otherwise.
     */
    private boolean loadConnectionData()
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("queryDBConfig.xml");
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            url = (String)xpath.compile("//web-app//jdbc//url").evaluate(document, XPathConstants.STRING);;
            username = (String) xpath.compile("//web-app//jdbc//username").evaluate(document, XPathConstants.STRING);
            password = (String) xpath.compile("//web-app//jdbc//password").evaluate(document, XPathConstants.STRING);
            driver = (String) xpath.compile("//web-app//jdbc//driver").evaluate(document, XPathConstants.STRING);
        }
        catch(XPathExpressionException ex)
        {
            System.out.println("Could not load connection data from web.xml. Reason: \n"+ ex.getMessage());
            return false;
        }
        catch(SAXException ex)    
        {
            System.out.println("Could not parse web.xml. Reason: \n"+ ex.getMessage());
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
    }
   
    /**
     * Initializes connection with the database.
     * @return Returns TRUE if connection was established. FALSE otherwise.
     */
    public boolean iniConnection()
    {
        try {
            // loading the JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Could not load the database driver. Reason: \n" + cnfe.getMessage());
            return false;
        }
        
        if(!loadConnectionData())
        {
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
}
