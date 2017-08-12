package tang.oldBrand.service.imp;
import tang.oldBrand.dao.IOldBrandDao;
import tang.oldBrand.pojo.OldBrand;
import tang.oldBrand.service.IOldBrandService;
import util.service.BaseService;
/**
 * 老字号Service接口实现类
 *
 */
public class OldBrandService extends BaseService  <OldBrand> implements IOldBrandService{
	@SuppressWarnings("unused")
	private IOldBrandDao oldBrandDao;

	public void setOldBrandDao(IOldBrandDao oldBrandDao) {
		this.baseDao =this.oldBrandDao = oldBrandDao;
	}
	
	
}