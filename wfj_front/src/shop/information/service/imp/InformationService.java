package shop.information.service.imp;
/**
 * InformationService 店铺内部资讯分类  service接口实现类
 * @author mqr
 */
import shop.information.dao.IInformationDao;
import shop.information.pojo.Information;
import shop.information.service.IInformationService;
import util.service.BaseService;
public class InformationService extends BaseService<Information> implements IInformationService{
	@SuppressWarnings("unused")
	private IInformationDao informationDao;
	public void setInformationDao(
			IInformationDao informationDao) {
		this.baseDao=this.informationDao = informationDao;
	}
}
