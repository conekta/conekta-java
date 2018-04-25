package io.conekta;

import org.json.JSONException;

/**
 *
 * @author mauricio
 */
public class EventTest extends ConektaBase {

    public EventTest() throws JSONException {
        super();
        setApiVersion("0.3.0");
    }

    // @Test
    public void testSuccesfulWhere() throws Error, ErrorList {
       ConektaObject events = Event.where();
       assertTrue(events.get(0) instanceof Event);
    }  

}
