package pl.polsl.model.queryHistory;

import pl.polsl.model.CalculationData;
import pl.polsl.model.IntegralData;
import pl.polsl.model.PredefinedCommunicates;
import pl.polsl.model.exceptions.NoQueryFoundException;

/**Manages the query history - responsible for creating, storing and searching for integral calculation query
 * data.
 * @author Kozuch Karol
 * @version 1.0.2*/
public class QueryManager implements CalcResultListener {
    /**Stores queries' data.*/
    private QueryHistory history = new QueryHistory();
    /**
     * Adds query to the history.
     * @param newQuery New query to add.
     * @return Index of the newly added query.
     */
    public int addQuery(SingleQuery newQuery) {
        return history.addItem(newQuery);
    }
    /**Returns query saved under given index.
     * @param index Index of requested query.
     * @return Query saved under passed index.
     * @throws pl.polsl.model.exceptions.NoQueryFoundException If tried to access non-existing query.*/
    public SingleQuery getQuery(int index)throws NoQueryFoundException {
        return history.getQuery(index);
    }
    /**Returns last performed query data.
     * @return Last saved query.
     * @throws pl.polsl.model.exceptions.NoQueryFoundException If tried to access non-existing query.*/
    public SingleQuery getLastQuery()throws NoQueryFoundException
    {
        return history.getQuery();
    }
    /**Gets the query storing object, for ease of iteration through.
     * @return Object that stores all queries.*/
    public QueryHistory getQueryHistory()
    {
        return history;
    }
    /**
     * Gathers information from all queries, concats into one string and returns it to the caller.
     * @param lineSeparator What kind of separator will be used between the line.
     * @return String with complete history of queries that are stored.
     */
    public String getAllQueriesDesc(String lineSeparator)
    {
        StringBuilder queries = new StringBuilder();
        if(history.size() <= 0)
        {
            queries.append(PredefinedCommunicates.noQueriesInHistory());
        }
        else
        {
            for (SingleQuery q : history) {
                q.setLineSeparator(lineSeparator);
                queries.append(q.toString());
                queries.append(lineSeparator);
            }
        }

        return queries.toString();
    }
    @Override
    public void newCalculationPerformed(CalculationData calculationData, IntegralData integralData) {
        addQuery(new SingleQuery(integralData, calculationData));
    }

}
