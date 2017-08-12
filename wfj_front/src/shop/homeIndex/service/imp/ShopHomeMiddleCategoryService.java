package shop.homeIndex.service.imp;
import shop.homeIndex.dao.IShopHomeMiddleCategoryDao;
import shop.homeIndex.pojo.ShopHomeMiddleCategory;
import shop.homeIndex.service.IShopHomeMiddleCategoryService;
import util.service.BaseService;
/**
 * 首页中间分类service层实现类
 * @author mqr
 * 2014-01-15
 */
public class ShopHomeMiddleCategoryService extends BaseService<ShopHomeMiddleCategory> implements IShopHomeMiddleCategoryService{
	@SuppressWarnings("unused")
	private IShopHomeMiddleCategoryDao shopHomeMiddleCategoryDao;
	public void setShopHomeMiddleCategoryDao(
			IShopHomeMiddleCategoryDao shopHomeMiddleCategoryDao) {
		this.baseDao=this.shopHomeMiddleCategoryDao = shopHomeMiddleCategoryDao;
	}
}
