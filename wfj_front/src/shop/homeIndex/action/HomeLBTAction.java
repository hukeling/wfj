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

import org.springframework.stereotype.Controller;

import shop.homeIndex.pojo.HomeLBT;
import shop.homeIndex.service.IHomeLBTService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import util.other.JSONFormatDate;
import util.upload.ImageFileUploadUtil;
import basic.pojo.Users;
/**
 * 首页轮播图Action
 * @author whb
 * @time  20140115
 */
@SuppressWarnings("serial")
@Controller
public class HomeLBTAction extends BaseAction{
	private IHomeLBTService homeLBTService;//首页轮播图service
	private List<HomeLBT> homeLBTList = new ArrayList<HomeLBT>();
	List<Map> homeLBTList2 = new ArrayList<Map>();
	private HomeLBT homeLBT;
	private String broadcastingId;
	private String ids;
	private File imagePath;// 上传文件路径 
	private String imagePathFileName;// 上传文件名称
	//跳转到首页轮播图列表页面
	public String gotoHomeLBTPage(){
		return SUCCESS;
	}
	//测试首页轮播图跳转
	public String gotoIndexLBTPage(){
		String hql = "select h.broadcastingIamgeUrl as broadcastingIamgeUrl,h.interlinkage as interlinkage from HomeLBT h where h.isShow=1 order by sortCode asc";
		homeLBTList2 = homeLBTService.findListMapByHql(hql);
		return SUCCESS;
	}
	//查询所有轮播图信息列表
	@SuppressWarnings("unchecked")
	public void listHomeLBT() throws IOException{
		//String selectFlag=request.getParameter("selectFlag");
		//获取查询参数
		String title = request.getParameter("title");
		String isShow = request.getParameter("isShow");
		String where=" where 1=1";
		if(title!=null&&!"".equals(title.trim())){
			title = title.trim();
			where+=" and o.title like '%"+title+"%'";
		}
		if(isShow!=null&&!"".equals(isShow)){
			where+=" and o.isShow = "+isShow;
		}
		StringBuffer hqlsb = new StringBuffer();
		hqlsb.append(where);
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" broadcastingId desc"));
		int totalRecordCount = homeLBTService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		homeLBTList = homeLBTService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", homeLBTList);
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
	public void saveOrUpdateHomeLBT() throws IOException{
		if(homeLBT!=null){
			Users user = (Users) session.getAttribute("users");
			if(homeLBT.getBroadcastingId()!=null){
				homeLBT.setModifyUser(user.getUserName());
				homeLBT.setUpdateTime(new Date());
			}else{
				homeLBT.setPublishUser(user.getUserName());
				homeLBT.setCreateTime(new Date());
			}
			homeLBT = (HomeLBT) homeLBTService.saveOrUpdateObject(homeLBT);
			if(homeLBT.getBroadcastingId()!=null){
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
	public void getHomeLBTObject() throws IOException{
		homeLBT = (HomeLBT) homeLBTService.getObjectByParams(" where o.broadcastingId='"+broadcastingId+"'");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(homeLBT,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteHomeLBT() throws IOException{
		Boolean isSuccess = homeLBTService.deleteObjectsByIds("broadcastingId",ids);
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
			String otherImg = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_homeLBT");
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
		String hql = " select h.broadcastingIamgeUrl as broadcastingIamgeUrl, h.interlinkage as interlinkage from HomeLBT h where h.isShow = 1 "	;
	}*/
	public void setHomeLBTService(IHomeLBTService homeLBTService) {
		this.homeLBTService = homeLBTService;
	}
	public List<HomeLBT> getHomeLBTList() {
		return homeLBTList;
	}
	public void setHomeLBTList(List<HomeLBT> homeLBTList) {
		this.homeLBTList = homeLBTList;
	}
	public HomeLBT getHomeLBT() {
		return homeLBT;
	}
	public void setHomeLBT(HomeLBT homeLBT) {
		this.homeLBT = homeLBT;
	}
	public String getBroadcastingId() {
		return broadcastingId;
	}
	public void setBroadcastingId(String broadcastingId) {
		this.broadcastingId = broadcastingId;
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
	public List<Map> getHomeLBTList2() {
		return homeLBTList2;
	}
	public void setHomeLBTList2(List<Map> homeLBTList2) {
		this.homeLBTList2 = homeLBTList2;
	}
}
