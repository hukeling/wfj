package promotion.service.imp;
import promotion.dao.IStoreApplyPromotionDao;
import promotion.pojo.StoreApplyPromotion;
import promotion.service.IStoreApplyPromotionService;
import util.service.BaseService;
/** 
* StoreApplyPromotionService - 店铺申请促销活动Service接口实现类 
* ============================================================================ 
* 版权所有 2010-2013 XXXX软件有限公司，并保留所有权利。 
* 官方网站：http://www.shopjsp.com
* ============================================================================ 
* @author 孟琦瑞
*/ 
public class StoreApplyPromotionService extends BaseService  <StoreApplyPromotion> implements IStoreApplyPromotionService{
	@SuppressWarnings("unused")
	private IStoreApplyPromotionDao storeApplyPromotionDao;
	public void setStoreApplyPromotionDao(
			IStoreApplyPromotionDao storeApplyPromotionDao) {
		this.baseDao =this.storeApplyPromotionDao = storeApplyPromotionDao;
	}
}