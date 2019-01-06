/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.model.querydb;

import java.sql.Connection;

/**Manages classes that communicate with and form the query database.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0*/
public class QueryDBManager {
    /**
     * Instance of the singleton.
     */
    private static QueryDBManager instance;
    /**
     * Instance of table recreating object.
     */
    private final QueryDBCreator queryDBCreator = new QueryDBCreator();
    /**
     * Instance of the connection manager.
     */
    private final QueryDBConnectionManager queryDBConnManager = new QueryDBConnectionManager();
    /**
     * Instance of the query selector - object that retrieves data from the database.
     */
    private final QueryDBSelector queryDBSelector;
    /**
     * Instance of data inserting class.
     */
    private final QueryDBInserter queryDBInserter;
    /**
     * Default constructor - used to allow queryDBSelector and queryDBInserter references
     * to be final.
     */
    private QueryDBManager()
    {
        queryDBSelector = new QueryDBSelector();
        queryDBInserter = new QueryDBInserter();
    }
    /**
     * Initializes the components of this class.
     * @throws DBManagerIniException when could not establish connection with the databse.
     */
    private void iniComponents() throws DBManagerIniException
    {
        if(!queryDBConnManager.iniConnection())
        {
            throw new DBManagerIniException("Could not establish connection with the query database! " 
                    + queryDBConnManager.toString());
        }
        Connection dbConnection = queryDBConnManager.getDBConnection();
        queryDBCreator.setupDatabase(dbConnection);
    }
    /**
     * If the instance is not yet existent - creates it.
     * @return Instance of this class' singleton.
     * @throws DBManagerIniException when could not establish connection with the databse.
     */
    public static QueryDBManager getInstance() throws DBManagerIniException
    {
        if(instance == null)
        {
            instance = new QueryDBManager();
            try
            {
                instance.iniComponents();
            }
            catch(DBManagerIniException ex)
            {
                instance = null;            //Abort singleton initialization - something went terribly wrong.
                throw ex;
            }
        }
        return instance;
    }
    /**
     * Gets the selector object, allowing to perform SELECT operation on database.
     * @return Final reference to the queryDBSelector object. 
     */
    public QueryDBSelector getSelector()
    {
        return queryDBSelector;
    }
    /**
     * Gets the inserter object, allowing to insert data into the database.
     * @return Final reference to the queryDBInserter object. 
     */
    public QueryDBInserter getInserter()
    {
        return queryDBInserter;
    }
    /**
     * Gets the current connection to the database.
     * @return Object containing data about connection with the db.
     */
    public Connection getDBConnection()
    {
        return queryDBConnManager.getDBConnection();
    }
}
