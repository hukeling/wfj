package shop.customer.service.imp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import shop.common.pojo.CoinRules;
import shop.customer.dao.IVirtualCoinDao;
import shop.customer.pojo.VirtualCoin;
import shop.customer.service.IVirtualCoinService;
import util.other.SerialNumberUtil;
import util.service.BaseService;
/**
 * 虚拟金币Service接口实现类
 * 
 * @author ss
 * 
 */
public class VirtualCoinService extends BaseService<VirtualCoin> implements
		IVirtualCoinService {
	/** 虚拟金币dao **/
	private IVirtualCoinDao virtualCoinDao;
	/**
	 * 注册赠送天海币
	 * 
	 * @param id
	 *            用户ID
	 * @param keyBookMap
	 *            天海币规则keyBookMap
	 */
	@SuppressWarnings("unchecked")
	public void saveRegisterVirtualCoin(Integer id,Map<String, Object> keyBookMap) {
		VirtualCoin v = new VirtualCoin();// 新建虚拟金币明细
		v.setCustomerId(id);// 插入客户ID
		v.setSerialNumber(SerialNumberUtil.VirtualCoinNumber());// 虚拟金币流水号
		v.setType(1);// 类型为1 代表收入
		v.setOrdersId(0);// 没有订单ID 所以填0
		v.setOrdersNo("");
		v.setTradeTime(new Date());// 时间
		v.setOperatorTime(new Date());
		v.setRemarks("注册所得");
		// 从虚拟金币数据字典中中获取注册应该赠送的天海币数量
		List<CoinRules> crList = (List<CoinRules>)keyBookMap.get("coinRulesRegister");
		String value = crList.get(0).getValue();
		v.setTransactionNumber(new BigDecimal(value));// 赠送金币
		v.setRemainingNumber(new BigDecimal(value));// 用户剩余金币
		virtualCoinDao.saveOrUpdateObject(v);
	}
	/**
	 * 推荐赠送天海币
	 * @param id  推荐人ID
	 * @param keyBookMap 天海币规则keyBookMap
	 */
	@SuppressWarnings("unchecked")
	public void saveRecommendVirtualCoin(String id,Map<String, Object> keyBookMap) {
		VirtualCoin v1 = (VirtualCoin) virtualCoinDao.get(" where o.customerId = "+Integer.valueOf(id)+" order by o.tradeTime desc limit 0,1 ");//查询该id的用户剩余天海币数量
		VirtualCoin v2 = new VirtualCoin();
		v2.setCustomerId(Integer.valueOf(id));
		v2.setType(1);//类型为1  代表收入
		v2.setSerialNumber(SerialNumberUtil.VirtualCoinNumber());
		v2.setTradeTime(new Date());
		v2.setOperatorTime(new Date());
		v2.setOrdersId(0);//没有订单ID  所以填0
		v2.setOrdersNo("");
		v2.setRemarks("推荐所得");
		List<CoinRules> crList = (List<CoinRules>) keyBookMap.get("coinRulesRecommend");
		String value = crList.get(0).getValue();
		v2.setTransactionNumber(new BigDecimal(value));//赠送金币
		if(v1!=null){
			BigDecimal remainingNumber = v1.getRemainingNumber().add(new BigDecimal(value));
			v2.setRemainingNumber(remainingNumber);//用户剩余金币
		}else{
			v2.setRemainingNumber(new BigDecimal(value));
		}
		virtualCoinDao.saveOrUpdateObject(v2);
	}
	public void setVirtualCoinDao(IVirtualCoinDao virtualCoinDao) {
		this.baseDao = this.virtualCoinDao = virtualCoinDao;
	}
}