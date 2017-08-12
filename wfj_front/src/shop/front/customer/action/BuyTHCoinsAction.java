package shop.front.customer.action;
import java.util.List;
import java.util.Map;

import shop.common.pojo.CoinRules;
import shop.common.service.ICoinRulesService;
import shop.customer.pojo.Customer;
import shop.front.customer.service.IBuyTHCoinsService;
import util.action.BaseAction;
import util.other.Escape;
import util.other.SerialNumberUtil;
/**
 * 前台用户购买天海币action
 * @author mf
 *
 */
public class BuyTHCoinsAction extends BaseAction {
	private static final long serialVersionUID = -1260197590092319129L;
	/**天海比赠送规则Service**/
	private ICoinRulesService coinRulesService;
	/**购买天海币Service**/
	private IBuyTHCoinsService buyTHCoinsService;
	/**天海币比例**/
	private String proportion;
	/**生成虚拟金币明细流水号**/
	private String virtualCoinNumber;
	/**商品名称**/
	private String subject="购买天海币";
	/**
	 * 页面跳转
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String gotobuyTHCoins(){
		//天海币购买比例
		Map<String,Object> map= (Map<String, Object>) servletContext.getAttribute("coinRules");
		List<CoinRules> crList = (List<CoinRules>) map.get("purchaseProportion");
		proportion=crList.get(0).getValue();
		//生成流水号
		virtualCoinNumber = SerialNumberUtil.VirtualCoinNumber();
		//商品名称
		subject=Escape.escape(subject);
		return SUCCESS;
	}
	/**
	 * 保存购买天海比信息
	 * 	1插入收支明细
	 * 	2插入天海明细
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String saveBuyInfo(){
		Customer customer = (Customer) session.getAttribute("customer");
		Map<String,Object> map= (Map<String, Object>) servletContext.getAttribute("coinRules");
		List<CoinRules> crList = (List<CoinRules>) map.get("purchaseProportion");
		proportion=crList.get(0).getValue();//兑换比例
		Integer opamount=Integer.parseInt(request.getParameter("opamount"));
		buyTHCoinsService.saveBuyInfo(customer,opamount,Integer.parseInt(proportion),servletContext);
		return SUCCESS;
	}
	//setter getter
	public String getProportion() {
		return proportion;
	}
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	public void setCoinRulesService(ICoinRulesService coinRulesService) {
		this.coinRulesService = coinRulesService;
	}
	public String getVirtualCoinNumber() {
		return virtualCoinNumber;
	}
	public void setVirtualCoinNumber(String virtualCoinNumber) {
		this.virtualCoinNumber = virtualCoinNumber;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setBuyTHCoinsService(IBuyTHCoinsService buyTHCoinsService) {
		this.buyTHCoinsService = buyTHCoinsService;
	}
}
