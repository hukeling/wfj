package shop.rightShow.service.imp;
import shop.rightShow.dao.IRightShowInfoDao;
import shop.rightShow.pojo.RightShowInfo;
import shop.rightShow.service.IRightShowInfoService;
import util.service.BaseService;
/**
 * 首页右面模块信息service层实现类
 * @author 张攀攀
 */
public class RightShowInfoService extends BaseService<RightShowInfo> implements IRightShowInfoService {
	@SuppressWarnings("unused")
	private IRightShowInfoDao rightShowInfoDao;
	public void setRightShowInfoDao(IRightShowInfoDao rightShowInfoDao) {
		this.baseDao = this.rightShowInfoDao = rightShowInfoDao;
	}
}
