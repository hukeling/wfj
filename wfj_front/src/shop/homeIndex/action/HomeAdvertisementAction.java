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
import shop.homeIndex.pojo.HomeAdvertisement;
import shop.homeIndex.service.IHomeAdvertisementService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import util.other.JSONFormatDate;
import util.upload.ImageFileUploadUtil;
import basic.pojo.Users;
/**
 * 首页广告位Action
 * @author whb
 * @time  20140115
 */
@SuppressWarnings("serial")
public class HomeAdvertisementAction extends BaseAction{
	private IHomeAdvertisementService homeAdvertisementService;//首页广告位service
	private List<HomeAdvertisement> homeAdvertisementList = new ArrayList<HomeAdvertisement>();
	List<Map<String, Object>> homeAdvertisementList2 = new ArrayList<Map<String, Object>>();
	private HomeAdvertisement homeAdvertisement;
	private String advertisementId;
	private String ids;
	private File imagePath;// 上传文件路径 
	private String imagePathFileName;// 上传文件名称
	//跳转到首页广告位列表页面
	public String gotoHomeAdvertisementPage(){
		return SUCCESS;
	}
	//查询所有广告位信息列表
	@SuppressWarnings("unchecked")
	public void listHomeAdvertisement() throws IOException{
		//String selectFlag=request.getParameter("selectFlag");
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
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" advertisementId desc"));
		int totalRecordCount = homeAdvertisementService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		homeAdvertisementList = homeAdvertisementService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", homeAdvertisementList);
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
	public void saveOrUpdateHomeAdvertisement() throws IOException{
		if(homeAdvertisement!=null){
			Users user = (Users) session.getAttribute("users");
			if(homeAdvertisement.getAdvertisementId()!=null){
				homeAdvertisement.setModifyUser(user.getUserName());
				homeAdvertisement.setUpdateTime(new Date());
			}else{
				homeAdvertisement.setPublishUser(user.getUserName());
				homeAdvertisement.setCreateTime(new Date());
			}
			homeAdvertisement = (HomeAdvertisement) homeAdvertisementService.saveOrUpdateObject(homeAdvertisement);
			if(homeAdvertisement.getAdvertisementId()!=null){
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
	public void getHomeAdvertisementObject() throws IOException{
		homeAdvertisement = (HomeAdvertisement) homeAdvertisementService.getObjectByParams(" where o.advertisementId='"+advertisementId+"'");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(homeAdvertisement,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteHomeAdvertisement() throws IOException{
		Boolean isSuccess = homeAdvertisementService.deleteObjectsByIds("advertisementId",ids);
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
			String otherImg = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_homeAdvertisement");
			jo.accumulate("photoUrl", otherImg);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
		} else {
			jo.accumulate("photoUrl", "false1");
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/*public void showImage(){
		String hql = " select h.iamgeUrl as iamgeUrl, h.link as link from HomeAdvertisement h where h.isShow = 1 "	;
	}*/
	public List<HomeAdvertisement> getHomeAdvertisementList() {
		return homeAdvertisementList;
	}
	public void setHomeAdvertisementList(
			List<HomeAdvertisement> homeAdvertisementList) {
		this.homeAdvertisementList = homeAdvertisementList;
	}
	public List<Map<String, Object>> getHomeAdvertisementList2() {
		return homeAdvertisementList2;
	}
	public void setHomeAdvertisementList2(
			List<Map<String, Object>> homeAdvertisementList2) {
		this.homeAdvertisementList2 = homeAdvertisementList2;
	}
	public HomeAdvertisement getHomeAdvertisement() {
		return homeAdvertisement;
	}
	public void setHomeAdvertisement(HomeAdvertisement homeAdvertisement) {
		this.homeAdvertisement = homeAdvertisement;
	}
	public String getAdvertisementId() {
		return advertisementId;
	}
	public void setAdvertisementId(String advertisementId) {
		this.advertisementId = advertisementId;
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
	public void setHomeAdvertisementService(
			IHomeAdvertisementService homeAdvertisementService) {
		this.homeAdvertisementService = homeAdvertisementService;
	}
}
