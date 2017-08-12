package shop.store.service.imp;
import shop.store.dao.IShopInfoDao;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.service.BaseService;
/**
 * ShopInfoService - 店铺基本信息的service实现层
 * @Author §j*fm§
 */
public class ShopInfoService extends BaseService<ShopInfo> implements IShopInfoService {
	@SuppressWarnings("unused")
	private IShopInfoDao shopInfoDao;
	public void setShopInfoDao(IShopInfoDao shopInfoDao) {
		this.baseDao = this.shopInfoDao = shopInfoDao;
	}
	public boolean saveOrUpdateIsCloss(Integer isClose,String shopInfoIds){
		String sql = " update shop_shopinfo a SET a.isClose="+isClose+" where a.shopInfoId in ("+shopInfoIds+");";
		boolean flag = shopInfoDao.updateObject(sql);
		return flag;
	}
}
