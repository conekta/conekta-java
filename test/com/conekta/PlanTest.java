package com.conekta;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.conekta.Plan;
import com.conekta.Error;
import com.conekta.ConektaObject;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.Assert.*;

/**
 *
 * @author mauricio
 */
public class PlanTest extends ConektaTest {
    JSONObject params;
    Integer id;

    public PlanTest() throws JSONException, Error {
        super();
        ConektaObject plans = Plan.where();
        id = (new Random()).nextInt(1000);
        params = new JSONObject("{'id' : 'gold-plan"+ id +"','name' : 'Gold Plan','amount' : 10000,'currency' : 'MXN','interval' : 'month','frequency' : 10,'trial_period_days' : 15,'expiry_count' : 12}");
    }

    // @Test
    public void testSuccesfulPlanCreate() throws Error {
       ConektaObject plans = Plan.where();
       Plan plan = Plan.create(params);
       assertTrue(plan.id.equals("gold-plan"+id));
    }

    // @Test
    public void testUpdatePlan() throws Error, JSONException {
       ConektaObject plans = Plan.where();
       Plan plan = Plan.create(params);
       plan.update(new JSONObject("{'name':'Silver Plan'}"));
       assertTrue(plan.name.equals("Silver Plan"));
    }

    // @Test
    public void testDeletePlan() throws Error, JSONException {
       ConektaObject plans = Plan.where();
       Plan plan = Plan.create(params);
       plan.delete();
       assertTrue(plan.deleted);
    }

}
