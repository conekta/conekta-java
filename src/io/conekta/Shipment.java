/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package io.conekta;

/**
 *
 * @author mauricio
 */
public class Shipment extends Resource {
    public String carrier;
    public String service;
    public String tracking_id;
    public Integer price;
    public Address address;
}
