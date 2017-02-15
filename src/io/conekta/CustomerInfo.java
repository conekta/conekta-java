package io.conekta;

import java.util.HashMap;

/**
 *
 * @author L.Carlos
 */
public class CustomerInfo extends Resource{
    public String name;
    public String email;
    public String phone;
    public String customer_id;
    public HashMap antifraud_info = new HashMap();
}
