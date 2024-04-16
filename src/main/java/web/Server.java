package web;

import dao.AccountJdbiDAO;
import dao.JdbiDaoFactory;
import domain.Account;
import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.gson.GsonModule;
import java.util.Collection;
import java.util.Set;

public class Server extends Jooby {

    public Server() {
        AccountJdbiDAO accountDao = JdbiDaoFactory.getAccountDAO();
        
        
        Collection<Account> accounts = accountDao.getAccounts();
        System.out.println(accounts);

        mount(new StaticAssetModule());
        install(new GsonModule());
        
        install(new BasicAccessAuth(accountDao, Set.of("/api/.*"), Set.of("/api/register")));
        mount(new AccountModule(JdbiDaoFactory.getAccountDAO()));

    }

    public static void main(String[] args) {
        System.out.println("\nStarting Server.");
        new Server()
                .setServerOptions(new ServerOptions().setPort(8087))
                .start();
    }

}