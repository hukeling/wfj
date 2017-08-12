package promotion.service.imp;
import promotion.dao.IDisproductDao;
import promotion.pojo.SalesPromotionDiscount;
import promotion.service.IDisproductService;
import util.service.BaseService;
/**
 * 商品折扣Service接口实现类
 * @author ss
 *
 */
public class DisproductService extends BaseService  <SalesPromotionDiscount> implements IDisproductService{
	@SuppressWarnings("unused")
	private IDisproductDao disproductDao;
	public void setDisproductDao(IDisproductDao disproductDao) {
		this.baseDao =this.disproductDao= disproductDao;
	}
}