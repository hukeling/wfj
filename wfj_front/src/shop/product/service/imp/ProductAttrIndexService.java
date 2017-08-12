package shop.product.service.imp;

import shop.product.dao.IProductAttrIndexDao;
import shop.product.pojo.ProductAttrIndex;
import shop.product.service.IProductAttrIndexService;
import util.service.BaseService;
/**
 * ProductAttributeService - 类描述信息
 */
public class ProductAttrIndexService extends BaseService<ProductAttrIndex> implements
		IProductAttrIndexService {
	@SuppressWarnings("unused")
	private IProductAttrIndexDao productAttrIndexDao;
	public void setProductAttrIndexDao(IProductAttrIndexDao productAttrIndexDao) {
		this.baseDao = this.productAttrIndexDao = productAttrIndexDao;
	}
}
