package com.conekta;

import java.lang.reflect.Field;
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
    public Boolean deleted;
    public ConektaList fiscal_entities;

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
        
        if(Conekta.apiVersion.equals("1.1.0")){
            String[] submodels = { "fiscal_entities" };
            
            for (String submodel : submodels) {
                ConektaList list = new ConektaList(submodel);
                list.loadFrom(jsonObject.getJSONObject(submodel));
                
                Field field = this.getClass().getField(submodel);
                field.setAccessible(true);
                field.set(this, list);
                this.setVal(submodel, list);
                
                if(list.elements_type.equals("fiscal_entities")){
                    for (Object item : list){
                        FiscalEntity fiscalEntity = (FiscalEntity) item;
                        fiscalEntity.customer = this;
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
        return (ConektaObject) scpWhere(className, params);
    }

    public static ConektaObject where() throws Error, ErrorList {
        String className = Customer.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, null);
    }

    public void delete() throws Error, ErrorList {
        this.delete(null, null);
    }

    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }

    public Card createCard(JSONObject params) throws Error, ErrorList {
        return (Card) this.createMember("cards", params);
    }

    public Subscription createSubscription(JSONObject params) throws Error, ErrorList {
        return (Subscription) this.createMember("subscription", params);
    }
    
    public FiscalEntity createFiscalEntity(JSONObject params) throws JSONException, Error, ErrorList{
        return (FiscalEntity) this.createMember("fiscal_entities", params);
    }
}
