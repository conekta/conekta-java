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
public class Card extends Resource {
    public Customer customer;
    public String name;
    public String last4;
    public String exp_month;
    public String exp_year;
    public Boolean deleted;

    @Override
    public String instanceUrl() throws Error {
        if (id.length() == 0) {
            throw new Error("Could not get the id of Resource instance.", null, null, null, null);
        }
        String base = this.customer.instanceUrl();
        return base + "/cards/" + id;
    }

    @Override
    public void update(JSONObject params) throws Error {
        super.update(params);
    }

    public void delete() throws Error {
        this.delete("customer", "cards");
    }
}
