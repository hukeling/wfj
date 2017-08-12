package shop.shops.service.imp;
import shop.shops.dao.IShopsShopinfoDao;
import shop.shops.pojo.ShopsShopinfo;
import shop.shops.service.IShopsShopinfoService;
import util.service.BaseService;
/**
 * 商城与店铺关系service层实现类
 * @author 郑月龙
 *
 */
public class ShopsShopinfoService extends BaseService<ShopsShopinfo> implements IShopsShopinfoService{
	@SuppressWarnings("unused")
	private IShopsShopinfoDao ShopsShopinfoDao;
	public void setShopsShopinfoDao(IShopsShopinfoDao shopsShopinfoDao) {
		this.baseDao = this.ShopsShopinfoDao = shopsShopinfoDao;
	}
}
