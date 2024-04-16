package web;

import dao.AccountJdbiDAO;
import domain.Account;
import io.jooby.Jooby;
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
        
        get("/api/accounts/searchByUsername", ctx -> {
            String username = ctx.query("username").value();
            Account account = dao.getAccountsByUsername(username);

            if (account == null) {
                ctx.setResponseCode(StatusCode.NOT_FOUND);
                return "User '" + username + "' not found.";
            } else {
                return account;
            }
        });
        
        post("/api/accounts", ctx -> {
            Account account = ctx.body().to(Account.class);
            if (dao.isUsernameTaken(account.getUserName())) {
                ctx.setResponseCode(StatusCode.BAD_REQUEST);
                return "Username '" + account.getUserName() + "' is already taken.";
            }
            dao.createAccount(account);
            ctx.setResponseCode(StatusCode.CREATED);
            return account;
        });
        
        put("/api/accounts/update", ctx -> {
            Account account = ctx.body(Account.class);
            dao.updateAccount(account);
            return ctx.send(StatusCode.OK);
        });
        
        delete("/api/accounts/deleteByUsername", ctx -> {
            String username = ctx.query("username").value();
            dao.deleteAccountByUsername(username);
            ctx.setResponseCode(StatusCode.NO_CONTENT);
            return "";
        });
        
        post("/api/accounts/changePassword", ctx -> {
            String username = ctx.query("username").value();
            String newPassword = ctx.query("newPassword").value();
            dao.changePassword(username, newPassword);
            return "Password changed successfully.";
        });
    }
}