package shop.shops.service.imp;
import shop.shops.dao.IShopsDao;
import shop.shops.pojo.Shops;
import shop.shops.service.IShopsService;
import util.service.BaseService;
/**
 * 商城(线下实际商城)service层实现类
 * @author 郑月龙
 *
 */
public class ShopsService extends BaseService<Shops> implements IShopsService{
	@SuppressWarnings("unused")
	private IShopsDao shopsDao;
	public void setShopsDao(IShopsDao shopsDao) {
		this.baseDao = this.shopsDao = shopsDao;
	}
}
