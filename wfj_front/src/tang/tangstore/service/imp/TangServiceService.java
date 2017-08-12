package tang.tangstore.service.imp;

import tang.tangstore.dao.ITangServiceDao;
import tang.tangstore.pojo.TangService;
import tang.tangstore.service.ITangServiceService;
import util.service.BaseService;

public class TangServiceService extends BaseService<TangService> implements ITangServiceService {
	@SuppressWarnings("unused")
	private ITangServiceDao tangServiceDao;
	public void setTangServiceDao(ITangServiceDao tangServiceDao) {
		this.baseDao =this.tangServiceDao = tangServiceDao;
	}
	
	
	
}
