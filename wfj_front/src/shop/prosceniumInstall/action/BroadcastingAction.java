package shop.prosceniumInstall.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.prosceniumInstall.pojo.Broadcasting;
import shop.prosceniumInstall.service.IBroadcastingService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import util.upload.ImageFileUploadUtil;
/**
 * BroadcastingAction - 前台图片轮播Action类
 */
@SuppressWarnings("serial")
public class BroadcastingAction extends BaseAction{
	private IBroadcastingService broadcastingService;//前台图片轮播Service
	private List<Broadcasting> broadcastingList = new ArrayList<Broadcasting>();//前台图片轮播List
	private Broadcasting broadcasting;//前台图片轮播
	private String broadcastingId;
	private String ids;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	// 异步ajax 图片上传
	public void uploadImage() throws Exception {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 1图片上传
		if (imagePath != null) {
			String otherImg = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_broadcasting");
			jo.accumulate("photoUrl", otherImg);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
		} else {
			jo.accumulate("photoUrl", "false1");
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//跳转到前台图片轮播列表页面
	public String gotoBroadcastingPage(){
		return SUCCESS;
	}
	//查询所有信息列表
	public void listBroadcasting() throws IOException{
		@SuppressWarnings("unused")
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
//		if("true".equals(selectFlag)){//判断是否点击查询按钮
//			String brandName = request.getParameter("brandName");
//			if(brandName!=null&&!"".equals(brandName)){
//				StringBuffer sb=CreateWhereSQLForSelect.appendLike("brandName","like",request.getParameter("brandName"));
//				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
//			}
//		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" broadcastingId desc"));
		int totalRecordCount = broadcastingService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		broadcastingList = broadcastingService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", broadcastingList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateBroadcasting() throws IOException{
		if(broadcasting!=null){
			broadcasting = (Broadcasting) broadcastingService.saveOrUpdateObject(broadcasting);
			if(broadcasting.getBroadcastingId()!=null){
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
	public void getBroadcastingInfo() throws IOException{
		broadcasting = (Broadcasting) broadcastingService.getObjectByParams(" where o.broadcastingId='"+broadcastingId+"'");
		JSONObject jo = JSONObject.fromObject(broadcasting);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteBroadcasting() throws IOException{
		Boolean isSuccess = broadcastingService.deleteObjectsByIds("broadcastingId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public List<Broadcasting> getBroadcastingList() {
		return broadcastingList;
	}
	public void setBroadcastingList(List<Broadcasting> broadcastingList) {
		this.broadcastingList = broadcastingList;
	}
	public Broadcasting getBroadcasting() {
		return broadcasting;
	}
	public void setBroadcasting(Broadcasting broadcasting) {
		this.broadcasting = broadcasting;
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
	public void setBroadcastingService(IBroadcastingService broadcastingService) {
		this.broadcastingService = broadcastingService;
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
}
