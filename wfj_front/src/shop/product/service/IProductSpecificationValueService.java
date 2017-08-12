package shop.product.service;
import java.util.List;

import shop.product.pojo.ProductSpecificationValue;
import util.service.IBaseService;
/**
 * IProductSpecificationValueService - 商品和规格值Service接口
 */
public interface IProductSpecificationValueService extends IBaseService <ProductSpecificationValue> {
	//根据列名去重数据
		@SuppressWarnings("unchecked")
		public List findUnDistinctList(String column,String params);
}
