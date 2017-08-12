package shop.common.service.imp;
import shop.common.dao.IShopSeoDao;
import shop.common.pojo.ShopSeo;
import shop.common.service.IShopSeoService;
import util.service.BaseService;
/**
 * 商城SEOService接口实现类
 * @author ss
 *
 */
public class ShopSeoService extends BaseService  <ShopSeo> implements IShopSeoService{
	@SuppressWarnings("unused")
	private IShopSeoDao shopSeoDao;
	public void setShopSeoDao(IShopSeoDao shopSeoDao) {
		this.baseDao =this.shopSeoDao = shopSeoDao;
	}
}
