package shop.homeModule.service.imp;
import shop.homeModule.dao.IDayRecommendLBTDao;
import shop.homeModule.pojo.DayRecommendLBT;
import shop.homeModule.service.IDayRecommendLBTService;
import util.service.BaseService;
/**
 * DayRecommendLBTService - 前台每日推荐轮播图Service接口实现类
 * @author 张攀攀
 * 2014-01-07
 *
 */
public class DayRecommendLBTService extends BaseService<DayRecommendLBT> implements IDayRecommendLBTService{
	@SuppressWarnings("unused")
	private IDayRecommendLBTDao dayRecommendLBTDao;
	public void setDayRecommendLBTDao(IDayRecommendLBTDao dayRecommendLBTDao) {
		this.baseDao = this.dayRecommendLBTDao = dayRecommendLBTDao;
	}
}
