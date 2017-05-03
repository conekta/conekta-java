package io.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class ErrorListTest extends ConektaBase {

    JSONObject invalidOrder;

    public ErrorListTest() throws JSONException {
        invalidOrder = new JSONObject("{}");
    }

    // @Test
    public void testNoIdError() throws Error{
        try {
                Order.find("0");
        } catch(ErrorList e) {
            assertTrue(e.details.get(0).message.equals("El recurso no ha sido encontrado."));
        }
    }

    // @Test
    public void testApiError() throws Error{
        try {
            Order.create(invalidOrder);
        } catch(ErrorList e) {
            assertTrue(e.details.get(0).message.equals("El parametro \"line_items\" es requerido."));
        }
    }
}
