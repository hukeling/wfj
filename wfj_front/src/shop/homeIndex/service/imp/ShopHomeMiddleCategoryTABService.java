package shop.homeIndex.service.imp;
import shop.homeIndex.dao.IShopHomeMiddleCategoryTABDao;
import shop.homeIndex.pojo.ShopHomeMiddleCategoryTAB;
import shop.homeIndex.service.IShopHomeMiddleCategoryTABService;
import util.service.BaseService;
/**
 * 首页中间分类tab service层实现类
 * @author mqr
 * 2014-01-15
 */
public class ShopHomeMiddleCategoryTABService extends BaseService<ShopHomeMiddleCategoryTAB> implements IShopHomeMiddleCategoryTABService{
	@SuppressWarnings("unused")
	private IShopHomeMiddleCategoryTABDao shopHomeMiddleCategoryTABDao;
	public void setShopHomeMiddleCategoryTABDao(
			IShopHomeMiddleCategoryTABDao shopHomeMiddleCategoryTABDao) {
		this.baseDao=this.shopHomeMiddleCategoryTABDao = shopHomeMiddleCategoryTABDao;
	}
}
