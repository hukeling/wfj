package shop.product.service.imp;
import shop.product.dao.ISpecificationValueDao;
import shop.product.pojo.SpecificationValue;
import shop.product.service.ISpecificationValueService;
import util.service.BaseService;
/**
 * SpecificationValueService - 商品规格值Service接口实现类
 */
public class SpecificationValueService extends BaseService <SpecificationValue> implements ISpecificationValueService{
	@SuppressWarnings("unused")
	private ISpecificationValueDao specificationValueDao;
	public void setSpecificationValueDao(
			ISpecificationValueDao specificationValueDao) {
		this.baseDao = this.specificationValueDao = specificationValueDao;
	}
}
