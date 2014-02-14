
import org.json.JSONObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauricio
 */
public class ConektaJava {
    public static void main(String[] args) throws Exception {
        Charge charge = (Charge) Charge.find("52f567a8cfc26c8e6c000006");
        System.out.println(charge.toString());
        System.out.println(charge.status);
        System.out.println(((CardPayment)charge.payment_method).toString());

        //Charge charge = (Charge) Charge.find("52fc0a6ecfc26c87bc00000e");
        //System.out.println(charge.getAmount());
        //System.out.println(charge.toString());
        JSONObject params = new JSONObject("{'description':'Stogies'," +
                                            "'reference_id':'9839-wolf_pack',"+
                                            "'amount':20000,"+
                                            "'currency':'MXN',"+
                                            "'card':'tok_test_visa_4242'}");
        //Charge charge2 = (Charge) Charge.create(params);
        //System.out.println(charge2.toString());
        ConektaObject charges = (ConektaObject) Charge.where(null);
        System.out.println(charges.toString());
        Charge charge2 = (Charge) charges.get(0);
        System.out.println(charge2.getClass().toString());
        System.out.println(charge2.toString());
        
    }
}
