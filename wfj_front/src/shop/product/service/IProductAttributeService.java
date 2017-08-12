package shop.product.service;
import java.util.List;

import shop.product.pojo.ProductAttribute;
import util.service.IBaseService;
/**
 * IProductAttributeService - 类描述信息
 */
public interface IProductAttributeService extends IBaseService<ProductAttribute> {
	/**查询所有的SHOP_商品扩展属性**/
	@SuppressWarnings("unchecked")
	public List findAllProductAttribute();
}
