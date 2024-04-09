package web;

import dao.CustomerDAO;
import dao.DaoFactory;
import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.gson.GsonModule;
import java.util.Set;

public class Server extends Jooby {

    public Server() {
        CustomerDAO custDao = DaoFactory.getCustomerDAO();

        mount(new StaticAssetModule());
        install(new GsonModule());
        
        install(new BasicAccessAuth(custDao, Set.of("/api/.*"), Set.of("/api/register")));
        mount(new ProductModule(DaoFactory.getProductDAO()));
        mount(new SaleModule(DaoFactory.getSaleDAO()));
        mount(new CustomerModule(custDao));

    }

    public static void main(String[] args) {
        System.out.println("\nStarting Server.");
        new Server()
                .setServerOptions(new ServerOptions().setPort(8087))
                .start();
    }

}
