package shop.store.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.store.pojo.ShopInfo;
import shop.store.pojo.SupplierBrokerageCoinDetail;
import shop.store.service.ISupplierBrokerageCoinDetailService;
import util.action.BaseAction;
/**
 * 供应商佣金币收支明细Action
 * @author lmh
 * **/
@SuppressWarnings("serial")
public class SupplierBrokerageCoinDetailAction extends BaseAction{
	/**
	 * 供应商佣金币收支明细Service
	 * **/
	private ISupplierBrokerageCoinDetailService supplierBrokerageCoinDetailService;
	/**
	 * 供应商佣金币收支明细bean
	 * **/
	private SupplierBrokerageCoinDetail supplierBrokerageCoinDetail;
	/**
	 * 供应商佣金收支明细List
	 * **/
	private List<Map> SupplierBrokerageCoinDetailList;
	/**
	 * 分享总数
	 */
	private BigDecimal expenditure;
	//跳转供应商佣金币收支明细页面
	public String gotoSupplierBrokerageCoinDetailPage(){
		return SUCCESS;
	}
	//供应商佣金币收支明细列表信息
	public void listSupplierBrokerageCoinDetail()throws IOException{
		//hql查询语句
		String hql="SELECT a.supplierNo as supplierNo,a.ordersNo as ordersNo,a.tradingTime as tradingTime ,a.giveBrokerageCoin as giveBrokerageCoin ,a.totalOutPut as totalOutPut  FROM SupplierBrokerageCoinDetail a"; 
		String countHql="SELECT count(a.supplierBrokerageCoinDetailId) FROM SupplierBrokerageCoinDetail a";
		//获取前台查询参数
		String supplierNo = request.getParameter("supplierNo");
		String registerDate = request.getParameter("registerDate");
		String registerDate2 = request.getParameter("registerDate2");
		String ordersNo = request.getParameter("ordersNo");
		hql+=" where 1=1";
		countHql+=" where 1=1";
		//追加where条件
		if(supplierNo!=null&&!"".equals(supplierNo)){
			hql+=" and a.supplierNo like '%"+supplierNo+"%'";
			countHql+=" and a.supplierNo like '%"+supplierNo+"%'";
		}
		if(registerDate!=null&&!"".equals(registerDate)){
			hql+= " and UNIX_TIMESTAMP(a.tradingTime) >= UNIX_TIMESTAMP('"+registerDate+"')";
			countHql+= " and UNIX_TIMESTAMP(a.tradingTime) >= UNIX_TIMESTAMP('"+registerDate+"')";
		}
		if(registerDate2!=null&&!"".equals(registerDate2)){
			hql+= " and UNIX_TIMESTAMP(a.tradingTime) <= UNIX_TIMESTAMP('"+registerDate2+"')";
			countHql+= " and UNIX_TIMESTAMP(a.tradingTime) <= UNIX_TIMESTAMP('"+registerDate2+"')";
		}
		if(ordersNo!=null&&!"".equals(ordersNo)){
			hql+=" and a.ordersNo like '%"+ordersNo+"%'";
			countHql+=" and a.ordersNo like '%"+ordersNo+"%'";
		}
		int totalRecordCount = supplierBrokerageCoinDetailService.getMultilistCount(countHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		SupplierBrokerageCoinDetailList = supplierBrokerageCoinDetailService.findListMapPage(hql+" order by a.tradingTime desc", pageHelper);
		if(SupplierBrokerageCoinDetailList!=null&&SupplierBrokerageCoinDetailList.size()>0){
			for(Map<String,Object> m:SupplierBrokerageCoinDetailList){
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String time = fm.format(m.get("tradingTime"));
				m.put("tradingTime", time);
			}
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", SupplierBrokerageCoinDetailList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	public String gotoSupplierBrokerageCoinDetailListPage(){
		ShopInfo shopInfo=(ShopInfo) session.getAttribute("shopInfo");
		//hql查询语句
		String hql="SELECT a.supplierBrokerageCoinDetailId as supplierBrokerageCoinDetailId,a.ordersNo as ordersNo,a.tradingTime as tradingTime ,a.giveBrokerageCoin as giveBrokerageCoin ,a.totalOutPut as totalOutPut FROM SupplierBrokerageCoinDetail a"; 
		String countHql="SELECT count(a.supplierBrokerageCoinDetailId) FROM SupplierBrokerageCoinDetail a"; 
		//获取前台查询参数
		String ordersNo = request.getParameter("ordersNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		//追加where条件
		hql+=" where a.supplierId="+shopInfo.getShopInfoId();
		countHql+=" where a.supplierId="+shopInfo.getShopInfoId();
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
		int totalRecordCount = supplierBrokerageCoinDetailService.getMultilistCount(countHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		SupplierBrokerageCoinDetailList = supplierBrokerageCoinDetailService.findListMapPage(hql+" order by a.tradingTime desc", pageHelper);
		if(SupplierBrokerageCoinDetailList!=null&&SupplierBrokerageCoinDetailList.size()>0){
			for(Map<String,Object> m:SupplierBrokerageCoinDetailList){
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String time = fm.format(m.get("tradingTime"));
				m.put("tradingTime", time);
			}
		}
		List<SupplierBrokerageCoinDetail> vcList = supplierBrokerageCoinDetailService.findObjects(" where o.supplierId="+shopInfo.getShopInfoId()+" order by o.tradingTime desc limit 1");
		if(vcList!=null&&vcList.size()>0){
			expenditure = vcList.get(0).getTotalOutPut();
		}else{
			expenditure=new BigDecimal(0);
		}
		return SUCCESS;
	}
	
	public List<Map> getSupplierBrokerageCoinDetailList() {
		return SupplierBrokerageCoinDetailList;
	}
	public void setSupplierBrokerageCoinDetailList(
			List<Map> supplierBrokerageCoinDetailList) {
		SupplierBrokerageCoinDetailList = supplierBrokerageCoinDetailList;
	}
	public void setSupplierBrokerageCoinDetailService(
			ISupplierBrokerageCoinDetailService supplierBrokerageCoinDetailService) {
		this.supplierBrokerageCoinDetailService = supplierBrokerageCoinDetailService;
	}
	public SupplierBrokerageCoinDetail getSupplierBrokerageCoinDetail() {
		return supplierBrokerageCoinDetail;
	}
	public void setSupplierBrokerageCoinDetail(
			SupplierBrokerageCoinDetail supplierBrokerageCoinDetail) {
		this.supplierBrokerageCoinDetail = supplierBrokerageCoinDetail;
	}
	public BigDecimal getExpenditure() {
		return expenditure;
	}
	public void setExpenditure(BigDecimal expenditure) {
		this.expenditure = expenditure;
	}
	
}
