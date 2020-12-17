package io.conekta;

import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

public class PlanV2Test extends ConektaBase {
    JSONObject params;
    Integer id;

    public PlanV2Test() throws JSONException, Error, ErrorList {
        super();
        setApiVersion("2.0.0");
        ConektaObject plans = Plan.where();
        id = (new Random()).nextInt(10000);
        params = new JSONObject("{'id' : 'gold-plan2"+ id +"','name' : 'Gold Plan','amount' : 10000,'currency' : 'MXN','interval' : 'month','frequency' : 10,'trial_period_days' : 15,'expiry_count' : 12}");
    }

    // @Test
    public void testGetAllPlan() throws Error, JSONException, ErrorList {
        ConektaObject plans = Plan.where();
        assertTrue(!plans.isEmpty());
    }

    // @Test
    public void testGetAPlan() throws Error, JSONException, ErrorList {
        params =new JSONObject("{'id' : 'gold-plan21550'}");

        //Obtener plan
        Plan plans1 = new Plan();
        plans1 =plans1.find("gold-plan21550");
        System.out.println("Planbuscado:" + plans1);

        //Buscar plan con parametro
        ConektaObject plans = Plan.where(params);
        System.out.println(plans);
        assertTrue(!plans.isEmpty());
    }

    //@Test
    public void testCreatePlan()throws Error, JSONException, ErrorList {
        ConektaObject plans = Plan.where();
        Plan plan = Plan.create(params);
        assertTrue(plan.id.equals("gold-plan2"+id));
    }

    // @Test
    public void testDeletePlan() throws Error, JSONException, ErrorList {
        ConektaObject plans = Plan.where();
        Plan plan = Plan.create(params);
        plan.delete();
        assertTrue(plan.deleted);
    }
}
