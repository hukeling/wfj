package phone.pojo;

import java.io.Serializable;

import shop.front.shoppingOnLine.pojo.ShoppingCart;
import shop.product.pojo.ProductInfo;
import util.pojo.BaseEntity;

@SuppressWarnings("serial")
public class Cart extends BaseEntity implements Serializable{
	/*private Integer productId;
	
	private Integer shopCartId;
	
	private Integer customerId;
	
	private Integer quantit;*/
	private ShoppingCart shoppingCart;
	
	private ProductInfo productInfo;

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	
	
}
