package com.conekta;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.conekta.Error;
import com.conekta.ConektaObject;
import com.conekta.Event;
import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.Assert.*;

/**
 *
 * @author mauricio
 */
public class EventTest extends ConektaTest {

    public EventTest() throws JSONException {
        super();
    }

    // @Test
    public void testSuccesfulWhere() throws Error {
       ConektaObject events = Event.where();
       assertTrue(events.get(0) instanceof Event);
    }  

}
