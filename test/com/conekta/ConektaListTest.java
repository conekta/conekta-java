package com.conekta;

import static junit.framework.TestCase.assertTrue;
import org.json.JSONException;
import org.json.JSONObject;

public class ConektaListTest extends ConektaTest{
    
    ConektaList list;
    JSONObject paginateParams;
    public ConektaListTest() throws JSONException, Error {
        setApiVersion("1.1.0");
        paginateParams = new JSONObject("{'limit': 20}");
        list = Order.where(paginateParams);
    }
    
    // @Test
    public void testSuccsessfulNext() throws JSONException, Error{
        JSONObject paginateParams = new JSONObject("{'limit': 10}");
        JSONObject nextPaginateParams = new JSONObject("{'limit': 1}");
        ConektaList firstWindow = Order.where(paginateParams);
        firstWindow.next(nextPaginateParams);
        
        Order order = (Order) firstWindow.get(0);
        String id = order.id;
        Order lastItem = (Order) list.get(10);
        
        assertTrue(id.equals(lastItem.id));
    }
    
    // @Test
    public void testSuccsessfulPrevious() throws JSONException, Error{
        Order last = (Order) list.get(10);
        JSONObject paginateParams = new JSONObject("{'limit': 10, 'starting_after': " + last.id + "}");
        JSONObject previousPaginateParams = new JSONObject("{'limit': 1}");
        ConektaList lastWindow = Order.where(paginateParams);
        lastWindow.previous(previousPaginateParams);
        
        Order order = (Order) lastWindow.get(0);
        String id = order.id;
        
        assertTrue(id.equals(last.id));
    }
}