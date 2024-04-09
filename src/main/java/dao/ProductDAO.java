package dao;

import domain.Product;
import java.util.Collection;

/**
 * @author Mark George
 */
public interface ProductDAO {

	Collection<Product> filterByCategory(String category);

	Collection<String> getCategories();

	Collection<Product> getProducts();

	void removeProduct(Product product);

	void saveProduct(Product product);

	Product searchById(String id);

}
