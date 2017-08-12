package shop.product.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.product.pojo.IntegralexChange;
import shop.product.pojo.ProductInfo;
import shop.product.service.IIntegralexChangeService;
import shop.product.service.IProductInfoService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
/**
 * 积分兑换Action
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class IntegralexChangeAction extends BaseAction {
	private IIntegralexChangeService integralexChangeService;//积分兑换Service
	private IProductInfoService productInfoService;//商品Service
	private List<IntegralexChange> integralexChangeList = new ArrayList<IntegralexChange>();//积分兑换List
	private List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
	private IntegralexChange integralexChange;
	private String integralId;
	private String ids;
	//跳转到积分兑换列表页面
	public String gotoIntegralexChangePage(){
		productInfoList = productInfoService.findObjects(null);
		return SUCCESS;
	}
	//查询所有信息列表
	public void listIntegralexChange() throws IOException{
		int totalRecordCount = integralexChangeService.getCount(" order by o.integralId");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		integralexChangeList = integralexChangeService.findListByPageHelper(null,pageHelper, " order by o.integralId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", integralexChangeList);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateIntegralexChange() throws IOException{
		if(integralexChange!=null){
			integralexChange = (IntegralexChange) integralexChangeService.saveOrUpdateObject(integralexChange);
			if(integralexChange.getIntegralId()!=null){
				JSONObject jo = new JSONObject();
				jo.accumulate("isSuccess", "true");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println(jo.toString());
				out.flush();
				out.close();
			}
		}
	}
	//获取一条记录
	public void getIntegralexChangeInfo() throws IOException{
		integralexChange = (IntegralexChange) integralexChangeService.getObjectByParams(" where o.integralId='"+integralId+"'");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(integralexChange,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteIntegralexChange() throws IOException{
		Boolean isSuccess = integralexChangeService.deleteObjectsByIds("integralId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public List<IntegralexChange> getIntegralexChangeList() {
		return integralexChangeList;
	}
	public void setIntegralexChangeList(List<IntegralexChange> integralexChangeList) {
		this.integralexChangeList = integralexChangeList;
	}
	public List<ProductInfo> getProductInfoList() {
		return productInfoList;
	}
	public void setProductInfoList(List<ProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
	}
	public IntegralexChange getIntegralexChange() {
		return integralexChange;
	}
	public void setIntegralexChange(IntegralexChange integralexChange) {
		this.integralexChange = integralexChange;
	}
	public String getIntegralId() {
		return integralId;
	}
	public void setIntegralId(String integralId) {
		this.integralId = integralId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setIntegralexChangeService(
			IIntegralexChangeService integralexChangeService) {
		this.integralexChangeService = integralexChangeService;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
}
