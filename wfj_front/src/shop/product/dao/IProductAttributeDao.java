package shop.product.dao;
import java.util.List;

import shop.product.pojo.ProductAttribute;
import util.dao.IBaseDao;
/**
 * IProductAttribute - 类描述信息
 */
public interface IProductAttributeDao extends IBaseDao<ProductAttribute> {
	/**查询所有的SHOP_商品扩展属性**/
	@SuppressWarnings("unchecked")
	public List findAllProductAttribute();
}
