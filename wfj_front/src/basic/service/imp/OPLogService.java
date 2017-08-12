package basic.service.imp;
import util.service.BaseService;
import basic.dao.IOPLogDao;
import basic.pojo.OPLog;
import basic.service.IOPLogService;
/**
 * 操作日志记录Service接口实现类
 * 
 * @author 刘青松
 *
 */
public class OPLogService extends BaseService  <OPLog> implements IOPLogService {
	@SuppressWarnings("unused")
	private IOPLogDao opLogDao;
	public void setOpLogDao(IOPLogDao opLogDao) {
		this.baseDao =this.opLogDao = opLogDao;
	}
}
