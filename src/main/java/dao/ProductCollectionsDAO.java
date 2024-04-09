package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import domain.Product;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark George
 */
public class ProductCollectionsDAO implements ProductDAO {

	private static final Multimap<String, Product> categories = HashMultimap.create();
	private static final Map<String, Product> products = new HashMap<>();

        
        
	@Override
	public void saveProduct(Product product) {
		products.put(product.getProductId(), product);
		categories.put(product.getCategory(), product);
	}

	@Override
	public void removeProduct(Product product) {
		products.remove(product.getProductId());
		categories.remove(product.getCategory(), product);
	}

	@Override
	public Collection<Product> getProducts() {
		return products.values();
	}

	@Override
	public Collection<String> getCategories() {
		return categories.keySet();
	}

	@Override
	public Product searchById(String id) {
		return products.get(id);
	}

	@Override
	public Collection<Product> filterByCategory(String category) {
		return categories.get(category);
	}

}
