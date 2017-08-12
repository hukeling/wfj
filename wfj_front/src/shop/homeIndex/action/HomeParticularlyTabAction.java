package shop.homeIndex.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.homeIndex.pojo.HomeParticularlyTab;
import shop.homeIndex.service.IHomeParticularlyTabService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import util.other.JSONFormatDate;
import util.upload.ImageFileUploadUtil;
import basic.pojo.Users;
/**
 * 首页特别商品Action
 * @author whb
 * @time  20140115
 */
@SuppressWarnings("serial")
public class HomeParticularlyTabAction extends BaseAction{
	private IHomeParticularlyTabService homeParticularlyTabService;//首页特别商品service
	private List<HomeParticularlyTab> homeParticularlyTabList = new ArrayList<HomeParticularlyTab>();
	List<Map<String, Object>> homeParticularlyTabList2 = new ArrayList<Map<String, Object>>();
	private HomeParticularlyTab homeParticularlyTab;
	private String tabProductId;
	private String ids;
	private File imagePath;// 上传文件路径 
	private String imagePathFileName;// 上传文件名称
	//跳转到首页特别商品列表页面
	public String gotoHomeParticularlyTabPage(){
		return SUCCESS;
	}
	//查询所有特别商品信息列表
	@SuppressWarnings("unchecked")
	public void listHomeParticularlyTab() throws IOException{
		//获取查询参数
		String title = request.getParameter("title");
		String isShow = request.getParameter("isShow");
		String showLocation = request.getParameter("showLocation");
		String where=" where 1=1";
		if(title!=null&&!"".equals(title.trim())){
			title = title.trim();
			where+=" and o.title like '%"+title+"%'";
		}
		if(isShow!=null&&!"".equals(isShow)){
			where+=" and o.isShow = "+isShow;
		}
		if(showLocation!=null&&!"".equals(showLocation)){
			where+=" and o.showLocation = "+showLocation;
		}
		StringBuffer hqlsb = new StringBuffer();
		hqlsb.append(where);
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" tabProductId desc"));
		int totalRecordCount = homeParticularlyTabService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		homeParticularlyTabList = homeParticularlyTabService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", homeParticularlyTabList);
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
	public void saveOrUpdateHomeParticularlyTab() throws IOException{
		if(homeParticularlyTab!=null){
			Users user = (Users) session.getAttribute("users");
			if(homeParticularlyTab.getTabProductId()!=null){
				homeParticularlyTab.setModifyUser(user.getUserName());
				homeParticularlyTab.setUpdateTime(new Date());
			}else{
				homeParticularlyTab.setPublishUser(user.getUserName());
				homeParticularlyTab.setCreateTime(new Date());
			}
			homeParticularlyTab = (HomeParticularlyTab) homeParticularlyTabService.saveOrUpdateObject(homeParticularlyTab);
			if(homeParticularlyTab.getTabProductId()!=null){
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
	public void getHomeParticularlyTabObject() throws IOException{
		homeParticularlyTab = (HomeParticularlyTab) homeParticularlyTabService.getObjectByParams(" where o.tabProductId='"+tabProductId+"'");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(homeParticularlyTab,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteHomeParticularlyTab() throws IOException{
		Boolean isSuccess = homeParticularlyTabService.deleteObjectsByIds("tabProductId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	// 异步ajax 图片上传
	public void uploadImage() throws Exception {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 1图片上传
		if (imagePath != null) {
			String otherImg = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_homeParticularlyTab");
			jo.accumulate("photoUrl", otherImg);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
		} else {
			jo.accumulate("photoUrl", "false1");
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public List<HomeParticularlyTab> getHomeParticularlyTabList() {
		return homeParticularlyTabList;
	}
	public void setHomeParticularlyTabList(
			List<HomeParticularlyTab> homeParticularlyTabList) {
		this.homeParticularlyTabList = homeParticularlyTabList;
	}
	public List<Map<String, Object>> getHomeParticularlyTabList2() {
		return homeParticularlyTabList2;
	}
	public void setHomeParticularlyTabList2(
			List<Map<String, Object>> homeParticularlyTabList2) {
		this.homeParticularlyTabList2 = homeParticularlyTabList2;
	}
	public HomeParticularlyTab getHomeParticularlyTab() {
		return homeParticularlyTab;
	}
	public void setHomeParticularlyTab(HomeParticularlyTab homeParticularlyTab) {
		this.homeParticularlyTab = homeParticularlyTab;
	}
	public String getTabProductId() {
		return tabProductId;
	}
	public void setTabProductId(String tabProductId) {
		this.tabProductId = tabProductId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public File getImagePath() {
		return imagePath;
	}
	public void setImagePath(File imagePath) {
		this.imagePath = imagePath;
	}
	public String getImagePathFileName() {
		return imagePathFileName;
	}
	public void setImagePathFileName(String imagePathFileName) {
		this.imagePathFileName = imagePathFileName;
	}
	public void setHomeParticularlyTabService(
			IHomeParticularlyTabService homeParticularlyTabService) {
		this.homeParticularlyTabService = homeParticularlyTabService;
	}
}
