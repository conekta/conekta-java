
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauricio
 */
public abstract class PaymentMethod extends ConektaObject {
    String type;
}

class CardPayment extends PaymentMethod {
    public String brand;
    public String auth_code;
    public String last4;
    public String exp_month;
    public String exp_year;
    public String name;

    public CardPayment(JSONObject jsonObject) throws JSONException {
        super();
        this.type = "card_payment";
        this.brand = jsonObject.getString("brand");
        this.auth_code = jsonObject.getString("auth_code");
        this.last4 = jsonObject.getString("last4");
        this.exp_month = jsonObject.getString("exp_month");
        this.exp_year = jsonObject.getString("exp_year");
        this.name = jsonObject.getString("name");
    }
}

class OxxoPayment extends PaymentMethod {
     public String expiry_date;
     public String barcode;
     public String barcode_url;
    public OxxoPayment(JSONObject jsonObject) throws JSONException {
        super();
        this.type = "cash_payment";
        this.expiry_date = jsonObject.getString("expiry_date");
        this.barcode = jsonObject.getString("barcode");
        this.barcode_url = jsonObject.getString("barcode_url");
    }
}

class BankTransferPayment extends PaymentMethod {
    public String service_name;
    public String service_number;
    public String reference;
    public BankTransferPayment(JSONObject jsonObject) throws JSONException {
        super();
        this.type = "bank_transfer_payment";
        this.service_name = jsonObject.getString("service_name");
        this.service_number = jsonObject.getString("service_number");
        this.reference = jsonObject.getString("reference");
    }
}