package tang.tangstore.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;

import shop.store.pojo.ShopCategory;
import shop.store.service.IShopCategoryService;
import tang.tangstore.pojo.TangService;
import tang.tangstore.service.ITangServiceService;
import util.action.BaseAction;

public class TangServiceAction extends BaseAction {
	private static final long serialVersionUID = 4931015132417882953L;
	private ITangServiceService tangServiceService;
	private IShopCategoryService shopCategoryService; 
	private List<TangService> tangServiceList=new ArrayList<TangService>();
	private TangService tangService;
	private String serviceId;
	private String serviceIds;
	
	/**
	 * 跳转到唐智府服务信息管理页面
	 * @return
	 */
	public String gotoTangServicePage(){
		getShopCategory();
		return SUCCESS;
	}
	
	/**
	 * 获取店铺分类List(老子号和玩转部分店铺分类)
	 * @param tangServiceService
	 */
	public void  getShopCategory(){
		Map<Integer,String> shopCategoryMap=new HashMap<Integer,String>();
		List<ShopCategory> scList=new ArrayList<ShopCategory>();
		String sql="where o.parentId =3 or o.parentId=58";
		scList=shopCategoryService.findObjects(sql);
		for(ShopCategory sc:scList){
			shopCategoryMap.put(sc.getShopCategoryId(), sc.getShopCategoryName());
		}
		request.setAttribute("shopCategoryMap", shopCategoryMap);
	}
	
	/**
	 *添加或修改服务
	 * @param tangServiceService
	 * @throws IOException 
	 */
	public void saveorUpdatetangService() throws IOException{
		if(tangService!=null){
		JSONObject jo = new JSONObject(); 
			tangService = (TangService)tangServiceService.saveOrUpdateObject(tangService);
			if(tangService.getServiceId()!=null){
					jo.accumulate("isSuccess", true);
				}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	 }
	}
	
	/**
	 * 获取服务信息列表页面
	 * @param tangServiceService
	 * @throws IOException 
	 */
	public void listTangService() throws IOException{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		hqlsb.append(" where 1=1");
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String serviceName = request.getParameter("serviceName");
			String serviceType = request.getParameter("serviceType");
			String shopCategoryId=request.getParameter("shopCategoryId");
			if(StringUtils.isNotEmpty(serviceName)){
				serviceName = serviceName.trim();
				hqlsb.append(" and o.serviceName like '%"+serviceName+"%'");
			}
			if(serviceType!=null&&!"".equals(serviceType)){
				serviceType = serviceType.trim();
				hqlsb.append(" and o.serviceType= "+Integer.valueOf(serviceType));
			}
			if(shopCategoryId!=null&&!"".equals(shopCategoryId)){
				hqlsb.append(" and o.shopCategoryId= "+Integer.valueOf(shopCategoryId));
			}
		}
		hqlsb.append(" order by o.serviceId desc");
		int totalRecordCount = tangServiceService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		tangServiceList = tangServiceService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", tangServiceList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 获取一条店铺信息
	 * @param tangServiceService
	 * @throws IOException 
	 */
	public void getOnetangService() throws IOException{
		tangService = (TangService) tangServiceService.getObjectByParams(" where o.serviceId="+Integer.valueOf(serviceId));
		JSONObject jo = JSONObject.fromObject(tangService);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	/** 
	 * 批量删除店铺信息
	 * @param tangServiceService
	 * @throws IOException 
	 */
	public void deletetangService() throws IOException{
		JSONObject jo = new JSONObject();
		Boolean isSuccess =tangServiceService.deleteObjectsByIds("serviceId", serviceIds);
 		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	
	


	public void setTangServiceService(ITangServiceService tangServiceService) {
		this.tangServiceService = tangServiceService;
	}

	public void setTangService(TangService tangService) {
		this.tangService = tangService;
	}

	public void setServiceIds(String serviceIds) {
		this.serviceIds = serviceIds;
	}

	public TangService getTangService() {
		return tangService;
	}

	public String getServiceIds() {
		return serviceIds;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public ITangServiceService getTangServiceService() {
		return tangServiceService;
	}

	public void setShopCategoryService(IShopCategoryService shopCategoryService) {
		this.shopCategoryService = shopCategoryService;
	}
	
	
	

}
