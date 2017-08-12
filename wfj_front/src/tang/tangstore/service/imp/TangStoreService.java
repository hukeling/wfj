package tang.tangstore.service.imp;

import tang.tangstore.dao.ITangStoreDao;
import tang.tangstore.pojo.TangStore;
import tang.tangstore.service.ITangStoreService;
import util.service.BaseService;

public class TangStoreService extends BaseService<TangStore> implements ITangStoreService {
	@SuppressWarnings("unused")
	private ITangStoreDao tangStoreDao;
	public void setTangStoreDao(ITangStoreDao tangStoreDao) {
		this.baseDao =this.tangStoreDao = tangStoreDao;
	}
	
	
	
}
