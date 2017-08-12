package rating.buyersStrategy.service.imp;
import rating.buyersStrategy.dao.IBuyersStrategyDao;
import rating.buyersStrategy.pojo.BuyersStrategy;
import rating.buyersStrategy.service.IBuyersStrategyService;
import util.service.BaseService;
/**
 * 买家等级策略Service接口实现
 * @author wsy
 *
 */
public class BuyersStrategyService extends BaseService<BuyersStrategy> implements IBuyersStrategyService{
	@SuppressWarnings("unused")
	private IBuyersStrategyDao buyersStrategyDao;//买家等级策略Dao
	public void setBuyersStrategyDao(IBuyersStrategyDao buyersStrategyDao) {
		this.baseDao = this.buyersStrategyDao = buyersStrategyDao;
	}
}
