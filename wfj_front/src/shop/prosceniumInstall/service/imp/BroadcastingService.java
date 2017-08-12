package shop.prosceniumInstall.service.imp;
import shop.prosceniumInstall.dao.IBroadcastingDao;
import shop.prosceniumInstall.pojo.Broadcasting;
import shop.prosceniumInstall.service.IBroadcastingService;
import util.service.BaseService;
/**
 * BroadcastingService - 前台图片轮播Service接口实现类
 */
public class BroadcastingService extends BaseService  <Broadcasting> implements IBroadcastingService{
	@SuppressWarnings("unused")
	private IBroadcastingDao broadcastingDao;
	public void setBroadcastingDao(IBroadcastingDao broadcastingDao) {
		this.baseDao = this.broadcastingDao = broadcastingDao;
	}
}
