package shop.store.service.imp;
import shop.store.dao.IShopProductDao;
import shop.store.pojo.ShopProduct;
import shop.store.service.IShopProductService;
import util.service.BaseService;
/**
 * ShopCategoryService - 店铺商品Service接口实现类
 * @author 孟琦瑞
 */
@SuppressWarnings("unused")
public class ShopProductService extends BaseService  <ShopProduct> implements IShopProductService{
	private IShopProductDao shopProductDao;//店铺商品Dao
	public void setShopProductDao(IShopProductDao shopProductDao) {
		this.baseDao = this.shopProductDao = shopProductDao;
	}
}
