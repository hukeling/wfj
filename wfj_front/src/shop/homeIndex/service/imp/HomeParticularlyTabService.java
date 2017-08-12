package shop.homeIndex.service.imp;
import shop.homeIndex.dao.IHomeParticularlyTabDao;
import shop.homeIndex.pojo.HomeParticularlyTab;
import shop.homeIndex.service.IHomeParticularlyTabService;
import util.service.BaseService;
/**
 * 首页特别商品Service
 * 
 * @author whb
 * @time 20140115
 */
public class HomeParticularlyTabService extends BaseService<HomeParticularlyTab> implements
		IHomeParticularlyTabService {
	private IHomeParticularlyTabDao homeParticularlyTabDao;
	public void setHomeParticularlyTabDao(
			IHomeParticularlyTabDao homeParticularlyTabDao) {
		this.baseDao = this.homeParticularlyTabDao = homeParticularlyTabDao;
	}
}
