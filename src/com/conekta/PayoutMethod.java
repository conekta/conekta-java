/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conekta;

import org.json.JSONObject;

/**
 *
 * @author picharras
 */
public class PayoutMethod extends Resource{

    public void delete() throws Error {
        this.delete(null, null);
    }

    @Override
    public void update(JSONObject params) throws Error {
        super.update(params);
    }
}