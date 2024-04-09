/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web;

import dao.ProductDAO;
import domain.Product;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import java.util.Collection;

/**
 *
 * @author Raddy
 */
public class ProductModule extends Jooby {

    public ProductModule(ProductDAO dao) {
        get("/api/products", ctx -> dao.getProducts());
        get("/api/products/{id}", ctx -> {
            Integer id = ctx.path("id").intValue();
            Product product = dao.searchById(id.toString());

            if (product == null) {
                return ctx.send(StatusCode.NOT_FOUND);
            } else {
                return product;
            }

        });
        get("/api/categories", ctx -> dao.getCategories());
        get("/api/categories/{category}", ctx -> {
            String category = ctx.path("category").toString();
            Collection<Product> products = dao.filterByCategory(category);
            if(products.size() == 0){
                return ctx.send(StatusCode.NOT_FOUND);
            } else {
                return products;
            }
        });
            
    }

}
