package promotion.service.imp;
import promotion.dao.ISalesPromotionDiscountDao;
import promotion.pojo.SalesPromotionDiscount;
import promotion.service.ISalesPromotionDiscountService;
import util.service.BaseService;
/**
 * 平台管理促销活动折扣Service接口实现类
 * @author wy
 *
 */
public class SalesPromotionDiscountService extends BaseService  <SalesPromotionDiscount> implements ISalesPromotionDiscountService{
	@SuppressWarnings("unused")
	private ISalesPromotionDiscountDao salesPromotionDiscountDao;
	public void setSalesPromotionDiscountDao(ISalesPromotionDiscountDao salesPromotionDiscountDao) {
		this.baseDao =this.salesPromotionDiscountDao= salesPromotionDiscountDao;
	}
}