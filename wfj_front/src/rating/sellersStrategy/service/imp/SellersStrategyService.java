package rating.sellersStrategy.service.imp;
import rating.sellersStrategy.dao.ISellersStrategyDao;
import rating.sellersStrategy.pojo.SellersStrategy;
import rating.sellersStrategy.service.ISellersStrategyService;
import util.service.BaseService;
/**
 * 卖家等级策略Service接口实现
 * @author wsy
 *
 */
public class SellersStrategyService extends BaseService<SellersStrategy> implements ISellersStrategyService{
	@SuppressWarnings("unused")
	private ISellersStrategyDao sellersStrategyDao;//卖家等级策略Dao
	public void setSellersStrategyDao(ISellersStrategyDao sellersStrategyDao) {
		this.baseDao = this.sellersStrategyDao = sellersStrategyDao;
	}
}
