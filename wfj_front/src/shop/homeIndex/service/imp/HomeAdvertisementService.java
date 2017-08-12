package shop.homeIndex.service.imp;
import shop.homeIndex.dao.IHomeAdvertisementDao;
import shop.homeIndex.pojo.HomeAdvertisement;
import shop.homeIndex.service.IHomeAdvertisementService;
import util.service.BaseService;
/**
 * 首页广告位Service
 * 
 * @author whb
 * @time 20140115
 */
public class HomeAdvertisementService extends BaseService<HomeAdvertisement> implements
		IHomeAdvertisementService {
	private IHomeAdvertisementDao homeAdvertisementDao;
	public void setHomeAdvertisementDao(IHomeAdvertisementDao homeAdvertisementDao) {
		this.baseDao = this.homeAdvertisementDao = homeAdvertisementDao;
	}
}
