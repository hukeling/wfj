package shop.returnsApply.service.imp;
import shop.returnsApply.dao.IReturnsApplyDao;
import shop.returnsApply.pojo.ReturnsApply;
import shop.returnsApply.service.IReturnsApplyService;
import util.service.BaseService;
/**
 * 退换申请Service接口实现类
 * @author ss
 *
 */
public class ReturnsApplyService extends BaseService  <ReturnsApply> implements IReturnsApplyService{
	@SuppressWarnings("unused")
	private IReturnsApplyDao returnsApplyDao;
	public void setReturnsApplyDao(IReturnsApplyDao returnsApplyDao) {
		this.baseDao = this.returnsApplyDao = returnsApplyDao;
	}
}
