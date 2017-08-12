package shop.common.service.imp;
/**
 * CoinRulesService 虚拟金币赠送规service接口实现类
 * @author mf
 */
import java.util.List;


import shop.common.dao.ICoinRulesDao;
import shop.common.pojo.CoinRules;
import shop.common.service.ICoinRulesService;
import util.service.BaseService;
@SuppressWarnings("all")
public class CoinRulesService extends BaseService<CoinRules> implements ICoinRulesService{
	private ICoinRulesDao coinRulesDao;
	public void setCoinRulesDao(ICoinRulesDao coinRulesDao) {
		this.baseDao=this.coinRulesDao = coinRulesDao;
	}
	/**
	 * 虚拟金币赠送规则根据列名去重
	 * 
	 * @param column  列名
	 * 
	 * @param whereCondition  查询条件
	 * 
	 * @return 返回List集合
	 */
	@SuppressWarnings("unchecked")
	public List distinctType(String column,String whereCondition){
		return coinRulesDao.findUnSame(column, whereCondition);
	}
}
