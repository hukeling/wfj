package shop.front.customer.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import shop.customer.pojo.Customer;
import shop.customer.service.IMallCoinService;
import util.action.BaseAction;

/**
 * 用户商城币
 * @author Mengqirui
 */
public class MallCoinAction extends BaseAction{
	private static final long serialVersionUID = -6417507708628666256L;
	/**商城币service**/
	private IMallCoinService mallCoinService;
	/**用户对象**/
	private Customer customer;
	/**集合**/
	private List<Map> mapList;
	/**导出excel文件的列名称**/
	private List<String> typeNameList = new ArrayList<String>();
	/**
	 * 用户商城币明细
	 * @author mengqirui
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getCustomerMallCoinDetail() throws Exception{
		customer = (Customer) request.getSession().getAttribute("customer");
		String ordersNo = request.getParameter("ordersNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		request.setAttribute("ordersNo", ordersNo);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		String where ="";
		if(ordersNo!=null&&!"".equals(ordersNo.trim())){
			where +=" and v.ordersNo like '%"+ordersNo.trim()+"%'";
		}
		if(startTime!=null&&!"".equals(startTime.trim())){
			where += " and UNIX_TIMESTAMP(v.tradeTime) >= UNIX_TIMESTAMP('"+startTime.trim()+" 00:00:00')";
		}
		if(endTime!=null&&!"".equals(endTime.trim())){
			where += " and UNIX_TIMESTAMP(v.tradeTime) <= UNIX_TIMESTAMP('"+endTime.trim()+" 23:59:59')";
		}
		String hql1 = " select count(v.mallCoinId) from MallCoin v where v.customerId = "+customer.getCustomerId();
		int total = mallCoinService.getCountByHQL(hql1+where);
		pageHelper.setPageInfo(pageSize, total, currentPage);
		String hql2 = " select v.ordersId as ordersId, v.ordersNo as ordersNo, v.serialNumber as serialNumber,v.transactionNumber as transactionNumber, v.remainingNumber as remainingNumber, v.type as type,v.operatorTime as operatorTime from MallCoin v where v.customerId = "+customer.getCustomerId()+where+" order by v.operatorTime desc ";
		mapList = mallCoinService.findListMapPage(hql2, pageHelper);
		//格式化日期
		for(Map map:mapList){
			map.put("operatorTime", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)map.get("operatorTime"))).toString());
		}
		//查询天海币总收入
		String hql3 = " select sum(v.transactionNumber) as transactionNumber from MallCoin v where v.customerId ="+customer.getCustomerId()+" and v.type in (1,3) ";
		//查询天海币总支出
		String hql4 = " select sum(v.transactionNumber) as transactionNumber from MallCoin v where v.customerId ="+customer.getCustomerId()+" and v.type = 2 ";
		List<Map> mapList2 = mallCoinService.findListMapByHql(hql3);
		if(mapList2.get(0).get("transactionNumber")!=null){
			request.setAttribute("earning", mapList2.get(0).get("transactionNumber"));
		}else{
			request.setAttribute("earning", 0);
		}
		List<Map> mapList3 = mallCoinService.findListMapByHql(hql4);
		if(mapList3.get(0).get("transactionNumber")!=null){
			request.setAttribute("expenditure", mapList3.get(0).get("transactionNumber"));
		}else{
			request.setAttribute("expenditure", 0);
		}
		return SUCCESS;
	}
	
	/**
	 * 导出商城币明细excel
	 * @throws IOException 
	 */
	public String exportExcel() throws IOException{
		customer = (Customer) request.getSession().getAttribute("customer");
		String ordersNo = request.getParameter("ordersNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		request.setAttribute("ordersNo", ordersNo);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		String where ="";
		if(ordersNo!=null&&!"".equals(ordersNo.trim())){
			where +=" and v.ordersNo like '%"+ordersNo.trim()+"%'";
		}
		if(startTime!=null&&!"".equals(startTime.trim())){
			where += " and UNIX_TIMESTAMP(v.tradeTime) >= UNIX_TIMESTAMP('"+startTime.trim()+" 00:00:00')";
		}
		if(endTime!=null&&!"".equals(endTime.trim())){
			where += " and UNIX_TIMESTAMP(v.tradeTime) <= UNIX_TIMESTAMP('"+endTime.trim()+" 23:59:59')";
		}
		String hql = " select v.ordersId as ordersId, v.ordersNo as ordersNo, v.serialNumber as serialNumber,v.transactionNumber as transactionNumber, v.remainingNumber as remainingNumber, v.type as type,v.operatorTime as operatorTime from MallCoin v where v.customerId = "+customer.getCustomerId()+where+" order by v.operatorTime desc ";
		mapList = mallCoinService.findListMapByHql(hql);
		if(mapList!=null&&mapList.size()>0){
			//格式化日期
			for(Map map:mapList){
				map.put("operatorTime", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)map.get("operatorTime"))).toString());
			}
			session.setAttribute("columnNames", ordersColumnNamesList());//把所需要传递的columnNames列名集合放在session中。
			session.setAttribute("columnValues", ordersColumnValuesList(mapList));//把所需要传递的columnValues列名对应的值集合放在session中。
			session.setAttribute("moduleName", "CustomerMallCoin");
			return "success";
		}else {
			this.addActionError("没有数据");
			return "error";
		}
	}
	
	/**excel列名**/
	private List<String> ordersColumnNamesList(){
		typeNameList.add(0, "流水号");
		typeNameList.add("订单号");
		typeNameList.add("交易金币");
		typeNameList.add("类型");
		typeNameList.add("剩余金币");
		typeNameList.add("时间");
		return typeNameList;
	}
	
	/**excel对应列的值**/
	public List<List<String>> ordersColumnValuesList(List<Map> list) throws IOException{
		//保存二维表样式数据 List <List<String>>
		List<List<String>> columnRowsList = new ArrayList<List<String>>();
		List<String> columnValuesList = null;
		for(Map<String,Object> cc : list){
			columnValuesList = new ArrayList<String>();
			columnValuesList.add(String.valueOf(cc.get("serialNumber")));
			Object object2 = cc.get("ordersNo");
			String ordersNo="";
			if(object2!=null){
				ordersNo=String.valueOf(object2);
			}
			columnValuesList.add(ordersNo);
			columnValuesList.add(String.valueOf(cc.get("transactionNumber")));
			String type=String.valueOf(cc.get("type"));
			if("1".equals(type)){
				type="收入";
			}else if("2".equals(type)){
				type="支出";
			}else if("3".equals(type)){
				type="取消订单";
			}else if("4".equals(type)){
				type="交易作废";
			}
			columnValuesList.add(type);		
			columnValuesList.add(String.valueOf(cc.get("remainingNumber")));
			columnValuesList.add(String.valueOf(cc.get("operatorTime")));
			columnRowsList.add(columnValuesList);//把当前的行 添加到 列表中			
		}		
		return columnRowsList;
	}
	//setter getter
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Map> getMapList() {
		return mapList;
	}
	public void setMapList(List<Map> mapList) {
		this.mapList = mapList;
	}
	public void setMallCoinService(IMallCoinService mallCoinService) {
		this.mallCoinService = mallCoinService;
	}
	public List<String> getTypeNameList() {
		return typeNameList;
	}
	public void setTypeNameList(List<String> typeNameList) {
		this.typeNameList = typeNameList;
	}
}
