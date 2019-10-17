package io.conekta;

import static junit.framework.TestCase.assertTrue;
import org.json.JSONException;
import org.json.JSONObject;

public class ConektaListTest extends ConektaBase{

    ConektaList list;
    JSONObject paginateParams;
    public ConektaListTest() throws JSONException, Error, ErrorList {
        setApiVersion("2.0.0");
        paginateParams = new JSONObject("{'limit': 20}");
        list = Order.where(paginateParams);
    }

    // @Test
    public void testSuccsessfulNext() throws JSONException, Error, ErrorList{
        JSONObject paginateParams = new JSONObject("{'limit': 10}");
        ConektaList firstWindow = Order.where(paginateParams);
        firstWindow.next();
                
        Order order = (Order) firstWindow.get(0);
        String id = order.id;
        Order lastItem = (Order) list.get(10);

        assertTrue(id.equals(lastItem.id));
    }

    // @Test
    public void testSuccsessfulPrevious() throws JSONException, Error, ErrorList{
        setApiVersion("2.0.0");
        Order last = (Order) list.get(0);
        JSONObject paginateParams = new JSONObject("{ 'limit': 10 }");
        ConektaList lastWindow = Order.where(paginateParams);
        lastWindow.next();
        lastWindow.previous();

        Order order = (Order) lastWindow.get(0);
        String id = order.id;

        assertTrue(id.equals(last.id));
    }
    
    // 	@Test
    public void testToString() throws JSONException, Error, ErrorList{
        JSONObject paginateParams = new JSONObject("{ 'limit': 10 }");
        ConektaList lastWindow = Order.where(paginateParams);
        String listAsString = lastWindow.toString();

        assertTrue(listAsString.contains("elements_type"));
        assertTrue(listAsString.contains("next_page_url"));
        assertTrue(listAsString.contains("previous_page_url"));
        assertTrue(listAsString.contains("has_more"));
        assertTrue(listAsString.contains("total"));
        assertTrue(listAsString.contains("data"));
    }
}
