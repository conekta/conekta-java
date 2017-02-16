/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.conekta;

/**
 *
 * @author picharras
 */
public class RequestBody extends Resource {
    public Card card;
    public String company_id;
    public String user_account_id;
    public ConektaObject request_headers;
}