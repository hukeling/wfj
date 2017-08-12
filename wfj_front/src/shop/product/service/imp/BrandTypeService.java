package shop.product.service.imp;
import shop.product.dao.IBrandTypeDao;
import shop.product.pojo.BrandType;
import shop.product.service.IBrandTypeService;
import util.service.BaseService;
/**
 * 品牌和分类Service接口实现类
 * @author ss
 *
 */
public class BrandTypeService extends BaseService <BrandType> implements IBrandTypeService{
	@SuppressWarnings("unused")
	private IBrandTypeDao brandTypeDao;
	public void setBrandTypeDao(IBrandTypeDao brandTypeDao) {
		this.baseDao=this.brandTypeDao = brandTypeDao;
	}
	/**
	 * 批量保存品牌分类关系
	 * @param brandIds
	 * @param productTypeId
	 * @return
	 */
	public boolean saveMoreBrandType(Integer[] brandIds,Integer productTypeId ){
		boolean flag = true;
		for(Integer brandId:brandIds){
			BrandType brandType = new BrandType();
			brandType.setBrandTypeId(null);
			brandType.setBrandId(brandId);
			brandType.setProductTypeId(productTypeId);
			brandType = brandTypeDao.saveOrUpdateObject(brandType);
			if(brandType.getBrandTypeId()==null){
				flag = false;
			}
		}
		return flag;
	}
}