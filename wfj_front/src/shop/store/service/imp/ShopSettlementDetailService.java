package shop.store.service.imp;
import java.util.Date;

import shop.store.dao.IShopSettlementDetailDao;
import shop.store.pojo.ShopSettlementDetail;
import shop.store.service.IShopSettlementDetailService;
import util.service.BaseService;
import basic.pojo.Users;
/**
 * 店铺申请结算明细表service实现类
 * 
 * @author 刘钦楠
 * 
 */
public class ShopSettlementDetailService extends
		BaseService<ShopSettlementDetail> implements
		IShopSettlementDetailService {
	private IShopSettlementDetailDao shopSettlementDetailDao;
	/**
	 * 修改店铺申请结算审核状态 审核不可逆
	 * 
	 * @return
	 */
	public ShopSettlementDetail updateReviewStatus(String id, Users users,
			String status) {
		ShopSettlementDetail shopSettlementDetail = shopSettlementDetailDao.getObjectById(id);
		if(shopSettlementDetail!=null&&"1".equals(status)){
			shopSettlementDetail.setReviewer(users.getUserName());
			shopSettlementDetail.setReviewerDate(new Date());
			shopSettlementDetail.setStatus(Integer.valueOf(status));
		}
		return shopSettlementDetail;
	}
	public void setShopSettlementDetailDao(
			IShopSettlementDetailDao shopSettlementDetailDao) {
		this.baseDao = this.shopSettlementDetailDao = shopSettlementDetailDao;
	}
}
