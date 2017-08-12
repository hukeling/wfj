package tang.tangstore.service.imp;

import tang.tangstore.dao.ITangTrafficDao;
import tang.tangstore.pojo.TangTraffic;
import tang.tangstore.service.ITangTrafficService;
import util.service.BaseService;

public class TangTrafficService extends BaseService<TangTraffic> implements ITangTrafficService {

	@SuppressWarnings("unused")
	private ITangTrafficDao tangTrafficDao;

	public void setTangTrafficDao(ITangTrafficDao tangTrafficDao) {
		this.baseDao=this.tangTrafficDao = tangTrafficDao;
	}
	
	
}
