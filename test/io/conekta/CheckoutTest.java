package io.conekta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 *
 * @author L.Carlos
 */
public class CheckoutTest extends ConektaBase{

    JSONObject validCheckout;

    public CheckoutTest() throws JSONException, Error, ErrorList {
        super();
        setApiVersion("2.0.0");
        validCheckout = new JSONObject(
                "{" +
                        "    'name': 'Payment Link Name'," +
                        "    'type': 'PaymentLink'," +
                        "    'force_3ds_flow': false," +
                        "    'recurrent': false," +
                        "    'expired_at': " + Long.toString(System.currentTimeMillis() / 1000L + 259200L) + "," +
                        "    'allowed_payment_methods': ['cash', 'card', 'bank_transfer']," +
                        "    'needs_shipping_contact': true," +
                        "    'monthly_installments_enabled': false," +
                        "    'monthly_installments_options': [3, 6, 9, 12]," +
                        "    'order_template': {" +
                        "      'line_items': [{" +
                        "        'name': 'Red Wine'," +
                        "        'unit_price': 1000," +
                        "        'quantity': 10" +
                        "      }]," +
                        "      'currency': 'MXN'," +
                        "      'customer_info': {" +
                        "        'name': 'Juan Perez'," +
                        "        'email': 'test@conekta.com'," +
                        "        'phone': '5566982090'" +
                        "      }" +
                        "    }" +
                        "  }"
        );
    }

    //@Test
    public void testSuccesfulCheckoutCreate() throws Exception {
        Checkout checkout = Checkout.create(validCheckout);

        assertTrue(checkout instanceof Checkout);
        assertTrue(checkout.livemode == false);
        assertTrue(checkout.name.equals("Payment Link Name"));
        assertTrue(checkout.type.equals("PaymentLink"));
        assertTrue(checkout.status.equals("Issued"));
        assertFalse(checkout.recurrent);
        assertTrue(checkout.needs_shipping_contact);
        assertFalse(checkout.monthly_installments_enabled);
        assertFalse(checkout.force_3ds_flow);
        assertNotNull(checkout.expired_at);
        assertNotNull(checkout.url);
        assertNotNull(checkout.slug);
        assertEquals("checkout", checkout.object);
        ArrayList<String> expectedPaymentMethods = new ArrayList<String>();
        expectedPaymentMethods.add("bank_transfer");
        expectedPaymentMethods.add("card");
        expectedPaymentMethods.add("cash");
        Collections.sort(checkout.allowed_payment_methods);
        assertEquals(
                expectedPaymentMethods,
                checkout.allowed_payment_methods);
        ArrayList<Integer> expectedMonthlyInstallmentOptions = new ArrayList<Integer>();
        expectedMonthlyInstallmentOptions.add(3);
        expectedMonthlyInstallmentOptions.add(6);
        expectedMonthlyInstallmentOptions.add(9);
        expectedMonthlyInstallmentOptions.add(12);
        Collections.sort(checkout.monthly_installments_options);
        assertEquals(
                expectedMonthlyInstallmentOptions,
                checkout.monthly_installments_options);
        assertTrue(checkout.sms_sent.equals(0));
        assertTrue(checkout.emails_sent.equals(0));
    }


    //@Test
    public void testSuccesfulCheckoutCreateWithoutCustomer() throws Exception {
        JSONObject checkoutParamsWithoutCustomer = new JSONObject(validCheckout);
        checkoutParamsWithoutCustomer.remove("customer_info");
        Checkout checkout = Checkout.create(validCheckout);

        assertTrue(checkout instanceof Checkout);
    }

    //@Test
    public void testSuccesfulCheckoutFind() throws Exception {
        Checkout checkout = Checkout.create(validCheckout);

        Checkout checkoutFound = Checkout.find(checkout.id);

        assertTrue(checkoutFound instanceof Checkout);
        assertTrue(checkoutFound.livemode == false);
        assertEquals(checkout.id, checkoutFound.id);
    }

    // @Test
    public void testSuccesfulCheckoutWhere() throws Exception {
        JSONObject paginateParams = new JSONObject("{'limit': 10}");

        ConektaList checkouts = Checkout.where(paginateParams);
        Checkout checkout = (Checkout) checkouts.get(0);

        assertTrue(checkouts instanceof ConektaList);
        assertTrue(checkouts.size() == 10);assertTrue(checkouts.size() == 10);
        assertTrue(checkout instanceof Checkout);
    }
    

    // @Test
    public void testSuccessfulCheckoutCancel() throws JSONException, Error, ErrorList, Exception {
        Checkout checkout = Checkout.create(validCheckout);

        checkout.cancel();

        assertTrue(checkout.status.equals("Cancelled"));
    }

    // @Test
    public void testSuccessfulCheckoutSendSms() throws JSONException, Error, ErrorList, Exception {
        Checkout checkout = Checkout.create(validCheckout);

        checkout.sendSms("5555555555");

        assertTrue(checkout.sms_sent.equals(1));
    }

    // @Test
    public void testSuccessfulCheckoutSendEmail() throws JSONException, Error, ErrorList, Exception {
        Checkout checkout = Checkout.create(validCheckout);

        checkout.sendEmail("mail@mail.com");

        assertTrue(checkout.emails_sent.equals(1));
    }

    //@Test
    public void testInvalidExpiryDate() throws Exception {
        JSONObject invalidCheckoutParams = new JSONObject(validCheckout.toString());
        invalidCheckoutParams.put("expires_at", 0);
        invalidCheckoutParams.put("expired_at", 0);

        try {
            Checkout checkout = Checkout.create(invalidCheckoutParams);
        } catch(ErrorList e) {
            assertTrue(e.details.get(0).message.equals("Formato inválido para \"expired_at\"."));
        }
    }
}
