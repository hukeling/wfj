package shop.customer.service.imp;
import shop.customer.dao.IIncomePayDetailDao;
import shop.customer.pojo.IncomePayDetail;
import shop.customer.service.IIncomePayDetailService;
import util.service.BaseService;
/**
 * 收支明细Service接口实现类
 * @author ss
 *
 */
public class IncomePayDetailService extends BaseService  <IncomePayDetail> implements IIncomePayDetailService{
	@SuppressWarnings("unused")
	private IIncomePayDetailDao incomePayDetailDao;
	public void setIncomePayDetailDao(IIncomePayDetailDao incomePayDetailDao) {
		this.baseDao = this.incomePayDetailDao = incomePayDetailDao;
	}
}
