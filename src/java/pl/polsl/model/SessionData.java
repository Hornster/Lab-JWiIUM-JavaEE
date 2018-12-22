/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.model;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpSession;

import java.util.Map;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/** Stores data about sessions that are currently on.
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0
 */
public class SessionData implements HttpSessionBindingListener {
    /**
     * Indicator for this class' instance used during binding it to sessions.
     */
    private final String bindingName = "QueryList";
    /**
     * Stores indexes of queries made by given sessions.
     */
    Map<String, List<Integer>> sessionQueries = new HashMap<>();
    
    /**
     * Returns list of queries indexes made by the session.
     * @param sessionID ID of the session requesting its query history.
     * @return List of query indexes made by session. List is empty if none have been made yet.
     */
    public List<Integer> getSessionQueries(String sessionID)
    {
        List<Integer> queriesIndexes = sessionQueries.get(sessionID);
        if(queriesIndexes == null)
        {
            queriesIndexes = new LinkedList<>();
        }
        return  queriesIndexes;
    }
    /**
     * Adds another query to (or and) session to the session queries map.
     * @param sessionID ID to which new query shall be assigned to.
     * @param queryIndex Index of the query being assigned to sessionID.
     */
    public void addQueryToSession(String sessionID, Integer queryIndex)
    {
        
        if(sessionQueries.containsKey(sessionID))
        {
            sessionQueries.get(sessionID).add(queryIndex);
        }
        else
        {
            List<Integer> newValues = new LinkedList<>();
            newValues.add(queryIndex);
            sessionQueries.put(sessionID, newValues);
        }
        
    }
    /**
     * Checks if this class was already bound to provided session. If no - binds it and sets the max inactive interval.
     * @param session Session to check.
     */
    public void ChkIfSessionBound(HttpSession session)
    {
        if(session.getAttribute(bindingName) == null)
        {
            session.setMaxInactiveInterval(600);        //Every connection will time out after 10 minutes
            session.setAttribute(bindingName, this);
        }
    }
    /**
     * Called when new session is created. Adds the new session ID to the sessionQueries.
     * @param event Event object containing data about valueBound event.
     */
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        sessionQueries.put(event.getSession().getId(), new LinkedList<>());
    }
    /**
     * Called when session is closed. Removes the session ID from the sessionQueries.
     * @param event Event containing sessionID of recently closed session.
     */
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        String removedSessionID = event.getSession().getId();
        
        sessionQueries.remove(removedSessionID);
    }
}
