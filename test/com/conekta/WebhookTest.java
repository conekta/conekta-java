/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conekta;

import static junit.framework.TestCase.assertTrue;
import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.Assert.*;

/**
 *
 * @author picharras
 */

public class WebhookTest extends ConektaTest {
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