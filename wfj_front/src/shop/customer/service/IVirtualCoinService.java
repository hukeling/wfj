package shop.customer.service;
import java.util.Map;

import shop.customer.pojo.VirtualCoin;
import util.service.IBaseService;
/**
 * 虚拟金币Service接口
 * @author ss
 *
 */
public interface IVirtualCoinService extends IBaseService <VirtualCoin> {
	/**
	 * 注册赠送天海币
	 * @param id 用户ID 
	 * @param keyBookMap  天海币规则keyBookMap
	 */
	public void saveRegisterVirtualCoin(Integer id,Map<String,Object> keyBookMap);
	/**
	 * 推荐赠送天海币
	 * @param id  推荐人ID
	 * @param keyBookMap 天海币规则keyBookMap
	 */
	public void saveRecommendVirtualCoin(String id,Map<String, Object> keyBookMap);
}
