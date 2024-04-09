/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import domain.Sale;
import domain.SaleItem;
import java.time.LocalDateTime;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

/**
 *
 * @author Raddy
 */
public interface SaleJdbiDAO extends SaleDAO {
    @SqlUpdate("insert into Sales(Date, CustomerID, Status) values (:date, :customer.customerId, :status)")
    @GetGeneratedKeys
    Integer insertSale(@BindBean Sale sale);
    
    @SqlUpdate("insert into SaleItems(ProductID, SaleID, QuantityPurchased, SalePrice) values (:product.productId, :SaleId, :quantityPurchased, :salePrice)")
    void insertSaleItem(@BindBean SaleItem item, @Bind("SaleId") Integer saleId);
    
    @SqlUpdate("update Products set QuantityInStock = QuantityInStock - :quantityPurchased where ProductID = :product.productId")
    void updateStockLevel(@BindBean SaleItem item);
    
    @Override 
    @Transaction
    default void save(Sale sale) {
        sale.setDate(LocalDateTime.now());
        sale.setStatus("NEW ORDER");
        Integer saleId = insertSale(sale);
        sale.setSaleId(saleId);
        for (SaleItem item :sale.getItems()) {
            insertSaleItem(item, saleId);
            updateStockLevel(item);
        }
    }
}
