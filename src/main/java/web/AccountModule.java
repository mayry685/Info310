/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web;

import dao.AccountJdbiDAO;
import io.jooby.Jooby;
import domain.Account;
import io.jooby.StatusCode;

/**
 *
 * @author chsur
 */
public class AccountModule extends Jooby {

    public AccountModule(AccountJdbiDAO dao) {
        
        get("/api/accounts", ctx -> {
            return dao.getAccounts();
        });

        get("/api/accounts/{username}", ctx -> {
            String username = ctx.path("username").toString();
            Account account = dao.getAccountsByUsername(username);

            if (account == null) {
                ctx.setResponseCode(StatusCode.NOT_FOUND);
                return "User '" + username + "' not found.";
            } else {
                return account;
            }
        });
        
   
    
    }
    
}
