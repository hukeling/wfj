package shop.store.service;
import shop.store.pojo.ShopInfo;
import util.service.IBaseService;
/**
 * IShopInfoService - 店铺基本信息的service层
 * @Author §j*fm§
 */
public interface IShopInfoService extends IBaseService<ShopInfo> {
	public boolean saveOrUpdateIsCloss(Integer isClose,String shopInfoIds);
}
