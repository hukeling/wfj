package promotion.service.imp;
import promotion.dao.IPromotionApplyDao;
import promotion.pojo.PromotionApply;
import promotion.service.IPromotionApplyService;
import util.service.BaseService;
/**
 * PromotionApplyService - 热销Service层接口实现类
 */
public class PromotionApplyService extends BaseService<PromotionApply> implements IPromotionApplyService {
	@SuppressWarnings("unused")
	private IPromotionApplyDao promotionApplyDao;
	public void setPromotionApplyDao(IPromotionApplyDao promotionApplyDao) {
		this.baseDao = this.promotionApplyDao = promotionApplyDao;
	}
}
