package tang.tangstore.service.imp;

import tang.tangstore.dao.IStoreServiceDao;
import tang.tangstore.pojo.StoreService;
import tang.tangstore.service.IStoreServiceService;
import tang.tangstore.service.ITangStoreService;
import util.service.BaseService;

public class StoreServiceService extends BaseService<StoreService> implements IStoreServiceService {
	
	@SuppressWarnings("unused")
	private IStoreServiceDao storeServiceDao;

	public void setStoreServiceDao(IStoreServiceDao storeServiceDao) {
		this.baseDao=this.storeServiceDao = storeServiceDao;
	}
	
}
