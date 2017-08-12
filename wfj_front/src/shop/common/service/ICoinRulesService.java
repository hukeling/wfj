package shop.common.service;
/**
 * ICoinRulesService 虚拟金币赠送规则 service接口类
 * @author mf
 */
import java.util.List;

import shop.common.pojo.CoinRules;
import util.service.IBaseService;
public interface ICoinRulesService  extends IBaseService <CoinRules> {
	/**
	 * 虚拟金币赠送规则-根据列名去重
	 * 
	 * @param column  列名
	 * 
	 * @param whereCondition  查询条件
	 * 
	 * @return 返回List集合
	 */
	public List<String> distinctType(String column,String whereCondition);
}
