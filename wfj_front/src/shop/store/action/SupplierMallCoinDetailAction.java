package shop.store.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.common.pojo.CoinRules;
import shop.customer.pojo.Customer;
import shop.store.pojo.ShopInfo;
import shop.store.pojo.ShopMallCoinWithdrawals;
import shop.store.pojo.SupplierMallCoinDetail;
import shop.store.service.IShopMallCoinWithdrawalsService;
import shop.store.service.ISupplierMallCoinDetailService;
import util.action.BaseAction;
import util.other.Utils;
/**
 * 供应商商城币收支明细Action
 * @author lmh
 * **/
@SuppressWarnings("serial")
public class SupplierMallCoinDetailAction extends BaseAction{
	/**供应商商城币收支明细Service**/
	private ISupplierMallCoinDetailService supplierMallCoinDetailService;
	/**
	 * 供应商商城币收支明细bean
	 * **/
	private SupplierMallCoinDetail supplierMallCoinDetail;
	/**
	 * 供应商商城币收支明细List
	 * **/
	private List<Map> supplierMallCoinDetailList;
	/**
	 * 供应商商城币提现List
	 * **/
	private List<Map> shopMallCoinWithdrawalsList;
	/**供应商商城币提现Object**/
	private ShopMallCoinWithdrawals shopMallCoinWithdrawals;
	/**供应商商城币提现Service**/
	private IShopMallCoinWithdrawalsService shopMallCoinWithdrawalsService;
	/**总收入**/
	private BigDecimal earning;
	/**总支出**/
	private BigDecimal expenditure;
	/**导出excel文件的列名称**/
	private List<String> typeNameList = new ArrayList<String>();
	/**
	 * 集合类
	 */
	@SuppressWarnings("unchecked")
	private List<Map> mapList;
	
	//跳转供应商商城币收支明细页面
	public String gotoSupplierMallCoinDetailPage(){
			return SUCCESS;
	}
	//供应商商城币明细列表
	public void listSupplierMallCoinDetail()throws IOException{
		//hql查询语句
		String hql="SELECT a.supplierMallCoinDetailId as supplierMallCoinDetailId,a.ordersNo as ordersNo,a.tradingTime as tradingTime ,a.type as type ,a.tradMallCoin as tradMallCoin FROM SupplierMallCoinDetail a"; 
		String countHql="SELECT count(a.supplierMallCoinDetailId) FROM SupplierMallCoinDetail a"; 
		//获取前台查询参数
		String type = request.getParameter("type");
		String ordersNo = request.getParameter("ordersNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		//追加where条件
		hql+=" where 1=1";
		countHql+=" where 1=1";
		if(type!=null&&!"".equals(type)){
			hql+=" and a.type like '%"+type+"%'";
			countHql+=" and a.type like '%"+type+"%'";
		}
		if(startTime!=null&&!"".equals(startTime.trim())){
			hql += " and UNIX_TIMESTAMP(a.tradingTime) >= UNIX_TIMESTAMP('"+startTime.trim()+" 00:00:00')";
			countHql += " and UNIX_TIMESTAMP(a.tradingTime) >= UNIX_TIMESTAMP('"+startTime.trim()+" 00:00:00')";
		}
		if(endTime!=null&&!"".equals(endTime.trim())){
			hql += " and UNIX_TIMESTAMP(a.tradingTime) <= UNIX_TIMESTAMP('"+endTime.trim()+" 23:59:59')";
			countHql += " and UNIX_TIMESTAMP(a.tradingTime) <= UNIX_TIMESTAMP('"+endTime.trim()+" 23:59:59')";
		}
		if(ordersNo!=null&&!"".equals(ordersNo)){
			hql+=" and a.ordersNo like '%"+ordersNo+"%'";
			countHql+=" and a.ordersNo like '%"+ordersNo+"%'";
		}
		int totalRecordCount = supplierMallCoinDetailService.getMultilistCount(countHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		supplierMallCoinDetailList = supplierMallCoinDetailService.findListMapPage(hql+" order by a.tradingTime desc", pageHelper);
		if(supplierMallCoinDetailList!=null&&supplierMallCoinDetailList.size()>0){
			for(Map<String,Object> m:supplierMallCoinDetailList){
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String time = fm.format(m.get("tradingTime"));
				m.put("tradingTime", time);
			}
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", supplierMallCoinDetailList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	//跳转供应商商城币提现页面
	@SuppressWarnings("unchecked")
	public String gotoSCBTXPage(){
		//供应商商城币提现比例
		Map<String,Object> map= (Map<String, Object>) servletContext.getAttribute("coinRules");
		List<CoinRules> crList = (List<CoinRules>) map.get("gysSCBtxBL");//gysSCBtxBL供应商商城币提现比例
		String gysSCBtxBL=crList.get(0).getValue();
		request.setAttribute("gysSCBtxBL", gysSCBtxBL);
		//获取用户供应商商城币最大数目
		ShopInfo shopInfo=(ShopInfo) session.getAttribute("shopInfo");
		List<SupplierMallCoinDetail> vcList = supplierMallCoinDetailService.findObjects(" where o.supplierId="+shopInfo.getShopInfoId()+" order by o.supplierMallCoinDetailId desc limit 1");
		if(vcList!=null&&vcList.size()>0){
			BigDecimal surplusMallCoinBig = vcList.get(0).getTotalInPut();//总收入商城币
				if(surplusMallCoinBig!=null){
					String string = surplusMallCoinBig.toString();
					if(string.indexOf(".")>0){
						string=string.substring(0,string.lastIndexOf("."));
					}
					request.setAttribute("gysSCBMAX",string);//将当前用户的总收入商城币放到request域中
					//可兑换金额
					BigDecimal bd2=new BigDecimal(gysSCBtxBL);
					BigDecimal divide = surplusMallCoinBig.divide(bd2,2, BigDecimal.ROUND_HALF_EVEN);
					request.setAttribute("divide",divide);
				}else{
					request.setAttribute("gysSCBMAX","0");//将当前用户的总收入商城币放到request域中
					request.setAttribute("divide","0");
				}
		}else{
			request.setAttribute("gysSCBMAX","0");//将当前用户的供应商商城币余额放到request域中
			request.setAttribute("divide","0");
		}
		return SUCCESS;
	}
	
	//保存供应商商城币提现记录
	public String saveSupplierMallCoinDetailInfo(){
		if(shopMallCoinWithdrawals!=null){
			Customer customer=(Customer) session.getAttribute("customer");
			ShopInfo shopInfo=(ShopInfo) session.getAttribute("shopInfo");
			//生成流水号
			SimpleDateFormat sdf1 = new  SimpleDateFormat("yyyyMMddHHmmss");
			String no = sdf1.format(new Date());
			Double random = Math.random();
			String num = random.toString().substring(2, 8);
			shopMallCoinWithdrawals.setSerialNumber(no+num);//流水
			shopMallCoinWithdrawals.setIncomeExpensesType(5);//交易类型，5：代表天海币提现
			shopMallCoinWithdrawals.setState(1);//交易状态(1申请提现，2.平台支付完成，3.平台支付失败)
			shopMallCoinWithdrawals.setCreateTime(new Date());
			shopMallCoinWithdrawals.setTradeTime(new Date());
			shopMallCoinWithdrawals.setUpdateTime(new Date());
			shopMallCoinWithdrawals.setPayeeId(customer.getCustomerId());//收款人id
			shopMallCoinWithdrawals.setPayeeName(customer.getLoginName());//收款人(当前登录人)
			//交易金额处理
			BigDecimal transactionAmount = shopMallCoinWithdrawals.getTransactionAmount();
			//天海币提现比例
			Map<String,Object> map= (Map<String, Object>) servletContext.getAttribute("coinRules");
			List<CoinRules> crList = (List<CoinRules>) map.get("gysSCBtxBL");
			String gysSCBtxBL=crList.get(0).getValue();
			BigDecimal bd2=new BigDecimal(gysSCBtxBL);
			BigDecimal divide = transactionAmount.divide(bd2,2, BigDecimal.ROUND_HALF_EVEN);
			shopMallCoinWithdrawals.setTransactionAmount(divide);
			shopMallCoinWithdrawals.setWithdrawBL(gysSCBtxBL);//此时天海比分享比例
			ShopMallCoinWithdrawals ipd = (ShopMallCoinWithdrawals) shopMallCoinWithdrawalsService.saveOrUpdateObject(shopMallCoinWithdrawals);
			if(ipd!=null){//如果ipd不为空 则冻结提现商城金币 等待审核结果
				SupplierMallCoinDetail sc = new  SupplierMallCoinDetail();
				sc.setSupplierId(shopInfo.getShopInfoId());//客户ID
				sc.setSupplierLoginName(shopInfo.getCustomerName());//供应商账号
				sc.setType(3);//取现
				//提现金币数
				BigDecimal multiply = divide.multiply(new BigDecimal(gysSCBtxBL));
				sc.setTradMallCoin(multiply);
				//查询出最后一条交易记录的余额
				List<SupplierMallCoinDetail> findObjects = supplierMallCoinDetailService.findObjects(" where o.supplierId="+shopInfo.getShopInfoId()+" order by o.supplierMallCoinDetailId desc limit 1");
				if(findObjects!=null){
					SupplierMallCoinDetail supplierMallCoinDetail = findObjects.get(0);
					BigDecimal subtract = supplierMallCoinDetail.getTotalInPut().subtract(multiply);
					sc.setTotalInPut(subtract);//总收入
					sc.setTotalOutPut(supplierMallCoinDetail.getTotalOutPut());
				}
				sc.setTradingTime(new Date());
				supplierMallCoinDetailService.saveOrUpdateObject(sc);
			}
		}
		return SUCCESS;
	}
	
	//跳转供应商商城币收支明细列表页面
	public String gotoShopSupplierMallCoinDetailListPage(){
		ShopInfo shopInfo=(ShopInfo) session.getAttribute("shopInfo");
		//hql查询语句
		String hql="SELECT a.supplierMallCoinDetailId as supplierMallCoinDetailId,a.ordersNo as ordersNo,a.tradingTime as tradingTime ,a.type as type ,a.tradMallCoin as tradMallCoin FROM SupplierMallCoinDetail a"; 
		String countHql="SELECT count(a.supplierMallCoinDetailId) FROM SupplierMallCoinDetail a"; 
		//获取前台查询参数
		String type = request.getParameter("type");
		String ordersNo = request.getParameter("ordersNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		//追加where条件
		hql+=" where a.supplierId="+shopInfo.getShopInfoId();
		countHql+=" where a.supplierId="+shopInfo.getShopInfoId();
		if(type!=null&&!"".equals(type)){
			hql+=" and a.type like '%"+type+"%'";
			countHql+=" and a.type like '%"+type+"%'";
		}
		if(startTime!=null&&!"".equals(startTime.trim())){
			hql += " and UNIX_TIMESTAMP(a.tradingTime) >= UNIX_TIMESTAMP('"+startTime.trim()+" 00:00:00')";
			countHql += " and UNIX_TIMESTAMP(a.tradingTime) >= UNIX_TIMESTAMP('"+startTime.trim()+" 00:00:00')";
		}
		if(endTime!=null&&!"".equals(endTime.trim())){
			hql += " and UNIX_TIMESTAMP(a.tradingTime) <= UNIX_TIMESTAMP('"+endTime.trim()+" 23:59:59')";
			countHql += " and UNIX_TIMESTAMP(a.tradingTime) <= UNIX_TIMESTAMP('"+endTime.trim()+" 23:59:59')";
		}
		if(ordersNo!=null&&!"".equals(ordersNo)){
			hql+=" and a.ordersNo like '%"+ordersNo+"%'";
			countHql+=" and a.ordersNo like '%"+ordersNo+"%'";
		}
		int totalRecordCount = supplierMallCoinDetailService.getMultilistCount(countHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		supplierMallCoinDetailList = supplierMallCoinDetailService.findListMapPage(hql+" order by a.tradingTime desc", pageHelper);
		if(supplierMallCoinDetailList!=null&&supplierMallCoinDetailList.size()>0){
			for(Map<String,Object> m:supplierMallCoinDetailList){
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String time = fm.format(m.get("tradingTime"));
				m.put("tradingTime", time);
			}
		}
		List<SupplierMallCoinDetail> vcList = supplierMallCoinDetailService.findObjects(" where o.supplierId="+shopInfo.getShopInfoId()+" order by o.tradingTime desc limit 1");
		if(vcList!=null&&vcList.size()>0){
			expenditure = vcList.get(0).getTotalOutPut();
			earning = vcList.get(0).getTotalInPut();
		}else{
			expenditure=new BigDecimal(0);
			earning=new BigDecimal(0);
		}
		return SUCCESS;
	}
	//跳转供应商商城币提现列表页面
	public String gotoSCBTXListPage(){
		Customer customer=(Customer) session.getAttribute("customer");
		//hql查询语句
		String hql="SELECT o.cardHolder as cardHolder,o.cardNumber as cardNumber,o.phone as phone,o.transactionAmount as transactionAmount,o.customerMessage as customerMessage,o.state as state,o.tradeTime as tradeTime FROM ShopMallCoinWithdrawals o"; 
		String countHql="SELECT count(o.detailId) FROM ShopMallCoinWithdrawals o"; 
		//获取前台查询参数
		String serialNumber = request.getParameter("serialNumber");
		String state = request.getParameter("state");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		//追加where条件
		hql+=" where o.payeeId="+customer.getCustomerId()+" and o.incomeExpensesType=5";
		countHql+=" where o.payeeId="+customer.getCustomerId()+" and o.incomeExpensesType=5";
		if(state!=null&&!"".equals(state)){
			hql+=" and o.state like '%"+state+"%'";
			countHql+=" and o.state like '%"+state+"%'";
		}
		if(startTime!=null&&!"".equals(startTime.trim())){
			hql += " and UNIX_TIMESTAMP(o.tradeTime) >= UNIX_TIMESTAMP('"+startTime.trim()+" 00:00:00')";
			countHql += " and UNIX_TIMESTAMP(o.tradeTime) >= UNIX_TIMESTAMP('"+startTime.trim()+" 00:00:00')";
		}
		if(endTime!=null&&!"".equals(endTime.trim())){
			hql += " and UNIX_TIMESTAMP(o.tradeTime) <= UNIX_TIMESTAMP('"+endTime.trim()+" 23:59:59')";
			countHql += " and UNIX_TIMESTAMP(o.tradeTime) <= UNIX_TIMESTAMP('"+endTime.trim()+" 23:59:59')";
		}
		if(serialNumber!=null&&!"".equals(serialNumber)){
			hql+=" and o.serialNumber like '%"+serialNumber+"%'";
			countHql+=" and o.serialNumber like '%"+serialNumber+"%'";
		}
		int totalRecordCount = shopMallCoinWithdrawalsService.getMultilistCount(countHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		shopMallCoinWithdrawalsList = shopMallCoinWithdrawalsService.findListMapPage(hql+" order by o.createTime desc", pageHelper);
		if(shopMallCoinWithdrawalsList!=null&&shopMallCoinWithdrawalsList.size()>0){
			for(Map<String,Object> m:shopMallCoinWithdrawalsList){
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String time = fm.format(m.get("tradeTime"));
				m.put("tradeTime", time);
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
		typeNameList.add("申请提现时间");
		typeNameList.add("操作人");
		typeNameList.add("操作时间");
		return typeNameList;
	}
	/**
	 * 导出供应商提现记录EXCEL
	 */
	public String exportSCBTXExcel() throws IOException{
		Customer customer=(Customer) session.getAttribute("customer");
		//hql查询语句
		String hql = "select o.reviewer as reviewer ,o.reviewerDate as reviewerDate, o.cardHolder as cardHolder,o.cardNumber as cardNumber,o.phone as phone,o.transactionAmount as transactionAmount,o.customerMessage as customerMessage,o.state as state,o.createTime as createTime from ShopMallCoinWithdrawals o where o.payeeId="+customer.getCustomerId()+" and o.incomeExpensesType=5"; 
		//获取前台查询参数
		String state = request.getParameter("state");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(state!=null&&!"".equals(state)){
			hql+=" and o.state like '%"+state+"%'";
		}
		if(startTime!=null&&!"".equals(startTime.trim())){
			hql += " and UNIX_TIMESTAMP(o.createTime) >= UNIX_TIMESTAMP('"+startTime.trim()+" 00:00:00')";
		}
		if(endTime!=null&&!"".equals(endTime.trim())){
			hql += " and UNIX_TIMESTAMP(o.createTime) <= UNIX_TIMESTAMP('"+endTime.trim()+" 23:59:59')";
		}
		mapList = shopMallCoinWithdrawalsService.findListMapByHql(hql+" order by o.createTime desc");
		if(mapList!=null&&mapList.size()>0){
			//格式化日期
			for(Map map:mapList){
				String createTime = "";//创建时间
				Object createTimeObj = map.get("createTime");
				if(Utils.objectIsNotEmpty(createTimeObj)){
					createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((createTimeObj));
					map.put("createTime", createTime);
				}
				String reviewerDate = "";//审核时间
				Object reviewerDateObj = map.get("reviewerDate");
				if(Utils.objectIsNotEmpty(reviewerDateObj)){
					reviewerDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((reviewerDateObj));
					map.put("reviewerDate", reviewerDate);
				}
			}
			session.setAttribute("columnNames", ordersColumnNamesList());//把所需要传递的columnNames列名集合放在session中。
			session.setAttribute("columnValues", ordersColumnValuesList(mapList));//把所需要传递的columnValues列名对应的值集合放在session中。
			session.setAttribute("moduleName", "SCBTXList");
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
			String state = "";
			String stateStr = String.valueOf(cc.get("state"));
			if("1".equals(stateStr)){
				state = "申请提现";
			}else if("2".equals(stateStr)){
				state = "平台审核通过";
			}else if("3".equals(stateStr)){
				state = "平台审核未通过";
			}else if("4".equals(stateStr)){
				state = "平台支付完成";
			}else if("5".equals(stateStr)){
				state = "平台支付失败";
			}
			columnValuesList.add(state);
			if(Utils.objectIsNotEmpty(cc.get("createTime"))){
				columnValuesList.add(String.valueOf(cc.get("createTime")));
			}else{
				columnValuesList.add("");
			}
			if(Utils.objectIsNotEmpty(cc.get("reviewerDate"))){
				columnValuesList.add(String.valueOf(cc.get("reviewerDate")));
			}else{
				columnValuesList.add("");
			}
			columnRowsList.add(columnValuesList);//把当前的行 添加到 列表中			
		}		
		return columnRowsList;
	}
	public void setSupplierMallCoinDetailService(
			ISupplierMallCoinDetailService supplierMallCoinDetailService) {
		this.supplierMallCoinDetailService = supplierMallCoinDetailService;
	}

	public SupplierMallCoinDetail getSupplierMallCoinDetail() {
		return supplierMallCoinDetail;
	}

	public void setSupplierMallCoinDetail(
			SupplierMallCoinDetail supplierMallCoinDetail) {
		this.supplierMallCoinDetail = supplierMallCoinDetail;
	}
	public List<Map> getSupplierMallCoinDetailList() {
		return supplierMallCoinDetailList;
	}
	public void setSupplierMallCoinDetailList(List<Map> supplierMallCoinDetailList) {
		this.supplierMallCoinDetailList = supplierMallCoinDetailList;
	}
	public ShopMallCoinWithdrawals getShopMallCoinWithdrawals() {
		return shopMallCoinWithdrawals;
	}
	public void setShopMallCoinWithdrawals(
			ShopMallCoinWithdrawals shopMallCoinWithdrawals) {
		this.shopMallCoinWithdrawals = shopMallCoinWithdrawals;
	}
	public void setShopMallCoinWithdrawalsService(
			IShopMallCoinWithdrawalsService shopMallCoinWithdrawalsService) {
		this.shopMallCoinWithdrawalsService = shopMallCoinWithdrawalsService;
	}
	public List<Map> getShopMallCoinWithdrawalsList() {
		return shopMallCoinWithdrawalsList;
	}
	public void setShopMallCoinWithdrawalsList(List<Map> shopMallCoinWithdrawalsList) {
		this.shopMallCoinWithdrawalsList = shopMallCoinWithdrawalsList;
	}
	public BigDecimal getEarning() {
		return earning;
	}
	public void setEarning(BigDecimal earning) {
		this.earning = earning;
	}
	public BigDecimal getExpenditure() {
		return expenditure;
	}
	public void setExpenditure(BigDecimal expenditure) {
		this.expenditure = expenditure;
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
