package basic.service.imp;
import util.service.BaseService;
import basic.dao.IFunctionsDao;
import basic.pojo.Functions;
import basic.service.IFunctionsService;
/**
 * 功能权限Service接口实现类
 * @author 刘青松
 *
 */
	public class FunctionsService extends BaseService  <Functions> implements IFunctionsService {
		@SuppressWarnings("unused")
		private IFunctionsDao functionsDao;
		public void setFunctionsDao(IFunctionsDao functionsDao) {
			this.baseDao =this.functionsDao = functionsDao;
		}
	}
