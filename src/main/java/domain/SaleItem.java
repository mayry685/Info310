package domain;

import java.math.BigDecimal;

/**
 * @author Mark George
 */
public class SaleItem {

	private BigDecimal quantityPurchased;
	private BigDecimal salePrice;

	private Product product;

	public SaleItem() {
	}

	public SaleItem(BigDecimal quantityPurchased, BigDecimal salePrice, Product product) {
		this.quantityPurchased = quantityPurchased;
		this.salePrice = salePrice;
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getQuantityPurchased() {
		return quantityPurchased;
	}

	public void setQuantityPurchased(BigDecimal quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getItemTotal() {
		return this.quantityPurchased.multiply(salePrice);
	}

	@Override
	public String toString() {
		return "SaleItem{" + "product=" + product + ", quantityPurchased=" + quantityPurchased + ", salePrice=" + salePrice + '}';
	}

}
