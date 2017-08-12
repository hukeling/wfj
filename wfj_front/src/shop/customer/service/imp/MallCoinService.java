package shop.customer.service.imp;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import shop.common.pojo.CoinRules;
import shop.customer.dao.IMallCoinDao;
import shop.customer.pojo.MallCoin;
import shop.customer.service.IMallCoinService;
import util.other.SerialNumberUtil;
import util.service.BaseService;
/**
 * 商城币Service接口实现类
 * @author mf
 */
public class MallCoinService extends BaseService<MallCoin> implements
		IMallCoinService {
	/** 商城币dao **/
	private IMallCoinDao mallCoinDao;
	/**
	 * 注册赠送商城币
	 * 
	 * @param id
	 *            用户ID
	 * @param keyBookMap
	 *            商城币规则keyBookMap
	 */
	@SuppressWarnings("unchecked")
	public void saveRegisterVirtualCoin(Integer id,Map<String, Object> keyBookMap,Integer type) {
		MallCoin v = new MallCoin();// 新建虚拟金币明细
		v.setCustomerId(id);// 插入客户ID
		v.setSerialNumber(SerialNumberUtil.VirtualCoinNumber());// 虚拟金币流水号
		v.setType(1);// 类型为1 代表收入
		v.setOrdersId(0);// 没有订单ID 所以填0
		v.setOrdersNo("");
		v.setTradeTime(new Date());// 时间
		v.setOperatorTime(new Date());
		// 从虚拟金币数据字典中中获取注册应该赠送的天海币数量
		List<CoinRules> crList=new ArrayList<CoinRules>();
		if(type==1){
			crList = (List<CoinRules>)keyBookMap.get("coinRulesRegister");
			v.setRemarks("新用户注册赠送！");
			String value = crList.get(0).getValue();
			v.setRemainingNumber(new BigDecimal(value));// 用户剩余金币
			v.setTransactionNumber(new BigDecimal(value));// 赠送金币
		}else{
			crList = (List<CoinRules>)keyBookMap.get("coinRulesRecommend");
			v.setRemarks("分享用户注册赠送！");
			String value = crList.get(0).getValue();
			v.setTransactionNumber(new BigDecimal(value));// 赠送金币
			List<MallCoin> mcl = mallCoinDao.findObjects(" where o.customerId = "+String.valueOf(id)+" order by o.tradeTime desc limit 1");
			if(mcl!=null&&mcl.size()>0){
				MallCoin mc = mcl.get(0);
				BigDecimal remainingNumber = mc.getRemainingNumber();
				remainingNumber=remainingNumber.add(new BigDecimal(value));
				v.setRemainingNumber(remainingNumber);// 用户剩余金币
			}else{
				v.setRemainingNumber(new BigDecimal(value));// 用户剩余金币
			}
		}
		mallCoinDao.saveOrUpdateObject(v);
	}
	/**
	 * 推荐赠送商城币
	 * @param id  推荐人ID
	 * @param keyBookMap 商城币规则keyBookMap
	 */
	@SuppressWarnings("unchecked")
	public void saveRecommendVirtualCoin(String id,Map<String, Object> keyBookMap) {
		MallCoin v1 = (MallCoin) mallCoinDao.get(" where o.customerId = "+Integer.valueOf(id)+" order by o.tradeTime desc limit 0,1 ");//查询该id的用户剩余天海币数量
		MallCoin v2 = new MallCoin();
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
		mallCoinDao.saveOrUpdateObject(v2);
	}
	public void setMallCoinDao(IMallCoinDao mallCoinDao) {
		this.baseDao=this.mallCoinDao = mallCoinDao;
	}
}