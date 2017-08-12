package shop.front.customer.action;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import shop.common.pojo.CoinRules;
import shop.customer.pojo.Customer;
import shop.customer.pojo.IncomePayDetail;
import shop.customer.pojo.VirtualCoin;
import shop.customer.service.IIncomePayDetailService;
import shop.customer.service.IVirtualCoinService;
import util.action.BaseAction;
import util.other.SerialNumberUtil;
import util.other.Utils;
/**
 * 天海币提现action
 * @author mf
 */
public class THBTXAction extends BaseAction {
	private static final long serialVersionUID = -7989855704129356100L;
	/**虚拟金币明细Service**/
	private IVirtualCoinService virtualCoinService;
	/**收支明细Service**/
	private IIncomePayDetailService incomePayDetailService;
	/**收支明细Object**/
	private IncomePayDetail incomePayDetail;
	/**导出excel文件的列名称**/
	private List<String> typeNameList = new ArrayList<String>();
	/**
	 * 集合类
	 */
	@SuppressWarnings("unchecked")
	private List<Map> mapList;
	//前台列表页面
	public String gotoListPage(){
		Customer customer=(Customer) session.getAttribute("customer");
		Integer totalRecordCount=incomePayDetailService.getCount("where o.payeeId="+customer.getCustomerId()+" and o.incomeExpensesType=5");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		List<IncomePayDetail> list = incomePayDetailService.findListByPageHelper(null, pageHelper," where o.payeeId="+customer.getCustomerId()+" and o.incomeExpensesType=5 order by o.createTime desc");
		if(list!=null&&list.size()>0){
			request.setAttribute("list", list);
		}
		return SUCCESS;
	}
	//跳转
	@SuppressWarnings("unchecked")
	public String gotoTHBTXPage(){
		//天海币提现比例
		Map<String,Object> map= (Map<String, Object>) servletContext.getAttribute("coinRules");
		List<CoinRules> crList = (List<CoinRules>) map.get("thbtxBL");//thbtxBL天海币提现比例
		String thbtxBL=crList.get(0).getValue();
		request.setAttribute("thbtxBL", thbtxBL);
		//获取用户天海币最大数目
		Customer customer=(Customer) session.getAttribute("customer");
		List<VirtualCoin> vcList = virtualCoinService.findObjects(" where o.customerId="+customer.getCustomerId()+" order by o.virtualCoinId desc limit 1");
		if(vcList!=null&&vcList.size()>0){
			BigDecimal remainingNumberBig = vcList.get(0).getRemainingNumber();//当前剩余商城币数目
				String string = remainingNumberBig.toString();
				if(string.indexOf(".")>0){
					string=string.substring(0,string.lastIndexOf("."));
				}
				request.setAttribute("thbMAX",string);//将当前用户的天海币余额放到request域中
				//可兑换金额
				BigDecimal bd2=new BigDecimal(thbtxBL);
				BigDecimal divide = remainingNumberBig.divide(bd2,2, BigDecimal.ROUND_HALF_EVEN);
				request.setAttribute("divide",divide);
		}else{
			request.setAttribute("thbMAX","0");//将当前用户的天海币余额放到request域中
			request.setAttribute("divide","0");
		}
		return SUCCESS;
	}
	//储存天海币提现信息至收支明细
	@SuppressWarnings("unchecked")
	public String saveInfo(){
		if(incomePayDetail!=null){
			Customer customer=(Customer) session.getAttribute("customer");
			//生成流水号
			SimpleDateFormat sdf1 = new  SimpleDateFormat("yyyyMMddHHmmss");
			String no = sdf1.format(new Date());
			Double random = Math.random();
			String num = random.toString().substring(2, 8);
			incomePayDetail.setSerialNumber(no+num);//流水
			incomePayDetail.setIncomeExpensesType(5);//交易类型，5：代表天海币提现
			incomePayDetail.setState(1);//交易状态(1申请提现，2.平台支付完成，3.平台支付失败)
			incomePayDetail.setCreateTime(new Date());
			incomePayDetail.setTradeTime(new Date());
			incomePayDetail.setUpdateTime(new Date());
			incomePayDetail.setPayeeId(customer.getCustomerId());//收款人id
			incomePayDetail.setPayeeName(customer.getLoginName());//收款人(当前登录人)
			//交易金额处理
			BigDecimal transactionAmount = incomePayDetail.getTransactionAmount();
			//天海币提现比例
			Map<String,Object> map= (Map<String, Object>) servletContext.getAttribute("coinRules");
			List<CoinRules> crList = (List<CoinRules>) map.get("thbtxBL");
			String thbtxBL=crList.get(0).getValue();
			BigDecimal bd2=new BigDecimal(thbtxBL);
			BigDecimal divide = transactionAmount.divide(bd2,2, BigDecimal.ROUND_HALF_EVEN);
			incomePayDetail.setTransactionAmount(divide);
			incomePayDetail.setWithdrawBL(thbtxBL);//此时天海比分享比例
			IncomePayDetail ipd = (IncomePayDetail) incomePayDetailService.saveOrUpdateObject(incomePayDetail);
			if(ipd!=null){//如果ipd不为空 则冻结提现商城金币 等待审核结果
				VirtualCoin vc = new  VirtualCoin();
				vc.setCustomerId(customer.getCustomerId());//客户ID
				vc.setSerialNumber(SerialNumberUtil.VirtualCoinNumber());//流水
				vc.setType(2);//支出
				//提现金币数
				BigDecimal multiply = divide.multiply(new BigDecimal(thbtxBL));
				vc.setTransactionNumber(multiply);
				//查询出最后一条交易记录的余额
				List<VirtualCoin> findObjects = virtualCoinService.findObjects(" where o.customerId="+customer.getCustomerId()+" order by o.virtualCoinId desc limit 1");
				if(findObjects!=null){
					VirtualCoin virtualCoin = findObjects.get(0);
					BigDecimal subtract = virtualCoin.getRemainingNumber().subtract(multiply);
					vc.setRemainingNumber(subtract);//金币余额
				}
				vc.setFrozenNumber(multiply);
				vc.setTradeTime(new Date());
				vc.setProportion(Integer.parseInt(thbtxBL));
				vc.setOperatorTime(new Date());
				vc.setRemarks("佣金币提现:"+multiply+"个,冻结佣金币:"+multiply+"个!");
				virtualCoinService.saveOrUpdateObject(vc);
			}
			
		}
		return SUCCESS;
	}
	/**excel列名**/
	private List<String> ordersColumnNamesList(){
		typeNameList.add(0, "持卡人");
		typeNameList.add("银行卡号");
		typeNameList.add("预留手机号");
		typeNameList.add("提现金额");
		typeNameList.add("用户留言");
		typeNameList.add("交易状态");
		typeNameList.add("提现时间");
		typeNameList.add("操作人");
		typeNameList.add("操作时间");
		return typeNameList;
	}
	/**
	 * 导出分享记录EXCEL
	 */
	public String exportTHBTXExcel() throws IOException{
		Customer customer=(Customer) session.getAttribute("customer");
		String hql = "select o.cardHolder as cardHolder,o.cardNumber as cardNumber,o.phone as phone,o.transactionAmount as transactionAmount,o.customerMessage as customerMessage,o.state as state,o.createTime as createTime,o.operatorName as operatorName,o.operatorTime as operatorTime from IncomePayDetail o where o.payeeId="+customer.getCustomerId()+" and o.incomeExpensesType=5";
		mapList = incomePayDetailService.findListMapByHql(hql);
		if(mapList!=null&&mapList.size()>0){
			//格式化日期
			for(Map map:mapList){
				map.put("createTime", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)map.get("createTime"))).toString());
			}
			session.setAttribute("columnNames", ordersColumnNamesList());//把所需要传递的columnNames列名集合放在session中。
			session.setAttribute("columnValues", ordersColumnValuesList(mapList));//把所需要传递的columnValues列名对应的值集合放在session中。
			session.setAttribute("moduleName", "THBTXList");
			return "success";
		}else {
			this.addActionError("没有数据");
			return "error";
		}
	}
	/**excel对应列的值**/
	public List<List<String>> ordersColumnValuesList(List<Map> list) throws IOException{
		//保存二维表样式数据 List <List<String>>
		List<List<String>> columnRowsList = new ArrayList<List<String>>();
		List<String> columnValuesList = null;
		for(Map<String,Object> cc : list){
			columnValuesList = new ArrayList<String>();
			columnValuesList.add(String.valueOf(cc.get("cardHolder")));
			columnValuesList.add(String.valueOf(cc.get("cardNumber")));
			columnValuesList.add(String.valueOf(cc.get("phone")));
			columnValuesList.add(String.valueOf(cc.get("transactionAmount")));
			columnValuesList.add(String.valueOf(cc.get("customerMessage")));
			if("1".endsWith(String.valueOf(cc.get("state")))){
				columnValuesList.add("申请提现");
			}else if("2".endsWith(String.valueOf(cc.get("state")))){
				columnValuesList.add("平台支付完成");
			}else{
				columnValuesList.add("平台支付失败");
			}
			columnValuesList.add(String.valueOf(cc.get("createTime")));
			if(Utils.objectIsNotEmpty(cc.get("operatorName"))){
				columnValuesList.add(String.valueOf(cc.get("operatorName")));
			}else{
				columnValuesList.add("");
			}
			if(Utils.objectIsNotEmpty(cc.get("operatorTime"))){
				columnValuesList.add(String.valueOf(cc.get("operatorTime")));
			}else{
				columnValuesList.add("");
			}
			columnRowsList.add(columnValuesList);//把当前的行 添加到 列表中			
		}		
		return columnRowsList;
	}
	public void setVirtualCoinService(IVirtualCoinService virtualCoinService) {
		this.virtualCoinService = virtualCoinService;
	}
	public IncomePayDetail getIncomePayDetail() {
		return incomePayDetail;
	}
	public void setIncomePayDetail(IncomePayDetail incomePayDetail) {
		this.incomePayDetail = incomePayDetail;
	}
	public void setIncomePayDetailService(
			IIncomePayDetailService incomePayDetailService) {
		this.incomePayDetailService = incomePayDetailService;
	}
	public List<String> getTypeNameList() {
		return typeNameList;
	}
	public void setTypeNameList(List<String> typeNameList) {
		this.typeNameList = typeNameList;
	}
	public List<Map> getMapList() {
		return mapList;
	}
	public void setMapList(List<Map> mapList) {
		this.mapList = mapList;
	}
}
