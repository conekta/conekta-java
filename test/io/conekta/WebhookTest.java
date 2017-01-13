package io.conekta;

import static junit.framework.TestCase.assertTrue;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author picharras
 */

public class WebhookTest extends ConektaBase {
    JSONObject webhookParams;

    public WebhookTest() throws JSONException {
        super();
        webhookParams = new JSONObject("{\"url\":\"http://misitio.pichhhhhhhhh.com/webhooks\", \"subscribed_events\":[\"charge.created\", \"charge.paid\", \"charge.under_fraud_review\"]}");
    }

    public Webhook testSuccesfulCreate() throws Exception {
        Webhook webhook = Webhook.create(webhookParams);
        assertTrue(webhook instanceof ConektaObject);
        return webhook;
    }

    public void testSuccesfulFind() throws Exception {
        Webhook webhook = testSuccesfulCreate();
        Webhook webhook2 = Webhook.find(webhook.id);
        assertTrue(webhook2 instanceof ConektaObject);
        webhook2.delete();
    }
}