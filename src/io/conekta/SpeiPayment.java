package io.conekta;

import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class SpeiPayment  extends PaymentMethod {

    public String clabe;
    public String tracking_code;
    public String bank;

    public SpeiPayment(JSONObject jsonObject) throws Error {
        super();

        try {
            this.clabe = jsonObject.getString("clabe");
            this.tracking_code = jsonObject.optString("tracking_code", "");
            this.bank = jsonObject.getString("bank");
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
    }
}
