package shop.product.service.imp;
import shop.product.dao.IIntegralexChangeDao;
import shop.product.pojo.IntegralexChange;
import shop.product.service.IIntegralexChangeService;
import util.service.BaseService;
/**
 * 积分兑换Dao接口实现类
 * @author ss
 *
 */
public class IntegralexChangeService extends BaseService  <IntegralexChange> implements IIntegralexChangeService{
	@SuppressWarnings("unused")
	private IIntegralexChangeDao integralexChangeDao;
	public void setIntegralexChangeDao(IIntegralexChangeDao integralexChangeDao) {
		this.baseDao = this.integralexChangeDao = integralexChangeDao;
	}
}
