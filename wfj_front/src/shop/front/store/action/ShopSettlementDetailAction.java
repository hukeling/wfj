//package shop.front.store.action;
//
//import java.io.PrintWriter;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONObject;
//import shop.customer.pojo.Customer;
//import shop.store.pojo.ShopSettlementDetail;
//import shop.store.service.IShopSettlementDetailService;
//import util.action.BaseAction;
//
///**
// * 店铺申请结算明细表前台action
// * 
// * @author 刘钦楠
// * 
// */
//public class ShopSettlementDetailAction extends BaseAction {
//
//	private static final long serialVersionUID = 6799162511139100513L;
//	/**
//	 * 店铺申请结算明细表service
//	 */
//	private IShopSettlementDetailService shopSettlementDetailService;
//
//	private List<ShopSettlementDetail> shopSettlementDetailList;
//
//	private ShopSettlementDetail shopSettlmentDetail;
//
//	/**************************************************************************/
//
//	/**
//	 * 前台进入店铺申请结算页面
//	 */
//	public String gotoShopSettementDetail() throws Exception {
//		Customer c = (Customer) request.getSession().getAttribute("customer");// 获取当前用户
//		String condition = " where o.customerId = " + c.getCustomerId();// 条件查询
//		String ym = request.getParameter("s_ym");
//		String cycle = request.getParameter("s_cycle");
//		String reviewStatus = request.getParameter("s_reviewStatus");
//		if(ym!=null&&!"".equals(ym.trim())){
//			condition += " and o.settlementMonth like '%"+ym.trim()+"%' ";
//		}
//		if(cycle!=null&&!"".equals(cycle.trim())){
//			condition += " and o.cycle='"+cycle+"' ";
//		}
//		if(reviewStatus!=null&&!"".equals(reviewStatus)){
//			condition += " and o.reviewStatus="+Integer.valueOf(reviewStatus)+" ";
//		}
//		int total = shopSettlementDetailService.getCount(condition);
//		pageHelper.setPageInfo(pageSize, total, currentPage);
//		condition += " order by o.createDate desc ";
//		shopSettlementDetailList = shopSettlementDetailService
//				.findListByPageHelper(null, pageHelper, condition);
//		return SUCCESS;
//	}
//	/**
//	 * 计算店铺该周期内的成本与销售额并插入数据库中
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	public void applySettlement() throws Exception {
//		Customer c = (Customer) request.getSession().getAttribute("customer");// 获取当前用户
//		String ym = request.getParameter("ym");//获取年月
//		String cycle = request.getParameter("cycle");//获取周期
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String ymdate = ym + "-01 00:00:00";
//		Date data = sdf.parse(ymdate);
//		Calendar cd = Calendar.getInstance(); 
//		cd.setTime(data);
//		String beginTime="";//开始日期
//		String endTime="";//结束日期
//		if("1".equals(cycle)){//上半月   月初到十五号
//			beginTime=sdf.format(cd.getTime());//b
//			cd.add(Calendar.DATE, 15);
//			cd.add(Calendar.MILLISECOND, -1);//减少一秒钟
//			endTime=sdf.format(cd.getTime());//e
//		}else if("2".equals(cycle)){//下半月  16号到月末
//			cd.add(Calendar.DATE, 15);
//			beginTime=sdf.format(cd.getTime());//b
//			Calendar cd2 = Calendar.getInstance();
//			cd2.setTime(data);
//			cd2.add(Calendar.MONTH, 1);//加一个月
//			cd2.add(Calendar.MILLISECOND, -1);//减少一秒钟
//			endTime=sdf.format(cd2.getTime());//e
//		}else{//一整月
//			beginTime=sdf.format(cd.getTime());//b
//			cd.add(Calendar.MONTH, 1);//加一个月
//			cd.add(Calendar.MILLISECOND, -1);//减少一秒钟
//			endTime=sdf.format(cd.getTime());//e
//		}
//		String sql1 = " select sum(o.totalAmount) as totalAmount from ShopInfo s,Orders o,OrdersOPLog l where s.customerId = "+c.getCustomerId()+" and s.shopInfoId = o.shopInfoId and o.ordersId = l.ordersId and l.ordersOperateState = 2 and UNIX_TIMESTAMP(l.operatorTime)>=UNIX_TIMESTAMP('"+beginTime+"') and UNIX_TIMESTAMP(l.operatorTime)<=UNIX_TIMESTAMP('"+endTime+"') ";
//		List<Map> mapList = shopSettlementDetailService.findListMapByHql(sql1);
//		String sql2 = " select sum(ol.costPrice) as costPrice from OrdersList ol,ShopInfo s,Orders o,OrdersOPLog l where o.ordersId = ol.ordersId and s.customerId = "+c.getCustomerId()+" and s.shopInfoId = o.shopInfoId and o.ordersId = l.ordersId and l.ordersOperateState = 2 and UNIX_TIMESTAMP(l.operatorTime)>=UNIX_TIMESTAMP('"+beginTime+"') and UNIX_TIMESTAMP(l.operatorTime)<=UNIX_TIMESTAMP('"+endTime+"') group by s.shopInfoId ";
//		List<Map> mapList2 = shopSettlementDetailService.findListMapByHql(sql2);
//		String hql3 = " select s.shopInfoId as shopInfoId,s.shopName as shopName from ShopInfo s where s.customerId = "+c.getCustomerId();
//		List<Map> mapList3 = shopSettlementDetailService.findListMapByHql(hql3);
//		JSONObject jo = new JSONObject();
//		shopSettlmentDetail = new ShopSettlementDetail();
//		if(mapList.size()!=0&&mapList.get(0).get("totalAmount")!=null){
//			shopSettlmentDetail.setTotalSales((BigDecimal) mapList.get(0).get("totalAmount"));
//			if(mapList2.size()!=0&&mapList2.get(0).get("costPrice")!=null){
//				shopSettlmentDetail.setTotalCost((BigDecimal) mapList2.get(0).get("costPrice"));
//			}else{
//				shopSettlmentDetail.setTotalCost(new BigDecimal(0));
//			}
//			shopSettlmentDetail.setShopInfoId(Integer.valueOf(mapList3.get(0).get("shopInfoId").toString()));
//			shopSettlmentDetail.setShopName(mapList3.get(0).get("shopName").toString());
//			shopSettlmentDetail.setCreateDate(new Date());
//			shopSettlmentDetail.setCycle(cycle);
//			shopSettlmentDetail.setCustomerId(c.getCustomerId());
//			shopSettlmentDetail.setLoginName(c.getLoginName());
//			shopSettlmentDetail.setSettlementMonth(ym);
//			shopSettlmentDetail.setReviewStatus(0);
//			shopSettlmentDetail = (ShopSettlementDetail) shopSettlementDetailService.saveOrUpdateObject(shopSettlmentDetail);
//			if(shopSettlmentDetail!=null){
//				jo.accumulate("isSuccess", "true");
//			}else{
//				jo.accumulate("isSuccess", "false1");
//			}
//		}else{
//			jo.accumulate("isSuccess", "false2");
//		}
//		response.setContentType("text/html;charset=utf-8");
//		PrintWriter out = response.getWriter();
//		out.println(jo.toString());
//		out.flush();
//		out.close();
//	}
//
//	/**
//	 * 校验当前日期和周期是否结算过
//	 * 
//	 * @throws Exception
//	 */
//	public void check() throws Exception {
//		String ym = request.getParameter("ym");
//		String cycle = request.getParameter("cycle");
//		JSONObject jo = new JSONObject();
//		shopSettlementDetailList = shopSettlementDetailService.findObjects(" where o.settlementMonth ='"+ym.trim()+"' ");
//		if(shopSettlementDetailList.size()==0){
//			jo.accumulate("isSuccess", "true");
//		}else if(shopSettlementDetailList.size()==1){
//			if("0".equals(shopSettlementDetailList.get(0).getCycle())){
//				jo.accumulate("isSuccess", "false1");
//			}else if("0".equals(cycle.trim())){
//				jo.accumulate("isSuccess", "false3");
//			}else if(cycle.trim().equals(shopSettlementDetailList.get(0).getCycle())){
//				jo.accumulate("isSuccess", "false2");
//			}else{
//				jo.accumulate("isSuccess", "true");
//			}
//		}else if(shopSettlementDetailList.size()==2){
//			jo.accumulate("isSuccess", "false1");
//		}
//		response.setContentType("text/html;charset=utf-8");
//		PrintWriter out = response.getWriter();
//		out.println(jo.toString());
//		out.flush();
//		out.close();
//	}
//
//	/********************************** set get方法 *****************************/
//	public void setShopSettlementDetailService(
//			IShopSettlementDetailService shopSettlementDetailService) {
//		this.shopSettlementDetailService = shopSettlementDetailService;
//	}
//
//	public List<ShopSettlementDetail> getShopSettlementDetailList() {
//		return shopSettlementDetailList;
//	}
//
//	public void setShopSettlementDetailList(
//			List<ShopSettlementDetail> shopSettlementDetailList) {
//		this.shopSettlementDetailList = shopSettlementDetailList;
//	}
//
//	public ShopSettlementDetail getShopSettlmentDetail() {
//		return shopSettlmentDetail;
//	}
//
//	public void setShopSettlmentDetail(ShopSettlementDetail shopSettlmentDetail) {
//		this.shopSettlmentDetail = shopSettlmentDetail;
//	}
//
//}
