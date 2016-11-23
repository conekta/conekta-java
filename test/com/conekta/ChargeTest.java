package com.conekta;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class ChargeTest extends ConektaTest {

    JSONObject valid_payment_method;
    JSONObject invalid_payment_method;
    JSONObject valid_visa_card;
    JSONObject params;

    public ChargeTest() throws JSONException {
        super();
        valid_payment_method = new JSONObject("{'description':'Stogies'," +
                "'reference_id':'9839-wolf_pack'," +
                "'amount':20000," +
                "'currency':'MXN',"+
                "'details':{'name':'Wolverine', 'phone':'403-342-0642', 'email':'logan@x-men.org', 'billing_address': {'street1':'tamesis'}, line_items: [{'name':'Box of Cohiba S1s', 'sku':'cohb_s1','unit_price': 20000,'description':'Imported from Mex.','quantity':1,'type':'other_human_consumption'}]}"+
                "}");
        invalid_payment_method = new JSONObject("{'description':'Stogies'," +
                "'reference_id':'9839-wolf_pack'," +
                "'amount':10," +
                "'currency':'MXN'}");
        valid_visa_card = new JSONObject("{'card':'tok_test_visa_4242'}");

        params = new JSONObject("{"+
"    'currency': 'MXN',"+
"    'details': {"+
"        'billing_address': {"+
"            'zip': '53422',"+
"            'state': 'Estado de Mexico',"+
"            'country': 'MX',"+
"            'city': 'Naucalpan',"+
"            'email': 'oscar.jimenez+loctommy@edgebound.com',"+
"            'phone': '5514945290',"+
"            'street1': 'Los Remedios 17, Loma Colorada 2a Seccion'"+
"        },"+
"        'line_items': ["+
"            {"+
"                'type': 'ecommerce_shopping',"+
"                'quantity': 2,"+
"                'name': 'Bg Ss Shoshanna Yd Polo Dress',"+
"                'description': 'Un vestido que podría ser definido como sporty- chic. De cuello blanco y líneas horizontales en varios colores, hace referencia a la vida en altamar.  ',"+
"                'sku': '7500244741187',"+
"                'unit_price': 59000"+
"            },"+
"            {"+
"                'type': 'ecommerce_shopping',"+
"                'quantity': 1,"+
"                'name': 'Farris Chino Gmd St',"+
"                'description': 'Producto Tommy',"+
"                'sku': '8718771353202',"+
"                'unit_price': 69000"+
"            }"+
"        ],"+
"        'name': 'Oscar',"+
"        'email': 'oscar.jimenez+loctommy@edgebound.com',"+
"        'phone': '5514945290'"+
"    },"+
"    'amount': 187000,"+
"    'bank': {"+
"        'type': 'banorte'"+
"    },"+
"    'description': 'Stogies',"+
"    'reference_id': 'conekta.tommy_12014_11019'"+
"}");
    }   

    public Charge testSuccesfulCardPMCreate() throws Exception {
        JSONObject params = valid_payment_method.put("card", valid_visa_card.get("card"));
        Charge charge = Charge.create(params);
        assertTrue(charge.status.equals("paid"));
        assertTrue(charge.details instanceof Details);
        assertTrue(charge.details.billing_address instanceof BillingAddress);
        assertTrue(charge.details.line_items instanceof ConektaObject);
        assertTrue(charge.details.line_items.get(0) instanceof LineItems);
        assertTrue(charge.details.name.equals("Wolverine"));
        assertTrue(charge.payment_method instanceof CardPayment);
        return charge;
    }

    //@Test
    public void testSuccesfulFindCharge() throws Exception {
        Charge charge = testSuccesfulCardPMCreate();
        charge = Charge.find(charge.id);
        assertTrue(charge instanceof Charge);
    }

    //@Test
    public void testSuccesfulWhere() throws Exception {
        JSONObject where_params = new JSONObject("{'description':'Stogies'}");
        ConektaObject charges = Charge.where(where_params);
        assertTrue(charges instanceof ConektaObject);
        assertTrue(charges.get(0) instanceof ConektaObject);
    }

    //@Test
    public void testSuccesfulBankPMCreate() throws Exception {
        JSONObject bank = new JSONObject("{'bank':{'type':'banorte'}}");
        JSONObject params = valid_payment_method.put("bank", bank.get("bank"));
        Charge charge = Charge.create(params);
        assertTrue(charge.payment_method instanceof BankTransferPayment);
        assertTrue(charge.status.equals("pending_payment"));
    }
    
    //@Test
    public void testSuccesfulSPEIPMCreate() throws Exception {
        JSONObject bank = new JSONObject("{'bank':{'type':'spei'}}");
        JSONObject params = valid_payment_method.put("bank", bank.get("bank"));
        Charge charge = Charge.create(params);
        assertTrue(charge.payment_method instanceof SpeiPayment);
        assertTrue(charge.status.equals("pending_payment"));
        assertTrue(
                !((SpeiPayment)charge.payment_method).clabe.isEmpty()
                  );
    }

    //@Test
    public void testSuccesfulOxxoPMCreate() throws Exception {
        JSONObject oxxo = new JSONObject("{'cash':{'type':'oxxo'}}");
        JSONObject params = valid_payment_method.put("cash", oxxo.get("cash"));
        Charge charge = Charge.create(params);
        assertTrue(charge.payment_method instanceof OxxoPayment);
        assertTrue(charge.status.equals("pending_payment"));
    }
      

    //@Test
    public void testUnsuccesfulPMCreate() throws Exception {
        JSONObject params = invalid_payment_method.put("card", valid_visa_card.get("card"));
        try {
            Charge.create(params);
            assertTrue(false);
        } catch (ParameterValidationError e) {
            assertTrue(e instanceof ParameterValidationError);
        }
    }

    //@Test
    public void testSuccesfulRefund() throws Exception {
        Charge charge = testSuccesfulCardPMCreate();
        charge.refund(20000);
        assertTrue(charge.status.equals("refunded"));
    }

    //@Test
    public void testUnsuccesfulRefund() throws Exception {
        Charge charge = testSuccesfulCardPMCreate();
        try {
            charge.refund(30000);
            //assertTrue(false);
        } catch(Error e) {
            assertTrue(e.message.equals("The amount to refund exceeds the charge total."));
        }
    }

    //@Test
    public Charge testSuccesfulCapture() throws Exception {
        JSONObject capture = new JSONObject("{'capture': false}");
        JSONObject params = valid_payment_method.put("card", valid_visa_card.get("card")).put("capture", capture.get("capture"));
        Charge charge = Charge.create(params);
        assertTrue(charge.status.equals("pre_authorized"));
        charge.capture();
        assertTrue(charge.status.equals("paid"));
        return charge;
    }
}
