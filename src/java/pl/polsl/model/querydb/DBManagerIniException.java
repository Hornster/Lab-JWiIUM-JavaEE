/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.model.querydb;

/**Exception that is thrown when initialization of database manager goes wrong
 * and the manager is not able to deal with it.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0*/
public class DBManagerIniException extends Throwable{
    public DBManagerIniException(String msg)
    {
        super(msg);
    }
}
