package io.conekta;

import java.lang.reflect.Field;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class Order extends Resource {
    public String payment_status;
    public String currency;
    public Integer amount;
    public Integer created_at;
    public Boolean livemode;
    public CustomerInfo customer_info;
    public HashMap metadata = new HashMap();
    public HashMap transitions = new HashMap();
    public ConektaList discount_lines = new ConektaList("discount_lines");
    public ShippingContact shipping_contact;
    public ConektaList tax_lines = new ConektaList("tax_lines");
    public ConektaList charges = new ConektaList("charges");
    public ConektaList shipping_lines = new ConektaList("shipping_lines");
    public ConektaList line_items = new ConektaList("line_items");
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

        if(Conekta.apiVersion.equals("2.0.0")){
            String[] submodels = { "discount_lines", "tax_lines", "shipping_lines", "line_items", "charges" };

            for (String submodel : submodels) {
                ConektaList list = new ConektaList(submodel);
                
                if(jsonObject.has(submodel)){
                    list.loadFrom(jsonObject.getJSONObject(submodel));

                    Field field = this.getClass().getField(submodel);
                    field.setAccessible(true);
                    field.set(this, list);
                    this.setVal(submodel, list);
                    
                    if(jsonObject.has("discount_lines")){
                        if(list.elements_type.equals("discount_lines")){
                            for (Object item : list){
                                DiscountLine discountLine = (DiscountLine) item;
                                discountLine.order = this;
                            }
                        }
                    }

                    if(list.elements_type.equals("tax_lines")){
                        for (Object item : list){
                            TaxLine taxLine = (TaxLine) item;
                            taxLine.order = this;
                        }
                    }

                    if(list.elements_type.equals("charges")){
                        for (Object item : list){
                            Charge charge = (Charge) item;
                            charge.order = this;
                        }
                    }

                    if(list.elements_type.equals("shipping_lines")){
                        for (Object item : list){
                            ShippingLine shippingLine = (ShippingLine) item;
                            shippingLine.order = this;
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
    }
    
    public void reload() throws Exception{
        Requestor requestor = new Requestor();
        JSONObject orderJson = (JSONObject) requestor.request("GET", this.instanceUrl(), null);
        this.loadFromObject(orderJson);
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
        return (ConektaList) scpWhere(className, params);
    }

    public DiscountLine createDiscountLine(JSONObject params) throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        return (DiscountLine) this.createMemberWithRelation("discount_lines", params, this);
    }

    public ShippingContact createShippingContact(JSONObject params) throws JSONException, Error, ErrorList{
        JSONObject updateParams = new JSONObject();
        updateParams.put("shipping_contact", params);
        this.update(updateParams);

        return this.shipping_contact;
    }

    public TaxLine createTaxLine(JSONObject params) throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        return (TaxLine) this.createMemberWithRelation("tax_lines", params, this);
    }
    
    public void capture() throws JSONException, Error, ErrorList{
        this.customAction("PUT", "capture", null);
    }

    public Charge createCharge(JSONObject params) throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        return (Charge) this.createMemberWithRelation("charges", params, this);
    }
  
    public ShippingLine createShippingLine(JSONObject params) throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        return (ShippingLine) this.createMemberWithRelation("shipping_lines", params, this);
    }

    public LineItems createLineItem(JSONObject params) throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        return (LineItems) this.createMemberWithRelation("line_items", params, this);
    }
    
    public Order refund(JSONObject params) throws Exception {
        Order order = (Order) this.customAction("POST", "refund", params);
        this.reload();
        return order;
    }
    
    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }
    
    public void delete() throws Error, ErrorList {
        this.delete(null, null);
    }
}
