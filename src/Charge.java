
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mauricio
 */
public class Charge extends Resource {

    public Boolean livemode;
    public Integer created_at;
    public String status;
    public String currency;
    public String description;
    public String reference_id;
    public String failure_code;
    public String failure_message;
    public Integer amount;
    public PaymentMethod payment_method;

    public Charge(String id) {
        super(id);
    }

    public Charge() {
        super();
    }

    public static Charge find(String id) throws Exception {
        String className = Charge.class.getName();
        return (Charge) scpFind(className, id);
    }

    public static ConektaObject where(JSONObject params) throws Exception {
        String className = Charge.class.getName();
        return (ConektaObject) scpWhere(className, params);
    }

    public static ConektaObject where() throws Exception {
        String className = Charge.class.getName();
        return (ConektaObject) scpWhere(className, null);
    }

    public static Charge create(JSONObject params) throws Exception {
        String className = Charge.class.getName();
        return (Charge) scpCreate(className, params);
    }

    public Charge capture() throws Exception {
        return (Charge) customAction("POST", "capture", null);
    }

    public Charge refund(Integer amount) throws Exception {
        JSONObject params = new JSONObject("{'amount':" + amount + "}");
        return (Charge) customAction("POST", "refund", params);
    }
}
