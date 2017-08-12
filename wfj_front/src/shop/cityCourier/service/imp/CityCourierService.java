package shop.cityCourier.service.imp;
import shop.cityCourier.dao.ICityCourierDao;
import shop.cityCourier.pojo.CityCourier;
import shop.cityCourier.service.ICityCourierService;
/**
 * CityCourierService 同城快递员人事档案service接口实现类
 * @author wy
 */
import util.service.BaseService;
@SuppressWarnings("all")
public class CityCourierService extends BaseService<CityCourier> implements ICityCourierService{
	private ICityCourierDao cityCourierDao;
	public void setCityCourierDao(ICityCourierDao cityCourierDao) {
		this.baseDao=this.cityCourierDao = cityCourierDao;
	}
}
