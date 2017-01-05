package com.conekta;

import java.lang.reflect.Field;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class Order extends Resource {
    public String status;
    public String currency;
    public String customer_id;
    public Integer amount;
    public Integer created_at;
    public Boolean livemode;
    public HashMap customer_info = new HashMap();
    public HashMap metadata = new HashMap();
    public HashMap last_payment_info = new HashMap();
    public HashMap transitions = new HashMap();
    public FiscalEntity fiscal_entity;
    public ConektaList discount_lines;
    public ShippingContact shipping_contact;
    public ConektaList tax_lines;
    public ConektaList line_items;
    public Integer amount_refunded;


    public Order(String id) {
        super(id);
    }

    public Order() {
        super();
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
            String[] submodels = { "discount_lines", "tax_lines", "line_items"  };

            for (String submodel : submodels) {
                ConektaList list = new ConektaList(submodel);
                list.loadFrom(jsonObject.getJSONObject(submodel));

                Field field = this.getClass().getField(submodel);
                field.setAccessible(true);
                field.set(this, list);
                this.setVal(submodel, list);

                if(list.elements_type.equals("discount_lines")){
                    for (Object item : list){
                        DiscountLine discountLine = (DiscountLine) item;
                        discountLine.order = this;
                    }
                }

                if(list.elements_type.equals("tax_lines")){
                    for (Object item : list){
                        TaxLine taxLine = (TaxLine) item;
                        taxLine.order = this;
                    }
                }

                if(list.elements_type.equals("line_items")){
                    for (Object item : list){
                        LineItems lineItem = (LineItems) item;
                        lineItem.order = this;
                    }
                }
            }
        }
    }

    public static Order create(JSONObject params) throws ErrorList, Error {
        String className = Order.class.getCanonicalName();
        return (Order) scpCreate(className, params);
    }

    public static Order find(String id) throws ErrorList, Error {
        String className = Order.class.getCanonicalName();

        return (Order) scpFind(className, id);
    }

    public static ConektaList where(JSONObject params) throws Error, JSONException, ErrorList {
        String className = Order.class.getSimpleName();
        return (ConektaList) scpWhereList(className, params);
    }

    public FiscalEntity createFiscalEntity(JSONObject params) throws JSONException, Error, ErrorList{
        JSONObject updateParams = new JSONObject();
        updateParams.put("fiscal_entity", params);
        this.update(updateParams);

        return this.fiscal_entity;
    }

    public DiscountLine createDiscountLine(JSONObject params) throws JSONException, Error, ErrorList{
        return (DiscountLine) this.createMember("discount_lines", params);
    }

    public ShippingContact createShippingContact(JSONObject params) throws JSONException, Error, ErrorList{
        JSONObject updateParams = new JSONObject();
        updateParams.put("shipping_contact", params);
        this.update(updateParams);

        return this.shipping_contact;
      }

    public TaxLine createTaxLine(JSONObject params) throws JSONException, Error, ErrorList{
        return (TaxLine) this.createMember("tax_lines", params);
    }

    public LineItems createLineItem(JSONObject params) throws JSONException, Error, ErrorList{
        return (LineItems) this.createMember("line_items", params);
    }
}
