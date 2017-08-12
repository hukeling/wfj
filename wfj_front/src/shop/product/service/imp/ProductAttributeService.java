package shop.product.service.imp;
import java.util.List;

import shop.product.dao.IProductAttributeDao;
import shop.product.pojo.ProductAttribute;
import shop.product.service.IProductAttributeService;
import util.service.BaseService;
/**
 * ProductAttributeService - 类描述信息
 */
public class ProductAttributeService extends BaseService<ProductAttribute> implements
		IProductAttributeService {
	private IProductAttributeDao productAttributeDao;
	public void setProductAttributeDao(IProductAttributeDao productAttributeDao) {
		this.baseDao = this.productAttributeDao = productAttributeDao;
	}
	/**查询所有的SHOP_商品扩展属性**/
	@SuppressWarnings("unchecked")
	public List findAllProductAttribute(){
		return productAttributeDao.findAllProductAttribute();
	}
}
