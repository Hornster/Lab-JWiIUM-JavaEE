/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.polsl.model.queryHistory.SingleQuery;

import java.util.LinkedList;
import java.util.List;

/**
 * Test for SessionData class (Model).
 * @author Karol Kozuch Group 4 Section 8
 * @version 1.0
 */
public class SessionDataTests {
    private SessionData sessionData = new SessionData();
    private String session1 = "session1";
    private String session2 = "session2";
    private String session3 = "session3";
    private List<Integer> session1Queries = new LinkedList<>();
    private List<Integer> session2Queries = new LinkedList<>();
    private List<Integer> session3Queries = new LinkedList<>();
    @Before
    public void prepareTests()
    {
        sessionData.addQueryToSession(session1, 1);
        session1Queries.add(1);
        sessionData.addQueryToSession(session1, 2);
        session1Queries.add(2);
        sessionData.addQueryToSession(session2, 3);
        session2Queries.add(3);
        sessionData.addQueryToSession(session1, 4);
        session1Queries.add(4);
        sessionData.addQueryToSession(session3, 5);
        session3Queries.add(5);
        sessionData.addQueryToSession(session2, 6);
        session2Queries.add(6);
    }
    private void testArgs(List<Integer> source, List<Integer> testedSource)
    {
        Assert.assertTrue(source.size() == testedSource.size());
        for(Integer i : source)
        {
            Assert.assertTrue(testedSource.contains(i));
        }
    }
    @Test
    public void testGetSessionQueries()
    {
        List<Integer> testedSession = sessionData.getSessionQueries(session1);
        testArgs(session1Queries, testedSession);
        
        testedSession = sessionData.getSessionQueries(session2);
        testArgs(session2Queries, testedSession);
        
        testedSession = sessionData.getSessionQueries(session3);
        testArgs(session3Queries, testedSession);
    }
}
