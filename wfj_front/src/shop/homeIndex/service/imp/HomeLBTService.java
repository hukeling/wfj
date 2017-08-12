package shop.homeIndex.service.imp;
import shop.homeIndex.dao.IHomeLBTDao;
import shop.homeIndex.pojo.HomeLBT;
import shop.homeIndex.service.IHomeLBTService;
import util.service.BaseService;
/**
 * 首页轮播图Service
 * 
 * @author whb
 * @time 20140113
 */
public class HomeLBTService extends BaseService<HomeLBT> implements
		IHomeLBTService {
	private IHomeLBTDao homeLBTDao;
	public void setHomeLBTDao(IHomeLBTDao homeLBTDao) {
		this.baseDao = this.homeLBTDao = homeLBTDao;
	}
}
