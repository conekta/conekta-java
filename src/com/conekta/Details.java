package com.conekta;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauricio
 */
public class Details extends Resource {
    public String name;
    public String phone;
    public String email;
    public String date_of_birth;
    public ConektaObject line_items;
    public Address billing_address;
    public Shipment shipment;
}