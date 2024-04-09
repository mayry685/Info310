/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web;

import dao.CustomerDAO;
import domain.Customer;
import io.jooby.Jooby;
import io.jooby.StatusCode;

/**
 *
 * @author Raddy
 */
public class CustomerModule extends Jooby{
    
    public CustomerModule(CustomerDAO dao) {
        post("/api/register", ctx -> {
            Customer customer = ctx.body().to(Customer.class);
            dao.saveCustomer(customer);
            return ctx.send(StatusCode.CREATED);
        });
        get("/api/customers/{username}", ctx -> {
            String username = ctx.path("username").toString();
            Customer customer = dao.searchByUsername(username);
            customer.setPassword(null);
            customer.setUsername(null);
            return customer;
        });
    }
    
}
