package shop.product.service.imp;
import shop.product.dao.IProductSpecificationDao;
import shop.product.pojo.ProductSpecification;
import shop.product.service.IProductSpecificationService;
import util.service.BaseService;
/**
 * ProductSpecificationService - 商品和规格Service接口实现类
 */
public class ProductSpecificationService extends BaseService  <ProductSpecification> implements IProductSpecificationService{
	@SuppressWarnings("unused")
	private IProductSpecificationDao productSpecificationDao;
	public void setProductSpecificationDao(
			IProductSpecificationDao productSpecificationDao) {
		this.baseDao = this.productSpecificationDao = productSpecificationDao;
	}
}
