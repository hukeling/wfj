package shop.homeIndex.service.imp;
import shop.homeIndex.dao.IShopHomeMiddleCategoryBilateralDao;
import shop.homeIndex.pojo.ShopHomeMiddleCategoryBilateral;
import shop.homeIndex.service.IShopHomeMiddleCategoryBilateralService;
import util.service.BaseService;
/**
 * 首页中间分类两侧维护service层实现类
 * @author mqr
 * 2014-01-15
 */
public class ShopHomeMiddleCategoryBilateralService extends BaseService<ShopHomeMiddleCategoryBilateral> implements IShopHomeMiddleCategoryBilateralService{
	@SuppressWarnings("unused")
	private IShopHomeMiddleCategoryBilateralDao shopHomeMiddleCategoryBilateralDao;
	public void setShopHomeMiddleCategoryBilateralDao(
			IShopHomeMiddleCategoryBilateralDao shopHomeMiddleCategoryBilateralDao) {
		this.baseDao=this.shopHomeMiddleCategoryBilateralDao = shopHomeMiddleCategoryBilateralDao;
	}
}
