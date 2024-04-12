/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web;

import dao.AccountJdbiDAO;
import io.jooby.Jooby;

/**
 *
 * @author chsur
 */
public class AccountModule extends Jooby {

    public AccountModule(AccountJdbiDAO accountDAO) {
        
        get("/api/accounts", ctx -> {
            return accountDAO.getAccounts();
        });
        
    }
    
}
