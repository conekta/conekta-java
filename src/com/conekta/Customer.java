package com.conekta;

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
        subscription = new Subscription();
    }

    public Customer() {
        super();
        cards = new ConektaObject();
        subscription = new Subscription();
    }

    @Override
    public void loadFromObject(JSONObject jsonObject) throws Error {
        if (jsonObject != null) {
            try {
                super.loadFromObject(jsonObject);
            } catch (Exception e) {
                throw new Error(e.toString(), null, null, null, null);
            }
        }
        for (int i = 0; i < cards.size(); i++) {
            ((Card) cards.get(i)).customer = this;
        }
        if (subscription != null) {
            subscription.customer = this;
        }
    }

    public static Customer find(String id) throws Error {
        String className = Customer.class.getCanonicalName();
        return (Customer) scpFind(className, id);
    }

    public static Customer create(JSONObject params) throws Error {
        String className = Customer.class.getCanonicalName();
        return (Customer) scpCreate(className, params);
    }

    public static ConektaObject where(JSONObject params) throws Error {
        String className = Customer.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, params);
    }

    public static ConektaObject where() throws Error {
        String className = Customer.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, null);
    }

    public void delete() throws Error {
        this.delete(null, null);
    }

    @Override
    public void update(JSONObject params) throws Error {
        super.update(params);
    }

    public Card createCard(JSONObject params) throws Error {
        return (Card) this.createMember("cards", params);
    }

    public Subscription createSubscription(JSONObject params) throws Error {
        return (Subscription) this.createMember("subscription", params);
    }
}
