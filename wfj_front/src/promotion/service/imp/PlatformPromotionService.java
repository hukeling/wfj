package promotion.service.imp;
import promotion.dao.IPlatformPromotionDao;
import promotion.pojo.PlatformPromotion;
import promotion.service.IPlatformPromotionService;
import util.service.BaseService;
/** 
* PlatformPromotionService - 平台管理促销活动Service接口实现类 
* ============================================================================ 
* 版权所有 2010-2013 XXXX软件有限公司，并保留所有权利。 
* 官方网站：http://www.shopjsp.com
* ============================================================================ 
* @author 孟琦瑞
*/ 
public class PlatformPromotionService extends BaseService  <PlatformPromotion> implements IPlatformPromotionService{
	private IPlatformPromotionDao platformPromotionDao;
	public void setPlatformPromotionDao(IPlatformPromotionDao platformPromotionDao) {
		this.baseDao =this.platformPromotionDao = platformPromotionDao;
	}
	
	public boolean updateObject(String sql){
		return platformPromotionDao.updateObject(sql);
	}
}