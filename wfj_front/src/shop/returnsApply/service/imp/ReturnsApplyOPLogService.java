package shop.returnsApply.service.imp;
import shop.returnsApply.dao.IReturnsApplyOPLogDao;
import shop.returnsApply.pojo.ReturnsApplyOPLog;
import shop.returnsApply.service.IReturnsApplyOPLogService;
import util.service.BaseService;
/**
 * 退换申请操作日志Service接口实现类
 * @author ss
 *
 */
public class ReturnsApplyOPLogService extends BaseService  <ReturnsApplyOPLog> implements IReturnsApplyOPLogService{
	@SuppressWarnings("unused")
	private IReturnsApplyOPLogDao returnsApplyOPLogDao;
	public void setReturnsApplyOPLogDao(IReturnsApplyOPLogDao returnsApplyOPLogDao) {
		this.baseDao = this.returnsApplyOPLogDao = returnsApplyOPLogDao;
	}
}
