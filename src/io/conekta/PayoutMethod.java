package io.conekta;

import org.json.JSONObject;

/**
 *
 * @author picharras
 */
public class PayoutMethod extends Resource{

    public void delete() throws Error, ErrorList {
        this.delete(null, null);
    }

    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }
}