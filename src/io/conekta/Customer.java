package io.conekta;

import java.lang.reflect.Field;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

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
    public String default_payment_source_id;
    public Boolean deleted;
    public HashMap antifraud_info;
    public ConektaList shipping_contacts = new ConektaList("shipping_contacts");
    public ConektaList payment_sources = new ConektaList("payment_sources");

    public Customer(String id) {
        super(id);
        cards = new ConektaObject();
        subscription = new Subscription();
    }

    public Customer() {
        super();
        cards = new ConektaObject();
        subscription = new Subscription();
    }

    @Override
    public void loadFromObject(JSONObject jsonObject) throws Exception {
        if (jsonObject != null) {
            try {
                super.loadFromObject(jsonObject);
            } catch (Exception e) {
                throw new Error(e.toString(), null, null, null, null);
            }
        }
        
        if(Conekta.apiVersion.equals("2.0.0")){
            String[] submodels = { "shipping_contacts", "payment_sources" };

            for (String submodel : submodels) {
                if (jsonObject.has(submodel)){
                    ConektaList list = new ConektaList(submodel);
                    list.loadFrom(jsonObject.getJSONObject(submodel));

                    Field field = this.getClass().getField(submodel);
                    field.setAccessible(true);
                    field.set(this, list);
                    this.setVal(submodel, list);

                    if(list.elements_type.equals("shipping_contacts")){
                        for (Object item : list){
                            ShippingContact shippingContact = (ShippingContact) item;
                            shippingContact.customer = this;
                        }
                    }

                    if(list.elements_type.equals("payment_sources")){
                        for (Object item : list){
                            PaymentSource source = (PaymentSource) item;
                            if(source.type.equals("oxxo_recurrent")) {
                                source = (OfflineRecurrentReference) item;
                            } else {
                                source = (Card) item;
                            }

                            source.customer = this;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < cards.size(); i++) {
                ((Card) cards.get(i)).customer = this;
            }
            if (subscription != null) {
                subscription.customer = this;
            }      
        }
    }

    public static Customer find(String id) throws Error, ErrorList {
        String className = Customer.class.getCanonicalName();
        return (Customer) scpFind(className, id);
    }

    public static Customer create(JSONObject params) throws Error, ErrorList {
        String className = Customer.class.getCanonicalName();
        return (Customer) scpCreate(className, params);
    }

    public static ConektaObject where(JSONObject params) throws Error, ErrorList {
        String className = Customer.class.getCanonicalName();
        if (Conekta.apiVersion.startsWith("2")) {
            return scpWhereList(className, params);
        }
        //default v1 behavior
        return scpWhere(className, params);
    }

    public static ConektaObject where() throws Error, ErrorList {
        String className = Customer.class.getCanonicalName();
        if (Conekta.apiVersion.startsWith("2")) {
            return scpWhereList(className, null);
        }
        //default v1 behavior
        return scpWhere(className, null);
    }

    public void delete() throws Error, ErrorList {
        this.delete(null, null);
    }

    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }

    public Card createCard(JSONObject params) throws Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if(Conekta.apiVersion.equals("2.0.0")) {
            return (Card) this.createMemberWithRelation("payment_sources", params, this);
        }

        return (Card) this.createMemberWithRelation("cards", params, this);
    }

    public OfflineRecurrentReference createOfflineRecurrentReference(JSONObject params) throws Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        return (OfflineRecurrentReference) this.createMemberWithRelation("payment_sources", params, this);
    }

    public Subscription createSubscription(JSONObject params) throws Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        return (Subscription) this.createMemberWithRelation("subscription", params, this);
    }

    public ShippingContact createShippingContact(JSONObject params) throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        return (ShippingContact) this.createMemberWithRelation("shipping_contacts", params, this);
    }

    public PaymentSource createPaymentSource(JSONObject params) throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        return (PaymentSource) this.createMemberWithRelation("payment_sources", params, this);
    }
}
