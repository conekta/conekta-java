package io.conekta;

import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class PlanTest extends ConektaBase {
    JSONObject params;
    Integer id;

    public PlanTest() throws JSONException, Error, ErrorList {
        super();
        setApiVersion("1.0.0");
        ConektaObject plans = Plan.where();
        id = (new Random()).nextInt(10000);
        params = new JSONObject("{'id' : 'gold-plan2"+ id +"','name' : 'Gold Plan','amount' : 10000,'currency' : 'MXN','interval' : 'month','frequency' : 10,'trial_period_days' : 15,'expiry_count' : 12}");
    }

    // @Test
    public void testSuccesfulPlanCreate() throws Error, ErrorList {
       Plan plan = Plan.create(params);
       assertTrue(plan.id.equals("gold-plan2"+id));
    }

    // @Test
    public void testUpdatePlan() throws Error, JSONException, ErrorList {
       ConektaObject plans = Plan.where(new JSONObject("{'name' : 'Gold Plan'}"));
       Plan plan = (Plan) plans.get(0);
       plan.update(new JSONObject("{'name':'Silver Plan'}"));
       assertTrue(plan.name.equals("Silver Plan"));
    }

    // @Test
    public void testDeletePlan() throws Error, JSONException, ErrorList {
       Plan plan = Plan.create(params);
       plan.delete();
       assertTrue(plan.deleted);
    }

}
