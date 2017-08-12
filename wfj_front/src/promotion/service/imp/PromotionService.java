package promotion.service.imp;
import promotion.dao.IPromotionDao;
import promotion.pojo.Promotion;
import promotion.service.IPromotionService;
import util.service.BaseService;
/**
 * 促销活动Service接口实现类
 * @author ss
 *
 */
public class PromotionService extends BaseService  <Promotion> implements IPromotionService{
	@SuppressWarnings("unused")
	private IPromotionDao promotionDao;
	public void setPromotionDao(IPromotionDao promotionDao) {
		this.baseDao =this.promotionDao= promotionDao;
	}
}