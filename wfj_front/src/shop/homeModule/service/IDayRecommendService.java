package shop.homeModule.service;
import shop.homeModule.pojo.DayRecommend;
import util.service.IBaseService;
/**
 * IDayRecommendService - 前台每日推荐Service接口
 * @author 张攀攀
 * 2014-01-07
 *
 */
public interface IDayRecommendService extends IBaseService<DayRecommend> {
	/**
	 * 保存每日推荐商品
	 */
	public Boolean saveOrUpdateDayRecommend(DayRecommend dayRecommends,String productData);
}
