package phone.action;

import java.io.IOException;
import java.util.List;

import phone.util.JsonIgnore;

import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
/**
 * 老字号
 * @author hkl
 */
public class PhoneOldBrandAction extends BaseAction {
	/** 店铺Service **/
	private IShopInfoService shopInfoService;
	/**
	 * 老字号商铺查询
	 * @throws IOException 
	 */
	public void searchOldShop() throws IOException{
		String sql="where o.shopCategoryId>53 and o.shopCategoryId<58";
		List<ShopInfo> oldshoplist=shopInfoService.findObjects(sql);
		String [] ignoreString=JsonIgnore.getIgnoreShopInfo();
		JsonIgnore.outputJo(response, oldshoplist, ignoreString);
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
}
