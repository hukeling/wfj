package shop.product.service.imp;
import shop.product.dao.ISpecificationDao;
import shop.product.pojo.Specification;
import shop.product.service.ISpecificationService;
import util.service.BaseService;
/**
 * SpecificationServiceService - 商品规格Service接口实现类
 */
public class SpecificationService extends BaseService  <Specification> implements ISpecificationService{
	@SuppressWarnings("unused")
	private ISpecificationDao specificationDao;
	public void setSpecificationDao(ISpecificationDao specificationDao) {
		this.baseDao = this.specificationDao = specificationDao;
	}
}
