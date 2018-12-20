package pl.polsl.model.queryHistory;

import pl.polsl.model.exceptions.NoQueryFoundException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**Manages collection  that stores all queries that have been made
 * by the user during runtime.
 *
 * @author Karol KozuchGroup 4 Section 8
 * @version 1.1.2*/
public class QueryHistory implements Iterable<SingleQuery> {
    /**Amount of mementos in this collection.*/
    private int itemsCount=0;
    /**All queries made by the user during single runtime.*/
    private Map<Integer, SingleQuery> queries = new HashMap<>();
    /**Adds single query to the history.
     * @param singleQuery Object to add.
     *@return  Index of the newly added query.*/
    public int addItem(SingleQuery singleQuery)
    {
        int queryIndex;
        queries.put(itemsCount, singleQuery);
        queryIndex = itemsCount;
        itemsCount++;
        return queryIndex;
    }
    /**Returns a copy of single query. If none available - returns null.
     * @param index Index of requested query.
     * @return Requested query or null, if none found under given index.
     * @throws pl.polsl.model.exceptions.NoQueryFoundException If tried to access non-existing query.*/
    public SingleQuery getQuery(int index) throws NoQueryFoundException
    {
        if(queries.size() > index && index >= 0) {
            return new SingleQuery(queries.get(index));
        }
        else
        {
            throw new NoQueryFoundException("Tried to access non-existing query under " + index + " index! ");
        }
    }
    /**Returns a copy of last query inserted into collection.
     * @return Last query or null, if no queries available.
     * @throws pl.polsl.model.exceptions.NoQueryFoundException If tried to access non-existing query*/
    public SingleQuery getQuery() throws NoQueryFoundException
    {
        if(queries.size() > 0) {
            return new SingleQuery(queries.get(queries.size() - 1));
        }
        else {
            throw new NoQueryFoundException("Tried to access non-existing query - the collection is empty!");
        }
    }
    /**An iterator used in foreach loop.
     * @return Iterator for foreach loop - allows iterating through SingleQueries in the collection.*/
    @Override
    public Iterator<SingleQuery> iterator() {
        return new QueryHistoryIterator();
    }

    /**
     * Gets the size of the history.
     * @return Amount of SingleQueries in the collection.
     */
    public int size()
    {
        return queries.size();
    }

    /**Iterator for QueryHistory. Allows iteration through the queries collection.
     * @author Karol Kozuch
     * @version 1.0*/
    public class QueryHistoryIterator implements Iterator<SingleQuery>
    {
        /**The index of current element.*/
        private int i = -1;
        /**Current element which is pointed at by the iterator.*/
        private SingleQuery current = null;
        /**Checks if the collection has next item.
         * @return Returns true if there's next item in the collection. False otherwise.*/
        @Override
        public boolean hasNext() {
            return i+1 < queries.size();
        }
        /**Moves iterator to next element in the collection.
         * @return Next element in the collection.*/
        @Override
        public SingleQuery next() {
            i++;
            current = queries.get(i);

            return current;
        }
    }

}
