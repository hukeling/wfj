package shop.front.store.service.imp;
import shop.front.store.dao.IShopProCateClassDao;
import shop.front.store.pojo.ShopProCateClass;
import shop.front.store.service.IShopProCateClassService;
import util.service.BaseService;
/**
 * ShopProCateClassService - 店铺内部商品分类和商品关系Service接口实现类
 */
public class ShopProCateClassService extends BaseService<ShopProCateClass> implements IShopProCateClassService{
	@SuppressWarnings("unused")
	private IShopProCateClassDao shopProCateClassDao;
	public void setShopProCateClassDao(IShopProCateClassDao shopProCateClassDao) {
		this.baseDao = this.shopProCateClassDao = shopProCateClassDao;
	}
}
