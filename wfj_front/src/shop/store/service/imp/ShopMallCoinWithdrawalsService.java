package shop.store.service.imp;
import shop.store.dao.IShopMallCoinWithdrawalsDao;
import shop.store.pojo.ShopMallCoinWithdrawals;
import shop.store.service.IShopMallCoinWithdrawalsService;
import util.service.BaseService;
/**
 * ShopInfoService - 供应商商城币提现的service实现层
 * @Author wy
 */
public class ShopMallCoinWithdrawalsService extends BaseService<ShopMallCoinWithdrawals> implements IShopMallCoinWithdrawalsService {
	@SuppressWarnings("unused")
	private IShopMallCoinWithdrawalsDao ShopMallCoinWithdrawalsDao;
	public void setShopMallCoinWithdrawalsDao(IShopMallCoinWithdrawalsDao ShopMallCoinWithdrawalsDao) {
		this.baseDao = this.ShopMallCoinWithdrawalsDao = ShopMallCoinWithdrawalsDao;
	}
}
