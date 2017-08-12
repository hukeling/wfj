package shop.product.service.imp;
import java.util.List;

import shop.product.dao.IProductSpecificationValueDao;
import shop.product.pojo.ProductSpecificationValue;
import shop.product.service.IProductSpecificationValueService;
import util.service.BaseService;
/**
 * ProductSpecificationValueService - 商品和规格值Service接口实现类
 */
public class ProductSpecificationValueService extends BaseService  <ProductSpecificationValue> implements IProductSpecificationValueService{
	@SuppressWarnings("unused")
	private IProductSpecificationValueDao productSpecificationValueDao;
	public void setProductSpecificationValueDao(
			IProductSpecificationValueDao productSpecificationValueDao) {
		this.baseDao = this.productSpecificationValueDao = productSpecificationValueDao;
	}
	//根据列名去重数据
	@SuppressWarnings("unchecked")
	public List findUnDistinctList(String column,String params){
		return productSpecificationValueDao.findUnDistinctList(column, params);
	}
}
