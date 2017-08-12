package shop.logistics.service.imp;

import shop.logistics.dao.ILogisticsDao;
import shop.logistics.pojo.Logistics;
import shop.logistics.service.ILogisticsService;
import util.service.BaseService;


/**
 * 物流公司Service接口实现类
 * @author Administrator
 * 2014-07-25
 */
public class LogisticsService extends BaseService<Logistics> implements ILogisticsService{
	@SuppressWarnings("unused")
	private ILogisticsDao logisticsDao;

	public void setLogisticsDao(ILogisticsDao logisticsDao) {
		this.baseDao = this.logisticsDao = logisticsDao;
	}
	
}
