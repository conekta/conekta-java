/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conekta;

import java.util.HashMap;

/**
 *
 * @author mauriciomurga
 */
public class ShippingDetails extends Resource {
    
    public String email;
    public String phone;
    public String receiver;
    public Address address;
    public HashMap between_streets = new HashMap();
    
    public ShippingDetails(String id) {
        super(id);
    }

    public ShippingDetails() {
        super();
    }
    
}
