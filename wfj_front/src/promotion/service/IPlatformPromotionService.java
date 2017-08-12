package promotion.service;
import promotion.pojo.PlatformPromotion;
import util.service.IBaseService;
/** 
* IPlatformPromotionService - 平台管理促销活动Service接口类 
* ============================================================================ 
* 版权所有 2010-2013 XXXX软件有限公司，并保留所有权利。 
* 官方网站：http://www.shopjsp.com
* ============================================================================ 
* @author 孟琦瑞
*/ 
public interface IPlatformPromotionService  extends IBaseService <PlatformPromotion> {
	/**
	 * 通过sql语句修改单个对象或者批量对象
	 */
	public boolean updateObject(String sql);
}
