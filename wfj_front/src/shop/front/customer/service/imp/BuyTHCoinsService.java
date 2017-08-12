package shop.front.customer.service.imp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import shop.common.pojo.CoinRules;
import shop.customer.dao.IIncomePayDetailDao;
import shop.customer.dao.IVirtualCoinDao;
import shop.customer.pojo.Customer;
import shop.customer.pojo.IncomePayDetail;
import shop.customer.pojo.VirtualCoin;
import shop.front.customer.service.IBuyTHCoinsService;
import util.other.SerialNumberUtil;
/**
 * 购买天海币service接口实现类
 * @author mf
 *
 */
public class BuyTHCoinsService implements IBuyTHCoinsService {
	/**收支明细dao**/
	private IIncomePayDetailDao incomePayDetailDao;
	/**天海币明细dao**/
	private IVirtualCoinDao virtualCoinDao;
	/**
	 * 购买天海币后执行操作
	 * 1.插入收支明细记录
	 * 2.插入天海比明细记录
	 * @param bd 购买金额
	 * @param proportion 购买天海币比例
	 * @author mf
	 */
	public void saveBuyInfo(Customer customer ,Integer bd,Integer proportion,ServletContext servletContext){
		//1.收支明细
		IncomePayDetail ipd=new IncomePayDetail();
		ipd.setPayerName(customer.getLoginName());//付款人
		ipd.setPayerId(customer.getCustomerId());//付款人id
		ipd.setPayeeId(0);//收款人id  0：代表平台
		ipd.setPayeeName("平台");//收款人姓名
		String virtualCoinNumber = SerialNumberUtil.VirtualCoinNumber();
		ipd.setSerialNumber(virtualCoinNumber);//流水号
		ipd.setTradeTime(new Date());//交易时间
		ipd.setIncomeExpensesType(2);//交易类型  2：会员支付
		BigDecimal bigDicimal=new BigDecimal(bd);
		ipd.setOutlay(bigDicimal);//交易金额
		ipd.setRemarks("从平台购买"+String.valueOf(bd)+"元的天海币,共:"+String.valueOf(bd*proportion)+"个");
		incomePayDetailDao.saveOrUpdateObject(ipd);
		//2.天海币明细
		String virtualCoinNumber2 = SerialNumberUtil.VirtualCoinNumber();
		VirtualCoin vc=new VirtualCoin();
		vc.setCustomerId(customer.getCustomerId());//客户id
		vc.setSerialNumber(virtualCoinNumber2);//流水号
		vc.setType(1);//类型 1：收入
		vc.setTransactionNumber(new BigDecimal(bd*proportion));//交易数量
		vc.setOperatorTime(new Date());//交易时间
		vc.setRemarks("从平台购买"+String.valueOf(bd)+"元的天海币,共:"+String.valueOf(bd*proportion)+"个");
		List<VirtualCoin> findSome = virtualCoinDao.findSome(0, 1, " where o.customerId="+customer.getCustomerId()+" order by o.operatorTime desc");
		VirtualCoin virtualCoin = findSome.get(0);
		//查询此时天海比购买比例
		Map<String, Object> keyBookMap = (Map<String, Object>) servletContext.getAttribute("coinRules");//获取天海数据字典
		List<CoinRules> coinRulesList= (List<CoinRules>) keyBookMap.get("purchaseProportion");
		CoinRules coinRules= coinRulesList.get(0);
		vc.setProportion(Integer.parseInt(coinRules.getValue()));
		//账户余额计算
		BigDecimal remainingNumber = virtualCoin.getRemainingNumber();
		BigDecimal opNumber = new BigDecimal(bd*proportion);
		remainingNumber = remainingNumber.add(opNumber);
		vc.setRemainingNumber(remainingNumber);
		virtualCoinDao.saveOrUpdateObject(vc);
	}
	//setter getter
	public void setIncomePayDetailDao(IIncomePayDetailDao incomePayDetailDao) {
		this.incomePayDetailDao = incomePayDetailDao;
	}
	public void setVirtualCoinDao(IVirtualCoinDao virtualCoinDao) {
		this.virtualCoinDao = virtualCoinDao;
	}
}
