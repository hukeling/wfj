package shop.store.service.imp;
import shop.store.dao.IShopBrandDao;
import shop.store.pojo.ShopBrand;
import shop.store.service.IShopBrandService;
import util.service.BaseService;
/** 
 * ShopBrandService - 店铺品牌关联表service接口实现类
 */
public class ShopBrandService extends BaseService<ShopBrand> implements IShopBrandService {
	@SuppressWarnings("unused")
	private IShopBrandDao shopBrandDao;
	public void setShopBrandDao(IShopBrandDao shopBrandDao) {
		this.baseDao=this.shopBrandDao = shopBrandDao;
	}
}
