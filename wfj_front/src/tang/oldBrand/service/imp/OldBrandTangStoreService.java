package tang.oldBrand.service.imp;
import tang.oldBrand.dao.IOldBrandTangStoreDao;
import tang.oldBrand.pojo.OldBrandTangStore;
import tang.oldBrand.service.IOldBrandTangStoreService;
import util.service.BaseService;
/**
 * 老字号店铺关联Service接口实现类
 *
 */
public class OldBrandTangStoreService extends BaseService  <OldBrandTangStore> implements IOldBrandTangStoreService{
	@SuppressWarnings("unused")
	private IOldBrandTangStoreDao oldBrandTangStoreDao;

	public void setOldBrandTangStoreDao(IOldBrandTangStoreDao oldBrandTangStoreDao) {
		this.baseDao =this.oldBrandTangStoreDao = oldBrandTangStoreDao;
	}

}