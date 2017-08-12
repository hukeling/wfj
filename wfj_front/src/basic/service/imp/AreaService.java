package basic.service.imp;
import util.service.BaseService;
import basic.dao.IAreaDao;
import basic.pojo.BasicArea;
import basic.service.IAreaService;
/**
 * 区域Service接口实现类
 * @author ACER
 * 2013-05-16
 */
public class AreaService extends BaseService<BasicArea> implements IAreaService {
	@SuppressWarnings("unused")
	private IAreaDao areaDao;
	public void setAreaDao(IAreaDao areaDao) {
		this.baseDao = this.areaDao = areaDao;
	}
}
