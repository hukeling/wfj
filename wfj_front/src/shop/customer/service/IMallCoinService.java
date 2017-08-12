package shop.customer.service;
import java.util.Map;

import shop.customer.pojo.MallCoin;
import util.service.IBaseService;
/**
 * 商城币Service接口
 * @author mf
 */
public interface IMallCoinService extends IBaseService <MallCoin> {
	/**
	 * 注册赠送商城币
	 * @param id 用户ID 
	 * @param keyBookMap  天海币规则keyBookMap
	 */
	public void saveRegisterVirtualCoin(Integer id,Map<String,Object> keyBookMap,Integer type);
	/**
	 * 推荐赠送商城币
	 * @param id  推荐人ID
	 * @param keyBookMap 天海币规则keyBookMap
	 */
	public void saveRecommendVirtualCoin(String id,Map<String, Object> keyBookMap);
}
