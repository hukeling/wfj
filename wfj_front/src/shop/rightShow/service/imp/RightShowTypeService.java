package shop.rightShow.service.imp;
import shop.rightShow.dao.IRightShowTypeDao;
import shop.rightShow.pojo.RightShowType;
import shop.rightShow.service.IRightShowTypeService;
import util.service.BaseService;
/**
 * 首页右面模块分类service层实现类
 * @author 张攀攀
 */
public class RightShowTypeService extends BaseService<RightShowType> implements IRightShowTypeService {
	@SuppressWarnings("unused")
	private IRightShowTypeDao rightShowTypeDao;
	public void setRightShowTypeDao(IRightShowTypeDao rightShowTypeDao) {
		this.baseDao = this.rightShowTypeDao = rightShowTypeDao;
	}
}
