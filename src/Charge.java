
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

    Boolean livemode;
    Long created_at;
    String status;
    String currency;
    String description;
    String reference_id;
    String failure_code;
    String failure_message;
    Integer amount;
    PaymentMethod payment_method;

    public Charge(String id) {
        super(id);
    }

    public Charge() {
        super();
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Object livemode) {
        this.livemode = (Boolean) livemode;
    }

    public Long getCreatedAt(Object created_at) {
        return (Long) created_at;
    }

    public void setCreatedAt(Object created_at) {
        this.created_at = (Long) created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = (String) status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(Object currency) {
        this.currency = (String) currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = (String) description;
    }

    public String getReferenceId() {
        return reference_id;
    }

    public void setReferenceId(Object reference_id) {
        this.reference_id = (String) reference_id;
    }

    public String getFailureCode() {
        return failure_code;
    }

    public void setFailureCode(Object failure_code) {
        this.failure_code = (String) failure_code;
    }

    public String getFailureMessage() {
        return failure_message;
    }

    public void setFailureMessage(Object failure_message) {
        this.failure_message = (String) failure_message;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = (Integer) amount;
    }

    public PaymentMethod getpaymentMethod() {
        return payment_method;
    }

    public void getpaymentMethod(Object payment_method) {
        this.payment_method = (PaymentMethod) payment_method;
    }

    public static Charge find(String id) throws Exception {
        String className = Charge.class.getName();
        return (Charge) scpFind(className, id);
    }

    public static Charge create(JSONObject params) throws Exception {
        String className = Charge.class.getName();
        return (Charge) scpCreate(className, params);
    }

    public static ConektaObject where(JSONObject params) throws Exception {
        String className = Charge.class.getName();
        return (ConektaObject) scpWhere(className, params);
    }
}
