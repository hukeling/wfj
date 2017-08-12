package shop.customer.action;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.customer.pojo.EvaluateGoods;
import shop.customer.service.IEvaluateGoodsService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
/**
 * 后台查看用户评价Action
 * 
 */
@SuppressWarnings("all")
public class CustomerEvaluateGoodsAction extends BaseAction {
	/**评价Service**/
	private IEvaluateGoodsService evaluateGoodsService; 
	/**评价对象**/
	private EvaluateGoods evaluateGoods; 
	/**评价集合**/
	private List<EvaluateGoods> evalGoodsList;
	/**评价Id**/
	private Integer evaluateId;
	/**Map类型的List*/
	@SuppressWarnings("unchecked")
	private List<Map> mapList;
	/**************************************************************************************/
	/**
	 * 跳转到会员评价列表
	 * @return
	 */
	public String gotoEvaluateGoods(){
		return SUCCESS;
	}
	/**
	 * 后台获取订单评价列表  带条件查询
	 * @author 刘钦楠  2013.12.25
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void listEvalGoods() throws Exception{
		String hql1 = " select count(e.evaluateId) from EvaluateGoods e,ProductInfo p where e.productId = p.productId ";
		String hql2 = " select e.evaluateId as evaluateId,e.ordersNo as ordersNo,p.logoImg as logoImg,e.level as level,e.content as content,e.appraiserName as appraiserName,e.shopName as shopName,e.createTime as createTime,e.evaluateState as evaluateState from EvaluateGoods e,ProductInfo p where e.productId = p.productId ";
		String condition = "";//条件查询
		String orderNo = request.getParameter("orderNo");//订单号
		if(orderNo!=null&&!"".equals(orderNo.toString())){
			condition += " and e.ordersNo like '%" + orderNo.trim() +"%'";;
		}
		String appraiserName = request.getParameter("appraiserName");//评价人
		if(appraiserName!=null&&!"".equals(appraiserName.trim())){
			condition += " and e.appraiserName like '%" + appraiserName.trim() +"%'";
		}
		String shopName = request.getParameter("shopName");//店铺名称
		if(shopName!=null&&!"".equals(shopName.trim())){
			condition += " and e.shopName like '%" + shopName.trim() +"%'";
		}
		String level = request.getParameter("level");//评价等级
		if(level!=null&&!"".equals(level)){
			condition += " and e.level =" + Integer.valueOf(level);
		}
		String evaluateState = request.getParameter("evaluateState");//评价状态
		if(evaluateState!=null&&!"".equals(evaluateState.trim())){
			condition += " and e.evaluateState =" + Integer.valueOf(evaluateState);
		}
		String startDate = request.getParameter("startDate");//查询起始日期
		String endDate = request.getParameter("endDate");//查询终止日期
		if(startDate!=null&&!"".equals(startDate)&&endDate!=null&&!"".equals(endDate)){
			condition += " and e.createTime between '"+startDate+" 00:00:00' and '"+endDate+" 23:59:59'";
		}
		int totalRecordCount = evaluateGoodsService.getCountByHQL(hql1+condition);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		mapList = evaluateGoodsService.findListMapPage(hql2+condition+" order by e.createTime desc ", pageHelper);
		for(Map map:mapList){
			map.put("createTime", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("createTime"))).toString());//将date类型转换成String类型
			map.put("logoImg", (String) fileUrlConfig.get("visitFileUploadRoot")+map.get("logoImg"));//将商品图片的相对路径变为绝对路径
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", mapList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 获取用户评价详细信息
	 * @throws Exception
	 */
	public void getEvaluateGoods() throws Exception {
		evaluateGoods = (EvaluateGoods) evaluateGoodsService.getObjectByParams(" where o.evaluateId="+evaluateId);
		mapList = evaluateGoodsService.findListMapByHql(" select p.logoImg as logoImg from ProductInfo p where p.productId = "+evaluateGoods.getProductId());
		String logoImg = (String) mapList.get(0).get("logoImg");//获取商品图片的相对路径
		if(logoImg!=null&&!"".equals(logoImg)){
			evaluateGoods.setRemark((String) fileUrlConfig.get("visitFileUploadRoot")+logoImg);//暂时将商品图片的绝对路径放入到remark字段中传入前台
		}else{
			evaluateGoods.setRemark("");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(evaluateGoods,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 修改评价内容和显式状态
	 * @author 刘钦楠
	 * @throws Exception
	 */
	public void updateContentAndEvaluateState() throws Exception{
		String id = request.getParameter("id");//评价ID	
		String content = request.getParameter("content");//评价内容
		String evaluateState = request.getParameter("evaluateState");//评价状态
		evaluateGoods = evaluateGoodsService.updateContentAndEvaluateState(id, content, evaluateState);
		JSONObject jo = new JSONObject();
		if(evaluateGoods!=null){
			jo.accumulate("isSuccess", "true");
		}else{
			jo.accumulate("isSuccess", "false");
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/*****************************************end*****************************************/
	public List<EvaluateGoods> getEvalGoodsList() {
		return evalGoodsList;
	}
	public void setEvalGoodsList(List<EvaluateGoods> evalGoodsList) {
		this.evalGoodsList = evalGoodsList;
	}
	public void setEvaluateGoodsService(IEvaluateGoodsService evaluateGoodsService) {
		this.evaluateGoodsService = evaluateGoodsService;
	}
	public void setEvaluateGoods(EvaluateGoods evaluateGoods) {
		this.evaluateGoods = evaluateGoods;
	}
	public Integer getEvaluateId() {
		return evaluateId;
	}
	public void setEvaluateId(Integer evaluateId) {
		this.evaluateId = evaluateId;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getMapList() {
		return mapList;
	}
	@SuppressWarnings("unchecked")
	public void setMapList(List<Map> mapList) {
		this.mapList = mapList;
	}
}
