package io.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class LineItemTest extends ConektaBase{

    JSONObject validOrder;
    JSONObject lineItem;

    public LineItemTest() throws JSONException {
        setApiVersion("2.0.0");
        validOrder = new JSONObject(
            "{ 'currency': 'mxn'," +
            "  'metadata': {" +
            "    'test': true"+
            "  }," +
            "  line_items: [{" +
            "    'name': 'Box of Cohiba S1s'," +
            "    'description': 'Imported From Mex.'," +
            "    'unit_price': 20000," +
            "    'quantity': 1," +
            "    'type': 'physical'," +
            "    'tags': ['food', 'mexican food']" +
            "  }, {" +
            "    'name': 'Box of Romeo y Julueta'," +
            "    'description': 'Imported From Cuba.'," +
            "    'unit_price': 20001," +
            "    'quantity': 1," +
            "    'type': 'physical'," +
            "    'tags': ['food', 'mexican food']" +
            "  }]" +
            "}"
        );
        
        lineItem = new JSONObject("{" +
            "    'name': 'Box of Cohiba S1s'," +
            "    'description': 'Imported From Mex.'," +
            "    'unit_price': 20000," +
            "    'quantity': 1," +
            "    'type': 'physical'," +
            "    'tags': ['food', 'mexican food']" +
            "  }");
    }

    // @Test
    public void testLineItemsDelete() throws ErrorList, Error{
        Order order = Order.create(validOrder);

        LineItems lineItem = (LineItems) order.line_items.get(0);

        lineItem.delete();

        assertTrue(lineItem.deleted);
    }

    // @Test
    public void testLineItemsUpdate() throws ErrorList, Error, JSONException{
        Order order = Order.create(validOrder);

        LineItems lineItem = (LineItems) order.line_items.get(0);

        lineItem.update(new JSONObject("{'name': 'Box of Romeo y Julieta'}"));

        assertTrue(lineItem.name.equals("Box of Romeo y Julieta"));
    }
    
    // @Test
    public void testCreateTransportationLineItem() throws Exception {
        Order order = Order.create(validOrder);
        JSONObject transportationLineItemJSON = new JSONObject("{" +
        "	'trip_id': 'trip-id'," +
        "	'driver_id': 'drive-id'," +
        "	'ticket_class': 'sencillo'," +
        "	'pickup_latlon': '22.1236634,-101.0261102,12'," +
        "	'dropoff_latlon': '22.1355397,-100.9976131,17'" +
        "}");
        
        lineItem.put("antifraud_info", transportationLineItemJSON);
        
        order.createLineItem(lineItem);
        
        LineItems item = (LineItems) order.line_items.get(2);
        
        assertTrue(item.antifraud_info.get("trip_id").equals("trip-id"));
        assertTrue(item.antifraud_info.get("driver_id").equals("drive-id"));
        assertTrue(item.antifraud_info.get("ticket_class").equals("sencillo"));
        assertTrue(item.antifraud_info.get("pickup_latlon").equals("22.1236634,-101.0261102,12"));
        assertTrue(item.antifraud_info.get("dropoff_latlon").equals("22.1355397,-100.9976131,17"));
    }
    
    // @Test
    public void testCreateTravelLineItem() throws Exception {
        Order order = Order.create(validOrder);
        JSONObject travelLineItemJSON = new JSONObject("{" +
        "	'trip_id': 'trip-id'," +
        "	'departs_at': '1484040996'," +
        "	'arrives_at': '1484040997'," +
        "	'ticket_class': 'Turista'," +
        "	'seat_number': '22'," +
        "	'passenger_type': 'Adulto'," +
        "	'origin': 'San Luis Potosí'," +
        "	'destination': 'Chiapas'" +
        "}");
        
        lineItem.put("antifraud_info", travelLineItemJSON);
        
        order.createLineItem(lineItem);
        
        LineItems item = (LineItems) order.line_items.get(2);
        
        assertTrue(item.antifraud_info.get("trip_id").equals("trip-id"));
        assertTrue(item.antifraud_info.get("departs_at").equals("1484040996"));
        assertTrue(item.antifraud_info.get("arrives_at").equals("1484040997"));
        assertTrue(item.antifraud_info.get("ticket_class").equals("Turista"));
        assertTrue(item.antifraud_info.get("seat_number").equals("22"));
        assertTrue(item.antifraud_info.get("passenger_type").equals("Adulto"));
        assertTrue(item.antifraud_info.get("origin").equals("San Luis Potosí"));
        assertTrue(item.antifraud_info.get("destination").equals("Chiapas"));
    }
    
    // @Test
    public void testCreateEventLineItem() throws Exception {
        Order order = Order.create(validOrder);
        JSONObject travelLineItemJSON = new JSONObject("{" +
        "	'starts_at': '1484040994'," +
        "	'ends_at': '1484040996'," +
        "	'ticket_class': 'VIP'," +
        "	'seat_number': '22'" +
        "}");
        
        lineItem.put("antifraud_info", travelLineItemJSON);
        
        order.createLineItem(lineItem);
        
        LineItems item = (LineItems) order.line_items.get(2);
        
        assertTrue(item.antifraud_info.get("starts_at").equals("1484040994"));
        assertTrue(item.antifraud_info.get("ends_at").equals("1484040996"));
        assertTrue(item.antifraud_info.get("ticket_class").equals("VIP"));
        assertTrue(item.antifraud_info.get("seat_number").equals("22"));
    }
    
    // @Test
    public void testCreateCrowdfundingLineItem() throws Exception {
        Order order = Order.create(validOrder);
        JSONObject travelLineItemJSON = new JSONObject("{" +
        "	'project_id': 'project-id'," +
        "	'starts_at': '1484040994'," +
        "	'ends_at': '1484040996'," +
        "	'target_amount': '220000'" +
        "}");
        
        lineItem.put("antifraud_info", travelLineItemJSON);
        
        order.createLineItem(lineItem);
        
        LineItems item = (LineItems) order.line_items.get(2);
        
        assertTrue(item.antifraud_info.get("project_id").equals("project-id"));
        assertTrue(item.antifraud_info.get("starts_at").equals("1484040994"));
        assertTrue(item.antifraud_info.get("ends_at").equals("1484040996"));
        assertTrue(item.antifraud_info.get("target_amount").equals("220000"));
    }
}
