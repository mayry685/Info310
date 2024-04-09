/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web;

import io.jooby.Jooby;
import dao.SaleDAO;
import domain.Sale;
import io.jooby.StatusCode;

/**
 *
 * @author Raddy
 */
public class SaleModule extends Jooby{
    public SaleModule(SaleDAO dao){
        post("/api/sales", ctx -> {
            Sale sale  = ctx.body().to(Sale.class);
            dao.save(sale);
            
            return ctx.send(StatusCode.CREATED);
        });
    }
    
}
