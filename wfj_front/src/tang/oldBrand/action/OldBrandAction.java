package tang.oldBrand.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import basic.pojo.Users;

import tang.oldBrand.pojo.OldBrand;
import tang.oldBrand.service.IOldBrandService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
/**
 * 老字号action
 */
public class OldBrandAction extends BaseAction {
	private static final long serialVersionUID = 2497912722613332914L;
	private IOldBrandService oldBrandService;
	
	private List<OldBrand> oldBrandList = new ArrayList<OldBrand>();//品牌list
	private OldBrand oldBrand;
	private String oldBrandId;
	private String oldBrandIds;//
	private Users user;
	
	
	public String gotoOldBrandPage(){
		return "success";
	}
	/**
	 * 保存或者修改
	 * @throws Exception
	 */
	public void saveOrUpdateOldBrand() throws Exception{
		user=(Users)request.getSession().getAttribute("users");
		if(oldBrand!=null){
			JSONObject jo = new JSONObject(); 
			if(oldBrand.getOldBrandId()==null&&(this.searchOldBrandByName(oldBrand.getOldBrandName())!=null)){
				jo.accumulate("isSuccess", false);//添加的老字号存在，添加会失败
			}else{
				oldBrand.setUpdateTime(new Date());//修改时间
				if(user!=null&&!"".equals(user.getUserName())){
					if(oldBrand.getCreateTime()==null){
						oldBrand.setCreateTime(new Date());//创建时间
						oldBrand.setCreateUsr(user.getUserName());//创建人
					}
				oldBrand.setUpdateUsr(user.getUserName());//修改人
				}
				oldBrand = (OldBrand)oldBrandService.saveOrUpdateObject(oldBrand);
				if(oldBrand.getOldBrandId()!=null){
					jo.accumulate("isSuccess", "true");
				}
		  }
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	
	//查询所有信息列表
		public void listOldBrand() throws IOException{
			String selectFlag=request.getParameter("selectFlag");
			StringBuffer hqlsb = new StringBuffer();
			hqlsb.append(" where 1=1");
			if("true".equals(selectFlag)){//判断是否点击查询按钮
				String oldBrandName = request.getParameter("oldBrandName");
				String isShow = request.getParameter("isShow");
				String isRecommend = request.getParameter("isRecommend");
				String isHomePage = request.getParameter("isHomePage");
				if(StringUtils.isNotEmpty(oldBrandName)){
					oldBrandName = oldBrandName.trim();
					hqlsb.append(" and o.oldBrandName like '%"+oldBrandName+"%'");
				}
				if(!"-1".equals(isHomePage)){
					hqlsb.append(" and o.isHomePage = "+isHomePage);
				}
				if(!"-1".equals(isShow)){
					hqlsb.append(" and o.isShow = "+isShow);
				}
				if(!"-1".equals(isRecommend)){
					hqlsb.append(" and o.isRecommend ="+isRecommend);
				}
			}
			hqlsb.append(" order by o.oldBrandId desc");
			int totalRecordCount = oldBrandService.getCount(hqlsb.toString());
			pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
			oldBrandList = oldBrandService.findListByPageHelper(null,pageHelper, hqlsb.toString());
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("total", totalRecordCount);
			jsonMap.put("rows", oldBrandList);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
			JSONObject jo = JSONObject.fromObject(jsonMap, jsonConfig);//格式化result
//			JSONObject jo = JSONObject.fromObject(jsonMap);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
		

		/**
		 * 获取一条记录
		 * @throws IOException
		 */
		public void getOneOldBrand() throws IOException{
			oldBrand = (OldBrand) oldBrandService.getObjectByParams(" where o.oldBrandId='"+oldBrandId+"'");
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
//			JSONObject jo = JSONObject.fromObject(oldBrand);
			JSONObject jo = JSONObject.fromObject(oldBrand,jsonConfig);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
		/**
		 * 删除记录
		 * @throws IOException
		 */
		public void deleteOldBrand() throws IOException{
			JSONObject jo = new JSONObject();
			Boolean isSuccess =oldBrandService.deleteObjectsByIds("oldBrandId", oldBrandIds);
	 		jo.accumulate("isSuccess", isSuccess + "");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
		
		/**
		 * 根据品牌名查询品牌
		 * 避免重复品牌出现
		 */
		public OldBrand searchOldBrandByName(String oldBrandName){
			OldBrand obd=(OldBrand)oldBrandService.getObjectByParams("where o.oldBrandName='"+oldBrandName+"'");
			if(obd==null){
				return null;
			}else{
			return obd;
			}
		}
		
		/**
		 * 根据oldBrandId查询店铺信息
		 * @return
		 */
		
	public OldBrand getOldBrand() {
		return oldBrand;
	}

	public void setOldBrand(OldBrand oldBrand) {
		this.oldBrand = oldBrand;
	}

	public void setOldBrandService(IOldBrandService oldBrandService) {
		this.oldBrandService = oldBrandService;
	}
	public String getOldBrandId() {
		return oldBrandId;
	}
	public void setOldBrandId(String oldBrandId) {
		this.oldBrandId = oldBrandId;
	}
	public String getOldBrandIds() {
		return oldBrandIds;
	}
	public void setOldBrandIds(String oldBrandIds) {
		this.oldBrandIds = oldBrandIds;
	}
	
	

}
