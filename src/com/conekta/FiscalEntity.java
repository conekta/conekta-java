/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conekta;

/**
 *
 * @author mauriciomurga
 */
public class FiscalEntity extends Resource {
    
    public String tax_id;
    public String company_name;
    public String email;
    public String phone;
    public Address address;
    
    
    public FiscalEntity(String id) {
        super(id);
    }
    
    public FiscalEntity() {
        super();
    }
}
