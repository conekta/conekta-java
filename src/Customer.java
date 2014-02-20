
import org.json.JSONObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauricio
 */
public class Customer extends Resource {

    public ConektaObject cards;
    public Subscription subscription;

    public Boolean livemode;
    public Integer created_at;
    public String name;
    public String email;
    public String phone;
    public String default_card_id;
    public Boolean deleted;

    public Customer(String id) {
        super(id);
        cards = new ConektaObject();
        subscription = null;
    }

    public Customer() {
        super();
        cards = new ConektaObject();
        subscription = null;
    }

    @Override
    public void loadFromObject(JSONObject jsonObject) throws Exception {
        super.loadFromObject(jsonObject);
        for (int i = 0; i < cards.size(); i ++) {
            ((Card)cards.get(0)).customer =  this;
        }
        if (subscription != null) {
            subscription.customer = this;
        }
    }

    public static Customer find(String id) throws Exception {
        String className = Customer.class.getName();
        return (Customer) scpFind(className, id);
    }

    public static Customer create(JSONObject params) throws Exception {
        String className = Customer.class.getName();
        return (Customer) scpCreate(className, params);
    }

        public static ConektaObject where(JSONObject params) throws Exception {
        String className = Customer.class.getName();
        return (ConektaObject) scpWhere(className, params);
    }

    public static ConektaObject where() throws Exception {
        String className = Customer.class.getName();
        return (ConektaObject) scpWhere(className, null);
    }

    public void delete() throws Exception {
        this.delete(null, null);
    }

    @Override
    public void update(JSONObject params) throws Exception {
        super.update(params);
    }

}
