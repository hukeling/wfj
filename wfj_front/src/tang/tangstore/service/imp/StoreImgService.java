package tang.tangstore.service.imp;

import tang.tangstore.dao.IStoreImgDao;
import tang.tangstore.pojo.StoreImg;
import tang.tangstore.service.IStoreImgService;
import util.service.BaseService;

public class StoreImgService extends BaseService<StoreImg> implements IStoreImgService {

	@SuppressWarnings("unused")
	private IStoreImgDao storeImgDao;

	public void setStoreImgDao(IStoreImgDao storeImgDao) {
		this.baseDao=this.storeImgDao = storeImgDao;
	}
	
}
